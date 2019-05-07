package main;

public class AttributeVal implements DataField {

  Attribute value;
  int duration;
  int amount;
  
  public static final int Max_Duration = 9;
  public static final int Max_Amount = 9;
  
  public AttributeVal() {
    reset();
  }
  
  @Override
  public void reset() {
    this.value = Attribute.None;
    this.duration = 0;
  }

  @Override
  public void submit(String str) {
    int idx = str.indexOf("+");
    int idx2 = str.indexOf(":");
    
    if (idx2 == -1)
      idx2 = str.length();
    if (idx == -1)
      idx = idx2;           // idx delineates where the enum string ends. If it is not present, but idx2 is, it must be equal to idx2.
    
    if (!valid(str)) return;
    
    this.value = Attribute.valueOf(str.substring(0,idx));
    if (idx < str.length() - 1 && idx != idx2)
      this.amount = str.charAt(idx+1) - '0';
    if (idx2 < str.length() - 1)
      this.duration = str.charAt(idx2+1) - '0';
    
    if (Main.program.equipsPage != null)    // This causes problems if not present when loading the program. It's 'cause I'm not being careful about protecting my cross-class fields, etc. I shouldn't even be able to do what I'm doing here.
      Main.program.equipsPage.get(Main.program.cursor.y).sortFields();
  }

  @Override
  public boolean valid(String str) {
    boolean digit1 = false;
    boolean digit2 = false;
    boolean attribute = false;
    int idx = str.indexOf("+");
    int idx2 = str.indexOf(":");
    
    // If a +# is present in the string, verify it is a legal digit char.
    if (idx > -1) {
      if (idx < str.length() - 1) {
        if (str.charAt(idx+1) - '0' <= '9')
          digit1 = true;
      }
    } else  // No :# was present; this is a legal string.
      digit1 = true;
    
    // If a :# is present in the string, verify it is a legal digit char.
    if (idx2 > -1) {
      if (idx2 < str.length() - 1) {
        if (str.charAt(idx2+1) - '0' <= '9')
          digit2 = true;
      }
    } else  // No :# was preset; this is a legal string.
      digit2 = true;
    
    // Verify the string refers to an Attribute proper.
    try {
      if (idx == -1) idx = str.length();
      if (idx2 == -1) idx2 = str.length();
      idx = Math.min(idx, idx2);
      Attribute.valueOf(str.substring(0,idx));
      attribute = true;
    } catch (IllegalArgumentException e) { }
    
    return digit1 && digit2 && attribute;
  }

  @Override
  public String value() {
    String r = "";
    if (this.value != Attribute.None)
      r = this.value.toString() + "+" + this.amount + ":" + this.duration;
    return r;
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
  public void increment() {
    this.amount++;
    if (this.amount > Max_Amount)
      this.amount = 0;
  }

  @Override
  public void decrement() {
    this.amount--;
    if (this.amount < 0)
      this.amount = Max_Amount;
  }

  @Override
  public void incrementAlt() {
    this.duration++;
    if (this.duration > Max_Duration)
      this.duration = 0;
  }

  @Override
  public void decrementAlt() {
    this.duration--;
    if (this.duration < 0)
      this.duration = Max_Duration;
  }

  @Override
  public String displayString() {
    String r = "";
    if (this.value != Attribute.None) {
      r = this.value.toString();
      if (this.amount > 0)
        r += "+" + this.amount;
      if (this.duration > 0)
        r +=  ":" + this.duration;
    }
    return r;
  }

  @Override
  public String displayHeader() {
    return "Attr";
  }

  @Override
  public int displayWidth() {
    return DisplaySettings.Attr_Chunk;
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
    return "Increment";
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
    return DataField.FieldType.Attribute;
  }

  public int getOrdinal() {
    int n = 0;
    if (this.value == Attribute.None)
      n = Integer.MAX_VALUE;
    else
      n = this.value.ordinal();
    return n;
  }
  
  // If ever important: This does not incorporate duration or intensity into the comparison!
  public boolean equals(AttributeVal other) {
    boolean r = false;
    if (this.value == other.value)
      r = true;
    return r;
  }
}