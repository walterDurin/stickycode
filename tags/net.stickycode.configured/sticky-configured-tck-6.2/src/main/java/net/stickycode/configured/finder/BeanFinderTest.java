package net.stickycode.configured.finder;

import static org.assertj.core.api.StrictAssertions.assertThat;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import net.stickycode.bootstrap.BeanNotFoundFailure;
import net.stickycode.bootstrap.ComponentContainer;
import net.stickycode.bootstrap.StickyBootstrap;

public class BeanFinderTest {

  @Inject
  ComponentContainer finder;

  @Before
  public void setup() {
    StickyBootstrap.crank(this, getClass()).find(SingletonBean.class);
  }

  @Test
  public void lookupPrototype() {
    Bean bean = finder.find(Bean.class);
    assertThat(bean).isNotNull();
    Bean bean2 = finder.find(Bean.class);
    assertThat(bean).isNotSameAs(bean2);
  }

  @Test
  public void lookupSingleton() {
    SingletonBean bean = finder.find(SingletonBean.class);
    assertThat(bean).isNotNull();
    SingletonBean bean2 = finder.find(SingletonBean.class);
    assertThat(bean).isSameAs(bean2);
  }

  @Test(expected = BeanNotFoundFailure.class)
  public void notFound() {
    finder.find(getClass());
  }

  @Test(expected = BeanNotFoundFailure.class)
  public void tooMany() {
    TooMany found = finder.find(TooMany.class);
    assertThat(found).isNotNull();
  }
}
