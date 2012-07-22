package net.stickycode.metadata;

import static org.fest.assertions.Assertions.assertThat;
import net.stickycode.stereotype.StickyComponent;
import net.stickycode.stereotype.StickyFramework;
import net.stickycode.stereotype.StickyPlugin;
import net.stickycode.stereotype.component.StickyRepository;
import net.stickycode.stereotype.plugin.StickyExtension;

import org.junit.Test;

public class ReflectiveTypeAnnotationResolverTest {

  @StickyComponent
  public interface AnnotatedContract {
  }

  public class WithAnnotatedContract
      implements AnnotatedContract {
  }

  public class WithInheritedAnnotatedContract
      extends WithAnnotatedContract {
  }

  @StickyComponent
  public class WithDirectContractAnnotation {
  }

  @Test
  public void annotated() {
    assertThat(type(AnnotatedContract.class).annotatedWith(StickyPlugin.class)).isFalse();
    assertThat(type(AnnotatedContract.class).annotatedWith(StickyExtension.class)).isFalse();
    assertThat(type(AnnotatedContract.class).annotatedWith(StickyRepository.class)).isFalse();
    assertThat(type(AnnotatedContract.class).annotatedWith(StickyComponent.class)).isTrue();
  }

  @Test
  public void contract() {
    assertThat(type(WithAnnotatedContract.class).annotatedWith(StickyPlugin.class)).isFalse();
    assertThat(type(WithAnnotatedContract.class).annotatedWith(StickyExtension.class)).isFalse();
    assertThat(type(WithAnnotatedContract.class).annotatedWith(StickyRepository.class)).isFalse();
    assertThat(type(WithAnnotatedContract.class).annotatedWith(StickyComponent.class)).isTrue();
  }

  @Test
  public void direct() {
    assertThat(type(WithDirectContractAnnotation.class).annotatedWith(StickyFramework.class)).isFalse();
    assertThat(type(WithDirectContractAnnotation.class).annotatedWith(StickyPlugin.class)).isFalse();
    assertThat(type(WithDirectContractAnnotation.class).annotatedWith(StickyExtension.class)).isFalse();
    assertThat(type(WithDirectContractAnnotation.class).annotatedWith(StickyComponent.class)).isTrue();
  }

  @Test
  public void inheritedContract() {
    assertThat(type(WithInheritedAnnotatedContract.class).annotatedWith(StickyFramework.class)).isFalse();
    assertThat(type(WithInheritedAnnotatedContract.class).annotatedWith(StickyPlugin.class)).isFalse();
    assertThat(type(WithInheritedAnnotatedContract.class).annotatedWith(StickyExtension.class)).isFalse();
    assertThat(type(WithInheritedAnnotatedContract.class).annotatedWith(StickyComponent.class)).isTrue();
  }

  private MetadataResolver type(Class<?> type) {
    return new ReflectiveTypeMetadataResolver(type);
  }
}
