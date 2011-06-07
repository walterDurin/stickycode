package net.stickycode.resource;

import java.io.File;
import java.io.InputStream;


public interface Resource {

  boolean canLoad();

  InputStream getSource();

  File toFile();

}
