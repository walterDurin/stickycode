package net.stickycode.mockwire;

import net.stickycode.mockwire.binder.MockerFactoryLoader;
import net.stickycode.mockwire.binder.TestManifestFactoryLoader;
import net.stickycode.mockwire.junit4.MockwireContext;
import net.stickycode.mockwire.reflector.Reflector;

public class MockwireContained
    implements MockwireContext {

  private static final String version;

  static {
    version = PomUtils.loadVersion("net.stickycode.mockwire", "sticky-mockwire");
    System.out.println("MockwireContainment v" + version + " see http://stickycode.net/mockwire");
  }
  private final Class<?> testClass;
  private final String[] scanRoots;

  public MockwireContained(Class<?> testClass, MockwireContainment containment) {
    this.testClass = testClass;
    this.scanRoots = deriveContainmentRoots(testClass, containment);
  }

  public MockwireContained(Class<?> testClass) {
    this.testClass = testClass;
    MockwireContainment containment = testClass.getAnnotation(MockwireContainment.class);
    this.scanRoots = deriveContainmentRoots(testClass, containment);
  }

  private String[] deriveContainmentRoots(Class<?> testClass2, MockwireContainment containment) {
    if (containment == null || containment.value().length == 0)
      return new String[] { (packageToPath(testClass.getPackage())) };

    for (String path : containment.value())
      if (!path.startsWith("/"))
        throw new CodingException("The scan root '{}' for containment must start with /", path);

    return containment.value();
  }

  private String packageToPath(Package p) {
    return "/" + p.getName().replace('.', '/');
  }

  @Override
  public void initialiseTestInstance(Object testInstance) {
    Mocker mocker = MockerFactoryLoader.load();
    IsolatedTestManifest manifest = TestManifestFactoryLoader.load();

    manifest.scanPackages(scanRoots);

    process(manifest, mocker, testInstance);
    manifest.autowire(testInstance);
  }

  private IsolatedTestManifest process(final IsolatedTestManifest manifest, final Mocker mocker, Object testInstance) {
    new Reflector()
          .forEachField(
              new MockAnnotatedFieldProcessor(Mock.class, manifest, mocker),
              new BlessAnnotatedFieldProcessor(Bless.class, manifest))
          .forEachMethod(
              new BlessAnnotatedMethodProcessor(Bless.class, manifest))
          .process(testInstance);

    return manifest;
  }
}
