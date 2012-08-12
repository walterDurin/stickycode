package net.stickycode.ws.testutils.v1;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface Example {

  @WebMethod
  Boolean invoke();

}
