package net.stickycode.stile.artifact.xml.v1;

import static org.fest.assertions.Assertions.assertThat;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;

import net.stickycode.resource.ClasspathResource;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.xml.sax.SAXException;

public class ArtifactLoadTest {

  @Rule
  public TestName testName = new TestName();

  XmlArtifactParser parser = new XmlArtifactParser();

  @Test
  public void sample1() throws JAXBException, SAXException {
    XmlArtifact artifact = load("sample1.xml");
    assertThat(artifact.getId()).isEqualTo("stile");
    assertThat(artifact.getGroup()).isEqualTo("net.stickycode.stile");
  }

  @Test
  public void save() {
    XmlArtifact t = new XmlArtifact();
    t.setGroup("net.stickycode.stile");
    t.setId("stile");
    t.setVersion("1.1-SNAPSHOT");
    t.setPackaging(artifactReference("net.stickycode.packaging.jar", "1.9"));
    t.setProject(artifactReference("net.stickycode.project", "1.12"));
    t.setDependencies(dependencies());
    save(t);
  }

  @Test(expected = ParseFailureException.class)
  public void empty() throws JAXBException, SAXException {
    load("empty.xml");
  }

  @Test(expected = ParseFailureException.class)
  public void justArtifact() throws JAXBException, SAXException {
    load("artifact-unqualified.xml");
  }

  @Test(expected = ParseFailureException.class)
  public void noGroup() throws JAXBException, SAXException {
    load("artifact-no-group.xml");
  }

  @Test(expected = ParseFailureException.class)
  public void noId() throws JAXBException, SAXException {
    load("artifact-no-id.xml");
  }

  @Test(expected = ParseFailureException.class)
  public void noPackaging() throws JAXBException, SAXException {
    load("artifact-no-packaging.xml");
  }

  @Test(expected = ParseFailureException.class)
  public void noProject() throws JAXBException, SAXException {
    load("artifact-no-project.xml");
  }

  private DependenciesType dependencies() {
    return new DependenciesType();
  }

  private ArtifactReferenceType artifactReference(String id, String version) {
    ArtifactReferenceType packaging = new ArtifactReferenceType();
    packaging.setId(id);
    packaging.setVersion(version);
    return packaging;
  }

  private void save(XmlArtifact t) {
    try {
      JAXBContext context = JAXBContext.newInstance(XmlArtifact.class);
      Marshaller marshaller = context.createMarshaller();
      Schema schema = parser.getSchema();
      marshaller.setSchema(schema);
      marshaller.setProperty("jaxb.formatted.output", true);
      marshaller.marshal(t, System.out);
    }
    catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }

  private XmlArtifact load(String string) {
    assertThat(string).isNotNull();
    InputStream sampleSource = getClass().getResourceAsStream(string);
    assertThat(sampleSource).isNotNull();
    return parser.unmarshall(new ClasspathResource(getClass(), string));
  }

}
