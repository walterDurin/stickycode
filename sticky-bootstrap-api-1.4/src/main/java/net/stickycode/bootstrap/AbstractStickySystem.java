package net.stickycode.bootstrap;

public abstract class AbstractStickySystem implements StickySystem {

  @Override
  public String toString() {
    return getLabel();
  }
  
  @Override
  public abstract String getLabel();
  
  @Override
  public Package getPackage() {
    return getClass().getPackage();
  }

  /**
   * Start the system, validating and verifying the environment is ok.
   */
  @Override
  public void start() {
  }

  /**
   * Complete any existing activities but reject any new activities.
   * 
   * e.g. stop scheduled jobs
   */
  @Override
  public void pause() {
  }

  /**
   * Restart activities stopped by a pause
   */
  @Override
  public void unpause() {
  }

  /**
   * Complete any existing activities and shutdown down all resources.
   */
  @Override
  public void shutdown() {
  }

  /**
   * @return true if this subsystem uses the given system
   */
  @Override
  public boolean uses(StickySystem system) {
    return false;
  }

  /**
   * @return true if the this subsystem is used by the given system
   */
  @Override
  public boolean isUsedBy(StickySystem system) {
    return false;
  }

}
