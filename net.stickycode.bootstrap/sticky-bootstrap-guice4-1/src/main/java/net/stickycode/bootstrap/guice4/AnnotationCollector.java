package net.stickycode.bootstrap.guice4;
/**
 * Copyright (C) 2010 Daniel Manzke <daniel.manzke@googlemail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import de.devsurf.injection.guice.scanner.features.ScannerFeature;

/**
 * Visitor implementation to collect field annotation information from class.
 *
 */
public class AnnotationCollector
    extends ClassVisitor {

  public static final int ASM_FLAGS = ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG
      | ClassReader.SKIP_FRAMES;

  protected Logger _logger = Logger.getLogger(AnnotationCollector.class.getName());

  protected String _name;

  protected Class<?> _class;

  protected boolean _ignore;

  protected Map<String, Annotation> _annotations;

  protected List<ScannerFeature> _features;

  public static final AnnotationVisitor EMPTY_ANNOTATION_VISITOR = new AnnotationVisitor(Opcodes.ASM4, null) {

    @Override
    public void visitEnum(String name, String desc, String value) {
    }

    @Override
    public void visitEnd() {
    }

    @Override
    public AnnotationVisitor visitArray(String name) {
      return this;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String name, String desc) {
      return this;
    }

    @Override
    public void visit(String name, Object value) {
    }
  };

  public AnnotationCollector() {
    super(Opcodes.ASM4);
    _features = new LinkedList<ScannerFeature>();
    _annotations = new HashMap<String, Annotation>();
  }

  public void addScannerFeature(ScannerFeature listener) {
    _features.add(listener);
  }

  public void removerScannerFeature(ScannerFeature listener) {
    _features.remove(listener);
  }

  public List<ScannerFeature> getScannerFeatures() {
    return new ArrayList<ScannerFeature>(_features);
  }

  public void destroy() {
    _annotations.clear();
    _annotations = null;
    _class = null;
    _features.clear();
    _features = null;
  }

  @Override
  public void visit(int version, int access, String name, String signature, String superName,
      String[] interfaces) {
    _name = name.replace('/', '.');
    for (String interf : interfaces) {
      if (interf.equals("java/lang/annotation/Annotation")) {
        _ignore = true;
        return;
      }
    }
  }

  @SuppressWarnings("unchecked")
  public AnnotationVisitor visitAnnotation(String sig, boolean visible) {
    if (_ignore) {
      return EMPTY_ANNOTATION_VISITOR;
    }
    String annotationClassStr = sig.replace('/', '.').substring(1, sig.length() - 1);
    if (_class == null) {
      try {
        _class = getClass().getClassLoader().loadClass(_name);
      }
      catch (ClassNotFoundException e) {
        _logger.log(Level.WARNING,
            "Failure while visitAnnotation. Class could not be loaded.", e);
        return EMPTY_ANNOTATION_VISITOR;
      }
    }
    try {
      Class<Annotation> annotationClass = (Class<Annotation>) getClass().getClassLoader()
          .loadClass(annotationClassStr);
      Annotation annotation = _class.getAnnotation(annotationClass);
      _annotations.put(annotationClassStr, annotation);
    }
    catch (ClassNotFoundException e) {
      e.printStackTrace();
      _logger.log(Level.WARNING, "Failure while visitAnnotation. Class could not be loaded.",
          e);
    }

    return EMPTY_ANNOTATION_VISITOR;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void visitEnd() {
    if (!_ignore && _annotations.size() > 0 && !_annotations.containsKey("javax.enterprise.inject.Alternative")) {
      for (ScannerFeature listener : _features) {
        listener.found((Class<Object>) _class, _annotations);
      }
    }
    _name = null;
    _class = null;
    _ignore = false;
    _annotations.clear();
  }

  @Override
  public void visitAttribute(Attribute attr) {
  }

  @Override
  public FieldVisitor visitField(int access, String name, String desc, String signature,
      Object value) {
    return null;
  }

  @Override
  public void visitInnerClass(String name, String outerName, String innerName, int access) {
  }

  @Override
  public MethodVisitor visitMethod(int access, String name, String desc, String signature,
      String[] exceptions) {
    return null;
  }

  @Override
  public void visitOuterClass(String owner, String name, String desc) {
  }

  @Override
  public void visitSource(String source, String debug) {
  }
}
