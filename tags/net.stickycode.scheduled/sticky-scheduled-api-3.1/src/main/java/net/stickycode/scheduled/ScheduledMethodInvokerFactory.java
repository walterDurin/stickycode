package net.stickycode.scheduled;

import java.lang.reflect.Method;

import net.stickycode.stereotype.StickyFramework;

/**
 * This abstraction allows for componentised extension of scheduling for methods.
 * 
 * Each factory defines if a given method can be invoked by the {@link ScheduledRunnable}'s that this factory is responsible for.
 */
@StickyFramework
public interface ScheduledMethodInvokerFactory {

  /**
   * Return true if the {@link ScheduledRunnable}'s of this factory invoke the given method
   */
  boolean canInvoke(Method method);

  /**
   * Create a scheduled runnable that will be used to invoke {@code method} on target with the given {@link Schedule schedule}
   */
  ScheduledRunnable create(Object target, Method method);

}
