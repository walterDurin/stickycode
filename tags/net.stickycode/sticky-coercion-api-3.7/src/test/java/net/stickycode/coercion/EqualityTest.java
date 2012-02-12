package net.stickycode.coercion;

import static org.fest.assertions.Assertions.assertThat;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import net.stickycode.coercion.target.CoercionTargets;
import net.stickycode.reflector.Fields;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class EqualityTest {

  @Rule
  public TestName name = new TestName();

  public static class PlainOld {
  }

  private PlainOld[] plainOldArray;

  private PlainOld plainOldField;

  private List<PlainOld> plainOldList;

  private Map<Integer, PlainOld> plainOldMap;

  private List<PlainOld>[] plainOldLists;

  @Test
  public void plainOldArray() {
    assertThat(target()).isEqualTo(target());
    checkNotEqualToOthers();
  }

  @Test
  public void plainOldList() {
    assertThat(target()).isEqualTo(target());
    checkNotEqualToOthers();
  }

  @Test
  public void plainOldLists() {
    assertThat(target()).isEqualTo(target());
    checkNotEqualToOthers();
  }

  @Test
  public void plainOldField() {
    assertThat(target()).isEqualTo(target());
    checkNotEqualToOthers();
  }

  @Test
  public void plainOldMap() {
    assertThat(target()).isEqualTo(target());
    checkNotEqualToOthers();
  }

  private CoercionTarget target(String name) {
    return CoercionTargets.find(Fields.find(getClass(), name));
  }

  private CoercionTarget target() {
    return target(name.getMethodName());
  }

  private void checkNotEqualToOthers() {
    for (Field f : getClass().getFields()) {
      if (!f.getName().equals(name.getMethodName()))
        assertThat(target()).isNotEqualTo(target(f.getName()));
    }
  }

}
