# Check out the examples and try it for yourself #

You will need to install [maven](http://maven.apache.org) for this example. AFAIK any version will work, i've tested with 2.1.0 and 3.0-RC3.

```
svn co http://stickycode.googlecode.com/svn/trunk/net.stickycode.examples/sticky-example-mockwire 

cd sticky-example-mockwire
mvn test
```

You should see
```
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running net.stickycode.example.mockwire.UnitTest
MockwireIsolator v1.11 see http://stickycode.net/mockwire
2010-09-29 01:40:06 I main n.s.m.s.SpringIsolatedTestManifest: registering definition 'unit' for type 'net.stickycode.example.mockwire.UnitTest$Unit'
2010-09-29 01:40:06 I main n.s.m.s.SpringIsolatedTestManifest: registering definition 'unit' for type 'net.stickycode.example.mockwire.UnitTest$Unit'
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.871 sec
Running net.stickycode.example.mockwire.UnitDependenciesTest
2010-09-29 01:40:06 I main n.s.m.s.SpringIsolatedTestManifest: registering definition 'unit' for type 'net.stickycode.example.mockwire.UnitDependenciesTest$Unit'
2010-09-29 01:40:07 I main n.s.m.s.SpringIsolatedTestManifest: registering bean 'mocked' of type 'net.stickycode.example.mockwire.UnitDependenciesTest$Dependency'
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.352 sec

Results :

Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
```