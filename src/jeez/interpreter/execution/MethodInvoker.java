package jeez.interpreter.execution;

import java.util.List;

import jeez.interpreter.execution.exception.UnknownMessageException;
import jeez.interpreter.execution.exception.WrongNumberOfArgumentsException;
import jeez.lang.JeezObject;
import jeez.lang.Method;
import jeez.lang.expression.Expression;

public class MethodInvoker {

  public JeezObject invoke(String methodName, JeezObject target, List<Expression> arguments, ExecutionContext context) {
    Method method = target.findMethod(methodName);
    if (method == null) {
      throw new UnknownMessageException(target, methodName);
    }
    if (method.getParametersCount() != arguments.size()) {
      throw new WrongNumberOfArgumentsException(method, arguments.size());
    }
    
    return method.execute(target, arguments, context);
  }
}
