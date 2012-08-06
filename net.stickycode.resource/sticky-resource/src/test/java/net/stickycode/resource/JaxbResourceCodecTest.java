package net.stickycode.resource;

import static org.fest.assertions.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.coercion.target.CoercionTargets;
import net.stickycode.resource.codec.JaxbResourceCodec;
import net.stickycode.resource.codec.ResourceDecodingFailureException;
import net.stickycode.resource.codec.ResourceEncodingFailureException;
import net.stickycode.xml.jaxb.JaxbFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JaxbResourceCodecTest {

  @XmlRootElement
  public static class RootBean {

    @XmlElement(required = true)
    String field;
  }

  @Spy
  JaxbFactory factory = new JaxbFactory();

  @InjectMocks
  JaxbResourceCodec<Object> codec = new JaxbResourceCodec<Object>();

  private Charset charset = Charset.forName("UTF-8");

  @Test
  public void cycleRootBean() {
    RootBean bean = new RootBean();
    bean.field = "blah";
    RootBean rehydrated = cycle(bean, RootBean.class);
    assertThat(rehydrated.field).isEqualTo("blah");
  }

  @Test(expected = ResourceEncodingFailureException.class)
  public void invalidBeanEncoding() {
    RootBean bean = new RootBean();
    RootBean rehydrated = cycle(bean, RootBean.class);
    assertThat(rehydrated.field).isEqualTo("blah");
  }

  @Test(expected = ResourceDecodingFailureException.class)
  public void invalidBeanDecoding() {
    load(RootBean.class, new ByteArrayInputStream(
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><rootBean/>".getBytes(charset)));
  }

  @SuppressWarnings("unchecked")
  private <T> T load(Class<T> type, InputStream in) {
    return (T) codec.load(CoercionTargets.find(type), in, charset);
  }

  @SuppressWarnings("unchecked")
  private <T> T cycle(T bean, Class<T> type) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    CoercionTarget target = CoercionTargets.find(type);
    codec.store(target, bean, out, charset);
    try {
      System.out.println(out.toString("UTF-8"));
    }
    catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    return (T) codec.load(target, new ByteArrayInputStream(out.toByteArray()), charset);
  }
}
