# Bringing the power of convention to Java #

Sticky Code is an attempt to learn from the experience of _convention over configuration frameworks_ and use that with plain old java.

Why **"Sticky"** well the sticky conventions let you build applications that don't need glue, it just sticks together, its "Sticky Code".

## Dependency Compositions ##

Using third party libraries is essential to java development. Library and tool support make java what it is.

StickyCompositions use the standard OO concept of composition to provide manageable lifecycles to third party libraries.

The lifecycle of compositions is explained by CompositionVersioning, with some example compositions described here:

  * LoggingComposition
  * SpringComposition
  * UnitTestingComposition

## Mockwire ##

Mockwire is a tool to remove wiring boiler plate wiring from tests, it provides a convention to wire tests using the same dependency injection system you use in production, i.e. Test it like you plan to run it.

It also simplifies the use of mocking for isolating code under test. Think empirical analysis and controlled variables.

  * MockwireQuickStart
  * MockwireDependency

# License #

StickyCode is all licensed under the liberal Apache License v2.0