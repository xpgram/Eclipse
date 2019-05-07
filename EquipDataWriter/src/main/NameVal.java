package main;

public class NameVal implements DataField {

  String value;
  
  public static final int Max_Len = 20;
  
  public NameVal() {
    reset();
  }
  
  @Override
  public void reset() {
    this.value = "???";
  }

  @Override
  public void submit(String str) {
    if (valid(str))
      this.value = str;
    if (str.length() == 0)
      reset();
  }
  
  @Override
  public boolean valid(String str) {
    boolean r = false;
    if (str.length() <= Max_Len)
      r = true;
    return r;
  }

  @Override
  public String value() {
    return this.value;
  }

  @Override
  public void tap() { }
  
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
    return value();
  }

  @Override
  public String displayHeader() {
    return "Name";
  }

  @Override
  public int displayWidth() {
    return DisplaySettings.Name_Chunk;
  }

  @Override
  public String contextEnter() {
    return "Edit";
  }

  @Override
  public String contextDel() {
    return "Erase";
  }

  @Override
  public String contextArrows() {
    return "None";
  }

  @Override
  public boolean editable() {
    return true;
  }

  @Override
  public boolean strictlyNumerical() {
    return false;
  }

  @Override
  public FieldType getType() {
    return DataField.FieldType.Name;
  }

}
