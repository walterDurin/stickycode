package net.stickycode.coercion.resource;

import static org.fest.assertions.Assertions.assertThat;

import javax.inject.Inject;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.coercion.target.CoercionTargets;
import net.stickycode.mockwire.MockwireConfigured;
import net.stickycode.mockwire.MockwireContainment;
import net.stickycode.mockwire.junit4.MockwireRunner;
import net.stickycode.reflector.Fields;
import net.stickycode.resource.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MockwireRunner.class)
@MockwireContainment
@MockwireConfigured({})
public class ResourceCoercionTest {

  private Resource<String> blah;

  @Inject
  private ResourceCoercion coercion;

  @Test
  public void defaultClasspath() {
    assertThat(coercion.coerce(target(), "stringResource.txt").get()).isEqualTo("hmm");
  }

  @Test
  public void loadedResource() {
    Resource<Object> actual = coercion.coerce(target(), "::stringResource.txt");
    assertThat(actual.get()).isEqualTo("hmm");
    assertThat(actual.get()).isSameAs(actual.get());
  }

  @Test
  public void classpathResource() {
    assertThat(coercion.coerce(target(), "::classpath://stringResource.txt").get()).isEqualTo("hmm");
  }

  @Test
  public void classpathWithSlashResource() {
    assertThat(coercion.coerce(target(), "::classpath:///net/stickycode/coercion/resource/stringResource.txt").get()).isEqualTo(
        "hmm");
  }

  // resource:type:protocol:uri
  @Test
  public void cachedHttpResource() {
    assertThat(coercion.coerce(target(), "::dummy://blah").get()).isEqualTo("hmm");
  }

  private CoercionTarget target() {
    return CoercionTargets.find(Fields.find(getClass(), "blah"));
  }

}
