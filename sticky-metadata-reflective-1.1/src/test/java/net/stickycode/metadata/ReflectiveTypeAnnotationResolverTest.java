package net.stickycode.metadata;

import static org.fest.assertions.Assertions.assertThat;
import net.stickycode.stereotype.StickyFramework;

import org.junit.Test;

public class ReflectiveTypeAnnotationResolverTest {

  @TypeMarker
  public interface AnnotatedContract {
  }

  public class WithAnnotatedContract
      implements AnnotatedContract {
  }

  public class WithInheritedAnnotatedContract
      extends WithAnnotatedContract {
  }

  @TypeMarker
  public class WithDirectContractAnnotation {
  }

  @Test
  public void annotated() {
    assertThat(type(AnnotatedContract.class).metaAnnotatedWith(StickyFramework.class)).isFalse();
    assertThat(type(AnnotatedContract.class).metaAnnotatedWith(TypeMarker.class)).isTrue();
  }

  @Test
  public void contract() {
    assertThat(type(WithAnnotatedContract.class).metaAnnotatedWith(StickyFramework.class)).isFalse();
    assertThat(type(WithAnnotatedContract.class).metaAnnotatedWith(TypeMarker.class)).isTrue();
  }

  @Test
  public void direct() {
    assertThat(type(WithDirectContractAnnotation.class).metaAnnotatedWith(StickyFramework.class)).isFalse();
    assertThat(type(WithDirectContractAnnotation.class).metaAnnotatedWith(TypeMarker.class)).isTrue();
  }

  @Test
  public void inheritedContract() {
    assertThat(type(WithInheritedAnnotatedContract.class).metaAnnotatedWith(StickyFramework.class)).isFalse();
    assertThat(type(WithInheritedAnnotatedContract.class).metaAnnotatedWith(TypeMarker.class)).isTrue();
  }

  private MetadataResolver type(Class<?> type) {
    return new ReflectiveTypeMetadataResolver(type);
  }
}
