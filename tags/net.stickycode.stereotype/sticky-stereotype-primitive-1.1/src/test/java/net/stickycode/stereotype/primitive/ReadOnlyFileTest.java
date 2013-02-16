package net.stickycode.stereotype.primitive;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class ReadOnlyFileTest {

  @Test(expected = NullPointerException.class)
  public void nullExcepts() {
    new ReadOnlyFile(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void blankExcepts() {
    new ReadOnlyFile("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void whitespaceExcepts() {
    new ReadOnlyFile(" ");
  }

  @Test(expected = ReadOnlyFileNotFoundException.class)
  public void notFound() {
    new ReadOnlyFile("/cant/see/it/precious");
  }

  @Test(expected = ReadOnlyFileIsModifiableException.class)
  public void writable()
      throws IOException {
    File file = File.createTempFile("precious", null);
    try {
      new ReadOnlyFile(file.getAbsolutePath());
    }
    finally {
      file.delete();
    }
  }

}
