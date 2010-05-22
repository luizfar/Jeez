package jeez.interpreter.parsers;

import static jeez.interpreter.lexer.Symbol.ASSIGN;
import static jeez.interpreter.lexer.Symbol.CLASS;
import static jeez.interpreter.lexer.Symbol.COMMA;
import static jeez.interpreter.lexer.Symbol.LEFT_CUR_BRACKET;
import static jeez.interpreter.lexer.Symbol.LEFT_PAR;
import static jeez.interpreter.lexer.Symbol.RIGHT_CUR_BRACKET;
import static jeez.interpreter.lexer.Symbol.RIGHT_PAR;
import static jeez.interpreter.lexer.Symbol.STATIC;
import jeez.lang.Attribute;
import jeez.lang.Block;
import jeez.lang.ClassAttribute;
import jeez.lang.ClassMethod;
import jeez.lang.Clazz;
import jeez.lang.Function;
import jeez.lang.Method;
import jeez.lang.Type;
import jeez.lang.Variable;

public class ClassParser {
  
  private Clazz currentClass;
  
  private JeezParser jeezParser;
  
  private BlockParser blockParser;
  
  private ExpressionParser expressionParser;
  
  public ClassParser(JeezParser jeezParser) {
    this.jeezParser = jeezParser;
    this.blockParser = new BlockParser(jeezParser);
    this.expressionParser = new ExpressionParser(jeezParser);
  }
  
  Clazz parseClass() {
    jeezParser.expect(CLASS);
    
    currentClass = new Clazz(jeezParser.parseIdentifier());
    
    jeezParser.expect(LEFT_CUR_BRACKET);
    
    while (jeezParser.getToken() != RIGHT_CUR_BRACKET) {
      if (jeezParser.getToken() == STATIC) {
        parseClassMember();
      } else {
        parseMember();
      }
    }
    
    jeezParser.expect(RIGHT_CUR_BRACKET);

    return currentClass;
  }
  
  private void parseClassMember() {
    jeezParser.expect(STATIC);
    Type type = jeezParser.parseType();
    String name = jeezParser.parseIdentifier();
    
    if (jeezParser.getToken() == LEFT_PAR) {
      currentClass.addToClassMethods(parseClassMethod(type, name));
    } else {
      ClassAttribute attribute = new ClassAttribute(name);
      if (jeezParser.getToken() == ASSIGN) {
        jeezParser.nextToken();
        attribute.setInitialExpression(expressionParser.parseExpression());
      }
      currentClass.addToClassAttributes(attribute);
    }
  }

  private void parseMember() {
    Type type = jeezParser.parseType();
    String name = jeezParser.parseIdentifier();
    
    if (jeezParser.getToken() == LEFT_PAR) {
      currentClass.addToMethods(parseMethod(type, name));
    } else {
      currentClass.addToAttributes(new Attribute(name));
    } 
  }    
  
  private Method parseMethod(Type type, String methodName) {
    Method method = new Method(currentClass, type, methodName);
    parseParametersAndBodyFor(method);
    return method;
  }  
  
  private ClassMethod parseClassMethod(Type type, String methodName) {
    ClassMethod method = new ClassMethod(currentClass, type, methodName);
    parseParametersAndBodyFor(method);
    return method;
  }
  
  private void parseParametersAndBodyFor(Function function) {
    jeezParser.expect(LEFT_PAR);
    if (jeezParser.getToken() != RIGHT_PAR) {
      parseParameterListFor(function);
    }
    jeezParser.nextToken();
    
    Block block = blockParser.parseBlock();
    function.setBlock(block);
  }

  private void parseParameterListFor(Function function) {
    function.addToParameters(parseParameter());
    while (jeezParser.getToken() == COMMA) {
      jeezParser.nextToken();
      function.addToParameters(parseParameter());
    }
  }
  
  private Variable parseParameter() {
    return new Variable(jeezParser.parseIdentifier());
  }
}
