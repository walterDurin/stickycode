package net.stickycode.stereotype.primitive;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataVolume {

  private Pattern pattern = Pattern.compile("^([0-9]+) *([mgktpMGKTP]?)i?([bB]?(?:it|yte)?)$");

  private Long valueInBytes;

  public Long getValueInBytes() {
    return valueInBytes;
  }

  public DataVolume(Long valueInBytes) {
    this.valueInBytes = valueInBytes;
  }

  public DataVolume(String value) {
    this.valueInBytes = parse(value);
  }

  private Long parse(String value) {
    Matcher matched = pattern.matcher(value);
    if (matched.matches()) {
      long multiplier = multiplier(matched.group(2));
      long dividend = dividend(matched.group(3));
      String group = matched.group(1);
      return Long.parseLong(group) * multiplier / dividend;
    }

    throw new DataVolumesMustBeNaturalNumbersException(value);
  }

  /**
   * small b for bits http://en.wikipedia.org/wiki/Kilobit
   */
  private long dividend(String group) {
    if ("b".equals(group))
      return 8;

    if ("bit".equalsIgnoreCase(group))
      return 8;

    return 1;
  }

  /**
   * So the standard is caps for 1024 aka binary and lowercase for decimal i.e. 1000
   * 
   * http://en.wikipedia.org/wiki/Kilobyte
   * http://en.wikipedia.org/wiki/Kilobit
   * 
   */
  private long multiplier(String group) {
    if ("k".equals(group))
      return 1000L;

    if ("K".equals(group))
      return 1024L;

    if ("m".equals(group))
      return 1000L * 1000L;

    if ("M".equals(group))
      return 1024L * 1024L;

    if ("g".equals(group))
      return 1000L * 1000L * 1000L;

    if ("G".equals(group))
      return 1024L * 1024L * 1024L;

    if ("t".equals(group))
      return 1000L * 1000L * 1000L * 1000L;

    if ("T".equals(group))
      return 1024L * 1024L * 1024L * 1024L;

    if ("p".equals(group))
      return 1000L * 1000L * 1000L * 1000L * 1000L;

    if ("P".equals(group))
      return 1024L * 1024L * 1024L * 1024L * 1024L;

    return 1;
  }

}
