package net.stickycode.stereotype.failure.resolver;

import java.util.Iterator;

public class FragmentIterator
    implements Iterator<Fragment> {

  private String message;

  private int index;

  private int nextQuote;

  private int nextParameter;

  public FragmentIterator(String message) {
    this.message = message;
    nextParameter();
    nextQuote();
  }

  private void nextQuote() {
    nextQuote = message.indexOf("''", index);
  }

  private void nextParameter() {
    nextParameter = message.indexOf("{}", index);
  }

  @Override
  public boolean hasNext() {
    if (index >= message.length())
      return false;

    return true;
  }

  @Override
  public Fragment next() {
    if (index == nextParameter) {
      updateIndex(nextParameter + 2);
      nextParameter();
      return new ParameterFragment();
    }

    if (index == nextQuote) {
      updateIndex(nextQuote + 2);
      nextQuote();
      return new QuoteFragment();
    }

    if (nextParameter == -1) {
      if (index < nextQuote) {
        return text(nextQuote);
      }
      return tail();
    }

    if (nextQuote == -1) {
      return text(nextParameter);
    }

    if (nextQuote < nextParameter) {
      return text(nextQuote);
    }

    return text(nextParameter);
  }

  private Fragment text(int nextIndex) {
    TextFragment textFragment = new TextFragment(message.substring(index, nextIndex));
    updateIndex(nextIndex);
    return textFragment;
  }

  private void updateIndex(int processed) {
    this.index = processed;
  }

  private Fragment tail() {
    TextFragment textFragment = new TextFragment(message.substring(index));
    index = message.length();
    return textFragment;
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

}
