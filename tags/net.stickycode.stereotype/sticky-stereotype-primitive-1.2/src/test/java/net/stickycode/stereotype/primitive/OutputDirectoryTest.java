package net.stickycode.stereotype.primitive;

import java.io.File;

import org.junit.Test;

public class OutputDirectoryTest {

  @Test(expected = NullPointerException.class)
  public void nullFails() {
    new OutputDirectory(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void empty() {
    new OutputDirectory("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void whitespace() {
    new OutputDirectory("  ");
  }

  @Test(expected = OutputDirectoryIsNotModifiableException.class)
  public void notWritable() {
    new OutputDirectory("/bin");
  }

  @Test(expected = OutputDirectoryCannotBeCreatedException.class)
  public void notCreatable() {
    new OutputDirectory("/xxx/yyy");
  }

  @Test(expected = OutputDirectoryCannotBeAFileException.class)
  public void cannotBeAfile() {
    new OutputDirectory("/bin/bash");
  }
  
  @Test
  public void needToCreate() {
    File directory = new OutputDirectory("/tmp/" + System.currentTimeMillis()).getDirectory();
    directory.delete();
  }

}
