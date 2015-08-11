package net.stickycode.configured.finder;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public abstract class AbstractBeanFinderTest {

  protected abstract BeanFinder getFinder();

  @Test
  public void lookupPrototype() {
    Bean bean = getFinder().find(Bean.class);
    assertThat(bean).isNotNull();
    Bean bean2 = getFinder().find(Bean.class);
    assertThat(bean).isNotSameAs(bean2);
  }

  @Test
  public void lookupSingleton() {
    BeanFinder finder = getFinder();
    SingletonBean bean = finder.find(SingletonBean.class);
    assertThat(bean).isNotNull();
    SingletonBean bean2 = finder.find(SingletonBean.class);
    assertThat(bean).isSameAs(bean2);
  }

  @Test(expected = BeanNotFoundException.class)
  public void notFound() {
    getFinder().find(getClass());
  }

  @Test(expected = BeanNotFoundException.class)
  public void tooMany() {
    getFinder().find(TooMany.class);
  }
}
