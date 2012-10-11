package co.nfigured.example.spring3.rs;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Name {

  @XmlElement
  private String value;

  public Name(String value) {
    super();
    this.value = value;
  }

  public Name() {
    super();

  }

  public String getValue() {
    return value;
  }

}
