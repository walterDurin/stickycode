package net.stickycode.ws.testutils.v1;

import net.stickycode.stereotype.StickyComponent;

import org.apache.cxf.annotations.SchemaValidation;

@SchemaValidation
@StickyComponent
public class RealExample
    implements Example {

  @Override
  public Boolean invoke() {
    return true;
  }

}
