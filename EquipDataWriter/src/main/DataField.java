package main;

public interface DataField {

  // Data read/write
  public void reset();
  public void submit(String str);
  public boolean valid(String str);
  public String value();
  
  // Controls
  public void tap();
  public void tapAlt();
  public void del();
  public void increment();
  public void decrement();
  public void incrementAlt();
  public void decrementAlt();
  
  // Display values
  public String displayString();
  public String displayHeader();
  public int displayWidth();
  
  // Context UI values
  public String contextEnter();
  public String contextDel();
  public String contextArrows();
  
  // Metadata
  public boolean editable();
  public boolean strictlyNumerical();
  public FieldType getType();
  
  public static enum FieldType {
    Generic,
    EID,
    Name,
    Description,
    DumbDescription,
    StatVal,
    Toggle,
    CycleList,
    Range,
    BlastRadius,
    TargetType,
    Value,
    Attribute,
    Req,
    XP,
    ComboReq,
    BlastCell,
    MiniMap,
    WeaponClass,
    WeightClass,
    RangeClass,
    DamageType
  }
  
}
