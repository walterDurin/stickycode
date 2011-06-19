package net.stickycode.stile.version.component;

public abstract class AbstractVersionComponent 
implements Comparable<AbstractVersionComponent>{

//	protected abstract T getValue();
  abstract public String toString();

  @Override
  public int compareTo(AbstractVersionComponent o) {
    int compare = getOrdering().compareTo(o.getOrdering());
    if (compare != 0)
      return compare;
    
    return 0;
  }

  abstract public ComponentOrdering getOrdering();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + valueHashCode();
		return result;
	}

	abstract protected int valueHashCode();

  @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		return valueEquals(obj);
	}

	abstract protected boolean valueEquals(Object other);

}
