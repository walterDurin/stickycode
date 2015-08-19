package net.stickycode.bootstrap.metadata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ScanTest {

  @Test
  public void empty() {
    ComponentRegister register = new ComponentRegister();
    new MetadataScanner(register);

    assertThat(register).isEmpty();
  }

  @Test
  public void emptyFolder() {
    ComponentRegister register = scan(new PackageFilter("net/stickycode/bootstrap/metadata/empty"));
    assertThat(register).isEmpty();
  }

  @Test
  public void emptyClasspath() {
    ComponentRegister register = scan(new PackageFilter("net.stickycode.bootstrap.metadata.empty"));
    assertThat(register).isEmpty();
  }


  @Test
  public void simpleClasspath() {
    ComponentRegister register = scan(new PackageFilter("net.stickycode.bootstrap.metadata.simple"));
    assertThat(register).hasSize(1);
    assertThat(register).contains(new ComponentDefinition().withName("net/stickycode/bootstrap/metadata/simple/Simple"));
  }

  private ComponentRegister scan(PackageFilter filter) {
    ComponentRegister register = new ComponentRegister();
    MetadataScanner scanner = new MetadataScanner(register);
    scanner.scan(filter);
    return register;
  }
}
