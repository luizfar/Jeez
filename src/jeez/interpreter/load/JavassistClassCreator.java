package jeez.interpreter.load;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import jeez.lang.Attribute;
import jeez.lang.Clazz;
import jeez.lang.Method;

@SuppressWarnings("unchecked")
public class JavassistClassCreator implements ClassCreator {

  private CtClass ctClass;
  
  @Override
  public void startClassCreation(Clazz clazz) {
    ClassPool pool = ClassPool.getDefault();
    ctClass = pool.makeClass(clazz.getName());
  }

  @Override
  public void createAttribute(Attribute attr) {
    try {
      
      CtClass objectClass = ClassPool.getDefault().get("java.lang.Object");
      CtField field = new CtField(objectClass, attr.getName(), ctClass);
      ctClass.addField(field);
      
    } catch (NotFoundException e) {
      throw new RuntimeException(e);
    } catch (CannotCompileException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void createMethod(Method method) {
//    CtClass objectClass;
//    try {
//      objectClass = ClassPool.getDefault().get("java.lang.Object");
//      CtMethod ctMethod = new CtMethod(objectClass, method.getName(), new CtClass[] {}, ctClass);
//    getName.setBody("{ return this.name; }");
//      ctClass.addMethod(ctMethod);
//      
//    } catch (NotFoundException e) {
//      throw new RuntimeException(e);
//    } catch (CannotCompileException e) {
//      throw new RuntimeException(e);
//    }
  }

  @Override
  public Class finishClassCreation(Clazz clazz) {
    try {
      return ctClass.toClass();
    } catch (CannotCompileException e) {
      throw new RuntimeException(e);
    }
  }
}
