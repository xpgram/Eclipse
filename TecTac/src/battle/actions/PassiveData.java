package battle.actions;

import battle.utils.Attribute;
import battle.utils.AttributeTagList;

public class PassiveData {

  String name;
  String description;
  int DEF;
  int RES;
  AttributeTagList attributes;
  
  
  public PassiveData(EquipID id) {
    load(id);
  }
  
  public PassiveData() {
    generateFalseData();
  }
  
  public void load(EquipID id) {
    // TODO Load an action's data based on a unique ID
  }
  
  public void generateFalseData() {
    String[] mockNames = {"Iron Armor", "Phase Shield", "Anti-Poison Bracelet"};
    this.name = mockNames[0]; // TODO randomize
    
    DEF = 0;    // TODO Randomize this too
    RES = 0;    // TODO Randomize everything, actually.
    attributes = new AttributeTagList();
  }
  
  public String getName() {
    return name;
  }
  
  public String getDescription() {
    return description;
  }
  
  public int getDEF() {
    return DEF;
  }
  
  public int getRES() {
    return RES;
  }
  
  // TODO remove this method?
  public AttributeTagList getAttributes() {
    AttributeTagList result = new AttributeTagList();
    result.copy(attributes);   // Prevents outside tampering with finalized data.
    return result;
  }
  
  // 
  public boolean containsAttribute(Attribute a) {
    return attributes.contains(a);
  }
  
}
