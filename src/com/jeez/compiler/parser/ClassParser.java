package com.jeez.compiler.parser;

import static com.jeez.compiler.lexer.Symbol.CLASS;
import static com.jeez.compiler.lexer.Symbol.LEFT_CUR_BRACKET;
import static com.jeez.compiler.lexer.Symbol.LEFT_PAR;
import static com.jeez.compiler.lexer.Symbol.RIGHT_CUR_BRACKET;
import jeez.lang.Attribute;
import jeez.lang.Block;
import jeez.lang.Clazz;
import jeez.lang.Method;
import jeez.lang.context.ExecutionContext;

public class ClassParser {
  
  private JeezParser jeezParser;
  
  private Clazz currentClass;
  
  private ExecutionContext context;
  
  public ClassParser(JeezParser jeezParser, ExecutionContext context) {
    this.jeezParser = jeezParser;
    this.context = context;
  }
  
  Clazz parseClass() {
    jeezParser.expect(CLASS);
    
    currentClass = new Clazz(jeezParser.parseIdentifier());
    
    jeezParser.expect(LEFT_CUR_BRACKET);
    
    while (jeezParser.getToken() != RIGHT_CUR_BRACKET) {
      parseMember();
    }
    
    jeezParser.expect(RIGHT_CUR_BRACKET);
    
    return currentClass;
  }

  private void parseMember() {
    String typeName = jeezParser.parseIdentifier();
    Clazz type = context.getClass(typeName);
    
    String memberName = jeezParser.parseIdentifier();
    
    if (jeezParser.getToken() == LEFT_PAR) {      
      currentClass.addToMethods(parseMethod(type, memberName));
    } else {
      currentClass.addToAttributes(new Attribute(type, memberName));
    }
  }    
  
  private Method parseMethod(Clazz type, String methodName) {
    jeezParser.expect(LEFT_CUR_BRACKET);
    
    int rightCurBracketsExpected = 1;
    while (rightCurBracketsExpected > 0) {
      if (jeezParser.getToken() == LEFT_CUR_BRACKET) {
        rightCurBracketsExpected++;
      }
      if (jeezParser.getToken() == RIGHT_CUR_BRACKET) {
        rightCurBracketsExpected--;
      }
      jeezParser.nextToken();
    }
    
    return new Method(currentClass, type, methodName, new Block());
  }
}
