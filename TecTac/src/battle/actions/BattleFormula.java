/*
 * 
 * Alternate names: AttackFormulas, DamageFormulas, etc.
 */

package battle.actions;

import java.util.List;

import battle.entities.unit.Unit;
import battle.utils.Attribute;

public class BattleFormula {
  
  private static final short ELEM_DEF = 2;
  private static final short ELEM_WEAK = 4;

  // Prevents instantiation - there's just no point to it.
  private BattleFormula() {}
  
  /* So, this does the damage doing, I take it? Is that how it should work?
   * I guess, I'm just not sure.
   * 
   * Question: if the target is a location and not a unit, what then? A separate method?
   * 
   * Also, I wrote this early for fun. This main point (other than unit-to-unit apply action) is
   * that AGI and action SPD determine how many "hits" you make, which determines your final damage.
   * For some actions, this could be taken literally. For others, it might be a 0, half, full, 1.5x
   * kind of thing. It's experimental, and doesn't make complete sense, but it enables AGI as a
   * useful stat.
   * 
   * TODO Break up step-chunks into helper methods that can be called in a clean sequence.
   * I guess this is an example of what not to do by that guy.
   * 
   * TODO Include more step-chunks to justify the above. (Counter Attacks? Status Effect Application? Alternative AGI Comparisons?) 
   */
  public static void applyActionEffects(Unit source, Unit[] targets, EquipID action) {
    
    if (action.isAction() != true)
      return;
    
    // EquipID is confirmed not to be a passive effect.
    
    EquipmentDatabaseManager edm = EquipmentDatabaseManager.instance();
    edm.using(action);
    
    Unit target;
    int damage, defense;
    
    for (int i = 0; i < targets.length; i++) {
      target = targets[i];
      
      // Gets either target's DEF or RES depending on which one the action is attacking.
      defense = edm.containsAttribute(Attribute.MagicDamage) ? target.getEquipment().getRES() : target.getEquipment().getDEF();
      if (edm.containsAttribute(Attribute.IgnoreDEF)) defense = 0;
      
      // Determine base damage
      damage = edm.getPOW() - defense;
      
      // Apply Elemental Effects
      {
        List<Attribute> atkElem = edm.getAttributes().parseElementTags();
        List<Attribute> defElem = target.getEquipment().getAttributes().parseElementTags();   // This feels silly, but uh... *shrug*
        List<Attribute> weakElem = target.getCondition().getAttributes().parseElementTags();
        for (Attribute atk : atkElem) {
          for (Attribute def : defElem) {
            if (atk == def) damage -= ELEM_DEF;
          }
          for (Attribute weak : weakElem) {
            if (atk == weak) damage += ELEM_WEAK;
          }
        }
      }
      
      // Fix damage negative range, and apply damage to unit.
      if (damage < 0) damage = 0;
      target.getCondition().damage(damage);
      
      // Check that target survived before continuing, else exit method.
      if (target.getCondition().isKOed()) {
        return;
      }
      
      // TODO Apply status effects.
      
    }
  }
  
}
