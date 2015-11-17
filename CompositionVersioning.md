# Introduction #

When using third party libraries is is useful to have control over the actual versions that you use, esp in a world of weakly defined dependencies as seen in maven central.

## Weak dependencies ##

They are weak because they don't use ranges. When you say I want version 2.4 in maven that means **something like 2.4**, if a project closer in the transitive tree defines a version 3.2 then that wins.

For example because A's dependency on D is closer that D's it wins conflict resolution.
```
A -> B -> C -> D 2.4
A -> D 3.2
```

Now if both versions were binary compatible that would be fine. But how do you know? With Ranges you can define the compatibility contracts with constraints.

## Ranges ##
In my world 2.4 and 3.2 are not compatible, the major version is different, so things like deprecated methods might be gone.

```
A -> B -> C -> D [2,3)
A -> D [3,4)
```

This results in a conflict when maven resolves the tree. A version of D cannot be selected that satisfies all the constraints.

I use compositions to hold fixed ranges for third party projects and provide a new version lifecycle to use in closer projects.

For example

```
<dependencies>
  <dependency>
    <groupId>net.stickycode.composite</groupId>
    <artifactId>sticky-composite-logging-api</artifactId>
    <version>[1,2)</version>
  </dependency>
  <dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>[2.5.6]</version>
    <exclusions>
      <exclusion>
	<artifactId>commons-logging</artifactId>
	<groupId>commons-logging</groupId>
      </exclusion>
    </exclusions>
  </dependency>
</dependencies>
<dependencies>
```

The square brackets define the range from 2.5.6 to 2.5.6 i.e. only that version is allowed. Now update everything that needs spring25 to depend on the composite.

![http://stickycode.googlecode.com/files/sticky-composite-spring25-graph.png](http://stickycode.googlecode.com/files/sticky-composite-spring25-graph.png)

## Non breaking upstream updates ##
If 2.5.6.SEC02 comes out and testing shows its fully compatible then sticky-composite-spring25 can be released again
```
<dependencies>
  <dependency>
    <groupId>net.stickycode.composite</groupId>
    <artifactId>sticky-composite-logging-api</artifactId>
    <version>[1,2)</version>
  </dependency>
  <dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>[2.5.6.SEC02]</version>
    <exclusions>
      <exclusion>
	<artifactId>commons-logging</artifactId>
	<groupId>commons-logging</groupId>
      </exclusion>
    </exclusions>
  </dependency>
</dependencies>
<dependencies>
```

Now of course all your projects used
```
<dependency>
  <groupId>net.stickycode.composite</groupId>
  <artifactId>sticky-composite-spring25</artifactId>
  <version>[1,2)</version>
</dependency>
```
So as soon as the new spring25 composite is released (aka tagged, built and pushed to a public repository) your project picks it up.

## Breaking upstream updates ##

If 2.5.6.SEC02 was found to break things then the major version of the composite-spring25 is updated ... I this case it would be from 1 to 2
```
<groupId>net.stickycode.composite</groupId>
<artifactId>sticky-composite-spring25</artifactId>
<packaging>jar</packaging>
<version>2.1-SNAPSHOT</version>
<dependencies>
  <dependency>
    <groupId>net.stickycode.composite</groupId>
    <artifactId>sticky-composite-logging-api</artifactId>
    <version>[1,2)</version>
  </dependency>
  <dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>[2.5.6.SEC02]</version>
    <exclusions>
      <exclusion>
	<artifactId>commons-logging</artifactId>
	<groupId>commons-logging</groupId>
      </exclusion>
    </exclusions>
  </dependency>
</dependencies>
<dependencies>
```

You can now have a controlled migration from the old version to the new version, ensuring that every project that needs to update takes account of the breaking change.

Generally you start with one project that needs to update, and then as you assemble larger applications you get conflicts, which you have to resolve.

p.s. Spring is generally very reliable in terms of binary compatibility, I just use them as a reference because I often use spring.