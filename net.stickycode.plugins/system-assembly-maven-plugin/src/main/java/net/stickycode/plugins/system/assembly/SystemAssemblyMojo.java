package net.stickycode.plugins.system.assembly;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import org.apache.commons.compress.archivers.ar.ArArchiveEntry;
import org.apache.commons.compress.archivers.ar.ArArchiveInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.maven.archiver.MavenArchiveConfiguration;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;
import org.apache.maven.repository.RepositorySystem;
import org.codehaus.plexus.archiver.Archiver;
import org.codehaus.plexus.archiver.ArchiverException;
import org.codehaus.plexus.archiver.jar.JarArchiver;
import org.codehaus.plexus.archiver.manager.ArchiverManager;
import org.codehaus.plexus.archiver.tar.TarEntry;
import org.codehaus.plexus.archiver.tar.TarInputStream;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.repository.RemoteRepository;

@Mojo(name = "freeze",
    requiresDependencyResolution = ResolutionScope.RUNTIME,
    defaultPhase = LifecyclePhase.PACKAGE)
public class SystemAssemblyMojo
    extends AbstractMojo {

  @Component
  private MavenProject project;

  public MavenProject getProject() {
    return project;
  }

  @Component
  protected RepositorySystem repository;

  @Component
  private MavenProjectHelper projectHelper;

  /**
   * The session used for resolving dependencies.
   */
  @Parameter(defaultValue = "${repositorySystemSession}")
  protected RepositorySystemSession session;

  /**
   * The remote repositories to use when resolving dependencies.
   */
  @Parameter(defaultValue = "${project.remoteProjectRepositories}")
  protected List<RemoteRepository> repositories;

  @Component(role = Archiver.class, hint = "jar")
  private JarArchiver archiver;

  @Parameter(defaultValue = "${project.build.outputDirectory}", readonly = true)
  private File buildDirectory;

  @Parameter(defaultValue = "${project.build.finalName}", readonly = true)
  private String finalName;

  @Parameter(defaultValue = "")
  private String classifier;

  @Parameter(defaultValue = "sticky")
  private String debianRelease;

  @Parameter(defaultValue = "deb")
  private String type;

  /**
   * The archive configuration to use.
   * See <a href="http://maven.apache.org/shared/maven-archiver/index.html">Maven Archiver Reference</a>.
   */
  @Parameter
  private MavenArchiveConfiguration config = new MavenArchiveConfiguration();

  @Component
  protected ArchiverManager archiverManager;

  @Override
  public void execute()
      throws MojoExecutionException, MojoFailureException {
    try {
      File outputDirectory = getOutputDirectory();
      outputDirectory.mkdirs();
      copyDebians(outputDirectory);
    }
    catch (ArchiverException e) {
      throw new RuntimeException(e);
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  File getOutputDirectory() {
    return new File(buildDirectory, debianRelease);
  }

  void copyDebians(File destination)
      throws ArchiverException, IOException {
    OutputStream writer = createPackagesStream();
    try {
      copyDebians(writer, destination);
    }
    finally {
      writer.close();
    }
  }

  OutputStream createPackagesStream()
      throws IOException {
    return new BufferedOutputStream(new FileOutputStream(new File(getOutputDirectory(), "Packages")));
  }

  protected void copyDebians(OutputStream writer, File destination)
      throws ArchiverException, IOException {
    for (File file : collectArtifacts()) {
      FileUtils.copyFile(file, new File(destination, file.getName()));
      processDebian(file, writer);
      processChecksums(file, writer);
      writer.write("\n".getBytes());
    }
  }

  private void processChecksums(File file, OutputStream writer) {
    try {
      InputStream input = new BufferedInputStream(new FileInputStream(file));
      try {
        byte[] buffer = new byte[2048];
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        MessageDigest sha1 = MessageDigest.getInstance("SHA1");
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");

        int read = input.read(buffer);
        while (read != -1) {
          md5.update(buffer, 0, read);
          sha1.update(buffer, 0, read);
          sha256.update(buffer, 0, read);
          read = input.read(buffer);
        }

        writer.write("MD5Sum: ".getBytes());
        writer.write(hex(md5));
        writer.write("\n".getBytes());
        writer.write("SHA1: ".getBytes());
        writer.write(hex(sha1));
        writer.write("\n".getBytes());
        writer.write("SHA256: ".getBytes());
        writer.write(hex(sha256));
        writer.write("\n".getBytes());
      }
      finally {
        input.close();
      }
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
    catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  private byte[] hex(MessageDigest digest) {
    BigInteger bigInt = new BigInteger(1, digest.digest());
    String output = bigInt.toString(16);
    return output.getBytes();
  }

  private void processDebian(File file, OutputStream writer)
      throws FileNotFoundException, IOException {
    ArArchiveInputStream archive = new ArArchiveInputStream(new FileInputStream(file));
    try {
      for (ArArchiveEntry entry = archive.getNextArEntry(); entry != null; entry = archive.getNextArEntry()) {
        if (entry.getName().equals("control.tar.gz")) {
          TarInputStream tar = consume(archive, entry.getSize());
          processControlTar(writer, tar);
        }
      }
    }
    finally {
      archive.close();
    }
  }

  private void processControlTar(OutputStream writer, TarInputStream tar)
      throws IOException {
    for (TarEntry e = tar.getNextEntry(); e != null; e = tar.getNextEntry()) {
      if (e.getName().equals("./control")) {
        tar.copyEntryContents(writer);
      }
    }
  }

  private TarInputStream consume(ArArchiveInputStream archive, long size)
      throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    byte[] buffer = new byte[2048];
    int read = archive.read(buffer);
    while (read != -1) {
      bytes.write(buffer, 0, read);
      read = archive.read(buffer);
    }
    return new TarInputStream(new GZIPInputStream(new ByteArrayInputStream(bytes.toByteArray())));
  }

  protected Collection<File> collectArtifacts() {
    Set<Artifact> artifacts = getProject().getArtifacts();
    Collection<File> files = new ArrayList<File>();

    for (Artifact artifact : artifacts) {
      if (artifact.getType().equals(type)) {
        files.add(artifact.getFile());
        getLog().info("adding " + artifact);
      }
    }

    return files;
  }

}
