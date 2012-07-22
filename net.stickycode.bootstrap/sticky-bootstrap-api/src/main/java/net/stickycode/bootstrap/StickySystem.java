package net.stickycode.bootstrap;

public interface StickySystem {

  String getLabel();

  Package getPackage();

  /**
   * Start the system, validating and verifying the environment is ok.
   */
  void start();

  /**
   * Complete any existing activities but reject any new activities.
   * 
   * e.g. stop scheduled jobs
   */
  void pause();

  /**
   * Restart activities stopped by a pause
   */
  void unpause();

  /**
   * Complete any existing activities and shutdown down all resources.
   */
  void shutdown();

  /**
   * @return true if this subsystem uses the given system
   */
  boolean uses(StickySystem system);

  /**
   * @return true if the this subsystem is used by the given system
   */
  boolean isUsedBy(StickySystem system);

}
