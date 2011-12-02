package net.stickycode.metadata;

import static org.fest.assertions.Assertions.assertThat;
import net.stickycode.stereotype.StickyFramework;

import org.junit.Test;

public class ReflectiveTypeMetaAnnotationResolverTest {

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
  public void metaAnnotated() {
    assertThat(AnnotatedTypeMarker.class.isAnnotationPresent(TypeMarker.class)).isTrue();
    assertThat(type(MetaAnnotatedContract.class).metaAnnotatedWith(StickyFramework.class)).isFalse();
    assertThat(type(MetaAnnotatedContract.class).metaAnnotatedWith(TypeMarker.class)).isTrue();
  }

  @Test
  public void metaAnnotatedContract() {
    assertThat(type(WithMetaAnnotatedContract.class).metaAnnotatedWith(StickyFramework.class)).isFalse();
    assertThat(type(WithMetaAnnotatedContract.class).metaAnnotatedWith(TypeMarker.class)).isTrue();
  }

  @Test
  public void metaDirect() {
    assertThat(type(WithDirectContractAnnotation.class).metaAnnotatedWith(StickyFramework.class)).isFalse();
    assertThat(type(WithDirectContractAnnotation.class).metaAnnotatedWith(TypeMarker.class)).isTrue();
  }

  @Test
  public void metaInheritedinheritedContract() {
    assertThat(type(WithInheritedMetaAnnotatedContract.class).metaAnnotatedWith(StickyFramework.class)).isFalse();
    assertThat(type(WithInheritedMetaAnnotatedContract.class).metaAnnotatedWith(TypeMarker.class)).isTrue();
  }

  private MetadataResolver type(Class<?> type) {
    return new ReflectiveTypeMetadataResolver(type);
  }
}
