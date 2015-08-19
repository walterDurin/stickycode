package net.stickycode.bootstrap.metadata;

import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IgnoringUrlProcessor
    implements UrlProcessor {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Path file;

  public IgnoringUrlProcessor(Path path) {
    this.file = path;
  }

  @Override
  public void process() {
    log.debug("ignoring {}", file);
  }

}
