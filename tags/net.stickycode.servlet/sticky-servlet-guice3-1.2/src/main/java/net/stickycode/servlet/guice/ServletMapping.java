package net.stickycode.servlet.guice;

import javax.servlet.http.HttpServlet;

public class ServletMapping {

  private String path;

  private Class<? extends HttpServlet> servlet;

  public ServletMapping(String path, Class<? extends HttpServlet> servlet) {
    this.path = path;
    this.servlet = servlet;
  }

  public String getPath() {
    return path;
  }

  public Class<? extends HttpServlet> getServlet() {
    return servlet;
  }

}
