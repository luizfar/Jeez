package jeez.lang;

import java.util.List;

import jeez.lang.context.ExecutionContext;
import jeez.lang.expression.Expression;

public class ClassMethod extends Method {

  public ClassMethod(Clazz owner, Type type, String name) {
    super(owner, type, name);
  }
  
  @Override
  public JeezObject execute(JeezObject target, List<Expression> arguments, ExecutionContext context) {
    context.addLocalContext();
    
    for (ClassAttribute attr : getOwner().getClassAttributes()) {
      context.addToLocalContext(attr.getVariable());
    }
    JeezObject result = super.execute(target, arguments, context);
    context.removeLocalContext();
    
    return result;
  }
}
