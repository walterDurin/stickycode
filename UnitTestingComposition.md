# Introduction #

I've spent a long time trying to figure out the best tools to use for unit testing and have found the ones that work for me.

Here is a brief introduction to them but please check out their websites they do much better sales pitches than I do.

## Dependency ##

For the repository of sticky code see StickyMavenRepository

```
<dependency>
  <groupId>net.stickycode.composite</groupId>
  <artifactId>sticky-composite-unittest</artifactId>
  <version>[1,2)</version>
</dependency>
```

![http://stickycode.googlecode.com/files/unittest-graph.png](http://stickycode.googlecode.com/files/unittest-graph.png)

## Junit4 for running ##

I use Junit4 because its supported by Infinitest, and could not live without Infinitest.
The Eclipse support is just a little bit better than Testng and it makes the difference, being able to run all the tests in a project is really handy.

I do not use the junit assertions they are too confusing.

## Fest Assert for assertions ##

A fluent api for asserting the state of code under test, it makes life very easy in IDE's with decent auto completion.

With standard junit asserts you would write
```
assertEquals(new Something().getValue(), "value");
```
With Fest you can say
```
assertThat(new Something().getValue()).isEqualTo("value");
```

Whipdee do you say, well how about
```
UserRepository repository = new UserRepository();
assertThat(repository.getActiveUsers()).contains(new User("Bob");

assertThat(repository.getUser("Bob")).isNotNull();
assertThat(repository.getUser("Bob").getAge()).isGreaterThan(30);
```

In eclipse add org.fest.assertions.Assertions.**to your favourites to get autocompletion**

## Mockito for mocking ##

Mockito was based of EasyMock with better defaults and less verbosity.

```
@Mock
Interface mocked;

@Before
public void setup() { 
MockitoAnnotations.initMocks(this) 
}

@Test 
public void test() {
  when(mocked.getValue("a")).thenReturn("some value");
  Service service = new Service(mocked);
  assertThat(service.lookup("a")).isEqualTo("some value");
}
```

## Infinitest for continuous testing ##

This is a commercial product for $29US, and money you will make back in less than a day.

In Eclipse it scans all the projects you have open and runs all the tests in the background as you change code that affects them. In runs in IntelliJ but is not so useful without an incremental compiler.

Once you try TDD with it you will never want to go back.

## Eclipse favourites for autocompletion ##

This lets you autocomplete static imports which are a hassle to type all the time otherwise.

![http://stickycode.googlecode.com/files/eclipse-favourites.png](http://stickycode.googlecode.com/files/eclipse-favourites.png)

## Links ##

  * http://improvingworks.com/
  * http://www.junit.org
  * http://mockito.org/
  * http://code.google.com/p/fest/