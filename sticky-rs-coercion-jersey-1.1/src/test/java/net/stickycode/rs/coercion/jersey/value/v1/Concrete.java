package net.stickycode.rs.coercion.jersey.value.v1;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Concrete
    implements Contract {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private Map<Integer, ValueObject> values = new HashMap<Integer, ValueObject>();

  private AtomicInteger sequence = new AtomicInteger();

  @Override
  public ValueObject get(Integer id) {
    log.info("id {}", id);
    if (values.containsKey(id))
      return values.get(id);

    throw new WebApplicationException(Response.status(404).header("X-Failure-Code", "NotFoundException").build());
  }

  @Override
  public Integer create(ValueObject value)
      throws GatewayFailure {
    Integer id = sequence.incrementAndGet();
    log.info("create {}", id);
    values.put(id, value);
    return id;
  }

  @Override
  public void delete(Integer id)
      throws NotFoundFailure {
    
    log.info("delete {}", id);
    
    if (!values.containsKey(id))
      throw new WebApplicationException(Response.status(404).header("X-Failure-Code", "NotFoundException").build());

    values.remove(id);
  }

}
