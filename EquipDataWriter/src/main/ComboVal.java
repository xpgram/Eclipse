package main;

public class ComboVal implements DataField {

  WeaponType value;
  boolean wType;
  
  public ComboVal() {
    wType = false;
    reset();
  }
  
  public ComboVal(boolean wType) {
    this.wType = wType;
    reset();
  }
  
  @Override
  public void reset() {
    this.value = WeaponType.None;
  }

  @Override
  public void submit(String str) {
    if (valid(str)) {
      this.value = WeaponType.valueOf(str);
      
      if (Main.program.equipsPage != null)    // This causes problems if not present when loading the program. It's 'cause I'm not being careful about protecting my cross-class fields, etc. I shouldn't even be able to do what I'm doing here.
        Main.program.equipsPage.get(Main.program.cursor.y).sortFields();
    }
  }

  @Override
  public boolean valid(String str) {
    boolean r = false;
    
    try {
      WeaponType.valueOf(str);
      r = true;
    } catch (IllegalArgumentException e) { }
    
    return r;
  }

  @Override
  public String value() {
    String str = "";
    if (this.value != WeaponType.None)
      str = this.value.toString();
    return str;
  }

  @Override
  public void tap() { }

  @Override
  public void tapAlt() { }

  @Override
  public void del() {
    reset();
    Main.program.equipsPage.get(Main.program.cursor.y).sortFields();
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
    return (this.wType) ? "W.Type" : "Combo";
  }

  @Override
  public int displayWidth() {
    return DisplaySettings.Combo_Chunk;
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
    return FieldType.ComboReq;
  }

  public int getOrdinal() {
    int n = 0;
    if (this.value == WeaponType.None)
      n = Integer.MAX_VALUE;
    else
      n = this.value.ordinal();
    return n;
  }
  
  public boolean equals(ComboVal other) {
    boolean r = false;
    if (this.value == other.value)
      r = true;
    return r;
  }
  
}
