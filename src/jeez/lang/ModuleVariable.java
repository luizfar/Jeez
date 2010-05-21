package jeez.lang;

public class ModuleVariable extends Variable {

  public ModuleVariable(Module module) {
    super(module.getName());
    setValue(module);
  }
}
