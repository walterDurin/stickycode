package net.stickycode.plugin.bounds;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Node;
import nu.xom.Nodes;
import nu.xom.ParentNode;
import nu.xom.ParsingException;
import nu.xom.Serializer;
import nu.xom.ValidityException;
import nu.xom.XPathContext;

import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.project.MavenProject;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.repository.RemoteRepository;
import org.sonatype.aether.resolution.VersionRangeRequest;
import org.sonatype.aether.resolution.VersionRangeResolutionException;
import org.sonatype.aether.resolution.VersionRangeResult;
import org.sonatype.aether.util.artifact.DefaultArtifact;
import org.sonatype.aether.version.Version;

/**
 * @description Update the lower bounds of a version range to match the current version. e.g. [1.1,2) might go to [1.3,2)
 * @goal update
 * @requiresDirectInvocation true
 * @threadSafe
 */
public class StickyBoundsMojo
    extends AbstractMojo {

  /**
   * The Maven Project.
   * 
   * @parameter expression="${project}"
   * @required
   * @readonly
   */
  private MavenProject project;

  /**
   * The entry point to Aether, i.e. the component doing all the work.
   * 
   * @component
   */
  private RepositorySystem repository;

  /**
   * The current repository/network configuration of Maven.
   * 
   * @parameter default-value="${repositorySystemSession}"
   * @readonly
   */
  private RepositorySystemSession session;

  private Pattern range = Pattern.compile("\\[[0-9.A-Za-z]+,([0-9]+\\))");

  /**
   * The project's remote repositories to use for the resolution.
   * 
   * @parameter default-value="${project.remoteProjectRepositories}"
   * @readonly
   */
  private List<RemoteRepository> repositories;

  public void execute() {
    Document pom = load();

    for (Dependency dependency : project.getDependencies()) {
      String version = dependency.getVersion();
      Matcher versionMatch = range.matcher(version);
      if (versionMatch.matches()) {
        getLog().info(dependency.toString());
        Artifact artifact = new DefaultArtifact(
            dependency.getGroupId(),
            dependency.getArtifactId(),
            dependency.getType(),
            dependency.getClassifier(),
            version);
        Version highestVersion = highestVersion(version, artifact);
        String newVersion = "[" + highestVersion.toString() + "," + versionMatch.group(1) + ")";
        update(pom, artifact, newVersion);
      }
    }
    
    Serializer serializer = createSerialiser();
    try {
      serializer.write(pom);
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private Serializer createSerialiser() {
    try {
      return new Serializer(new FileOutputStream(project.getFile()), "UTF-8");
    }
    catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private Version highestVersion(String version, Artifact artifact) {
    VersionRangeRequest request = new VersionRangeRequest(artifact, repositories, null);
    VersionRangeResult v = resolve(request);
    Version highestVersion = v.getHighestVersion();
    getLog().info("Resolving " + artifact.toString() + " with " + highestVersion.toString());
    return highestVersion;
  }

  void update(Document pom, Artifact artifact, String newVersion) {
    XPathContext context = new XPathContext("mvn", "http://maven.apache.org/POM/4.0.0");
    Nodes nodes = pom.query(dependencyPath(artifact.getArtifactId()), context);

    if (nodes.size() == 0)
      throw new RuntimeException("Got none but was expected one matching dependency " + artifact.getArtifactId());

    if (nodes.size() > 1)
      throw new RuntimeException("Expected one matching dependency " + nodes);

    ParentNode dependency = nodes.get(0).getParent();
    Node version = dependency.query("mvn:version", context).get(0);
    Element newRange = new Element("version", "http://maven.apache.org/POM/4.0.0");
    newRange.appendChild(newVersion);
    dependency.replaceChild(version, newRange);
  }

  private String dependencyPath(String artifactId) {
    return "//mvn:dependencies/mvn:dependency/mvn:artifactId[text()='" + artifactId + "']";
  }

  private Document load() {
    try {
      return new Builder().build(project.getFile());
    }
    catch (ValidityException e) {
      throw new RuntimeException(e);
    }
    catch (ParsingException e) {
      throw new RuntimeException(e);
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private VersionRangeResult resolve(VersionRangeRequest request) {
    try {
      return repository.resolveVersionRange(session, request);
    }
    catch (VersionRangeResolutionException e) {
      throw new RuntimeException(e);
    }
  }

}
