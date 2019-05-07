/*
 * This class represents a pointer to some snippet of data held in the equip item database.
 * This datatype is what gets passed around between units, shops, treasure chests, etc.
 * Accessing the data pointed to requires asking the equip item database manager to
 * retrieve it for you.
 * 
 * Because Commands and Accessories (Active v Passive) equips handle themselves and their
 * data slightly differently (and thus require different classes to represent them), an
 * equip's passivity--the type of equip it is--is built directly into the string ID.
 * 
 * TODO A built in string-validation function is named but not implemented.
 */

package battle.actions;

public class EquipID {

  public static final String NONVALID_ID = "NONV";
  
  String ID;
  
  
  public EquipID(String str) {
    ID = str;
    validate();
  }
  
  /* Checks that this object's string ID points to real item data in the database.
   * If not, throws an exception/error.
   */
  private void validate() {
    if (ID == NONVALID_ID) {
      // Do nothing. Pass.
    }
    // TODO Ask EquipDatabase to check its list?
  }
  
  /* Returns the naked string ID.
   */
  public String getID() {
    return ID;
  }
  
  /* Confirms this ID refers to an action command.
   * Passivity is built into the string ID.
   */
  public boolean isAction() {
    return (this.ID.substring(0,1) == "A");
  }
  
  /* Confirms this ID refers to a passive effect.
   * Passivity is built into the string ID.
   */
  public boolean isPassive() {
    return (this.ID.substring(0,1) == "P");
  }
  
  /* Confirms this ID refers to a special function.
   * Flags for functions are built into the string ID.
   */
  public boolean isSpecial() {
    return (this.ID.substring(0,1) == "F");
  }
  
  /* Returns true if this ID matches the string ID in the given EquipID object.
   */
  public boolean equals(EquipID id) {
    return (this.ID == id.getID());
  }
  
}
