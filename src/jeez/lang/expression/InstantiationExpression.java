package jeez.lang.expression;

import jeez.lang.Clazz;
import jeez.lang.context.ExecutionContext;

public class InstantiationExpression implements Expression {

  private String clazzName;
  
  public InstantiationExpression(String clazzName) {
    this.clazzName = clazzName;
  }
  
  @Override
  public Object evaluate(ExecutionContext symbolTable) {
    Clazz clazz = symbolTable.getClass(clazzName);
    if (clazz == null) {
      throw new RuntimeException("Can't find class " + clazzName);
    }
    
    return clazz.newObject();
  }
}
