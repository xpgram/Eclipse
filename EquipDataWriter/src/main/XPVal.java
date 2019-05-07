package main;

public class XPVal implements DataField {

  Stats value;
  int amount;
  
  public static final int Max_XP = 20;
  public static final int Def_XP = 10;
  public static final int Inc_XP = 5;
  
  public XPVal() {
    reset();
  }
  
  @Override
  public void reset() {
    this.value = Stats.NONE;
    this.amount = Def_XP;
  }

  @Override
  public void submit(String str) {
    if (valid(str)) {
      value = Stats.valueOf(str.substring(0,3));
      amount = Integer.valueOf(str.substring(3,5));
      if (amount > Max_XP) amount = Max_XP;
    }
  }

  @Override
  public boolean valid(String str) {
    boolean r = false;
    
    if (str.length() == 5) {
      try {
        Stats.valueOf(str.substring(0,3));
        if (utils.Common.charIsDigit(str.charAt(3)) &&
            utils.Common.charIsDigit(str.charAt(4)))
          r = true;
      } catch (IllegalArgumentException e) { }
    }
    
    return r;
  }

  @Override
  public String value() {
    String str = "";
    if (this.value != Stats.NONE)
      str = this.value.toString() + String.format("%02d", this.amount);
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
    this.amount += Inc_XP;
    if (this.amount > Max_XP)
      this.amount = 0;
    
    if (this.value == Stats.NONE)
      this.value = Stats.PHY;
  }

  @Override
  public void decrement() {
    this.amount -= Inc_XP;
    if (this.amount < 0)
      this.amount = Max_XP;
    
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
    return "XP";
  }

  @Override
  public int displayWidth() {
    return DisplaySettings.XP_Chunk;
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
    return FieldType.XP;
  }

  public int getOrdinal() {
    int n = 0;
    if (this.value == Stats.NONE)
      n = Integer.MAX_VALUE;
    else {
      n = this.value.ordinal();
      n += (Max_XP - this.amount) * 10;
    }
    return n;
  }
  
  public boolean equals(XPVal other) {
    boolean r = false;
    if (this.value == other.value)
      r = true;
    return r;
  }
  
}
