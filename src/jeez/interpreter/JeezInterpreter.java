package jeez.interpreter;


import jeez.interpreter.execution.ExecutionContext;
import jeez.interpreter.lexer.JeezLexer;
import jeez.interpreter.lexer.Symbol;
import jeez.interpreter.parsers.JeezParser;
import jeez.lang.Clazz;
import jeez.lang.Module;
import jeez.lang.expression.Expression;

public class JeezInterpreter {

  public void start(char[] input) {
    JeezLexer lexer = new JeezLexer(input);
    JeezParser parser = new JeezParser(lexer);
    ExecutionContext context = new ExecutionContext();
    
    Object parsed = parser.parseNext();
    
    while (parsed != Symbol.EOF) {
      if (parsed instanceof Clazz) {
        context.addClass((Clazz) parsed);
      } else if (parsed instanceof Module) {
        context.addModule((Module) parsed);
      } else if (parsed instanceof Expression) {
        ((Expression) parsed).evaluate(context);
      }
      parsed = parser.parseNext();  
    }
  }
}
