package net.stickycode.xml.jaxb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * This error handler throws all errors so fast fails on problems
 */
class StrictErrorHandler
    implements ErrorHandler {

  private Logger log = LoggerFactory.getLogger(getClass());

  @Override
  public void warning(SAXParseException exception) throws SAXException {
    log.warn("Warning when processing xml", exception);
  }

  @Override
  public void fatalError(SAXParseException exception) throws SAXException {
    throw exception;
  }

  @Override
  public void error(SAXParseException exception) throws SAXException {
    throw exception;
  }
}
