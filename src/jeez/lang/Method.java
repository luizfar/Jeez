package jeez.lang;

import java.util.List;

import jeez.lang.context.ExecutionContext;
import jeez.lang.expression.Expression;

public class Method extends Function {

  private Clazz owner;
  
  public Method(Clazz owner, Type type, String name) {
    super(type, name);
  }
  
  public Clazz getOwner() {
    return owner;
  }
  
  @Override
  public JeezObject execute(JeezObject target, List<Expression> arguments, ExecutionContext context) {
    context.addLocalContext();
    for (Variable var : target.getInstanceVariables()) {
      context.addToLocalContext(var);
    }
    JeezObject result = super.execute(target, arguments, context);
    context.removeLocalContext();
    
    return result;
  }
}
