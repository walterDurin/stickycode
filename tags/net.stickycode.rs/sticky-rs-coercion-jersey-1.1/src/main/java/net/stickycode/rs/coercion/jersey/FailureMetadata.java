package net.stickycode.rs.coercion.jersey;

import java.util.ArrayList;
import java.util.List;

import net.stickycode.stereotype.failure.Failure;
import net.stickycode.stereotype.failure.FailureClassification;

public class FailureMetadata {

  List<ExceptionMapping> mappings = new ArrayList<FailureMetadata.ExceptionMapping>();

  public class ExceptionMapping {

    private FailureClassification classification;

    private Class<?> type;

    public ExceptionMapping(Failure annotation, Class<?> type) {
      this.classification = annotation.value();
      this.type = type;
    }

    public boolean hasClassification(FailureClassification classification) {
      return this.classification.equals(classification);
    }

    public Throwable create() {
      try {
        return (Throwable) type.newInstance();
      }
      catch (InstantiationException e) {
        throw new RuntimeException(e);
      }
      catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }

  }

  public FailureMetadata(Class<?>... types) {
    for (Class<?> type : types) {
      Failure annotation = type.getAnnotation(Failure.class);
      if (annotation != null)
        mappings.add(new ExceptionMapping(annotation, type));
    }
  }

  public Throwable resolve(FailureClassification classification) {
    for (ExceptionMapping mapping : mappings) {
      if (mapping.hasClassification(classification))
        return mapping.create();
    }

    return new UnmappedClassificationFailure(classification);
  }
  
}
