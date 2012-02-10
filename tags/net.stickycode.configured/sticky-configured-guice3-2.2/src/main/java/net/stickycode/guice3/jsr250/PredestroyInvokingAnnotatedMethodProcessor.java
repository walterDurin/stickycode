package net.stickycode.guice3.jsr250;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PreDestroy;

import net.stickycode.configured.InvokingAnnotatedMethodProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PredestroyInvokingAnnotatedMethodProcessor
    extends InvokingAnnotatedMethodProcessor {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Set<Object> seen = new HashSet<Object>();

  public PredestroyInvokingAnnotatedMethodProcessor() {
    super(PreDestroy.class);
  }

  @Override
  public void processMethod(Object target, Method method) {
    if (seen.contains(target))
      return;

    super.processMethod(target, method);
    log.info("predestroy {}.{}", target.getClass().getName(), method.getName());
    seen.add(target);
  }

}
