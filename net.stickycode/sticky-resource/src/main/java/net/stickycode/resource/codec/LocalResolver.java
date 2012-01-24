package net.stickycode.resource.codec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

public class LocalResolver
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
    log.debug("generating {} with suggested name {}", namespaceUri, suggestedFileName);
    return streamResult;
  }

}
