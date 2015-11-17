# Introduction #

Using inheritance to manage dependencies might be fine for a very small project but taking it beyond that is leading to a world of hurt.

When coding you pull abstractions up via inheritance, if you follow this model for your artifacts you quickly find that you define your artifact parents by function. That is you pull up the plugin configurations and provide template hooks for artifacts to specify.

# The standard module layout #

Take a standard module
```
Parent 
 -> common
 -> server
 -> client
 -> war
```

If you only have one of these modules that could be fine, they all have the same lifecycle use the same dependencies on the whole. It kinda works.

However the next step is to have two deliverables
```
Parent 
 -> common
 -> server
 -> client
 -> war
Parent 2
 -> common 2
 -> server 2
 -> client 2
 -> war 2
```

In terms of dependencies that are common to children of Parent 2, does it makes sense to have any that are inherited by all but only needed by 1 or two? No it **does** not. That means that fundamentally inheritance is not the right model for dependencies.

However there is a workaround, in maven you can just specify the version but not a dependency on it in the **dependencyManagement** it seems too good to be true and in my opinion it is.

## DRY ##

Don't repeat yourself is broken - in practice - because
  1. You have to specify all the dependencies you use directly in each artifact, even if several artifacts have exactly the same dependencies
  1. Several parents will have the same dependency versions or worse you have one super pom with all the versions of every library that you use

## Composition ##

Thankfully its really easy to isolate shared dependencies, you just introduce a man in the middle dependency that composes them together with common shareable name.

Only the war projects would need a logging implementation in runtime scope so you define **logging-deploy** that both the war projects depend upon.

One other great example is unit testing, I have a set of libraries that I use to unit test
  * junit
  * mockito
  * logback
  * fest assertions
Should I specify that dependency in all of my artifacts? Well yes but there is not need to repeat myself. I define a UnitTestingComposition that gives that combination of libraries a name **composite-unittest** and it has a lifecycle that says junit 4 and mockito X and fest assertions Z all work together nicely as the standard set of unit tests.

Which do you prefer in all your artifact poms
```
<dependency>
  <groupId>org.mockito</groupId>
  <artifactId>mockito-core</artifactId>
  <exclusions>
    <exclusion>
      <artifactId>hamcrest-core</artifactId>
      <groupId>org.hamcrest</groupId>
    </exclusion>
  </exclusions>
</dependency>
<dependency>
  <groupId>junit</groupId>
  <artifactId>junit</artifactId>
</dependency>
<dependency>
  <groupId>org.easytesting</groupId>
  <artifactId>fest-assert</artifactId>
</dependency>
```

OR

```
<dependency>
  <groupId>net.stickycode.composite</groupId>
  <artifactId>sticky-composite-unittest</artifactId>
  <version>[1,2)</version>
</dependency>
```

## Mixed Dependencies ##

If that was not enough to convince you then consider that in any given dependency tree you will most likely have more than one parent with potentially conflicting versions.

e.g. You have **server 2** which uses **client 1** and their parents each have a dependency on