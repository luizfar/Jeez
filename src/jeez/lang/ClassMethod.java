package jeez.lang;

import java.util.List;

import jeez.lang.execution.ExecutionContext;
import jeez.lang.expression.Expression;

public class ClassMethod extends Function {

  private Clazz owner;
  
  public ClassMethod(Clazz owner, Type type, String name) {
    super(type, name);
    this.owner = owner;
  }
  
  public Clazz getOwner() {
    return owner;
  }
  
  @Override
  public Object execute(Object target, List<Expression> arguments, ExecutionContext context) {
    context.addLocalContext();
    
    for (ClassAttribute attr : getOwner().getClassAttributes()) {
      context.addToLocalContext(attr.getVariable());
    }
    Object result = super.execute(target, arguments, context);
    context.removeLocalContext();
    
    return result;
  }
}
