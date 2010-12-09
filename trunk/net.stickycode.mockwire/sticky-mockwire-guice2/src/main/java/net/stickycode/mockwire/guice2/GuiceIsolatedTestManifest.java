package net.stickycode.mockwire.guice2;

import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;

import org.guiceyfruit.support.GuiceyFruitModule;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

import net.stickycode.mockwire.Bless;
import net.stickycode.mockwire.IsolatedTestManifest;

public class GuiceIsolatedTestManifest
    implements IsolatedTestManifest {


  public class Bean {

    private Object instance;
    private Class<?> type;
    private String name;

    public Bean(String name, Object bean, Class<?> type) {
      this.instance = bean;
      this.type = type;
      this.name = name;
    }

    public Class<?> getType() {
      return type;
    }

    public Object getInstance() {
      return instance;
    }

    public String getName() {
      return name;
    }

  }

  public class Manifest {

    private List<Class<?>> types = new LinkedList<Class<?>>();
    private List<Bean> beans = new LinkedList<Bean>();

    public boolean hasRegisteredType(Class<?> type) {
      for (Class<?> t : types)
        if (t.isAssignableFrom(type))
          return true;

      for (Bean b : beans)
        if (b.getType().isAssignableFrom(type))
          return true;

      return false;
    }

    public void register(String beanName, Object bean, Class<?> type) {
      beans.add(new Bean(beanName, bean, type));
    }

    public void register(String beanName, Class<?> type) {
      types.add(type);
    }

    public List<Class<?>> getTypes() {
      return types ;
    }

    public List<Bean> getBeans() {
      return beans;
    }

  }

  private Manifest manifest = new Manifest();

  public class IsolatedTestModule
      extends AbstractModule {

    private Manifest manifest;

    public IsolatedTestModule(Manifest manifest) {
      this.manifest = manifest;
    }

    @Override
    protected void configure() {
      for (Class<?> type : manifest.getTypes()) {
        bind(type);
      }
      for (Bean b: manifest.getBeans()) {
        TypeLiteral type = TypeLiteral.get(b.getType());
        bind(type).toInstance(b.getInstance());
//        bindInstance(b.getType(), b.getName(), b.getInstance());
      }

//      bind
//
//      bindListener(Matchers.annotatedWith(Bless.class), new TypeListener() {
//
//        @Override
//        public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
//        }
//      };
    }

    /**
     * A helper method to bind the given type with the binding annotation.
     *
     * This allows you to replace this code <code> bind(Key.get(MyType.class, someAnnotation))
     * </code>
     *
     * with this <code> bind(KMyType.class, someAnnotation) </code>
     */
    protected <T> LinkedBindingBuilder<T> bind(Class<T> type, Annotation annotation) {
      return bind(Key.get(type, annotation));
    }
    /**
     * A helper method to bind the given type with the {@link com.google.inject.name.Named} annotation
     * of the given text value.
     *
     * This allows you to replace this code <code> bind(Key.get(MyType.class, Names.named("myName")))
     * </code>
     *
     * with this <code> bind(KMyType.class, "myName") </code>
     */
    protected <T> LinkedBindingBuilder<T> bind(Class<T> type, String namedText) {
      return bind(type, Names.named(namedText));
    }

    /**
     * A helper method which binds a named instance to a key defined by the given name and the
     * instances type. So this method is short hand for
     *
     * <code> bind(instance.getClass(), name).toInstance(instance); </code>
     */
    protected <T> void bindInstance(Class<T> type, String name, Object instance) {
      bind(type, name).toInstance((T)instance);
    }
  }

  public GuiceIsolatedTestManifest() {
    manifest.register("manifest", this, IsolatedTestManifest.class);
    // beans.put(IsolatedTestManifest.class, this);
  }

  @Override
  public void autowire(Object testInstance) {
    Injector injector = Guice.createInjector(new IsolatedTestModule(manifest));
    injector.injectMembers(testInstance);
  }

  @Override
  public boolean hasRegisteredType(Class<?> type) {
    return manifest.hasRegisteredType(type);
  }

  @Override
  public void registerBean(String beanName, Object bean, Class<?> type) {
    manifest.register(beanName, bean, type);
  }

  @Override
  public void registerType(String beanName, Class<?> type) {
    manifest.register(beanName, type);
  }

  @Override
  public Object getBeanOfType(Class<?> type) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void scanPackages(String[] scanRoots) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void registerConfigurationSystem(String name, Object configurationSystem, Class<?> type) {
    manifest.register(name, configurationSystem, type);
  }

}
