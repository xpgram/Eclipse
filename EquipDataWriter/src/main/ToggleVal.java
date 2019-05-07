package main;

public class ToggleVal implements DataField {

  boolean value;
  boolean defaultVal;
  String displayHeader;
  
  public ToggleVal(String header) {
    this.displayHeader = header;
    this.defaultVal = false;
    reset();
  }
  
  public ToggleVal(String header, boolean defaultSetting) {
    this.displayHeader = header;
    this.defaultVal = defaultSetting;
    reset();
  }
  
  @Override
  public void reset() {
    this.value = this.defaultVal;
  }

  @Override
  public void submit(String str) {
    if (valid(str))
      this.value = (str.toLowerCase().charAt(0) == 't') ? true : false;
  }

  @Override
  public boolean valid(String str) {
    boolean r = false;
    
    str = str.toLowerCase();
    
    if (str.length() > 0)
      if (str.charAt(0) == 't' || str.charAt(0) == 'f')
        r = true;
    
    return r;
  }

  @Override
  public String value() {
    return (this.value) ? "T" : "F";
  }

  @Override
  public void tap() {
    this.value = !this.value;
  }

  @Override
  public void tapAlt() { }

  @Override
  public void del() {
    reset();
  }

  @Override
  public void increment() { }

  @Override
  public void decrement() { }

  @Override
  public void incrementAlt() { }

  @Override
  public void decrementAlt() { }
  
  @Override
  public String displayString() {
    return (this.value) ? "T" : "-";
  }

  @Override
  public String displayHeader() {
    return this.displayHeader;
  }

  @Override
  public int displayWidth() {
    return DisplaySettings.Std_Chunk;
  }

  @Override
  public String contextEnter() {
    return "Toggle";
  }

  @Override
  public String contextDel() {
    return "Reset";
  }

  @Override
  public String contextArrows() {
    return "None";
  }

  @Override
  public boolean editable() {
    return false;
  }

  @Override
  public boolean strictlyNumerical() {
    return false;
  }

  @Override
  public FieldType getType() {
    return FieldType.Toggle;
  }
  
}
