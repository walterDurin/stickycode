package net.stickycode.rs.coercion.jersey.value.v1;

public class ValueObject {

  private Integer id;

  private String label;

  public ValueObject() {
    super();
  }

  public ValueObject(String label) {
    super();
    this.label = label;
  }

  public void withId(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public String getLabel() {
    return label;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((label == null) ? 0 : label.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ValueObject other = (ValueObject) obj;
    if (label == null) {
      if (other.label != null)
        return false;
    }
    else
      if (!label.equals(other.label))
        return false;
    return true;
  }

}
