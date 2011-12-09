package net.stickycode.metadata;

import net.stickycode.stereotype.StickyComponent;

import org.junit.Test;

public class ReflectiveTypeMetadataResolverTest {

  @StickyComponent
  public interface MetaAnnotatedContract {
  }

  public class WithMetaAnnotatedContract
      implements MetaAnnotatedContract {
  }

  public class WithInheritedMetaAnnotatedContract
      extends WithMetaAnnotatedContract {
  }

  @StickyComponent
  public class WithDirectContractAnnotation {
  }

  @Test
  public void fluent() {
    new ReflectiveMetadataResolverRegistry().is(MetaAnnotatedContract.class).metaAnnotatedWith(StickyComponent.class);
  }
}
