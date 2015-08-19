package net.stickycode.bootstrap.metadata;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class ComponentVisitor
    extends ClassVisitor {

  private ComponentDefinition definition;

  public ComponentVisitor() {
    super(Opcodes.ASM5);
  }

  @Override
  public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
    if (visible)
      definition().stereotype(desc);

    return super.visitAnnotation(desc, visible);
  }

  @Override
  public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
    definition().withName(name);
    super.visit(version, access, name, signature, superName, interfaces);
  }

  private ComponentDefinition definition() {
    if (this.definition == null)
      this.definition = new ComponentDefinition();

    return definition;
  }

  public boolean hasDefinition() {
    return definition != null && definition.hasSteretype();
  }

  public ComponentDefinition getDefinition() {
    return definition;
  }

}
