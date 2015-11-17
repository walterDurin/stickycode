# Introduction #

When using jetty (or something other) to run a built war for integration testing it is often useful to run the war and then look at it interactively. Using the daemon mode works but you get errors.

Include the wait plugin like so

```
<plugin>
  <groupId>net.stickycode.plugins</groupId>
    <artifactId>sticky-wait-plugin</artifactId>
    <version>1.3</version>
    <executions>
      <execution>
        <phase>integration-test</phase>
        <goals>
          <goal>wait</goal>
        </goals>
      </execution>
    </executions>
  </plugin>
</plugins>
```

Will give you this when you run `mvn verify`

> [INFO](INFO.md) --- sticky-wait-plugin:1.3:wait (default) @ sticky-helloworld-  ws-deploy ---

> Press ENTER key to continue...

# Custom messages #

If you want a custom prompt message you can configure it like so...
```
 <configuration>
  <promptMessage>I'm running on  http://localhost:8080/${project.artifactId}/, press ENTER to shutdown..</promptMessage>
 </configuration>
```


# Timeouts #

If you just want the service to stay up for a specified period of time you can configure it in milliseconds like this
```
 <configuration>
  <timeout>5000</timeout>
 </configuration>
```