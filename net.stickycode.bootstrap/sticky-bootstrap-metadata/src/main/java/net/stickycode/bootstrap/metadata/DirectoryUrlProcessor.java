package net.stickycode.bootstrap.metadata;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;

import org.objectweb.asm.ClassReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DirectoryUrlProcessor
    implements UrlProcessor {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Path baseDirectory;

  private ComponentRegister components;

  private PackageFilter filter;

  public DirectoryUrlProcessor(Path path, ComponentRegister register, PackageFilter filter) {
    this.baseDirectory = path;
    this.components = register;
    this.filter = filter;
  }

  @Override
  public void process() {
    try {
      log.debug("scanning directory: {}", baseDirectory.toString());
      processPath(baseDirectory);
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  private void processPath(Path path) throws IOException {
    Files.walkFileTree(path, new FileVisitor<Path>() {

      @Override
      public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
      }

      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        log.debug(file.toString());
        if (filter.includes(path.relativize(file))) {
          processClass(Files.newInputStream(file, StandardOpenOption.READ));
        }

        return FileVisitResult.CONTINUE;
      }

      @Override
      public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
      }

      @Override
      public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
      }
    });
  }

  private void processClass(InputStream in) {
    try {
      ClassReader reader = new ClassReader(in);
      ComponentVisitor visitor = new ComponentVisitor();
      reader.accept(visitor, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
      if (visitor.hasDefinition())
        components.register(visitor.getDefinition());
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
