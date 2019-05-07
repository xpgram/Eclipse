package main;

public class EIDVal implements DataField {

  Type etype;
  int value;
  
  public static final int Default_Val = 001;
  public static final int Max_Val = 999;
  
  public EIDVal() {
    reset();
  }
  
  @Override
  public void reset() {
    this.etype = Type.Null;
    this.value = Default_Val;
  }

  @Override
  public void submit(String str) {
    str.toUpperCase();
    if (valid(str)) {
      this.etype = getEType(str.charAt(0));
      this.value = Integer.valueOf(str.substring(1,4));
    }
  }

  @Override
  public boolean valid(String str) {
    boolean r = false;
    char c1, c2, c3, c4;
    
    if (str.length() == 4) {
      c1 = str.charAt(0);
      c2 = str.charAt(1);
      c3 = str.charAt(2);
      c4 = str.charAt(3);
      
      if ((c1 == 'W' || c1 == 'A' || c1 == 'T' || c1 == 'X' || c1 == 'P') &&
          isDigit(c2) &&
          isDigit(c3) &&
          isDigit(c4))
        r = true;
    }
    
    return r;
  }
  
  private boolean isDigit(char c) {
    return (c >= '0' && c <= '9');
  }

  @Override
  public String value() {
    return "" + getLetterCode() + String.format("%03d", this.value);
  }

  @Override
  public void tap() { }
  
  @Override
  public void tapAlt() { }

  @Override
  public void del() {
    Main.databook.deleteEquipment(this);
  }
  
  @Override
  public void increment() {
    EIDVal newID = new EIDVal();
    newID.clone(this);
    newID.value--;
    Main.databook.moveEquipment(this, newID);
    //Main.program.moveCursorToID(this);
    Main.program.arrowUp();
  }

  @Override
  public void decrement() {
    EIDVal newID = new EIDVal();
    newID.clone(this);
    newID.value++;
    Main.databook.moveEquipment(this, newID);
    //Main.program.moveCursorToID(this);
    Main.program.arrowDown();
  }

  @Override
  public void incrementAlt() { }    // I would ~probably~ use this for select mode, if I were to implement such a feature.

  @Override
  public void decrementAlt() { }

  @Override
  public String displayString() {
    return value();
  }

  @Override
  public String displayHeader() {
    return "EID";
  }

  @Override
  public int displayWidth() {
    return DisplaySettings.Std_Chunk;
  }

  @Override
  public String contextEnter() {
    return "Edit";
  }

  @Override
  public String contextDel() {
    return "Delete";
  }

  @Override
  public String contextArrows() {
    return "Move";
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
    return FieldType.EID;
  }

  public boolean isWeapon() {
    return (this.etype == Type.Weapon || this.etype == Type.Enemy);
  }
  
  public Type getEType(char c) {
    Type r = Type.Weapon;
    switch (c) {
      case 'W':
        break;
      case 'A':
        r = Type.Accessory;
        break;
      case 'T':
        r = Type.Trait;
        break;
      case 'X':
        r = Type.Enemy;
        break;
      case 'P':
        r = Type.Property;
        break;
      default:
    }
    return r;
  }
  
  public char getLetterCode() {
    char r = ' ';
    switch (this.etype) {
      case Weapon:
        r = 'W';
        break;
      case Accessory:
        r = 'A';
        break;
      case Trait:
        r = 'T';
        break;
      case Enemy:
        r = 'X';
        break;
      case Property:
        r = 'P';
        break;
      case Null:
        r = ' ';
        break;
    }
    return r;
  }
  
  public void reassignID(int ID) {
    if (ID > 0 && ID <= Max_Val)
      this.value = ID;
  }
  
  // Returns 1 if this object comes after the given EID in order, -1 if before, and 0 if they are equal.
  public int compareTo(EIDVal EID) {
    int r = 0;
    
    // Determine order, focusing on type-then-value.
    if (this.etype.ordinal() > EID.etype.ordinal())
      r = 1;
    else if (this.etype.ordinal() < EID.etype.ordinal())
      r = -1;
    else if (this.value > EID.value)
      r = 1;
    else if (this.value < EID.value)
      r = -1;
    
    return r;
  }
  
  // Copies the contents of the given EID
  public void clone(EIDVal EID) {
    this.etype = EID.etype;
    this.value = EID.value;
  }
  
  public boolean isNull() {
    return (this.etype == Type.Null);
  }
  
  public static enum Type {
    Null,
    Weapon,
    Accessory,
    Trait,
    Enemy,
    Property
  }
}
