package net.stickycode.stile.artifact.xml.v1;

import javax.xml.bind.JAXBException;

import net.stickycode.exception.PermanentException;


@SuppressWarnings("serial")
public class ParseFailureException
    extends PermanentException {

  public ParseFailureException(JAXBException e) {
    super(e, "failed to parse artifact");
  }

}
