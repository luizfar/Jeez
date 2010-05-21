package jeez.lang;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jeez.lang.context.ExecutionContext;
import jeez.lang.expression.Expression;

public class JeezObject implements MessageReceiver {

  private Clazz clazz;
  
  private Map<String, Variable> instanceVariables = new HashMap<String, Variable>();
  
  public JeezObject(Clazz clazz) {
    this.clazz = clazz;
    for (Attribute attribute : clazz.getAttributes()) {
      Variable var = new Variable(attribute.getName());
      instanceVariables.put(var.getName(), var);
    }
  }
  
  public JeezObject() {
    
  }
  
  public Clazz getClazz() {
    return clazz;
  }
  
  public Collection<Variable> getInstanceVariables() {
    return instanceVariables.values();
  }

  @Override
  public JeezObject receiveMessage(String messageName, List<Expression> arguments, ExecutionContext context) {
    Method method = clazz.getMethod(messageName);
    if (method == null) {
      throw new UnknownMessageException(clazz, messageName);
    }
    return method.execute(this, arguments, context);
  }
}
