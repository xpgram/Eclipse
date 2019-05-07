package main;

import java.util.ArrayList;

/* This is the weapon/accessory data container.
 */

public class EquipData {

  // I, uh... I'm not sure this is elegant. But these tags are how fields are segmented in written-to-file form.
  public static final String Tag_End = "<e>";
  public static final String Tag_EID = "<id>";
  public static final String Tag_Name = "<n>";
  public static final String Tag_Desc = "<desc>";
  public static final String Tag_DumbDesc = "<desc2>";
  public static final String Tag_AP = "<ap>";
  public static final String Tag_POW = "<pow>";
  public static final String Tag_DEF = "<def>";
  public static final String Tag_BAR = "<bar>";
  public static final String Tag_Range = "<r>";
  public static final String Tag_TargetType = "<targ>";
  public static final String Tag_ShopValue = "<val>";
  public static final String Tag_WeaponClass = "<dmg>";
  public static final String Tag_RangeClass = "<rtype>";
  public static final String Tag_DamageType = "<dtype>";
  public static final String Tag_WeightClass = "<wt>";
  public static final String Tag_Attributes = "<attr>";
  public static final String Tag_Req1 = "<req1>";
  public static final String Tag_Req2 = "<req2>";
  public static final String Tag_XP1 = "<xp1>";
  public static final String Tag_XP2 = "<xp2>";
  public static final String Tag_Combo1 = "<com1>";
  public static final String Tag_Combo2 = "<com2>";
  public static final String Tag_wType = "<wtype>";
  public static final String Tag_AoE = "<aoe>";
  
  // Fields actually relevant to the storing of equipment stats and effects.
  public EIDVal EID;
  public NameVal name;
  public DescVal description;
  public DumbDescVal dumbDescription;
  public StatVal AP;
  public StatVal POW;
  public StatVal DEF;
  public StatVal BAR;
  public RangeVal Range;
  public static BlastRadiusVal Radius = new BlastRadiusVal();
  public ToggleVal PickDir;
  public ToggleVal SpecialBlast;
  public CycleVal<TargetType> targetType;  // An enum specifying what kind of selectable tile this "action" may be used against.
  public ShopVal shopValue;     // The purchase amount in "Rones." This is the base value, it is chopped in half when selling back to the shop.
  public CycleVal<WeaponClass> weaponClass;
  public CycleVal<DamageType> damageType;
  public CycleVal<RangeClass> rangeClass;
  public CycleVal<WeightClass> weightClass;
  public ReqVal Req1;
  public ReqVal Req2;
  public XPVal XP1;
  public XPVal XP2;
  public ComboVal combo1;   // These should be put in a [3] size array, much like attributes are.
  public ComboVal combo2;   // They should also sort the same way that attributes do.
  public ComboVal wType;    // In fact, they should sort in the same function that they do.      Not wType, tho.
  public AoEMap blastmap;
  public MinimapVal minimap;
  
  public ArrayList<DataField> CoreStats;
  public ArrayList<DataField> Properties;
  public ArrayList<DataField> Attributes;
  public ArrayList<DataField> Requirements;
  public ArrayList<DataField> Description;
  public ArrayList<DataField> Description2;
  public ArrayList<DataField> AoE;
  public ArrayList<DataField> AoEHeader;
  
  public static final int Max_Attributes = 4;
  public AttributeVal[] attributes = new AttributeVal[Max_Attributes];    // A list of all special effects present on this equip.
  
  // Constructor! Initialize all fields!
  public EquipData() {
    init();
    reset();
    
    // 10 fields in CoreStats view
    CoreStats.add(EID);
    CoreStats.add(name);
    CoreStats.add(AP);
    CoreStats.add(POW);
    CoreStats.add(DEF);
    CoreStats.add(BAR);
    CoreStats.add(Range);
    CoreStats.add(minimap);
    CoreStats.add(weightClass);
    CoreStats.add(targetType);
    CoreStats.add(shopValue);
    
    // 6 fields in Properties view
    Properties.add(EID);
    Properties.add(name);
    Properties.add(weaponClass);
    Properties.add(rangeClass);
    Properties.add(damageType);
    Properties.add(weightClass);
    
    // 6 fields in Attributes view
    Attributes.add(EID);
    Attributes.add(name);
    Attributes.add(attributes[0]);
    Attributes.add(attributes[1]);
    Attributes.add(attributes[2]);
    Attributes.add(attributes[3]);
    
    // 9 fields in Requirements view
    Requirements.add(EID);
    Requirements.add(name);
    Requirements.add(Req1);
    Requirements.add(Req2);
    Requirements.add(XP1);
    Requirements.add(XP2);
    Requirements.add(combo1);
    Requirements.add(combo2);
    Requirements.add(wType);
    
    // 3 fields in Description view
    Description.add(EID);
    Description.add(name);
    Description.add(description);
    
    // 3 fields in Description view
    Description2.add(EID);
    Description2.add(name);
    Description2.add(dumbDescription);
    
    // 2 fields in AoE view
    AoE.add(EID);
    AoE.add(name);
    
    // 4 fields in AoEHeader
    AoEHeader.add(Range);
    AoEHeader.add(Radius);
  }
  
