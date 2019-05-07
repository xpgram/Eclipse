package battle.actions;

public class ItemID {

  public static final String NONVALID_ID = "NONV";
  
  String ID;
  
  
  public ItemID(String str) {
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
    // TODO Ask ItemDatabase to check its list?
  }
  
  /* Returns the naked string ID.
   */
  public String getID() {
    return ID;
  }
  
  /* Returns true if this ID matches the string ID in the given EquipID object.
   */
  public boolean equals(EquipID id) {
    return (this.ID == id.getID());
  }
  
}
