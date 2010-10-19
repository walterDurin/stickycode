package net.stickycode.mockwire.junit4;

import net.stickycode.mockwire.Bless;
import net.stickycode.mockwire.Mock;
import net.stickycode.mockwire.MockwireContained;
import net.stickycode.mockwire.MockwireContainment;
import net.stickycode.mockwire.MockwireIsolator;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
/**
 * A junit runner to make your test classes and code behave like it would when run live in a di context ala Mockwire.
 *
 * The default context used for Dependency Injection is a manifest defined by the test class itself. It will only contain
 * {@link Bless blessed} and {@link Mock mocked} classes in the actual test class or its super types.
 *
 * <pre>
 * package net.stickycode.example;
 *
 *  &#064;RunWith(MockwireRunner.class)
 *  public class ContainedTest {
 *
 *  &#064;Bless
 *  SomeConcreteClass field;
 *
 *  &#064;Inject
 *  SomeConcreteClass injectedInstanceOfBlessesClass;
 *
 *  &#064;Inject
 *  IsolateTestContext context;
 *
 *  &#064;Test
 *  public void testBless() {
 *    assertThat(context.hasRegisteredType(ConcreteClass.class)).isTrue();
 *  }
 * }
 * </pre>
 */
public class MockwireRunner extends BlockJUnit4ClassRunner {

  private MockwireContext mockwire;

  public MockwireRunner(Class<?> testClass) throws InitializationError {
    super(testClass);
    MockwireContainment containment = testClass.getAnnotation(MockwireContainment.class);
    if (containment == null)
      mockwire = new MockwireIsolator(testClass);
    else
      mockwire = new MockwireContained(testClass, containment);
  }

  @Override
  protected Object createTest() throws Exception {
    Object test = super.createTest();
    mockwire.initialiseTestInstance(test);
    return test;
  }

}
