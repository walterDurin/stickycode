package net.stickycode.bootstrap.metadata;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PackageFilter {

  private List<String> packages = new ArrayList<>();

  public PackageFilter() {
  }

  public PackageFilter(String include) {
    include(include);
  }

  public void include(String filter) {
    this.packages.add(filter.replaceAll("\\.", "/"));
  }

  public boolean included(String path) {
    for (String include : packages) {
      if (path.startsWith(include))
        return true;
    }

    return false;
  }

  public boolean includes(Path file) {
    return included(file.toString());
  }
}
