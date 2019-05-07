package main;

public class RangeVal implements DataField {

  int valueSub;
  int valueDom;
  Style style;
  
  public static final int Max_Val = 9;
  public static final String Directional_String = "Dir";
  public static final String Confirmable_String = "Cfrm";
  
  public RangeVal() {
    reset();
  }
  
  @Override
  public void reset() {
    this.valueSub = 1;
    this.valueDom = 1;
    this.style = Style.Radial;
  }

  @Override
  public void submit(String str) {
    if (valid(str)) {
      
      if (str.equals(Directional_String)) {
        this.style = Style.Directional;
        return;
      }
      
      if (str.equals(Confirmable_String)) {
        this.style = Style.Confirmable;
        return;
      }
      
      int idx = str.indexOf('-');
      int idxL = str.indexOf('L');
      if (idxL == -1) idxL = str.length();
      
      this.style = Style.Radial;
      this.valueSub = Integer.valueOf(str.substring(0,idx));
      this.valueDom = Integer.valueOf(str.substring(idx+1,idxL));
      if (idxL < str.length())
        this.style = (str.charAt(idxL) == 'L') ? Style.Linear : Style.Radial;
    }
  }
  
  @Override
  public boolean valid(String str) {
    // Check for special states
    if (str.equals(Directional_String)) return true;
    if (str.equals(Confirmable_String)) return true;
    
    // Set up useful numbers
    int n1, n2;
    boolean r = false;
    int idx = str.indexOf('-');
    int idxL = str.indexOf('L');
    if (idxL == -1) idxL = str.length();
    
    // Test format conditions, quit if invalid
    if (idx == -1) return false;
    if (idx == str.length()-1) return false;
    if (idxL <= idx) return false;
    
    // Verify contents of n-nL format string
    try {
      n1 = Integer.valueOf(str.substring(0,idx));
      n2 = Integer.valueOf(str.substring(idx+1, idxL));
      if (n1 <= n2 && n2 <= Max_Val)
        r = true;
    } catch (NumberFormatException e) {}
    
    return r;
  }
  
  @Override
  public String value() {
    if (style == Style.Directional) 
      return Directional_String;
    if (style == Style.Confirmable)
      return Confirmable_String;
    
    String r = "";
    r = String.valueOf(this.valueSub);
    r += "-";
    r += String.valueOf(this.valueDom);
    if (style == Style.Linear)
      r += "L";
    return r;
  }

  @Override
  public void tap() {
    if (style == Style.Radial)
      style = Style.Linear;
    else if (style == Style.Linear)
      style = Style.Directional;
    else if (style == Style.Directional)
      style = Style.Confirmable;
    else if (style == Style.Confirmable)
      style = Style.Radial;
  }
  
  @Override
  public void tapAlt() { }

  @Override
  public void del() {
    reset();
  }
  
  @Override
  public void increment() {
    this.valueDom++;
    
    if (this.valueDom > Max_Val)
      this.valueDom = 0;
    
    if (this.valueSub >= this.valueDom)
      this.valueSub = this.valueDom;
    
    if (this.valueSub == 0 && this.valueDom != 0)
      this.valueSub = 1;
  }
  
  @Override
  public void decrement() {
    this.valueDom--;
    
    if (this.valueDom < 0)
      this.valueDom = Max_Val;
    
    if (this.valueSub >= this.valueDom)
      this.valueSub = this.valueDom;
    
    if (this.valueSub == 0 && this.valueDom != 0)
      this.valueSub = 1;
  }
  
  @Override
  public void incrementAlt() {
    this.valueSub++;
    
    if (this.valueSub > this.valueDom)
      this.valueSub = 1;
    
    if (this.valueDom == 0)
      this.valueSub = 0;
  }

  @Override
  public void decrementAlt() {
    this.valueSub--;
    
    if (this.valueSub < 1)
      this.valueSub = this.valueDom;
    
    if (this.valueDom == 0)
      this.valueSub = 0;
  }

  @Override
  public String displayString() {
    return value();
  }

  @Override
  public String displayHeader() {
    return "Rng";
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
    return false;
  }
  
  @Override
  public FieldType getType() {
    return DataField.FieldType.Range;
  }

  public int getNullRange() {
    return this.valueSub;
  }
  
  public int getRange() {
    return this.valueDom;
  }
  
  public boolean isLine() {
    return (style == Style.Linear);
  }

  public static enum Style {
    Radial,
    Linear,
    Directional,
    Confirmable
  }
  
}
