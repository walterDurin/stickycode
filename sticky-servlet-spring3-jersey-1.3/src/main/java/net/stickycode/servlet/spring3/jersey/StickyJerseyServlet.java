package net.stickycode.servlet.spring3.jersey;

import javax.servlet.annotation.WebServlet;

import com.sun.jersey.spi.spring.container.servlet.SpringServlet;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/rs/*")
public class StickyJerseyServlet
    extends SpringServlet {

}
