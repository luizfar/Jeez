package jeez.interpreter.load;

import static jeez.lang.Type.VOID;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.NotFoundException;
import jeez.lang.Attribute;
import jeez.lang.Clazz;
import jeez.lang.Method;
import jeez.lang.Type;
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
    if ("new".equals(method.getName())) {
      createConstructor(method);
      return;
    }
    
    try {
      CtClass returnType = (method.getType() == VOID) ?
          CtClass.voidType : ClassPool.getDefault().get("java.lang.Object");
      
      CtMethod ctMethod = new CtMethod(returnType, method.getName(), new CtClass[] {}, ctClass);
      ctMethod.setBody(generateBodyFor(method));
      ctClass.addMethod(ctMethod);
      
    } catch (NotFoundException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } catch (CannotCompileException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
  
  public void createConstructor(Method method) {
    try {
      CtConstructor constructor = new CtConstructor(new CtClass[] {}, ctClass);
      constructor.setBody(generateBodyFor(method));
      ctClass.addConstructor(constructor);
    } catch (CannotCompileException e) {
      e.printStackTrace();
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
    if (method.getType() == Type.DUCK) {
      code.append("return null;");
    }
    code.append("}");
    return code.toString();
  }

  @Override
  public void generateAssignment(AssignmentExpression assignmentExpression) {
    code.append(assignmentExpression.getVariableName());
    code.append(" = ");
    assignmentExpression.getExpression().accept(this);
    code.append(";");
  }

  @Override
  public void generateBoolean(LiteralBooleanExpression literalBooleanExpression) {
    code.append(literalBooleanExpression.getValue());
  }

  @Override
  public void generateExpressionList(ExpressionList expressionList) {
    code.append("{\n");
    for (Expression e : expressionList.getExpressions()) {
      e.accept(this);
    }
    code.append("}\n");
  }

  @Override
  public void generateIf(IfExpression ifExpression) {
    code.append("if (");
    ifExpression.getBooleanExpression().accept(this);
    code.append(") {\n");
    ifExpression.getIfExpression().accept(this);
    code.append("}\n");
    if (ifExpression.getElseExpression() != null) {
      code.append("else {\n");
      ifExpression.getElseExpression().accept(this);
      code.append("}\n");
    }
  }

  @Override
  public void generateInteger(IntegerExpression integerExpression) {
    code.append(integerExpression.getValue());
  }

  @Override
  public void generateMessageSend(MessageSend messageSend) {
    messageSend.getReceiver().accept(this);
    code.append(".");
    code.append(messageSend.getMessageName());
    code.append("(");
    for (int i = 0; i < messageSend.getArguments().size(); i++) {
      messageSend.getArguments().get(i).accept(this);
      if (i != messageSend.getArguments().size() - 1) {
        code.append(",");
      }
    }
    code.append(");\n");
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
    code.append("return (");
    returnExpression.getExpression().accept(this);
    code.append(");");
  }

  @Override
  public void generateSelf(SelfExpression selfExpression) {
    code.append("this");
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
