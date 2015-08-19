package net.stickycode.bootstrap.metadata;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Collections;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.objectweb.asm.ClassReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JarFileUrlProceesor
    implements UrlProcessor {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Path path;

  private ComponentRegister register;

  private PackageFilter filter;

  public JarFileUrlProceesor(Path path, ComponentRegister register, PackageFilter filter) {
    this.path = path;
    this.register = register;
    this.filter = filter;
  }

  @Override
  public void process() {
    try {
      try (JarFile zip = new JarFile(path.toFile())) {
        Manifest manifest = zip.getManifest();
        log.info("manifest {}", manifest);
        processJarFile(zip);
      }
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void processJarFile(JarFile zip) {
    for (JarEntry entry : Collections.list(zip.entries())) {
      processEntry(zip, entry);
    }
  }

  private void processEntry(JarFile zip, JarEntry entry) {
    if (entry.isDirectory())
      return;

    if (filter.included(entry.getName()))
      processClass(getStream(zip, entry));
  }

  private InputStream getStream(JarFile zip, JarEntry entry) {
    try {
      return zip.getInputStream(entry);
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void processClass(InputStream in) {
    try {
      ClassReader reader = new ClassReader(in);
      ComponentVisitor visitor = new ComponentVisitor();
      reader.accept(visitor, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
      if (visitor.hasDefinition())
        register.register(visitor.getDefinition());
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