  /* Writes the equip's data into one string object, which you could put into a file maybe I don't know.
   */
  public String write() {
    String str = "";
    
    removeDuplicateFields();
    sortFields();
    
    str += Tag_EID + EID.value() + Tag_End;
    str += Tag_Name + name.value() + Tag_End;
    str += Tag_Desc + description.value() + Tag_End;
    str += Tag_DumbDesc + dumbDescription.value() + Tag_End;
    str += Tag_AP + AP.value() + Tag_End;
    str += Tag_POW + POW.value() + Tag_End;
    str += Tag_DEF + DEF.value() + Tag_End;
    str += Tag_BAR + BAR.value() + Tag_End;
    str += Tag_Range + Range.value() + Tag_End;
    str += Tag_TargetType + targetType.value() + Tag_End;
    str += Tag_ShopValue + shopValue.value() + Tag_End;
    str += Tag_WeaponClass + weaponClass.value() + Tag_End;
    str += Tag_RangeClass + rangeClass.value() + Tag_End;
    str += Tag_DamageType + damageType.value() + Tag_End;
    str += Tag_WeightClass + weightClass.value() + Tag_End;
    str += Tag_Req1 + Req1.value() + Tag_End;
    str += Tag_Req2 + Req2.value() + Tag_End;
    str += Tag_XP1 + XP1.value() + Tag_End;
    str += Tag_XP2 + XP2.value() + Tag_End;
    str += Tag_Combo1 + combo1.value() + Tag_End;
    str += Tag_Combo2 + combo2.value() + Tag_End;
    str += Tag_wType + wType.value() + Tag_End;
    str += Tag_AoE + blastmap.valueString() + Tag_End;
    
    str += Tag_Attributes;
    AttributeVal attrNone = new AttributeVal();
    for (int i = 0; i < attributes.length; i++)
      if (attributes[i].equals(attrNone) == false)
        str += attributes[i].value() + ";";
    str += Tag_End;
    
    return str;
  }
  
  /* Reads equip data from a singular string object, and separates it into this object's distinct fields.
   */
  public void read(String str) {
    EID.submit(getValue(Tag_EID, str));
    name.submit(getValue(Tag_Name, str));
    description.submit(getValue(Tag_Desc, str));
    dumbDescription.submit(getValue(Tag_DumbDesc, str));
    AP.submit(getValue(Tag_AP, str));
    POW.submit(getValue(Tag_POW, str));
    DEF.submit(getValue(Tag_DEF, str));
    BAR.submit(getValue(Tag_BAR, str));
    Range.submit(getValue(Tag_Range, str));
    targetType.submit(getValue(Tag_TargetType, str));
    shopValue.submit(getValue(Tag_ShopValue, str));
    weaponClass.submit(getValue(Tag_WeaponClass, str));
    rangeClass.submit(getValue(Tag_RangeClass, str));
    damageType.submit(getValue(Tag_DamageType, str));
    weightClass.submit(getValue(Tag_WeightClass, str));
    Req1.submit(getValue(Tag_Req1, str));
    Req2.submit(getValue(Tag_Req2, str));
    XP1.submit(getValue(Tag_XP1, str));
    XP2.submit(getValue(Tag_XP2, str));
    combo1.submit(getValue(Tag_Combo1, str));
    combo2.submit(getValue(Tag_Combo2, str));
    wType.submit(getValue(Tag_wType, str));
    blastmap.submit(getValue(Tag_AoE, str));
    
    // Parse Attributes List.
    String strA = getValue(Tag_Attributes, str);      // This rids us of all other data besides attributes, but at this point we shouldn't need it.
    int eidx, sidx, dur;     // End index, Separator Index
    for (int i = 0; i < Max_Attributes; i++) {
      if (strA.length() == 0) break;      // If no more attributes are listed, stop adding them.
      
      eidx = strA.indexOf(';');
      sidx = strA.indexOf(':');
      if (eidx == -1 || sidx == -1) break;         // TODO Throw exception, or some kind of warning, indicating that (some of) the read-in data is bad/corrupted.
      
      dur = Integer.valueOf(strA.substring(sidx+1, eidx));
      
      attributes[i].submit(strA.substring(0, sidx));
      for (int n = 0; n < dur; n++)
        attributes[i].increment();
      strA = strA.substring(eidx+1, strA.length());
    }
    
    removeDuplicateFields();
    sortFields();
  }
  
