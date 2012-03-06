package net.stickycode.plugin.bounds;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.io.IOException;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Node;
import nu.xom.Nodes;
import nu.xom.ParsingException;
import nu.xom.ValidityException;
import nu.xom.XPathContext;

import org.junit.Test;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.util.artifact.DefaultArtifact;

public class StickyBoundsMojoIntegrationTest {

  @Test
  public void reflector() throws ValidityException, ParsingException, IOException {
    Document pom = new Builder().build(new File(new File("src/it/reflector"), "pom.xml"));
    Artifact artifact = new DefaultArtifact(
        "net.stickycode",
        "sticky-reflector",
        "jar",
        "",
        "[1.1,2)");

    new StickyBoundsMojo().update(pom, artifact, "[1.10,2)");
    XPathContext context = new XPathContext("mvn", "http://maven.apache.org/POM/4.0.0");

    Nodes versions = pom.query("//mvn:version", context);
    assertThat(versions.size()).isEqualTo(3);
    Nodes nodes = pom.query("//mvn:version[text()='[1.10,2)']", context);
    assertThat(nodes.size()).isEqualTo(1);
    Node node = nodes.get(0);
    assertThat(node.getValue()).isEqualTo("[1.10,2)");
    //
    // MavenCli maven = new MavenCli();
    // maven.setLocalRepositoryDirectory(new File("src/it/repo"));
    // maven.start();
    //
    // File itbasedir = new File(getBasedir(), "src/it/it1");
    // MavenProject pom =
    // maven.readProjectWithDependencies(new File(itbasedir, "pom.xml"));
    //
    // EventMonitor eventMonitor =
    // new DefaultEventMonitor(
    // new PlexusLoggerAdapter(
    // new MavenEmbedderConsoleLogger()));
    // maven.execute(pom,
    // Collections.singletonList(
    // "org.apache.maven.plugins:maven-XXX-plugin:1.0-SNAPSHOT:yourGoal"),
    // eventMonitor,
    // new ConsoleDownloadMonitor(),
    // null,
    // itbasedir);
    //
    // maven.stop();
  }
}
