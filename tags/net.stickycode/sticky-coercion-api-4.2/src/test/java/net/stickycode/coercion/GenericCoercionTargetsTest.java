package net.stickycode.coercion;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;

import net.stickycode.coercion.target.CoercionTargets;
import net.stickycode.coercion.target.ParentCoercionTargetsAreRequiredToResolveTypeVariables;
import net.stickycode.reflector.Fields;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class GenericCoercionTargetsTest {

  @Rule
  public TestName name = new TestName();

  public static class OtherGeneric<T> {

  }

  public static class Generic<T> {

    OtherGeneric<T> field;
  }

  Generic<Float> real;

  Generic<Float> realValue = new Generic<Float>();

  OtherGeneric<Float> direct;

  OtherGeneric<?> wild;

  @SuppressWarnings("rawtypes")
  OtherGeneric raw;

  @Test
  public void real() {
    assertThat(fieldTarget().getType()).isEqualTo(Generic.class);
    assertThat(fieldTarget().hasComponents()).isTrue();
    assertThat(fieldTarget().getComponentCoercionTypes()).contains(CoercionTargets.find(Float.class));
  }

  @Test
  public void realValue() {
    assertThat(fieldTarget().getType()).isEqualTo(Generic.class);
    assertThat(fieldTarget().hasComponents()).isTrue();
    assertThat(fieldTarget().getComponentCoercionTypes()).contains(CoercionTargets.find(Float.class));

  }

  @Test(expected = ParentCoercionTargetsAreRequiredToResolveTypeVariables.class)
  public void parentsAreNeedForTypeVariables() {
    Field find = Fields.find(Generic.class, "field");
    CoercionTarget nested = CoercionTargets.find(find);
    assertThat(nested.getType()).isEqualTo(OtherGeneric.class);
    assertThat(nested.getComponentCoercionTypes()).contains(CoercionTargets.find(Float.class));
  }

  @Test
  public void typeVariable() {
    Field find = Fields.find(Generic.class, "field");
    CoercionTarget nested = CoercionTargets.find(find, CoercionTargets.find(Fields.find(getClass(), "realValue")));
    assertThat(nested.getType()).isEqualTo(OtherGeneric.class);
    assertThat(nested.getComponentCoercionTypes()).contains(CoercionTargets.find(Float.class));
  }

  @Test
  public void direct() {
    assertThat(fieldTarget().getType()).isEqualTo(OtherGeneric.class);
    assertThat(fieldTarget().hasComponents()).isTrue();
    assertThat(fieldTarget().getComponentCoercionTypes()).contains(CoercionTargets.find(Float.class));
  }

  @Test
  @Ignore("Can't figure a purpose for wild at he moment, so ignoring")
  public void wild() {
    assertThat(fieldTarget().getType()).isEqualTo(OtherGeneric.class);
    assertThat(fieldTarget().hasComponents()).isTrue();
    assertThat(fieldTarget().getComponentCoercionTypes()).isEmpty();
  }

  @Test
  @Ignore("Can't figure a purpose for raw at he moment, so ignoring")
  public void raw() {
    assertThat(fieldTarget().getType()).isEqualTo(OtherGeneric.class);
    assertThat(fieldTarget().hasComponents()).isTrue();

  }

  private CoercionTarget fieldTarget() {
    return CoercionTargets.find(Fields.find(getClass(), name.getMethodName()));
  }
}
