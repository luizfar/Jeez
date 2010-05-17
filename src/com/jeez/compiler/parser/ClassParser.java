package com.jeez.compiler.parser;

import static com.jeez.compiler.lexer.Symbol.CLASS;
import static com.jeez.compiler.lexer.Symbol.COMMA;
import static com.jeez.compiler.lexer.Symbol.LEFT_CUR_BRACKET;
import static com.jeez.compiler.lexer.Symbol.LEFT_PAR;
import static com.jeez.compiler.lexer.Symbol.RIGHT_CUR_BRACKET;
import static com.jeez.compiler.lexer.Symbol.RIGHT_PAR;
import jeez.lang.Attribute;
import jeez.lang.Block;
import jeez.lang.Clazz;
import jeez.lang.DynamicVariable;
import jeez.lang.Method;
import jeez.lang.Type;
import jeez.lang.Variable;

public class ClassParser {
  
  private JeezParser jeezParser;
  
  private Clazz currentClass;
  
  private BlockParser blockParser;
  
  public ClassParser(JeezParser jeezParser) {
    this.jeezParser = jeezParser;
    this.blockParser = new BlockParser(jeezParser);
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
    Type type = jeezParser.parseType();
    
    String memberName = jeezParser.parseIdentifier();
    
    if (jeezParser.getToken() == LEFT_PAR) {      
      currentClass.addToMethods(parseMethod(type, memberName));
    } else {
      currentClass.addToAttributes(new Attribute(type, memberName));
    }
  }    
  
  private Method parseMethod(Type type, String methodName) {
    Method method = new Method(currentClass, type, methodName);
    
    jeezParser.expect(LEFT_PAR);
    if (jeezParser.getToken() != RIGHT_PAR) {
      parseParameterListFor(method);
    }
    jeezParser.nextToken();
    
    Block block = blockParser.parseBlock();
    method.setBlock(block);
    
    return method;
  }

  private void parseParameterListFor(Method method) {
    method.addToParameters(parseParameter());
    while (jeezParser.getToken() == COMMA) {
      jeezParser.nextToken();
      method.addToParameters(parseParameter());
    }
  }
  
  private Variable parseParameter() {
    return new DynamicVariable(jeezParser.parseIdentifier());
  }
}
