package net.stickycode.configured.content;

import java.lang.reflect.Field;
import java.util.List;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.configuration.ResolvedConfiguration;
import net.stickycode.reflector.Fields;
import net.stickycode.stereotype.content.Content;

public class LocalisedContentField
    implements LocalisedAttribute {

  private final Object bean;

  private final Field field;

  public LocalisedContentField(Object bean, Field field) {
    this.bean = bean;
    this.field = field;
  }

  @Override
  public Class<?> getType() {
    return Content.class;
  }

  @Override
  public String getName() {
    return field.getName();
  }

  @Override
  public void setValue(String value) {
    setValue(new FixedContent(value));
  }

  @Override
  public void setValue(Content content) {
    Fields.set(bean, field, content);
  }

  @Override
  public String getValue() {
    Content value = Fields.get(bean, field);
    return value.get();
  }

  @Override
  public void resolvedWith(ResolvedConfiguration resolved) {
  }

  @Override
  public CoercionTarget getCoercionTarget() {
    return null;
  }

  @Override
  public List<String> join(String delimeter) {
    return null;
  }

}
