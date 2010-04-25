package com.jeez.compiler.ast.expr;

import java.util.List;

import com.jeez.compiler.ast.JeezCodeVisitor;
import com.jeez.compiler.ast.Method;

public class MessageSendToThisExpression implements Expression {

  private Method method;
  
  private List<Expression> arguments;
  
  public MessageSendToThisExpression(Method method, List<Expression> arguments) {
    this.method = method;
    this.arguments = arguments;
  }
  
  public Method getMethod() {
    return method;
  }
  
  public List<Expression> getArguments() {
    return arguments;
  }
  
  @Override
  public void receive(JeezCodeVisitor visitor) {
    // TODO luiz
  }
}
