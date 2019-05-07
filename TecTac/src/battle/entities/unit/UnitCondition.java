/*
 * Models the unit's current condition, including health, remaining ammunition, status effects, etc.
 */

package battle.entities.unit;

import battle.utils.AttributeTag;
import battle.utils.AttributeTagList;
import utils.Common;

public class UnitCondition extends UnitData {

  short HP, maxHP;
  short AP, maxAP;
  AttributeTagList attributes;
  boolean KO;     // Stands for Knock-Out or whatever it does, actually represents death.
  
  
  public UnitCondition(Unit parent) {
    super(parent);
  }
  
  public UnitCondition(Unit parent, String data) {
    super(parent, data);
  }
  
  @Override
  protected void init() {
    // Nothing, I think?
  }

  @Override
  void load(String data) {
    // TODO Load some shits, broby.
  }
  
  @Override
  String save() {
    // TODO Save the data, broilban.
    return "";
  }

  @Override
  void generateFalseData() {
    // How do I guarantee that condition is instantiated after stats?
    // I guess stats could be held inside condition, like it was before. Hm.
    maxHP = parent.getStats().getMaxHP();
    maxAP = parent.getStats().getMaxAP();
    HP = maxHP;
    AP = maxAP;
    attributes = new AttributeTagList();
    KO = false;
  }
  
  public boolean isKOed() {
    return this.KO;
  }
  
  public int getHP() {
    return HP;
  }
  
  /* Returns an int for the unit's maximum HP.
   * Redundant because UnitStats already knows, buuut this is a little more convenient.
   */
  public int getMaxHP() {
    return maxHP;
  }
  
  public int getAP() {
    return AP;
  }
  
  public int getMaxAP() {
    return maxAP;
  }
  
  public AttributeTagList getAttributes() {
      // Currently outside sources can add tags as they please. Should they have to go through a rigorous add method?
    //AttributeTagList result = new AttributeTagList();
    //result.copy(attributes);
    return attributes; 
  }
  
  public void damage(int dmg) {
    if (KO) return;
    
    HP -= dmg;
    HP = (short)Common.constrain(HP, 0, maxHP);
    // TODO Check for KO.
  }
  
  public boolean hasEnoughAP(int cost) {
    boolean result = false;
    if (AP >= cost) result = true;
    return result;
  }
  
  public void spendAP(int amount) {
    if (KO) return;
    
    if (AP > 0)
      AP -= amount;
    else
      ; // Throw exception or something. This shouldn't happen.
  }
  
  public void refillAP() {
    if (KO) return;
    
    AP = maxAP;
  }
  
  // Use to inflict status effects to the list.
  public void addStatusEffect(AttributeTag tag) {
    if (KO) return;
    attributes.add(tag);
  }
  
  // Use to cure status effects from the list.
  public void removeStatusEffect(AttributeTag tag) {
    if (KO) return;
    attributes.remove(tag);
  }
  
  // Removes all status effects from the attributes list.
  public void removeAllStatusEffects() {
    attributes.removeAll();
  }
  
  // This is where count-down timers and such would, you know, count down.
  public void update() {
    attributes.updateTimers();
  }  
}
