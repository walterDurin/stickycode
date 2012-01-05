package net.stickycode.configured.guice3.strategy;

import java.lang.reflect.Type;
import java.util.Set;

import net.stickycode.bootstrap.guice3.StickyModule;
import net.stickycode.configured.strategy.AbstractConfiguredStrategyTest;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.util.Types;

import de.devsurf.injection.guice.scanner.PackageFilter;

public class ConfiguredStrategyTest
    extends AbstractConfiguredStrategyTest {

  @Override
  protected void configure(WithStrategy instance) {
    PackageFilter packageFilter = PackageFilter.create("net.stickycode");
    Module startup = StickyModule.bootstrapModule(packageFilter);
    Injector injector = Guice.createInjector(startup)
        .createChildInjector(StickyModule.applicationModule(packageFilter), stategies());

    injector.injectMembers(this);
    injector.injectMembers(instance);
    system.configure();
  }

  @SuppressWarnings("unchecked")
  // wrapping a T in a Set safely returns a Set<T>
  static <T> TypeLiteral<Set<T>> setOf(TypeLiteral<T> elementType) {
    Type type = Types.setOf(elementType.getType());
    return (TypeLiteral<Set<T>>) TypeLiteral.get(type);
  }

  private Module stategies() {
    return new AbstractModule() {

      @Override
      protected void configure() {
        Multibinder<Strategy> binder = Multibinder.newSetBinder(binder(), Strategy.class);
        binder.addBinding().to(YesStrategy.class);
        binder.addBinding().to(NoStrategy.class);
      }
    };
  }

}
