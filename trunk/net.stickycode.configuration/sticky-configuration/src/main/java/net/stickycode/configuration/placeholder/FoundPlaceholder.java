package net.stickycode.configuration.placeholder;

import net.stickycode.configuration.LookupValues;

public class FoundPlaceholder
    implements Placeholder {

  private LookupValues value;

  private int start;

  private int end;

  /** The end of the placeholder after replacement.
   * e.g.
   * <ul>
   * <li>"a ${key} was here" would have start 3 and end 8</li>
   * <li>if key = value</li>
   * <li>endAfterReplacement would be 10</li>
   * </ul>
   * */
  private int endAfterReplacement;

  public FoundPlaceholder(LookupValues value, int indexOfStart, int indexOfClose) {
    this.value = value;
    this.start = indexOfStart;
    this.end = indexOfClose;
  }

  @Override
  public boolean notFound() {
    return false;
  }

  @Override
  public String getKey() {
    return value.substring(start + 2, end);
  }

  @Override
  public LookupValues replace(LookupValues lookup) {
    if (end == value.length())
      return lookup;

    endAfterReplacement = start + lookup.length();
    if (start == 0)
      return lookup + value.substring(end + 1);

    return value.substring(0, start) + lookup + value.substring(end + 1);
  }

  @Override
  public boolean contains(Placeholder placeholder) {
    if (placeholder.getStart() >= start)
      if (placeholder.getEnd() <= endAfterReplacement)
        return placeholder.getKey().equals(getKey());

    return false;
  }

  @Override
  public int getStart() {
    return start;
  }

  @Override
  public int getEnd() {
    return end;
  }

}
