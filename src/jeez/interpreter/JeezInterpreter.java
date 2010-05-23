package jeez.interpreter;


import jeez.interpreter.execution.ExecutionContext;
import jeez.interpreter.lexer.Lexer;
import jeez.interpreter.lexer.Symbol;
import jeez.interpreter.parsers.MainParser;
import jeez.lang.JeezClass;
import jeez.lang.Module;
import jeez.lang.expression.Expression;

public class JeezInterpreter {

  public void start(char[] input) {
    Lexer lexer = new Lexer(input);
    MainParser parser = new MainParser(lexer);
    ExecutionContext context = new ExecutionContext();
    context.prepare();
    
    Object parsed = parser.parseNext();
    
    while (parsed != Symbol.EOF) {
      if (parsed instanceof JeezClass) {
        context.addClass((JeezClass) parsed);
      } else if (parsed instanceof Module) {
        context.addModule((Module) parsed);
      } else if (parsed instanceof Expression) {
        ((Expression) parsed).evaluate(context);
      }
      parsed = parser.parseNext();  
    }
  }
}
