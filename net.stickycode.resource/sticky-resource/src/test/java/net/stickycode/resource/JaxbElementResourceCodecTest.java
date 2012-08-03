package net.stickycode.resource;

import static org.fest.assertions.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.coercion.target.CoercionTargets;
import net.stickycode.resource.codec.JaxbElementResourceCodec;
import net.stickycode.resource.codec.ResourceDecodingFailureException;
import net.stickycode.resource.codec.ResourceEncodingFailureException;
import net.stickycode.xml.jaxb.JaxbFactory;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
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

  @Spy
  JaxbFactory factory = new JaxbFactory();

  @InjectMocks
  JaxbElementResourceCodec<Object> codec = new JaxbElementResourceCodec<Object>();

  private Charset charset = Charset.forName("UTF-8");

  @Test
  public void cycleBean() {
    Bean bean = new Bean();
    bean.field = "blah";
    Bean rehydrated = cycle(bean, Bean.class);
    assertThat(rehydrated.field).isEqualTo("blah");
  }

  @Test
  @Ignore("Namespacing is hard... come back to it")
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

  @Test(expected = ResourceDecodingFailureException.class)
  public void invalidBeanDecoding() {
    load(Bean.class, new ByteArrayInputStream(
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><bean/>".getBytes(charset)));
  }

  @SuppressWarnings("unchecked")
  private <T> T load(Class<T> type, InputStream in) {
    return (T) codec.load(new TestResourceConnection(in, null), CoercionTargets.find(type));
  }

  @SuppressWarnings("unchecked")
  private <T> T cycle(T bean, Class<T> type) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    CoercionTarget target = CoercionTargets.find(type);
    codec.store(target, bean, new TestResourceConnection(null, out));
    try {
      System.out.println(out.toString("UTF-8"));
    }
    catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    return (T) codec.load(new TestResourceConnection(new ByteArrayInputStream(out.toByteArray())), target);
  }
}
