package net.stickycode.coercion.target;

import java.lang.reflect.TypeVariable;

import net.stickycode.exception.PermanentException;

@SuppressWarnings("serial")
public class ParentCoercionTargetsAreRequiredToResolveTypeVariables
    extends PermanentException {

  public ParentCoercionTargetsAreRequiredToResolveTypeVariables(TypeVariable<?> genericType, Class<?> owner) {
    super(
        "To resolve type variable {} on {} a parent must be provided. "
            +
            "For example to resolve the type of 'field' on realValue then the CoercionTarget of realValue must be provided to the finder for 'field'\n"
            +
            "public static class OtherGeneric<T> {\n" +
            "\n" +
            "}\n" +
            "\n" +
            "public static class Generic<T> {\n" +
            "  \n" +
            "  OtherGeneric<T> field;\n" +
            "}\n" +
            "\n" +
            "Generic<Float> realValue = new Generic<Float>();\n",
        genericType, owner);
  }
}
