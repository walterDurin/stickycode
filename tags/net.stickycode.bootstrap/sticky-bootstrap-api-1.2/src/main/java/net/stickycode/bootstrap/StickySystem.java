package net.stickycode.bootstrap;

public abstract class StickySystem {

  @Override
  public String toString() {
    return getLabel();
  }
  
  public abstract String getLabel();
  
  public abstract Package getPackage();

  /**
   * Start the system, validating and verifying the environment is ok.
   */
  public void start() {
  }

  /**
   * Complete any existing activities but reject any new activities.
   * 
   * e.g. stop scheduled jobs
   */
  public void pause() {
  }

  /**
   * Restart activities stopped by a pause
   */
  public void unpause() {
  }

  /**
   * Complete any existing activities and shutdown down all resources.
   */
  public void shutdown() {
  }

  /**
   * @return true if this subsystem uses the given system
   */
  public boolean uses(StickySystem system) {
    return false;
  }

  /**
   * @return true if the this subsystem is used by the given system
   */
  public boolean isUsedBy(StickySystem system) {
    return false;
  }

}
