package jeez.interpreter.parsers;

import static jeez.interpreter.lexer.Symbol.CLASS;
import static jeez.interpreter.lexer.Symbol.COMMA;
import static jeez.interpreter.lexer.Symbol.EXTENDS;
import static jeez.interpreter.lexer.Symbol.LEFT_CUR_BRACKET;
import static jeez.interpreter.lexer.Symbol.LEFT_PAR;
import static jeez.interpreter.lexer.Symbol.RIGHT_CUR_BRACKET;
import static jeez.interpreter.lexer.Symbol.RIGHT_PAR;
import static jeez.interpreter.lexer.Symbol.STATIC;
import jeez.interpreter.execution.ClassBuilder;
import jeez.lang.Attribute;
import jeez.lang.Block;
import jeez.lang.Function;
import jeez.lang.JeezClass;
import jeez.lang.Method;
import jeez.lang.Type;
import jeez.lang.Variable;

public class ClassParser {
  
  private JeezClass currentClass;
  
  private ClassBuilder classBuilder;
  
  private MainParser mainParser;
  
  private BlockParser blockParser;
  
  public ClassParser(MainParser mainParser) {
    this.mainParser = mainParser;
    this.blockParser = new BlockParser(mainParser);
    this.classBuilder = new ClassBuilder();
  }
  
  JeezClass parseClass() {
    mainParser.expect(CLASS);
    
    currentClass = classBuilder.build(mainParser.parseIdentifier());
    
    if (mainParser.getToken() == EXTENDS) {
      mainParser.nextToken();
      currentClass.setSuperClass(classBuilder.build(mainParser.parseIdentifier()));
    }
    
    mainParser.expect(LEFT_CUR_BRACKET);
    
    while (mainParser.getToken() != RIGHT_CUR_BRACKET) {
      if (mainParser.getToken() == STATIC) {
        parseMember();
      } else {
        parseClassMember();
      }
    }
    
    mainParser.expect(RIGHT_CUR_BRACKET);

    return currentClass;
  }
  
  private void parseMember() {
    mainParser.expect(STATIC);
    Type type = mainParser.parseType();
    String name = mainParser.parseIdentifier();
    
    if (mainParser.getToken() == LEFT_PAR) {
      currentClass.addToMethods(parseMethod(type, name));
    } else {
      currentClass.addToAttributes(new Variable(name));
    }
  }

  private void parseClassMember() {
    Type type = mainParser.parseType();
    String name = mainParser.parseIdentifier();
    
    if (mainParser.getToken() == LEFT_PAR) {
      currentClass.addToClassMethods(parseMethod(type, name));
    } else {
      currentClass.addToClassAttributes(new Attribute(name));
    } 
  }    
  
  private Method parseMethod(Type type, String methodName) {
    Method method = new Method(currentClass, type, methodName);
    parseParametersAndBodyFor(method);
    return method;
  }
  
  private void parseParametersAndBodyFor(Function function) {
    mainParser.expect(LEFT_PAR);
    if (mainParser.getToken() != RIGHT_PAR) {
      parseParameterListFor(function);
    }
    mainParser.nextToken();
    
    Block block = blockParser.parseBlock();
    function.setBlock(block);
  }

  private void parseParameterListFor(Function function) {
    function.addToParameters(parseParameter());
    while (mainParser.getToken() == COMMA) {
      mainParser.nextToken();
      function.addToParameters(parseParameter());
    }
  }
  
  private Variable parseParameter() {
    return new Variable(mainParser.parseIdentifier());
  }
}
