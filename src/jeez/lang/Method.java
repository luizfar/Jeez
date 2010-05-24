package jeez.lang;

import java.util.List;

import jeez.interpreter.execution.ExecutionContext;
import jeez.lang.expression.Expression;

public class Method extends Function {

  private JeezClass owner;
  
  public Method(JeezClass owner, Type type, String name) {
    super(type, name);
    this.owner = owner;
  }
  
  public JeezClass getOwner() {
    return owner;
  }
  
  @Override
  public JeezObject execute(JeezObject target, List<Expression> arguments, ExecutionContext context) {
    context.addLocalContext();
    for (Variable attr : target.getAttributes()) {
      context.addToLocalContext(attr);
    }
    
    JeezObject result = super.execute(target, arguments, context);
    
    context.removeLocalContext();
    
    return result;
  }
}
