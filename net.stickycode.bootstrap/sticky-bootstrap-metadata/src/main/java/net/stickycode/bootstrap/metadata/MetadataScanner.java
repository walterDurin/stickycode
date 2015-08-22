package net.stickycode.bootstrap.metadata;

import static java.nio.file.Files.isDirectory;
import static java.nio.file.Files.isReadable;

import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetadataScanner {

  private Logger log = LoggerFactory.getLogger(getClass());

  private List<URL> classpath;

  private ComponentRegister register;

  public MetadataScanner(ComponentRegister register) {
    super();
    this.register = register;
  }

  private List<String> javaJars = Arrays.asList(
      "sunec.jar", "sunpkcs11.jar", "localedata.jar", "zipfs.jar", "jfxrt.jar",
      "nashorn.jar", "cldrdata.jar", "dnsns.jar", "sunjce_provider.jar");


  public void deriveClasspath() {
    List<URL> classpath = new ArrayList<>();
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    while (loader != null) {
      if (loader instanceof URLClassLoader) {
        URL[] urls = ((URLClassLoader) loader).getURLs();
        Collections.addAll(classpath, urls);
      }
      loader = loader.getParent();
    }
    log.debug(System.getProperty("java.class.path"));

    this.classpath = classpath;
    log.debug("scanning {}", classpath);
  }

  public void scan(PackageFilter filter) {
    deriveClasspath();
    for (URL url : classpath) {
      try {
        UrlProcessor processor = processUrl(url, filter);
        processor.process();
      }
      catch (URISyntaxException e) {
        throw new RuntimeException(e);
      }

    }
  }

  private UrlProcessor processUrl(URL url, PackageFilter filter)
      throws URISyntaxException {
    if (url.getProtocol().equals("file")) {
      return processFile(url, filter);
    }

    return new UnsupportedUrlProcessor(url);
  }

  private UrlProcessor processFile(URL url, PackageFilter filter)
      throws URISyntaxException {
    Path path = Paths.get(url.toURI());
    if (!isReadable(path))
      return new IgnoringUrlProcessor(path);

    if (isDirectory(path))
      return new DirectoryUrlProcessor(path, register, filter);

    if (javaJars.contains(url.getPath()))
      return new IgnoringUrlProcessor(path);

    if (url.getFile().endsWith(".jar"))
      return new JarFileUrlProceesor(path, register, filter);

    return new UnsupportedUrlProcessor(url);
  }



}
