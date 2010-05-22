package jeez.interpreter;

import java.io.BufferedReader;
import java.io.FileReader;

public class Jeez {

  public static void main(String[] args) throws Exception {
    Jeez jeez = new Jeez();
    
    String code = jeez.readFile("/home/luiz/j.jz");
    jeez.run(code.toCharArray());
  }
  
  String readFile(String path) throws Exception {
    BufferedReader reader = new BufferedReader(new FileReader(path));
    StringBuffer code = new StringBuffer();
    String line = reader.readLine();
    while (line != null) {
      code.append(line + "\n");
      line = reader.readLine();
    }
    
    return code.toString();
  }
  
  void run(char[] input) {
    new JeezInterpreter().start(input);
  }
}
