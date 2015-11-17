# Spring 2.5 #

The spring 2.5 composition
  1. pulls in spring-context
  1. excludes commons-logging
  1. maps commons-logging api to slf4j

It fixes the spring version with [2.5.6] so mixing libraries with other versions of spring gives deterministic results.

As needed units tests will be added to enforce assumptions made about libraries working together.

Read about CompositionVersioning to better understand why wrapping third party libraries in this way is a good idea.