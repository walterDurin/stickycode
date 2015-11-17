# Introduction #

Mockwire is a tool that takes care of the boiler plate of wiring up your tests, that means that all you see in your tests is
  1. a simple manifest of what to test
  1. mocking of controlled variables
  1. assertions stating the assumptions to validate environment

Mockwire uses the test class itself to define which beans are mocks and which real by the use of two annotations @Mock and @Bless.

Implementations of Mockwire build a registry of these beans and mocks and wire them by type. Currently only a spring25 implementation is going but watch that space.

## Why use mockwire ##

Many projects rely heavily on Dependency Injection to manage application assembly, because its a powerful tool. However many setups suffer from too much boiler plate when defining tests to verify the assembly.

Ideally we would always test our code just as it gets wired in production, if we write boiler plate code in our tests and use dependency injection in production environments we aren't really testing our code as it is run, which is somewhat pointless.

Mockwire just leverages the standard DI containers to build that assembly just as if the code under test was wire up properly. I call the assemblies isolated because its a well defined subset of the final application.

### Including Mockwire in you project ###

Add the appropriate MockwireDependency to your project to get started

### To use Mockwire with Junit ###

I primarily use Junit because I use Infinitest and Junit support in eclipse is a tad better than Testng. If you don't use junit See UsingMockwireWithoutJunit

Mockwire provides a junit4 runner: [MockwireRunner](http://code.google.com/p/stickycode/source/browse/trunk/net.stickycode.mockwire/sticky-mockwire/src/main/java/net/stickycode/mockwire/junit4/MockwireRunner.java) to easily run your test all wired up.

```
@RunWith(MockwireRunner.class)
public class UnitTest {
```

### Blessing and Mocking ###

@UnderTest, @Controlled, @Uncontrolled and @Configured are used to define the types that end up in the wired test environment.

Mockwire scans the test class and identitifies the code you wish to test "@UnderTest",

e.g. SomeConcreteClass in being tested so we need to bless a real instance of it. How the class is turned into a real instance is left up to the DI tool in use.
```
@UnderTest
SomeConcreteClass codeToTest;
```

And controlled dependencies of the code to test,

e.g. SomeConcreteClass requires a SomeInterface, this requirement is defined by @Inject
```
@Controlled
SomeInterface thatSomeConcreteClassNeeds;
```
How the class actually gets mocked is left up to the Mocking implementation, I would highly recommend Mockito, but will supply bindings to other mocking libraries on request.

### For assertions and mocking ###

See UnitTestingComposition which defines the libraries used and what they do.

## Examples ##

  * MockwireHelloWorld
  * MockwireDecentExample
  * MockwireExampleProject

## Containment ##

Once you have confirmed that a fully isolated chunk of code is tested you want to integrated it. Thats where @MockwireContainment comes in. In the simplest case it defines the containment of a test as not just the manifest from the Test class but also the result of scanning the package that the Test class exists in for components.

See [Component Oriented Programming](http://www.c2.com/cgi/wiki?ComponentOrientedProgramming)

Any class you want to be defined in scanned context gets marked with a @StickyComponent (or some other Component annotation).

Given some interfaces and implementations all in the same package **net.stikcycode.example.containment**
```
public interface Service {
  boolean doIt();
}

public interface Other {
  boolean it();
}

@StickyComponent
public class AlwaysTrueOther {
  public boolean it() {
   return true;
 }
}

@StickyComponent
public class ServiceImpl {

  @Inject
  private Other anOther; 

  public boolean doIt() {
    anOther.it();
  }
}
```

For containment we define an integration test and Inject the code we wish to test
```
@RunWith(MockwireRunner.class)
@MockwireContainment
public class ServiceImplIntegrationTest {

  @Inject
  Service impl;

  @Test
  public void coherency() {
    assertThat(impl.doIt()).isTrue();
  }
}
```

### Specifying the package(s) to scan ###
```
package net.stickycode.example.containment.main;
public interface Service {
  boolean doIt();
}

package net.stickycode.example.containment.other;
public interface Other {
  boolean it();
}

package net.stickycode.example.containment.other;
@StickyComponent
public class AlwaysTrueOther {
  public boolean it() {
   return true;
 }
}

package net.stickycode.example.containment.main;
@StickyComponent
public class ServiceImpl {

  @Inject
  private Other anOther; 

  public boolean doIt() {
    anOther.it();
  }
}
```

The package(s) can be defined as a parameter to the MockwireContainment annotation
```
package net.stickycode.example.containment.main;

@RunWith(MockwireRunner.class)
@MockwireContainment("/net/stickycode/example/containment/main")
public class ServiceImplIntegrationTest {

  @Bless
  ServiceImpl impl;

  @Test
  public void coherency() {
    assertThat(impl.doIt()).isTrue();
  }
}
```

More than one package can be scanned with
```
@MockwireContainment("/net/stickycode/example/containment/main", "/some/other/package)
```

If you prefer the package syntax

```
@MockwireContainment("net.stickycode.example.containment.main")
```