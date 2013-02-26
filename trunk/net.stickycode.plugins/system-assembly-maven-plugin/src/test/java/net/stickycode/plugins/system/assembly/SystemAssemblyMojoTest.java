package net.stickycode.plugins.system.assembly;

import static org.fest.assertions.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collections;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.codehaus.plexus.archiver.ArchiverException;
import org.junit.Test;

public class SystemAssemblyMojoTest {

  @Test
  public void nothing()
      throws MojoExecutionException, MojoFailureException, ArchiverException, IOException {
    final ByteArrayOutputStream writer = new ByteArrayOutputStream();
    new SystemAssemblyMojo() {

      protected java.util.Collection<java.io.File> collectArtifacts() {
        return Collections.emptySet();
      };

      OutputStream createPackagesStream()
          throws IOException {
        return writer;
      };

    }.copyDebians(new File("target", "blah"));

    assertThat(writer.toString()).isEmpty();
  }

  @Test
  public void single()
      throws MojoExecutionException, MojoFailureException, ArchiverException, IOException {
    final ByteArrayOutputStream writer = new ByteArrayOutputStream();
    new SystemAssemblyMojo() {

      protected java.util.Collection<java.io.File> collectArtifacts() {
        return Arrays.asList(new File("src/deb/packages/sticky-debian-one_1.1_all.deb"));
      };

      OutputStream createPackagesStream()
          throws IOException {
        return writer;
      };

    }.copyDebians(new File("target", "blah"));

    assertThat(writer.toString())
        .isEqualTo(
            "Package: sticky-debian-one\n" +
                "Version: 1.1\nSection: examples\n" +
                "Priority: optional\nArchitecture: all\n" +
                "Installed-Size: 0\n" +
                "Maintainer: michael@redengine.co.nz\n" +
                "Description: A very simple debian package\n" +
                "MD5Sum: 334b909830ef8409f59caa0c1a82217d\n" +
                "SHA1: 1ecb078bc350d06d7a3294146d6408ac3c4f3740\n" +
                "SHA256: fa48f09f7f3a222c682de1c6406aa913bb074b795449cced4db6b085ca00a02d\n" +
                "\n"
        );
  }

  @Test
  public void two()
      throws MojoExecutionException, MojoFailureException, ArchiverException, IOException {
    final ByteArrayOutputStream writer = new ByteArrayOutputStream();
    new SystemAssemblyMojo() {

      protected java.util.Collection<java.io.File> collectArtifacts() {
        return Arrays.asList(
            new File("src/deb/packages/sticky-debian-one_1.1_all.deb"),
            new File("src/deb/packages/sticky-debian-two_1.2_all.deb")
            );
      };

      OutputStream createPackagesStream()
          throws IOException {
        return writer;
      };

    }.copyDebians(new File("target", "blah"));

    assertThat(writer.toString())
        .isEqualTo(
            "Package: sticky-debian-one\n" +
                "Version: 1.1\n" +
                "Section: examples\n" +
                "Priority: optional\n" +
                "Architecture: all\n" +
                "Installed-Size: 0\n" +
                "Maintainer: michael@redengine.co.nz\n" +
                "Description: A very simple debian package\n" +
                "MD5Sum: 334b909830ef8409f59caa0c1a82217d\n" +
                "SHA1: 1ecb078bc350d06d7a3294146d6408ac3c4f3740\n" +
                "SHA256: fa48f09f7f3a222c682de1c6406aa913bb074b795449cced4db6b085ca00a02d\n" +
                "\n" +
                "Package: sticky-debian-two\nVersion: 1.2\n" +
                "Section: examples\n" +
                "Priority: optional\n" +
                "Architecture: all\n" +
                "Installed-Size: 0\n" +
                "Maintainer: michael@redengine.co.nz\n" +
                "Description: A very simple debian package\n" +
                "MD5Sum: 1c03ceb6aca1b5dc4a56930526dc7f0\n" +
                "SHA1: f7923fb2c55f2f7ae12aecca5f5ed6e70e7f259\n" +
                "SHA256: 3a25204196b3c52316602a642450d994ab8bad992e59da3e7af98c736f2ed6dd\n" +
                "\n"
        );
  }

}