  /* I want to limit the amount of re-iterating over fields and tags we've already iterated over.
   * I guess I'll do or add that feature later, though; it's not actually slow enough to matter for my purposes.
   * 
   * This method takes in a searchable tag and returns the (String) value associated with it in the string. Simple. 
   */
  private String getValue(String tag, String data) {
    int pos = data.indexOf(tag);
    int end = data.indexOf(Tag_End, pos);
    if (pos == -1 || end == -1 || end < pos) return "";   // TODO Throw an exception indicating that read-in data was incomplete.
    
    return data.substring(pos + tag.length(), end);
  }
  
  /* Initializes all fields so that they are useable.
   */
  public void init() {
    CoreStats = new ArrayList<DataField>();
    Properties = new ArrayList<DataField>();
    Attributes = new ArrayList<DataField>();
    Requirements = new ArrayList<DataField>();
    Description = new ArrayList<DataField>();
    Description2 = new ArrayList<DataField>();
    AoE = new ArrayList<DataField>();
    AoEHeader = new ArrayList<DataField>();
    
    EID = new EIDVal();
    name = new NameVal();
    description = new DescVal();
    dumbDescription = new DumbDescVal();
    AP = new StatVal("AP");
    POW = new StatVal("POW");
    DEF = new StatVal("DEF");
    BAR = new StatVal("BAR");
    Range = new RangeVal();
    targetType = new CycleVal<TargetType>(TargetType.None);
    shopValue = new ShopVal();
    weaponClass = new CycleVal<WeaponClass>(WeaponClass.None);
    rangeClass = new CycleVal<RangeClass>(RangeClass.None);
    damageType = new CycleVal<DamageType>(DamageType.None);
    weightClass = new CycleVal<WeightClass>(WeightClass.None);
    Req1 = new ReqVal();
    Req2 = new ReqVal();
    XP1 = new XPVal();
    XP2 = new XPVal();
    combo1 = new ComboVal();
    combo2 = new ComboVal();
    wType = new ComboVal(true);
    minimap = new MinimapVal();
    blastmap = new AoEMap(minimap);
    
    for (int i = 0; i < Max_Attributes; i++)
      attributes[i] = new AttributeVal();
  }
  
  /* Sets all fields to their default values. Some of these default values are invalid on purpose; the writer must fill them in.
   */
  public void reset() {
    EID.reset();
    name.reset();
    description.reset();
    dumbDescription.reset();
    AP.reset();
    POW.reset();
    DEF.reset();
    BAR.reset();
    Range.reset();
    targetType.reset();
    shopValue.reset();
    weaponClass.reset();
    rangeClass.reset();
    damageType.reset();
    weightClass.reset();
    Req1.reset();
    Req2.reset();
    XP1.reset();
    XP2.reset();
    combo1.reset();
    combo2.reset();
    wType.reset();
    minimap.reset();
    blastmap.reset();
    
    for (int i = 0; i < attributes.length; i++)
      attributes[i].reset();
  }
  
