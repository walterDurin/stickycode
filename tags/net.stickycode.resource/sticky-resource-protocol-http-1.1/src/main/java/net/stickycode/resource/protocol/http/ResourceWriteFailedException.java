package net.stickycode.resource.protocol.http;

import org.apache.http.StatusLine;

import net.stickycode.exception.TransientException;

@SuppressWarnings("serial")
public class ResourceWriteFailedException
    extends TransientException {

  public ResourceWriteFailedException(StatusLine statusLine) {
    super("Failed to write resource, got {}", statusLine);
  }

}
