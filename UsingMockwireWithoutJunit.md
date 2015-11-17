# Mockwire without Junit #

The class Mockwire provides static methods that can be used to define Mockwire isolation and containment

## Isolation ##

```
public class Test {
 @Bless
 ConcreteService service;

 @Before
 public void setup() {
  Mockwire.isolate(this);
 }

 @Test
 public void test() {
  assertThat(service).isNotNull();
 }
```

## Containment ##

It can be useful to scan packages for components and ensure that the resulting wire tree is coherent.

Be default Mockwire.contain(testInstance) will scan from the testInstances package and below
```
package same;
@StickyComponent
public class Dependency {
}
```
```
package same;
@StickyComponent
public class Unit {
 @Inject
 Depedendency dep;
}
```
```
package same;
public class Test {
  @Bless
  Unit unit;

 @Before
 public void setup() {
  Mockwire.contain(this);
 }
}
```
Isolation in this case would fail, but contain looks in package **same** for @StickyComponent's and added them to the DependencyInjectionRegistry