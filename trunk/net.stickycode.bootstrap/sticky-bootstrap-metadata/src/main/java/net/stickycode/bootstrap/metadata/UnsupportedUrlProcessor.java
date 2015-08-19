package net.stickycode.bootstrap.metadata;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnsupportedUrlProcessor
    implements UrlProcessor {

  private Logger log = LoggerFactory.getLogger(getClass());

  private URL url;

  public UnsupportedUrlProcessor(URL url) {
    this.url = url;
  }

  @Override
  public void process() {
    log.warn("ignoring {}", url);
  }
}
