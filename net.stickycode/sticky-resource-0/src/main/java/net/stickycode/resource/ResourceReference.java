package net.stickycode.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public interface ResourceReference {

  boolean canLoad();

  InputStream getSource();

  File toFile();

  RuntimeException decodeException(IOException e);

}
