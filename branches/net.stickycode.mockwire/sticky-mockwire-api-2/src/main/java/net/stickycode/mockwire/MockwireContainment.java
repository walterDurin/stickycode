package net.stickycode.mockwire;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * <p>This annotation is used to define the containment of a Mockwire invocation where a runner is used for the tests.</p>
 *
 * <p>This covers the scenario when scanning is required outside of the test class itself. The default scanning path is that
 * of the test class itself. So in the following example the classpath will be scanned for StickyComponent's and they will be included
 * in the {@link IsolatedTestManifest}.</p>
 *
 * <pre>
 * package net.stickycode.example;
 *
 *  &#064;RunWith(MockwireRunner.class)
 *  &#064;MockwireContainment()
 *  public class ContainedTest {
 *
 *  &#064;Bless
 *  SomeConcreteClass field;
 *
 *  &#064;Inject
 *  SomeOtherClass thatLivesInTheSamePackageAsTheTest;
 *
 *  &#064;Inject
 *  IsolateTestContext context;
 *
 *  &#064;Test
 *  public void testBless() {
 *    assertThat(context.hasRegisteredType(ConcreteClass.class)).isTrue();
 *    assertThat(context.hasRegisteredType(SomeOtherClass.class)).isTrue();
 *  }
 * }
 * </pre>
 *
 * <p>It is possible to define the path(s) to scan from</p>
 * <pre>
 * package net.stickycode.example;
 *
 *  &#064;RunWith(MockwireRunner.class)
 *  &#064;MockwireContainment("net.stickcode.other")
 *  public class ContainedTest {
 *
 *  &#064;Bless
 *  SomeConcreteClass field;
 *
 *  &#064;Inject
 *  SomeOtherClass thatLivesInPackageNetStickycodeOther;
 *
 *  &#064;Test
 *  public void testBless() {
 *    assertThat(context.hasRegisteredType(ConcreteClass.class)).isTrue();
 *    assertThat(context.hasRegisteredType(SomeOtherClass.class)).isTrue();
 *  }
 * }
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MockwireContainment {
  String[] value() default {};
}
