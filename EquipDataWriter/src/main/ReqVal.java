package main;

public class ReqVal implements DataField {

  Stats value;
  int level;
  
  public static final int Max_Level = 5;
  
  public ReqVal() {
    reset();
  }
  
  @Override
  public void reset() {
    this.value = Stats.NONE;
    this.level = 0;
  }

  @Override
  public void submit(String str) {
    if (valid(str)) {
      value = Stats.valueOf(str.substring(0,3));
      level = utils.Common.charToDigit(str.charAt(3));
      if (level > Max_Level) level = Max_Level;
    }
  }

  @Override
  public boolean valid(String str) {
    boolean r = false;
    
    if (str.length() == 4) {
      try {
        Stats.valueOf(str.substring(0,3));
        if (utils.Common.charIsDigit(str.charAt(3)))
          r = true;
      } catch (IllegalArgumentException e) { }
    }
    
    return r;
  }

  @Override
  public String value() {
    String str = "";
    if (this.value != Stats.NONE)
      str = this.value.toString() + this.level;
    return str;
  }

  @Override
  public void tap() {
    incrementAlt();
  }

  @Override
  public void tapAlt() {
    decrementAlt();
  }

  @Override
  public void del() {
    reset();
  }

  @Override
  public void increment() {
    this.level++;
    if (this.level > Max_Level)
      this.level = 0;
    
    if (this.value == Stats.NONE)
      this.value = Stats.PHY;
  }

  @Override
  public void decrement() {
    this.level--;
    if (this.level < 0)
      this.level = Max_Level;
    
    if (this.value == Stats.NONE)
      this.value = Stats.PHY;
  }
  
  @Override
  public void incrementAlt() {
    int i = this.value.ordinal();
    i++;
    if (i >= Stats.values().length)
      i = 1;
    this.value = Stats.values()[i];
  }

  @Override
  public void decrementAlt() {
    int i = this.value.ordinal();
    i--;
    if (i < 0)
      i = Stats.values().length - 1;
    this.value = Stats.values()[i];
  }

  @Override
  public String displayString() {
    return value();
  }

  @Override
  public String displayHeader() {
    return "Req";
  }

  @Override
  public int displayWidth() {
    return DisplaySettings.Std_Chunk;
  }

  @Override
  public String contextEnter() {
    return "Cycle";
  }

  @Override
  public String contextDel() {
    return "Erase";
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
    return false;
  }
  
  @Override
  public FieldType getType() {
    return FieldType.Req;
  }

  public int getOrdinal() {
    int n = 0;
    if (this.value == Stats.NONE)
      n = Integer.MAX_VALUE;
    else {
      n = this.value.ordinal();
      n += (Max_Level - this.level) * 10;
    }
    return n;
  }
  
  public boolean equals(ReqVal other) {
    boolean r = false;
    if (this.value == other.value)
      r = true;
    return r;
  }
  
}
