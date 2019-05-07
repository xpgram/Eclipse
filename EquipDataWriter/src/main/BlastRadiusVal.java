package main;

/*
 * Now that is a toggle with which to help AoEMap build itself and not actually a saveable stat, this should (?) get some extra
 * features with regard to blast type.
 * 
 * I don't know if I wanna put the work in, though.
 * 
 * Anyway, first and only idea:
 *   o - denotes "omitCenter", which is useful, so let's add
 *   L - blast should exist as a line, and
 *   D - priority should depend on distance
 */

public class BlastRadiusVal implements DataField {

  int value;
  boolean omitCenter;
  
  public static final int Max_Val = 9;
  public static final int Min_Val = 0;
  
  public BlastRadiusVal() {
    reset();
  }
  
  @Override
  public void reset() {
    this.value = 1;
    this.omitCenter = false;
  }

  @Override
  public void submit(String str) {
    if (valid(str)) {
      this.value = utils.Common.charToDigit(str.charAt(0));
      if (str.length() > 1)
        this.omitCenter = (str.charAt(1) == 'o');
      if (this.value < Min_Val)
        this.value = Min_Val;
    }
  }
  
  @Override
  public boolean valid(String str) {
    boolean r = false;
    
    if ( str.length() == 1 && utils.Common.charIsDigit(str.charAt(0)) )
      r = true;
    
    if ( str.length() > 1 && utils.Common.charIsDigit(str.charAt(0)) && str.charAt(1) == 'o' )
      r = true;
    
    return r;
  }
  
  @Override
  public String value() {
    String r = "";
    r = String.valueOf(this.value);
    r += (this.omitCenter) ? "o" : "";
    return r;
  }

  @Override
  public void tap() {
    EquipData data = Main.program.viewFocus;
    if (data != null)
      data.blastmap.buildMap(data.Range, EquipData.Radius);
  }
  
  @Override
  public void tapAlt() { }

  @Override
  public void del() {
    reset();
  }
  
  @Override
  public void increment() {
    this.value++;
    
    if (this.value > Max_Val) {
      this.value = Min_Val;
    }
  }
  
  @Override
  public void decrement() {
    this.value--;
    
    if (this.value < Min_Val) {
      this.value = Max_Val;
    }
  }
  
  @Override
  public void incrementAlt() {
    this.omitCenter = !this.omitCenter;
  }

  @Override
  public void decrementAlt() {
    this.omitCenter = !this.omitCenter;
  }

  @Override
  public String displayString() {
    return value();
  }

  @Override
  public String displayHeader() {
    return "Rad";
  }

  @Override
  public int displayWidth() {
    return DisplaySettings.Small_Chunk;
  }

  @Override
  public String contextEnter() {
    return "Build";
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
    return DataField.FieldType.BlastRadius;
  }

  public int getRadius() {
    return this.value;
  }

  public boolean omitCenter() {
    return this.omitCenter;
  }
  
}
