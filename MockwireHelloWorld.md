# Simple unit test #
In this example a **Unit** of code defined in this case by an inner class is blessed into the test context. Several asserts are defined in a test method to verify
the unit of code.

```
@RunWith(MockwireRunner.class)
public class UnitTest {

  /**
   * A unit of code to be tested
   */
  static class Unit {
    public boolean echo(boolean echo) {
      return echo;
    }
  }

  @UnderTest
  Unit unit;

  @Test
  public void simple() {
    assertThat(unit).isNotNull();
    assertThat(unit.echo(true)).isTrue();
    assertThat(unit.echo(false)).isFalse();
  }
```

[Code sample](http://code.google.com/p/stickycode/source/browse/trunk/net.stickycode.examples/sticky-example-mockwire/src/test/java/net/stickycode/example/mockwire/UnitTest.java)

# Example unit test with interdependencies #

```
@RunWith(MockwireRunner.class)
public class UnitDependenciesTest {

  public interface Dependency {
    void call();
  }

  static class Unit {
    @Inject
    Dependency dependency;
    public void call() {
      dependency.call();
    }
  }

  @UnderTest
  Unit unit;

  @Controlled
  Dependency mocked;

  @Test
  public void simple() {
    assertThat(unit).isNotNull();
    assertThat(unit.dependency).isNotNull();
    
    unit.call();
    verify(mocked).call();
  }
}
```

[Code sample](http://code.google.com/p/stickycode/source/browse/trunk/net.stickycode.examples/sticky-example-mockwire/src/test/java/net/stickycode/example/mockwire/UnitDependenciesTest.java)