/*
 * This class is an info container that defines what its associated action does.
 * Executing special-effects happens elsewhere, this class can't run scripts, but
 * it will tell you about which ones are useable.
 * 
 * There are four elements to every equipment set.
 *  - UnitEquips    :Remembers what is equipped through IDs and handles artificial limitations.
 *  - Equip ID      :Tells us what equipment to load, and either knows or can retrieve what kind it is (active or passive).
 *  - ActionData    :Tells us everything we need to know about what the action does.
 *  - PassiveData   :Tells us everything we need to know about how the equip affects its wearer.
 */

package battle.actions;

import battle.constants.TargetType;
import battle.utils.Attribute;
import battle.utils.AttributeTagList;

public class ActionData {
  
  TargetType targetType;
  
  String name;
  String description;
  //String shortDesc;
  //int sellValue;
  int POW;
  int ACC;
  int AP;
  int range;
  int nullRange;
  //int DEF;
  //int RES;
  AreaEffectMap areaMap;
  AttributeTagList attributes;
  //boolean passiveEffect;
  
  //Sprite icon;        // Little icon. Used in menus.
  //ActionAnim anim;    // Used in battle, eye candy. Useless(?) for passives.
  
  // TODO Merge Action/Passive Data with EquipData.
  // Equip data knows everything relevant to a piece.
  //   passivity
  //   name, description and sell value
  //   stats
  //   AoE map
  //   List of special attributes
  //   What kind of target can be struck
  //   Stat requirements and XP levels
  // This should be fine because there's only ever one copy of this data
  // sitting in memory. The fact that some of these data points will be
  // useless for passives or vice versa shouldn't factor much.
  // 
  // But, this way, I don't have to worry about mixing classes. EquipData
  // handles everything, and it's all accessed upon request, anyway.
  
  
  public ActionData(EquipID id) {
    load(id);
  }
  
  public ActionData() {
    generateFalseData();
  }
  
  public void load(EquipID id) {
    // TODO Load an action's data based on a unique ID
  }
  
  public void generateFalseData() {
    String[] mockNames = {"Iron Sword", "Fire", "Blowback Pistol", "Gunhilde", "Fyrnpoly"};
    this.name = mockNames[0]; // TODO randomize
    this.description = "Gets up in the other guy, and just when he's like \"Oh, this is probably fine,\" it gives him a real good poke, and then he's like \"Oh man, this isn't fine at all\" 'cause now he been got when he ain't been got before.";
    
    POW = 2;    // TODO Randomize this too
    ACC = 70;   // TODO Randomize everything, actually.
    AP = 1;
    range = 3;
    nullRange = 1;
    areaMap = new AreaEffectMap();
    attributes = new AttributeTagList();
  }
  
  public TargetType getTargetType() {
    return targetType;
  }
  
  public String getName() {
    return name;
  }
  
  public String getDescription() {
    return description;
  }
  
  public int getPOW() {
    return POW;
  }
  
  public int getACC() {
    return ACC;
  }
  
  public int getAP() {
    return AP;
  }
  
  public int getRange() {
    return range;
  }
  
  public int getNullRange() {
    return nullRange;
  }
  
  public AreaEffectMap getAreaMap() {
    return areaMap;
  }
  
  /* Returns a copy of the entire list of attributes held by this equipment.
   * I return a copy because, since all these effects are defined parts of their weapons,
   * I want them to be read only.
   */
  public AttributeTagList getAttributes() {
    AttributeTagList result = new AttributeTagList();
    result.copy(attributes);
    return result;
  }
  
  // Returns true if a valid instance of Attribute att is found in the list of effects. 
  public boolean containsAttribute(Attribute a) {
    return attributes.contains(a);
  }

}