package net.stickycode.metadata;

import org.junit.Test;

public class ReflectiveTypeMetadataResolverTest {

  @AnnotatedTypeMarker
  public interface MetaAnnotatedContract {
  }

  public class WithMetaAnnotatedContract
      implements MetaAnnotatedContract {
  }

  public class WithInheritedMetaAnnotatedContract
      extends WithMetaAnnotatedContract {
  }

  @AnnotatedTypeMarker
  public class WithDirectContractAnnotation {
  }

  @Test
  public void fluent() {
    new ReflectiveMetadataResolverRegistry().is(MetaAnnotatedContract.class).metaAnnotatedWith(TypeMarker.class);
  }
}
