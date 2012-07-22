package net.stickycode.metadata;

import static org.fest.assertions.Assertions.assertThat;
import net.stickycode.stereotype.StickyComponent;
import net.stickycode.stereotype.StickyFramework;
import net.stickycode.stereotype.StickyPlugin;
import net.stickycode.stereotype.component.StickyRepository;
import net.stickycode.stereotype.plugin.StickyExtension;

import org.junit.Test;

public class ReflectiveTypeMetaAnnotationResolverTest {

  @StickyRepository
  public interface MetaAnnotatedContract {
  }

  public class WithMetaAnnotatedContract
      implements MetaAnnotatedContract {
  }

  public class WithInheritedMetaAnnotatedContract
      extends WithMetaAnnotatedContract {
  }

  @StickyRepository
  public class WithDirectContractAnnotation {
  }

  @Test
  public void metaAnnotated() {
    assertThat(StickyRepository.class.isAnnotationPresent(StickyComponent.class)).isTrue();
    assertThat(type(MetaAnnotatedContract.class).metaAnnotatedWith(StickyFramework.class)).isFalse();
    assertThat(type(MetaAnnotatedContract.class).metaAnnotatedWith(StickyComponent.class)).isTrue();
  }

  @Test
  public void metaAnnotatedContract() {
    assertThat(type(WithMetaAnnotatedContract.class).metaAnnotatedWith(StickyFramework.class)).isFalse();
    assertThat(type(WithMetaAnnotatedContract.class).metaAnnotatedWith(StickyPlugin.class)).isFalse();
    assertThat(type(WithMetaAnnotatedContract.class).metaAnnotatedWith(StickyExtension.class)).isFalse();
    assertThat(type(WithMetaAnnotatedContract.class).metaAnnotatedWith(StickyComponent.class)).isTrue();
  }

  @Test
  public void metaDirect() {
    assertThat(type(WithDirectContractAnnotation.class).metaAnnotatedWith(StickyExtension.class)).isFalse();
    assertThat(type(WithDirectContractAnnotation.class).metaAnnotatedWith(StickyPlugin.class)).isFalse();
    assertThat(type(WithDirectContractAnnotation.class).metaAnnotatedWith(StickyComponent.class)).isTrue();
  }

  @Test
  public void metaInheritedinheritedContract() {
    assertThat(type(WithInheritedMetaAnnotatedContract.class).metaAnnotatedWith(StickyFramework.class)).isFalse();
    assertThat(type(WithInheritedMetaAnnotatedContract.class).metaAnnotatedWith(StickyPlugin.class)).isFalse();
    assertThat(type(WithInheritedMetaAnnotatedContract.class).metaAnnotatedWith(StickyExtension.class)).isFalse();
    assertThat(type(WithInheritedMetaAnnotatedContract.class).metaAnnotatedWith(StickyComponent.class)).isTrue();
  }

  private MetadataResolver type(Class<?> type) {
    return new ReflectiveTypeMetadataResolver(type);
  }
}
