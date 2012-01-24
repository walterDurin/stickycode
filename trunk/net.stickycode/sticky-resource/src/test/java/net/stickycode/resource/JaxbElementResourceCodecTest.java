package net.stickycode.resource;

import static org.fest.assertions.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import net.stickycode.resource.codec.JaxbElementResourceCodec;
import net.stickycode.resource.codec.ResourceDecodingFailureException;
import net.stickycode.resource.codec.ResourceEncodingFailureException;

import org.junit.Ignore;
import org.junit.Test;

public class JaxbElementResourceCodecTest {

  @XmlType
  public static class Bean {

    @XmlAttribute(required = true)
    String field;
  }

  @XmlType(namespace = "xxx")
  public static class NamespacedBean {

    @XmlElement(required = true)
    String field;
  }

  private Charset charset = Charset.forName("UTF-8");

  @Test
  public void cycleBean() {
    Bean bean = new Bean();
    bean.field = "blah";
    Bean rehydrated = cycle(bean, Bean.class);
    assertThat(rehydrated.field).isEqualTo("blah");
  }

  @Test
  @Ignore("Working on namespacing")
  public void cycleNamespaceBean() {
    NamespacedBean bean = new NamespacedBean();
    bean.field = "blah";
    NamespacedBean rehydrated = cycle(bean, NamespacedBean.class);
    assertThat(rehydrated.field).isEqualTo("blah");
  }

  @Test(expected = ResourceEncodingFailureException.class)
  public void invalidBeanEncoding() {
    Bean bean = new Bean();
    Bean rehydrated = cycle(bean, Bean.class);
    assertThat(rehydrated.field).isEqualTo("blah");
  }

  @Test//(expected = ResourceDecodingFailureException.class)
  public void invalidBeanDecoding() {
    Bean rehydrated = load(Bean.class, new ByteArrayInputStream(
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><bean/>".getBytes(charset)));
  }

  private <T> T load(Class<T> type, InputStream in) {
    JaxbElementResourceCodec<T> codec = new JaxbElementResourceCodec<T>(type);
    return codec.load(in);
  }

  private <T> T cycle(T bean, Class<T> type) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    JaxbElementResourceCodec<T> codec = new JaxbElementResourceCodec<T>(type);
    codec.store(bean, out);
    return codec.load(new ByteArrayInputStream(out.toByteArray()));
  }
}
