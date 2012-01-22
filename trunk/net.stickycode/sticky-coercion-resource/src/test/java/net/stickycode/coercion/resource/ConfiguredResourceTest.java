package net.stickycode.coercion.resource;

import static org.fest.assertions.Assertions.assertThat;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Test;
import org.junit.runner.RunWith;

import net.stickycode.mockwire.MockwireConfigured;
import net.stickycode.mockwire.junit4.MockwireRunner;
import net.stickycode.resource.Resource;
import net.stickycode.stereotype.Configured;

@RunWith(MockwireRunner.class)
@MockwireConfigured({
    "configuredResourceTest.bean=bean.xml",
    "configuredResourceTest.jaxbElementBean=bean.xml"
})
public class ConfiguredResourceTest {

  @XmlRootElement
  public static class Bean {

    @XmlAttribute
    String value;
  }

  @Configured
  Resource<Bean> bean;

  @Configured
  Resource<JAXBElement<Bean>> jaxbElementBean;

  @Test
  public void configured() {
    assertThat(bean.get().value).isEqualTo("loaded");
    assertThat(jaxbElementBean.get().getValue().value).isEqualTo("loaded");
  }
}
