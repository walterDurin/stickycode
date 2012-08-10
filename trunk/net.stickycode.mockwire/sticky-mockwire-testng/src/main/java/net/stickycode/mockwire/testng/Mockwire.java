package net.stickycode.mockwire.testng;

import net.stickycode.mockwire.MockwireContext;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener2;
import org.testng.ITestContext;
import org.testng.ITestResult;

public class Mockwire
    implements IInvokedMethodListener2 {

  @Override
  public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
  }

  @Override
  public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
  }

  @Override
  public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
    if (!method.isTestMethod())
      return;

    MockwireContext mockwire = new MockwireContext(method.getTestMethod().getRealClass());
    mockwire.startup();
    context.setAttribute("mockwire", mockwire);
    
    // FIXME when infinitest upgrades the bundled testng (5.14.6) to 6+ then this can be changed 
    mockwire.initialiseTestInstance(method.getTestMethod().getInstances()[0]);
  }

  @Override
  public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
    if (!method.isTestMethod())
      return;

    MockwireContext mockwire = (MockwireContext) context.getAttribute("mockwire");
    if (mockwire != null)
      mockwire.shutdown();
  }

}