  // Retrieves the DataField object pointed to by an x-coordinate in CoreStats view.
  public DataField getCol(int x) {
    ProgramRedux.View view = Main.program.getView();
    
    if (view == ProgramRedux.View.CoreStats) {
      if (x >= 0 && x < CoreStats.size())
        return CoreStats.get(x);
    }
    else if (view == ProgramRedux.View.Properties) {
      if (x >= 0 && x < Properties.size())
        return Properties.get(x);
    }
    else if (view == ProgramRedux.View.Description) {
      if (x >= 0 && x < Description.size())
        return Description.get(x);
    }
    else if (view == ProgramRedux.View.Description2) {
      if (x >= 0 && x < Description.size())
        return Description2.get(x);
    }
    else if (view == ProgramRedux.View.Attributes) {
      if (x >= 0 && x < Attributes.size())
        return Attributes.get(x);
    }
    else if (view == ProgramRedux.View.Requirements) {
      if (x >= 0 && x < Requirements.size())
        return Requirements.get(x);
    }
    else if (view == ProgramRedux.View.AoEMap) {
      if (x >= 0 && x < AoE.size())
        return AoE.get(x);
    }
    
    return null;
  }
  
  // Retrieves the particular view list as determined by the current view.
  public ArrayList<DataField> getFields() {
    ProgramRedux.View view = Main.program.getView();
    ArrayList<DataField> r = new ArrayList<DataField>();
    
    if (view == ProgramRedux.View.CoreStats)
      r.addAll(CoreStats);
    else if (view == ProgramRedux.View.Properties)
      r.addAll(Properties);
    else if (view == ProgramRedux.View.Description)
      r.addAll(Description);
    else if (view == ProgramRedux.View.Description2)
      r.addAll(Description2);
    else if (view == ProgramRedux.View.Attributes)
      r.addAll(Attributes);
    else if (view == ProgramRedux.View.Requirements)
      r.addAll(Requirements);
    else if (view == ProgramRedux.View.AoEMap)
      r.addAll(AoE);
    
    return r;
  }
  
  // Retrieves the DataField object pointed to by an x- and y-coordinate, specifically in AoEMap view.
  public DataField getAoEData(int x, int y) {
    DataField data = null;
    
    if (y == 0) {
      if (x >= 0 && x < AoEHeader.size())
        data = AoEHeader.get(x);
    }
    else {
      y--;
      data = blastmap.getCell(x,y);
    }
    
    return data;
  }
  
  // Sorts the attributes array by ordinal number of the Attribute enum.
  public void sortFields() {
    // Attributes
    AttributeVal tmp;
    for (int i = 1; i < Max_Attributes; i++) {
      for (int j = i-1; j >= 0; j--) {
        if (attributes[j].getOrdinal() > attributes[j+1].getOrdinal()) {
          tmp = attributes[j];
          attributes[j] = attributes[j+1];
          attributes[j+1] = tmp;
        }
      }
    }
    
    // This was a fun bug to fix. Realized I'm an idiot for naming two different arrays the same word.
    Attributes.clear();
    Attributes.add(EID);
    Attributes.add(name);
    Attributes.add(attributes[0]);
    Attributes.add(attributes[1]);
    Attributes.add(attributes[2]);
    Attributes.add(attributes[3]);
    
    // Combos
    if (Req1.getOrdinal() > Req2.getOrdinal()) {
      ReqVal req = Req1;
      Req1 = Req2;
      Req2 = req;
      Requirements.remove(2);
      Requirements.add(2, Req1);
      Requirements.remove(3);
      Requirements.add(3, Req2);
    }
    
    // XPs
    if (XP1.getOrdinal() > XP2.getOrdinal()) {
      XPVal xp = XP1;
      XP1 = XP2;
      XP2 = xp;
      Requirements.remove(4);
      Requirements.add(4, XP1);
      Requirements.remove(5);
      Requirements.add(5, XP2);
    }
    
    // Combo Types
    if (combo1.getOrdinal() > combo2.getOrdinal()) {
      ComboVal com = combo1;
      combo1 = combo2;
      combo2 = com;
      Requirements.remove(6);
      Requirements.add(6, combo1);
      Requirements.remove(7);
      Requirements.add(7, combo2);
    }
  }
  
  // TODO Not yet implemented. Should at least call when saving/loading.
  public void removeDuplicateFields() {
    // Attributes
    for (int i = 1; i < Max_Attributes; i++) {
      for (int j = 0; j < i; j++) {
        if (attributes[i].equals(attributes[j]))
          attributes[j].reset();
      }
    }
    
    // Reqs
    if (Req1.equals(Req2))
      Req2.reset();
    
    // XPs
    if (XP1.equals(XP2))
      XP2.reset();
    
    // Combos
    if (combo1.equals(combo2))
      combo2.reset();
  }
}
