/*
 * Models equipment boys.
 */

package battle.entities.unit;

import java.util.ArrayList;
import java.util.List;

import battle.actions.EquipID;
import battle.actions.EquipmentDatabaseManager;
import battle.utils.AttributeTagList;

public class UnitEquipment extends UnitData {

  static final int MAX_EQUIPS = 3;
  static final int MAX_ACTIONS = 2;
  static final int MAX_PASSIVES = 3;
  
  List<EquipID> actions;          // These last two are a relic, I'm sure.
  List<EquipID> passiveEffects;   // Not sure that I really need them.
  boolean dataLoaded = false;
  
  
  public UnitEquipment(Unit parent) {
    super(parent);
  }
  
  public UnitEquipment(Unit parent, String data) {
    super(parent, data);
  }
  
  @Override
  protected void init() {
    actions = new ArrayList<EquipID>();
    passiveEffects = new ArrayList<EquipID>();
  }

  /* This parses a list of EquipIDs from a String of them separated by commas.
   * TODO Make sure endOfList is handled properly via this method's implementation.
   * @see battle.entities.unit.UnitInfo#load(java.lang.String)
   */
  @Override
  void load(String data) {
    String tmp;
    int idx;
    while (data.length() > 0 && data != ";") {
      idx = data.indexOf(",");
      tmp = data.substring(0, idx);
      equip(new EquipID(tmp));
      data = data.substring(idx+1);   // This shouldn't be outOfBounds, but be careful.
    }
  }
  
  /* This writes and returns a String of EquipIDs equivalent to the ones this object holds in memory,
   * separated by commas and terminated by a semicolon. 
   */
  public String save() {
    String result = "";
    for (EquipID e : actions) {
      result += e.getID() + ",";
    }
    for (EquipID e : passiveEffects) {
      result += e.getID() + ",";
    }
    result += ";";
    return result;
  }

  @Override
  void generateFalseData() {
    EquipmentDatabaseManager edm = EquipmentDatabaseManager.instance();
    this.actions.add(edm.randomActionID());
    this.actions.add(edm.randomActionID());
    this.passiveEffects.add(edm.randomPassiveID());
    this.passiveEffects.add(edm.randomPassiveID());
    this.passiveEffects.add(edm.randomPassiveID());
  }
  
  /* Returns a list of ActionData objects, a copy of the attached unit's performable actions.
   */
  public List<EquipID> getCommands() {
    ArrayList<EquipID> list = new ArrayList<EquipID>();
    list.addAll(this.actions);
    return list;
  }
  
  /* Returns a list of PassiveData objects, a copy of the attached unit's list of supplemental effects. 
   */
  public List<EquipID> getPassives() {
    ArrayList<EquipID> list = new ArrayList<EquipID>();
    list.addAll(this.passiveEffects);
    return list;
  }
  
  /* Equips an equipment item by sorting and adding its ID to the appropriate ID list.
   * Will refuse if ID list is already full.
   */
  public void equip(EquipID id) {
    if (canEquip(id)) {
      if (id.isAction())
        actions.add(id);
      else
        passiveEffects.add(id);
    }
    else
      ; // TODO Throw Exception? Return false?
  }
  
  /* Takes in an EquipID and requests that the appropriate list<EquipID> finds and removes an
   * equivalent EquipID from its list of IDs.
   */
  public void deEquip(EquipID id) {
    if (id.isAction())
      actions.remove(id);
    else
      passiveEffects.remove(id);
  }
  
  /* Returns true if the given EquipID can be added to the unit's equipment list in
   * accordance with rules about the number of commands vs. passive effects and max equips.
   */
  public boolean canEquip(EquipID id) {
    boolean result = false;
    
    if (actions.size() + passiveEffects.size() < MAX_EQUIPS) {
      if (id.isAction() && actions.size() < MAX_ACTIONS)
        result = true;
      if (id.isPassive() && passiveEffects.size() < MAX_PASSIVES)
        result = true;
    }
    
    return result;
  }
  
  /* Iterates over all passive equips and returns the aggregate defense value.
   */
  public int getDEF() {
    EquipmentDatabaseManager edm = EquipmentDatabaseManager.instance();
    int DEF = 0;
    
    for (EquipID equip : passiveEffects) {
      edm.using(equip);
      DEF += edm.getDEF();
    }
    
    return DEF;
  }
  
  /* Iterates over all passive equips and returns the aggregate resistance value.
   */
  public int getRES() {
    EquipmentDatabaseManager edm = EquipmentDatabaseManager.instance();
    int RES = 0;
    
    for (EquipID equip : passiveEffects) {
      edm.using(equip);
      RES += edm.getRES();
    }
    
    return RES;
  }
  
  /* Gets a list of all valid/enabled attributes applied to this unit through their equipment.
   */
  public AttributeTagList getAttributes() {
    EquipmentDatabaseManager edm = EquipmentDatabaseManager.instance();    
    AttributeTagList result = new AttributeTagList();
    
    for (EquipID equip : passiveEffects) {
      edm.using(equip);
      result.addAll(edm.getAttributes());
    }
    return result;
  }
  
}
