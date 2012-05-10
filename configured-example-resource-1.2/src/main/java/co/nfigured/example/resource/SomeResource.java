package co.nfigured.example.resource;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SomeResource {

  @XmlElement
  private String description;

  public String getDescription() {
    return description;
  }

}
