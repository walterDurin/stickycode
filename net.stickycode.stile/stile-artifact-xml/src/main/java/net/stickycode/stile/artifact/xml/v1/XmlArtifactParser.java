package net.stickycode.stile.artifact.xml.v1;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import net.stickycode.resource.ClasspathResource;
import net.stickycode.resource.Resource;
import net.stickycode.stile.artifact.Artifact;
import net.stickycode.stile.artifact.ArtifactParser;
import net.stickycode.stile.version.VersionParser;
import net.stickycode.stile.version.component.ComponentVersion;
import net.stickycode.stile.version.component.ComponentVersionParser;

import org.xml.sax.SAXException;

public class XmlArtifactParser
    implements ArtifactParser {

  private final JAXBContext context;

  private final Schema schema;

  private VersionParser versionParser = new ComponentVersionParser();

  public XmlArtifactParser() {
    this.context = loadContext();
    this.schema = loadSchema();
  }

  private JAXBContext loadContext() {
    try {
      return JAXBContext.newInstance(XmlArtifact.class);
    }
    catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Artifact parse(Resource resource) {
    XmlArtifact artifact = unmarshall(resource);
    ComponentVersion version = versionParser.parse(artifact.getVersion());
    return new Artifact(artifact.getGroup() + "." + artifact.getId(), version);
  }

  public XmlArtifact unmarshall(Resource resource) {
    StreamSource source = new StreamSource(resource.getSource());
    Unmarshaller unmarshaller = createUnmarshaller();
    try {
      return unmarshaller.unmarshal(source, XmlArtifact.class).getValue();
    }
    catch (JAXBException e) {
      throw new ParseFailureException(e);
    }
  }

  private Unmarshaller createUnmarshaller() {
    try {
      Unmarshaller unmarshaller = context.createUnmarshaller();
      unmarshaller.setSchema(schema);
      return unmarshaller;
    }
    catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }

  private Schema loadSchema() {
    SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    Resource schemaSource = new ClasspathResource(getClass(), "Artifact-v1.xsd");
    try {
      return factory.newSchema(new StreamSource(schemaSource.getSource()));
    }
    catch (SAXException e) {
      throw new RuntimeException(e);
    }
  }

  public Schema getSchema() {
    return schema;
  }

}
