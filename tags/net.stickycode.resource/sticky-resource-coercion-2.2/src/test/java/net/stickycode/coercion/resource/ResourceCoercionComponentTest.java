package net.stickycode.coercion.resource;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Properties;

import javax.inject.Inject;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.coercion.target.CoercionTargets;
import net.stickycode.mockwire.MockwireConfigured;
import net.stickycode.mockwire.MockwireContainment;
import net.stickycode.mockwire.junit4.MockwireRunner;
import net.stickycode.reflector.Fields;

import org.fest.assertions.MapAssert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MockwireRunner.class)
@MockwireContainment
@MockwireConfigured({})
public class ResourceCoercionComponentTest {

  @XmlRootElement
  public static class Bean {

      @XmlAttribute
      private String value;
  }

  @SuppressWarnings("unused")
  private String stringResource;

  @SuppressWarnings("unused")
  private Properties propertiesResource;

  @SuppressWarnings("unused")
  private Bean bean;

  @Inject
  private ResourceCoercion coercion;

  @Test
  public void jaxbResource() {
    CoercionTarget target = target("bean");
    Bean bean = (Bean)coercion.coerce(target, "classpath://bean.xml");
    assertThat(bean.value).isEqualTo("loaded");
  }
  
  @Test
  public void propertiesResource() {
    CoercionTarget target = target("propertiesResource");
    Properties bean = (Properties)coercion.coerce(target, "some.properties");
    assertThat(bean).includes(MapAssert.entry("a", "b"));
  }

  private CoercionTarget target(String fieldName) {
    return CoercionTargets.find(Fields.find(getClass(), fieldName));
  }
}
