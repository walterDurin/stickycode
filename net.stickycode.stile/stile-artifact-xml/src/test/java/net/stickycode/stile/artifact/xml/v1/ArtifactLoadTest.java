package net.stickycode.stile.artifact.xml.v1;

import static org.fest.assertions.Assertions.assertThat;

import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.junit.Test;
import org.xml.sax.SAXException;

public class ArtifactLoadTest {

  @Test
  public void load() {
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
      Schema schema = schema();
      marshaller.setSchema(schema);
      marshaller.setProperty("jaxb.formatted.output", true);
//      JAXBElement<XmlArtifact> e = new JAXBElement<XmlArtifact>(new QName("artifact"), XmlArtifact.class, t);
      marshaller.marshal(t, System.out);
    }
    catch (JAXBException e) {
      throw new RuntimeException(e);
    }
    catch (SAXException e) {
      throw new RuntimeException(e);
    }

//    return unmarshaller.marshal(new StreamSource(sampleSource), XmlArtifact.class).getValue();

  }

  private XmlArtifact load(String string) {
    assertThat(string).isNotNull();
    InputStream sampleSource = getClass().getResourceAsStream(string);
    assertThat(sampleSource).isNotNull();
    try {
      JAXBContext context = JAXBContext.newInstance(XmlArtifact.class);
      Unmarshaller unmarshaller = context.createUnmarshaller();
      Schema schema = schema();
      unmarshaller.setSchema(schema);
      return unmarshaller.unmarshal(new StreamSource(sampleSource), XmlArtifact.class).getValue();
    }
    catch (JAXBException e) {
      throw new RuntimeException(e);
    }
    catch (SAXException e) {
      throw new RuntimeException(e);
    }
  }

  private Schema schema() throws SAXException {
    SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    InputStream schemaSource = getClass().getResourceAsStream("Artifact.xsd");
    assertThat(schemaSource).isNotNull();
    Schema schema = factory.newSchema(new StreamSource(schemaSource));
    return schema;
  }

}
