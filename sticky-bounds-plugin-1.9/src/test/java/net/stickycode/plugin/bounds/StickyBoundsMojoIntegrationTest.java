package net.stickycode.plugin.bounds;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Node;
import nu.xom.Nodes;
import nu.xom.ParsingException;
import nu.xom.Serializer;
import nu.xom.ValidityException;
import nu.xom.XPathContext;

import org.junit.Test;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.util.artifact.DefaultArtifact;

public class StickyBoundsMojoIntegrationTest {

  @Test
  public void update() throws ValidityException, ParsingException, IOException {
    Document pom = new Builder().build(new File(new File("src/it/reflector"), "pom.xml"));
    Artifact artifact = new DefaultArtifact(
        "net.stickycode",
        "sticky-coercion",
        "jar",
        "",
        "[3.1,4)");

    new StickyBoundsMojo().update(pom, artifact, "[3.6,4)");
    XPathContext context = new XPathContext("mvn", "http://maven.apache.org/POM/4.0.0");

    Nodes versions = pom.query("//mvn:version", context);
    assertThat(versions.size()).isEqualTo(3);
    Nodes nodes = pom.query("//mvn:version[text()='[3.6,4)']", context);
    assertThat(nodes.size()).isEqualTo(1);
    Node node = nodes.get(0);
    assertThat(node.getValue()).isEqualTo("[3.6,4)");
  }

  @Test
  public void writeNamespacesUnchanged() throws ValidityException, ParsingException, IOException {
    Document pom = new Builder().build(new File(new File("src/it/reflector"), "pom.xml"));
    Serializer s = new StickySerializer(new FileOutputStream(new File("target/tmp.xml")), "UTF-8");
    s.write(pom);
  }
}
