/*
 * The purpose of this class is just to marry a StatID to an int.
 * This is used (exclusively?) in EquipData to model a requirement:
 *    StatID + int 0 to 5
 * And an XP growth giver maker:
 *    StatID + int 0 to 20 or something. I think it might just be a set value, actually.
 */

package battle.utils;

import battle.entities.unit.StatID;

public class StatVal {

  StatID stat;
  int value;
  
  public StatVal() {
    stat = StatID.PHY;
    value = 1;
  }
  
  /* TODO May want to deprecate this at some point.
   * At some point, all StatID's should be read from a "hard-coded" string.
   */
  public StatVal(StatID id, int val) {
    stat = id;
    value = val;
  }
  
  public StatVal(String data) {
    load(data);
  }
  
  public void load(String data) {
    // TODO Parse the string.
  }
  
  public StatID getStat() {
    return stat;
  }
  
  public int getValue() {
    return value;
  }
  
}
