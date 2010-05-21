package com.jeez.compiler;

import com.jeez.compiler.lexer.JeezLexer;
import com.jeez.compiler.lexer.Symbol;
import com.jeez.compiler.parser.JeezParser;

import jeez.lang.Clazz;
import jeez.lang.Module;
import jeez.lang.context.ExecutionContext;
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
