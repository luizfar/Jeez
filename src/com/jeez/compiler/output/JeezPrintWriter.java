package com.jeez.compiler.output;

import java.io.*;

public class JeezPrintWriter {

  public void add() {
    currentIndent += step;
  }

  public void sub() {
    currentIndent -= step;
  }

  public void set(PrintWriter out) {
    this.out = out;
    currentIndent = 0;
  }

  public void set(int indent) {
    currentIndent = indent;
  }

  public void print(String s) {
    out.print(space.substring(0, currentIndent));
    out.print(s);
  }

  public void println(String s) {
    out.print(space.substring(0, currentIndent));
    out.println(s);
  }

  int currentIndent = 0;
  
  public int step = 3;
  public PrintWriter out;

  static final private String space = "                                                                                                        ";
}
