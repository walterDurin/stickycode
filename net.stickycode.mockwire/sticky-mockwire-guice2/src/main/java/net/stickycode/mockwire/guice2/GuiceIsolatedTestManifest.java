package net.stickycode.mockwire.guice2;

import java.util.LinkedList;
import java.util.List;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.MembersInjector;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

import net.stickycode.mockwire.Bless;
import net.stickycode.mockwire.Controlled;
import net.stickycode.mockwire.IsolatedTestManifest;
import net.stickycode.mockwire.Mock;
import net.stickycode.mockwire.UnderTest;
import net.stickycode.reflector.AnnotatedFieldSettingProcessor;
import net.stickycode.reflector.Reflector;

public class GuiceIsolatedTestManifest
    implements IsolatedTestManifest {

  static public class Bean {

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

  static public class Manifest {

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
      return types;
    }

    public List<Bean> getBeans() {
      return beans;
    }

    public Object getBean(Class<?> type) {
      for (Bean b : beans) {
        if (b.getType().equals(type))
          return b.getInstance();
      }

      return null;
    }

  }

  private Manifest manifest = new Manifest();
  private Injector injector;

  public class IsolatedTestModule
      extends AbstractModule {

    private Manifest manifest;
    private Object testInstance;

    public IsolatedTestModule(Object testInstance, Manifest manifest) {
      this.manifest = manifest;
      this.testInstance = testInstance;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected void configure() {
      bindListener(Matchers.any(), new TypeListener() {

        @Override
        public <I> void hear(TypeLiteral<I> type, final TypeEncounter<I> encounter) {
          Class<? super I> rawType = type.getRawType();
          if (rawType.equals(testInstance.getClass())) {
            final Values valueCollector = new Values(encounter);
            new Reflector()
                .forEachField(valueCollector)
                .process(rawType);

            encounter.register(new MembersInjector<Object>() {

              @Override
              public void injectMembers(Object instance) {
                new Reflector()
                    .forEachField(
                        new AnnotatedFieldSettingProcessor(valueCollector,
                            Bless.class, UnderTest.class, Mock.class, Controlled.class))
                    .process(instance);
              }
            });
          }
        }
      });

      for (Class<?> type : manifest.getTypes()) {
        bind(type);
      }
      for (Bean b : manifest.getBeans()) {
        TypeLiteral type = TypeLiteral.get(b.getType());
        bind(type).toInstance(b.getInstance());
      }

    }

  }

  public GuiceIsolatedTestManifest() {
    manifest.register("manifest", this, IsolatedTestManifest.class);
  }

  @Override
  public void autowire(Object testInstance) {
    injector = Guice.createInjector(new IsolatedTestModule(testInstance, manifest));
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
    return manifest.getBean(type);
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
