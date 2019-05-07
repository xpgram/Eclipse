package main;

public class StatVal implements DataField {

  int value;
  String displayHeader;
  
  public static final int Max_Val = 30;
  public static final int Min_Val = -30;
  
  public StatVal(String header) {
    this.displayHeader = header;
    reset();
  }
  
  @Override
  public void reset() {
    this.value = 0;
  }

  @Override
  public void submit(String str) {
    if (valid(str)) {
      this.value = Integer.valueOf(str);
      if (this.value > Max_Val)
        this.value = Max_Val;
      if (this.value < Min_Val)
        this.value = Min_Val;
    }
  }

  @Override
  public boolean valid(String str) {
    boolean r = false;
    int n;
    
    try {
      n = Integer.valueOf(str);
      if (n >= 0 && n <= 99 ||
          n < 0 && n >= -99)
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
    this.value++;
    if (this.value > Max_Val)
      this.value = Min_Val;
  }

  @Override
  public void decrement() {
    this.value--;
    if (this.value < Min_Val)
      this.value = Max_Val;
  }

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
    return this.displayHeader;
  }

  @Override
  public int displayWidth() {
    return DisplaySettings.Small_Chunk;
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
    return DataField.FieldType.StatVal;
  }
  
}
