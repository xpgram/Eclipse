package main;

public class BlastGridCellVal implements DataField {

  int priority;
  int intensity;
  AoEMap map;     // Handle for the parent object keeping track of this one.
  
  public static final int Max_Priority = 9;
  public static final int Min_Priority = 1;
  public static final int Null_Priority = 0;
  
  public static final int Max_Intensity = 100;
  public static final int Min_Intensity = 5;
  public static final int Intensity_Rate = 5;
  
  public BlastGridCellVal(AoEMap map) {
    this.map = map;
    reset();
  }
  
  @Override
  public void reset() {
    this.priority = Null_Priority;
    this.intensity = Max_Intensity;
  }

  @Override
  public void submit(String str) {
    if (valid(str)) {
      this.priority = utils.Common.charToDigit(str.charAt(0));
      this.intensity = utils.Common.charToDigit(str.charAt(2)) * 10 + utils.Common.charToDigit(str.charAt(3));
      
      if (this.intensity % Intensity_Rate != 0)
        this.intensity = Max_Intensity;
      
      if (this.intensity == 0)
        this.intensity = 100;
    }
  }

  @Override
  public boolean valid(String str) {
    boolean r = false;
    
    if (str.length() == 4) {
      if ( utils.Common.charIsDigit(str.charAt(0)) &&
           str.charAt(1) == ':' &&
           utils.Common.charIsDigit(str.charAt(2)) &&
           utils.Common.charIsDigit(str.charAt(3)) )
        r = true;
    }
    
    return r;
  }

  @Override
  public String value() {
    String str = "";
    str += this.priority + ":";
    if (this.intensity < 100)
      str += String.format("%02d", this.intensity);
    else
      str += "00";
    return str;
  }

  @Override
  public void tap() {
    if (this.priority == Null_Priority) {
      this.priority = 1;
      this.intensity = Max_Intensity;
    }
    else
      this.priority = Null_Priority;
    
    map.updateMinimap();
  }

  @Override
  public void tapAlt() { }

  @Override
  public void del() {
    reset();
    map.updateMinimap();
  }

  @Override
  public void increment() {
    if (this.priority == Null_Priority) return;
    
    this.priority++;
    if (this.priority > Max_Priority)
      this.priority = Min_Priority;
  }

  @Override
  public void decrement() {
    if (this.priority == Null_Priority) return;
    
    this.priority--;
    if (this.priority < Min_Priority)
      this.priority = Max_Priority;
  }
  
  @Override
  public void incrementAlt() {
    if (this.priority == Null_Priority) return;
    
    this.intensity += Intensity_Rate;
    if (this.intensity > Max_Intensity)
      this.intensity = Min_Intensity;
  }

  @Override
  public void decrementAlt() {
    if (this.priority == Null_Priority) return;
    
    this.intensity -= Intensity_Rate;
    if (this.intensity < Min_Intensity)
      this.intensity = Max_Intensity;
  }

  @Override
  public String displayString() {
    return value();
  }

  @Override
  public String displayHeader() {
    return "";
  }

  @Override
  public int displayWidth() {
    return DisplaySettings.MapCell_Chunk;
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
    return FieldType.BlastCell;
  }

}
