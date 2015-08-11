package net.stickycode.bootstrap.tck.plugin;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.inject.Inject;

import net.stickycode.configured.guice3.StickyGuice;

import org.junit.Test;

import com.google.inject.Binding;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.util.Types;

public class PluggableTest
{

  @Inject
  Set<Pluggable> plugged;

  @Inject
  Set<PluggableContract> abstractInTheMiddle;

  @Inject
  Injector injector;

  @Test
  public void verify() {
    Injector injector = StickyGuice.createApplicationInjector(getClass().getPackage().getName());
    injector.injectMembers(this);

    assertThat(plugged).hasSize(2);

    Binding bindig = injector.getBinding(Key.get(Types.setOf(PluggableContract.class)));
  }
}
