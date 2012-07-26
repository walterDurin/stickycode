package net.stickycode.mockwire.guice3;

import javax.inject.Inject;

import net.stickycode.mockwire.Controlled;
import net.stickycode.mockwire.junit4.MockwireRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Turns out that unless you use a provider for the instances injection will be attempted, which causes issues with
 * instances that are mocks but happen to be classes are well. Using a provider of the instance injection is not attempted.
 */
@RunWith(MockwireRunner.class)
public class InjectionOfControlledFieldsTest {

  
  
  public class SomeOtherClass {

  }

  public static class SomeClass {
    @Inject
    SomeOtherClass other;
    
    public String someMethod() {
      return "blah";
    }
  }

  @Controlled
  SomeClass controlled;
  
  @Test
  public void test() {
    
  }
  
}
