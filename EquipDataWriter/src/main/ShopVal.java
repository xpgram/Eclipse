package main;

public class ShopVal implements DataField {

  int value;
  
  public static final int Max_Val = 99990;
  
  public ShopVal() {
    reset();
  }
  
  @Override
  public void reset() {
    this.value = 0;
  }

  @Override
  public void submit(String str) {
    if (valid(str))
      this.value = Integer.valueOf(str);
  }

  @Override
  public boolean valid(String str) {
    boolean r = false;
    int n;
    
    try {
      n = Integer.valueOf(str);
      if (n >= 0 && n <= Max_Val)
        r = true;
    } catch (NumberFormatException e) {}
    
    return r;
  }

  @Override
  public String value() {
    return String.valueOf(this.value);
  }

  @Override
  public void tap() {}
  
  @Override
  public void tapAlt() { }

  @Override
  public void del() {
    reset();
  }

  @Override
  public void increment() {
    this.value += 10;
    if (this.value > Max_Val)
      this.value = 0;
  }

  @Override
  public void decrement() {
    this.value -= 10;
    if (this.value < 0)
      this.value = Max_Val;
  }

  @Override
  public void incrementAlt() {
    this.value += 100;
    if (this.value > Max_Val)
      this.value = 0;
  }

  @Override
  public void decrementAlt() {
    this.value -= 100;
    if (this.value < 0)
      this.value = Max_Val;
  }

  @Override
  public String displayString() {
    return "$" + value();
  }

  @Override
  public String displayHeader() {
    return "Value";
  }

  @Override
  public int displayWidth() {
    return DisplaySettings.Value_Chunk;
  }

  @Override
  public String contextEnter() {
    return "None";
  }

  @Override
  public String contextDel() {
    return "Reset";
  }

  @Override
  public String contextArrows() {
    return "Increment";
  }

  @Override
  public boolean editable() {
    return false;
  }

  @Override
  public boolean strictlyNumerical() {
    return true;
  }

  @Override
  public FieldType getType() {
    return DataField.FieldType.Value;
  }

}
