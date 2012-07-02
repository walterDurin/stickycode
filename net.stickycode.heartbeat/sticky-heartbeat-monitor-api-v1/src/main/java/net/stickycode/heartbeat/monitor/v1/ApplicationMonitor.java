package net.stickycode.heartbeat.monitor.v1;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface ApplicationMonitor {

  @WebMethod(operationName = "isAlive")
  @WebResult(name = "alive")
  boolean isAlive();

  @WebMethod(operationName = "measure")
  @WebResult(name = "gauges")
  List<Gauge> measure();

  @WebMethod(operationName = "history")
  @WebResult(name = "history")
  List<Gauge> history(
      @WebParam(name = "startInSeconds") Integer startOffsetInSeconds,
      @WebParam(name = "endInSeconds") Integer endOffsetInSeconds);
}
