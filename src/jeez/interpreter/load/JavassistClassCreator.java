package jeez.interpreter.load;

import static jeez.lang.Type.VOID;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import jeez.lang.Attribute;
import jeez.lang.Clazz;
import jeez.lang.Method;
import jeez.lang.expression.AssignmentExpression;
import jeez.lang.expression.Expression;
import jeez.lang.expression.ExpressionList;
import jeez.lang.expression.IfExpression;
import jeez.lang.expression.IntegerExpression;
import jeez.lang.expression.LiteralBooleanExpression;
import jeez.lang.expression.LiteralStringExpression;
import jeez.lang.expression.MessageSend;
import jeez.lang.expression.NullExpression;
import jeez.lang.expression.PrintExpression;
import jeez.lang.expression.PrintlnExpression;
import jeez.lang.expression.ReturnExpression;
import jeez.lang.expression.SelfExpression;
import jeez.lang.expression.UnaryExpression;
import jeez.lang.expression.VariableDeclaration;
import jeez.lang.expression.VariableExpression;

@SuppressWarnings("unchecked")
public class JavassistClassCreator implements ClassCreator {

  private CtClass ctClass;
  
  private StringBuilder code;
  
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
    try {
      CtClass returnType = (method.getType() == VOID) ?
          CtClass.voidType : ClassPool.getDefault().get("java.lang.Object");
      
      CtMethod ctMethod = new CtMethod(returnType, method.getName(), new CtClass[] {}, ctClass);
      ctMethod.setBody(generateBodyFor(method));
      ctClass.addMethod(ctMethod);
      
    } catch (NotFoundException e) {
      throw new RuntimeException(e);
    } catch (CannotCompileException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Class finishClassCreation(Clazz clazz) {
    try {
      return ctClass.toClass();
    } catch (CannotCompileException e) {
      throw new RuntimeException(e);
    }
  }
  
  private String generateBodyFor(Method method) {
    code = new StringBuilder();
    code.append("{\n");
    for (Expression e : method.getBlock().getExpressions()) {
      e.accept(this);
    }
    code.append("}");
    return code.toString();
  }

  @Override
  public void generateAssignment(AssignmentExpression assignmentExpression) {
  }

  @Override
  public void generateBoolean(LiteralBooleanExpression literalBooleanExpression) {
  }

  @Override
  public void generateExpressionList(ExpressionList expressionList) {
  }

  @Override
  public void generateIf(IfExpression ifExpression) {
  }

  @Override
  public void generateInteger(IntegerExpression integerExpression) {
  }

  @Override
  public void generateMessageSend(MessageSend messageSend) {
  }

  @Override
  public void generateNull(NullExpression nullExpression) {
    code.append("null");
  }

  @Override
  public void generatePrint(PrintExpression printExpression) {
    code.append("System.out.print(");
    printExpression.getExpression().accept(this);
    code.append(");\n");
  }

  @Override
  public void generatePrintln(PrintlnExpression printlnExpression) {
    code.append("System.out.println(");
    printlnExpression.getExpression().accept(this);
    code.append(");\n");
  }

  @Override
  public void generateReturn(ReturnExpression returnExpression) {
    code.append("return(");
    returnExpression.getExpression().accept(this);
    code.append(");");
  }

  @Override
  public void generateSelf(SelfExpression selfExpression) {
  }

  @Override
  public void generateString(LiteralStringExpression literalStringExpression) {
    code.append("\"" + literalStringExpression.getValue() + "\"");
  }

  @Override
  public void generateUnaryExpression(UnaryExpression unaryExpression) {
  }

  @Override
  public void generateVariableDeclaration(
      VariableDeclaration variableDeclaration) {
  }

  @Override
  public void generateVariableExpression(VariableExpression variableExpression) {
  }
}
