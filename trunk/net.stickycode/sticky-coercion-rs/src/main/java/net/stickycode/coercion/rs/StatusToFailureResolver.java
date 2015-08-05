package net.stickycode.coercion.rs;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import net.stickycode.reflector.Fields;

import org.apache.cxf.jaxrs.client.ResponseExceptionMapper;
import org.apache.cxf.jaxrs.model.OperationResourceInfo;
import org.apache.cxf.message.Message;

@Provider
public class StatusToFailureResolver
    implements ResponseExceptionMapper<Throwable> {

  @Override
  public Throwable fromResponse(Response r) {
    Message message = Fields.get(r, Fields.find(r.getClass(), "responseMessage"));
    OperationResourceInfo operation = message.getContent(OperationResourceInfo.class);
    FailureMetadata failures = new FailureMetadata(operation.getAnnotatedMethod().getExceptionTypes());
    return failures.resolve(new FailureClassificationMapper().resolveClassification(r.getStatus()));
  }

}
