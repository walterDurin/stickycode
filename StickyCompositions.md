# Introduction #

When you have more than one deliverable e.g. two wars, you will most likely end up with a scenario where they share libraries in common. Consider a logging implementation as a good example.

## The logging implementation dependency ##

All my wars have a logback-0.9.24.jar in them. I could specify the version in each of the war projects however given that the lifecycles of the war projects are most likely not in sync I want to learn from what works with one when moving onto the next.

So I compose the logging implementation into its own project with its own version. Now I have my-logging-implementation-1.1. where 1.1 is an internal version not related to logback, I don't really care as the versions are meaningless in every respect **except** I assert that every my-logging-implementation-1.X will be compatible.

Now if each of my wars depends upon my-logging-implementation-1.X I can easily inject a new version of the logging implementation into a continous integration environment to see if anything breaks when ever the upstream library changes.


## Continuous integration to validate new upstream ##

The most common set up for Continuous Integration systems is the HEAD to HEAD, for mavenized projects this generally means the trunk of many artifacts installed as SNAPSHOT's. Using the version range as specified here

```
<dependency>
  <groupId>net.stickycode.composite</groupId>
  <artifactId>sticky-composite-logging-deploy</artifactId>
  <version>[1,2)</version>
</dependency>
```

I can easily commit a change to the head of the sticky-composite-logging-deploy project and have it affect CI built projects but not developers as I have not **released** it. No forcing a complete rebuild of all projects we can see whether or not any projects no longer compile and/or pass their tests. Obviously changing the logger has less risk of breaking things that other libraries but the principle holds true.

Say a new version of logback comes out 0.9.25, i would change
```
<dependency>
  <groupId>ch.qos.logback</groupId>
  <artifactId>logback-classic</artifactId>
  <version>[0.9.25]</version>
</dependency>
```
commit it and force a full CI rebuild - if there were no dependency base hooks of course - and watch for any alerts.

Thats the principle in a nutshell I hope. Let me know if you need anything clarified