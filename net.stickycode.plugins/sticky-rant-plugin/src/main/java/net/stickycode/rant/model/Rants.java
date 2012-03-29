package net.stickycode.rant.model;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Rants
    implements Iterable<Rant> {

  @XmlElements(@XmlElement(name="defect", type = Defect.class))
  private List<Rant> rants;

  @Override
  public Iterator<Rant> iterator() {
    if (rants == null)
      return Collections.<Rant> emptyList().iterator();

    return rants.iterator();
  }

  public Defects getDefects() {
    return null;
  }
}
