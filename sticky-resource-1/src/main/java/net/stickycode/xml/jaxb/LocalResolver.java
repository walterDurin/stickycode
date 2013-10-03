package net.stickycode.xml.jaxb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

class LocalResolver
    extends SchemaOutputResolver
    implements LSResourceResolver {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Map<String, ByteArrayOutputStream> generatedSchema = new HashMap<String, ByteArrayOutputStream>();

  @Override
  public LSInput resolveResource(String type, String namespaceURI, String publicId, final String systemId, String baseURI) {
    log.info("{} {} {} {}", new Object[] { type, namespaceURI, publicId, systemId });
    return new LSInput() {

      @Override
      public void setSystemId(String systemId) {
      }

      @Override
      public void setStringData(String stringData) {
      }

      @Override
      public void setPublicId(String publicId) {
      }

      @Override
      public void setEncoding(String encoding) {
      }

      @Override
      public void setCharacterStream(Reader characterStream) {
      }

      @Override
      public void setCertifiedText(boolean certifiedText) {
      }

      @Override
      public void setByteStream(InputStream byteStream) {
      }

      @Override
      public void setBaseURI(String baseURI) {
      }

      @Override
      public String getSystemId() {
        return systemId;
      }

      @Override
      public String getStringData() {
        return null;
      }

      @Override
      public String getPublicId() {
        return null;
      }

      @Override
      public String getEncoding() {
        return null;
      }

      @Override
      public Reader getCharacterStream() {
        try {
          System.out.println(generatedSchema.get(systemId).toString("UTF-8"));
          return new InputStreamReader(new ByteArrayInputStream(generatedSchema.get(systemId).toByteArray()), "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
          throw new RuntimeException(e);
        }
      }

      @Override
      public boolean getCertifiedText() {
        return false;
      }

      @Override
      public InputStream getByteStream() {
        return new ByteArrayInputStream(generatedSchema.get(systemId).toByteArray());
      }

      @Override
      public String getBaseURI() {
        return systemId;
      }
    };
  }

  @Override
  public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    generatedSchema.put(suggestedFileName, out);
    StreamResult streamResult = new StreamResult(out);
    streamResult.setSystemId("");
    log.debug("generating in ns '{}' with suggested name {}", namespaceUri, suggestedFileName);
    return streamResult;
  }

  public void postProcess() {
//    XmlSchemaCollection c = new XmlSchemaCollection();
//    for (String id : generatedSchema.keySet()) {
//      log.info("post process {}", id);
//      c.read(new StreamSource(new ByteArrayInputStream(generatedSchema.get(id).toByteArray()), id));
//    }
//    for (XmlSchema i : c.getXmlSchemas()) {
//      try {
//        i.write(System.out);
//        i.addMetaInfo("a", "b");
//        Document d = i.getSchemaDocument();
//        Node firstChild = d.getFirstChild();
//        Element e = d.createElementNS(firstChild.getNamespaceURI(), "xs:element");
//        e.setAttribute("name", "bob");
//        e.setAttribute("type", "blah");
//        firstChild.appendChild(e);
//        firstChild.getAttributes();
//        
//        i.addMetaInfo("a", "b");
//        t.getAnnotation().addMetaInfo("appinfo", "b");
//        i.write(System.err);
//        
//      }
//      catch (UnsupportedEncodingException e) {
//        throw new RuntimeException(e);
//      }
//      catch (DOMException e) {
//        throw new RuntimeException(e);
//      }
//      catch (XmlSchemaSerializerException e) {
//        throw new RuntimeException(e);
//      }
//    }
//    
//    XmlSchemaType schema = c.getTypeByQName(new QName("bean"));
//    schema.addMetaInfo("a", "b");
//System.out.println("XXX");
//System.out.println("XXX");
//System.out.println("XXX");
//    for (XmlSchema i : c.getXmlSchemas()) {
//      try {
//        i.write(System.out);
//      }
//      catch (UnsupportedEncodingException e) {
//        throw new RuntimeException(e);
//      }
//    }
  }

  public StreamSource[] getSources() {
      List<StreamSource> schemaSources = new ArrayList<StreamSource>();
      for (Entry<String, ByteArrayOutputStream> streamSource : generatedSchema.entrySet()) {
        schemaSources.add(new StreamSource(new ByteArrayInputStream(streamSource.getValue().toByteArray()), streamSource.getKey()));
      }
    return schemaSources.toArray(new StreamSource[generatedSchema.size()]);
  }

}
