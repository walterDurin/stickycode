Add this repository to you repository manager, settings.xml or if you have a very simple project to you pom.xml.

```
<repository>
  <id>stickycode</id>
  <name>Sticky Code</name>
  <url>https://nexus.stickycode.net/content/repositories/releases</url>
  <releases>
    <enabled>true</enabled>
    <updatePolicy>interval:600</updatePolicy>
  </releases>
  <snapshots>
    <enabled>false</enabled>
  </snapshots>
</repository>
```

See CompositionDependency for details