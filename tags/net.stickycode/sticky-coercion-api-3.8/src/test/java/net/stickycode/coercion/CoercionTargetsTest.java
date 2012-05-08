package net.stickycode.coercion;

import static org.fest.assertions.Assertions.assertThat;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import net.stickycode.coercion.target.CoercionTargets;
import net.stickycode.coercion.target.CoercionTargetsDoesNotRecogniseTypeException;
import net.stickycode.reflector.Fields;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

@SuppressWarnings("unused")
public class CoercionTargetsTest {

  @Rule
  public TestName name = new TestName();

  public static class PlainOld {
  }

  private int[] intArray;

  private PlainOld[] plainOldArray;

  private PlainOld plainOldField;

  private List<PlainOld> plainOldList;

  private Map<Integer, PlainOld> plainOldMap;

  private List<PlainOld>[] plainOldLists;

  @Test
  public void plainOldType() {
    CoercionTarget target = CoercionTargets.find(PlainOld.class);
    assertThat(target.hasComponents()).isFalse();
    assertThat(target.isArray()).isFalse();
  }

  @Test
  public void plainOldField() {
    CoercionTarget target = fieldTarget();
    assertThat(target.hasComponents()).isFalse();
    assertThat(target.isArray()).isFalse();
  }

  @Test
  public void intArray() {
    CoercionTarget target = fieldTarget();
    assertThat(target.hasComponents()).isTrue();
    assertThat(target.isArray()).isTrue();
    assertThat(target.getComponentCoercionTypes()).hasSize(1);
  }

  @Test
  public void plainOldArray() {
    CoercionTarget target = fieldTarget();
    assertThat(target.hasComponents()).isTrue();
    assertThat(target.isArray()).isTrue();
    assertThat(target.getComponentCoercionTypes()).hasSize(1);
  }

  @Test
  public void plainOldList() {
    CoercionTarget target = fieldTarget();
    assertThat(target.hasComponents()).isTrue();
    assertThat(target.isArray()).isFalse();
    assertThat(target.getComponentCoercionTypes()).hasSize(1);
    assertThat(target.getComponentCoercionTypes()).containsOnly(CoercionTargets.find(PlainOld.class));
  }

  @Test
  public void plainOldMap() {
    CoercionTarget target = fieldTarget();
    assertThat(target.hasComponents()).isTrue();
    assertThat(target.isArray()).isFalse();
    assertThat(target.getComponentCoercionTypes()).hasSize(2);
    assertThat(target.getComponentCoercionTypes()).containsOnly(
        CoercionTargets.find(Integer.class),
        CoercionTargets.find(PlainOld.class));
  }

  @Test
  public void plainOldLists() {
    CoercionTarget target = fieldTarget();
    assertThat(target.hasComponents()).isTrue();
    assertThat(target.isArray()).isTrue();
    assertThat(target.getComponentCoercionTypes()).hasSize(1);
    assertThat(target.getComponentCoercionTypes()).containsOnly(
        CoercionTargets.find(Fields.find(getClass(), "plainOldList")));
  }

  @Test(expected = CoercionTargetsDoesNotRecogniseTypeException.class)
  public void unknownGenerics() {
    CoercionTargets.find(new Type() {
    }, null, "none");
  }

  private CoercionTarget fieldTarget() {
    return CoercionTargets.find(Fields.find(getClass(), name.getMethodName()));
  }
}
