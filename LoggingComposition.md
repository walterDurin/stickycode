# Introduction #

[slf4j](http://www.slf4j.org/) is the only real logging api that makes sense. The api is just a set of interfaces. It leaves the actual logging implementation up the the end user.

StickyCode defines two compositions one for anything that needs to log which essentially just pulls in slf4j-api and because jcl still seems very common it pulls in the jcl to slf4j adapter.

It is safe to use the range as if a breaking change is made the major version will be bumped.

## Logging Api ##
```
<dependency>
  <groupId>net.stickycode.composition</groupId>
  <artifactId>sticky-composition-logging-api</artifactId>
  <version>[1,2)</version>
</dependency>
```

![http://stickycode.googlecode.com/files/logging-api-graph.png](http://stickycode.googlecode.com/files/logging-api-graph.png)

## Logging Implementation ##

This logging implementation uses logback. Its used by Mockwire and UnittestingComposition to provide logging for tests.

```
<dependency>
  <groupId>net.stickycode.composition</groupId>
  <artifactId>sticky-composition-logging-deploy</artifactId>
  <version>[1,2)</version>
</dependency>
```

![http://stickycode.googlecode.com/files/logging-deploy.png](http://stickycode.googlecode.com/files/logging-deploy.png)

See StickyMavenRepository for the repository details