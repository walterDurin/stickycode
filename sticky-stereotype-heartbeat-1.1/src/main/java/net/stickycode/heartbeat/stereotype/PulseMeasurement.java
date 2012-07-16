package net.stickycode.heartbeat.stereotype;

/**
 * A measurement of an application heartbeat is deliberately simple, to make sure usage is consistent.
 * 
 * Its either Normal, or Down which are both easy to describe, or in a critical failure scenario and will be dead shortly.
 */
public enum PulseMeasurement {

  /**
   * Heartbeat not measurable, system is down.
   */
  NoPulse,

  /**
   * Heartbeat is irregular and will fail shortly.
   */
  Critical,

  /**
   * Heartbeat is normal.
   */
  Normal;
}
