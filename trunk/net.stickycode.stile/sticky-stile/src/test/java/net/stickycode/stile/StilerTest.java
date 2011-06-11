package net.stickycode.stile;

import static org.fest.assertions.Assertions.assertThat;
import net.stickycode.mockwire.Controlled;
import net.stickycode.mockwire.UnderTest;
import net.stickycode.mockwire.junit4.MockwireRunner;
import net.stickycode.resource.Resources;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MockwireRunner.class)
public class StilerTest {

  @Controlled
  Workspace workspace;

  @UnderTest
  ResourceListeners listeners;

  @UnderTest
  ProcessesAnnotatedMethodProcessor processor;

  @UnderTest
  Stiler stiler;

  @UnderTest
  JavaCompilerStiler compiler;

  @UnderTest
  JavaSourcesStiler sources;

  @Test
  public void stiler() {
    System.out.println("eg");
    stiler.register(compiler);
    stiler.register(sources);
    Resources resources = stiler.produce(ResourcesTypes.JavaSource);
    assertThat(resources).hasSize(4);
    Resources classes = stiler.produce(ResourcesTypes.JavaByteCode);
    assertThat(classes).hasSize(3);
  }

}
