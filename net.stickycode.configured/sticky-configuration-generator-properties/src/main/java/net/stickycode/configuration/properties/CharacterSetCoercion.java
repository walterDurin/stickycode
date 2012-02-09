package net.stickycode.configuration.properties;

import java.nio.charset.Charset;

import net.stickycode.coercion.Coercion;
import net.stickycode.coercion.CoercionTarget;
import net.stickycode.stereotype.component.StickyExtension;

@StickyExtension
public class CharacterSetCoercion
    implements Coercion<Charset> {

  @Override
  public Charset coerce(CoercionTarget target, String characterSetName) {
    if (Charset.isSupported(characterSetName))
      return Charset.forName(characterSetName);

    throw new UnableToCoerceAsCharsetIsNotSupported(target, characterSetName);
  }

  @Override
  public boolean isApplicableTo(CoercionTarget target) {
    return Charset.class.isAssignableFrom(target.getType());
  }

  @Override
  public boolean hasDefaultValue() {
    return true;
  }

  @Override
  public Charset getDefaultValue(CoercionTarget target) {
    return Charset.defaultCharset();
  }

}
