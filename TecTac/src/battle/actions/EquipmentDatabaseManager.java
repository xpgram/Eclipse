/*
 * This class manages equipment data, loading and accessing, and acts as the
 * wrapper that keeps it all neatly confined into one place in memory.
 * Ideally, no object elsewhere in the code should hold a copy of the data
 * held in memory here; you certainly could do that, but there wouldn't be
 * any point to it.
 * 
 * This class' operation can be broken down thusly:
 *   - Equipment data is requested through .using(EquipID); this makes it "active."
 *   - If this manager finds the data for said equipment isn't loaded, it will do so.
 *     This happens internally. A list of EquipIDs can also be pre-loaded at once.
 *   - Once active, normal information retrieving operations may be called:
 *      = getPOW(), getDEF(), getRange(), etc.
 *      = isCommand(), isPassive()
 *      = getName(), getDesc()
 *      You get the point.
 *   - After we are done with this piece of equipment, we may call .using(EquipID)
 *     once more to access a different one.
 *   - Any data we no longer have need of, which may even be all of it, we can
 *     drop from memory easily. .unload(List<EquipID>) or .unloadAll()
 */

package battle.actions;

import java.util.ArrayList;
import java.util.List;

import battle.constants.TargetType;
import battle.utils.Attribute;
import battle.utils.AttributeTagList;
import battle.utils.StatVal;

public class EquipmentDatabaseManager {

  private static EquipmentDatabaseManager edm;
  
  List<EquipData> list;   // The list of references to all relevant (loaded) equipment data.
  EquipData focus;        // All getter methods access this object. New IDs must be requested before accessed.
  String cmdID_Wait = "F001";
  
  
  // Private singleton instantiator.
  private EquipmentDatabaseManager() {
    this.list = new ArrayList<EquipData>();
  }
  
  // Retrieves singleton object. Instantiates one if none exists.
  public static EquipmentDatabaseManager instance() {
    if (EquipmentDatabaseManager.edm == null)
      EquipmentDatabaseManager.edm = new EquipmentDatabaseManager();
    return EquipmentDatabaseManager.edm;
  }
  
  /* Loads data for an EquipID object into memory.
   * TODO If data cannot be found, reports which and throws an exception.
   */
  public void load(EquipID eid) {
    // TODO Load data pointed to by eid.
    
    // Here's how this works.
    // - I have a text file with everything neatly put together.
    // - Maybe a tool with fields helps me write this info, keep it uniform, etc.
    // - This method opens the file for reading, and skips to the EID entry (they're numbered).
    // - Read this info in, reversing the process used to write it.
    // - Store this info in memory.
    // - I have no cap applied at the moment. A cap is implied by the restriction of battle units and maximum commands per unit.
    // - After battle, flush memory entirely.
    
    // So, step #1, I should write a tool that asks me to fill in fields and writes these entries for me. Or even manages them.
    // Let's open python.
  }
  
  /* Loads data for a list of EquipID objects.
   * TODO Keeps track of all IDs which cannot be resolved and reports them before throwing an exception. 
   */
  public void load(List<EquipID> eidlist) {
    for (EquipID e : eidlist)
      this.load(e);
  }
  
  /* Checks the EquipID object against the database.
   * Returns true if a match could be found, false if the ID does not point to data in the database.
   */
  public boolean isIDValid(EquipID eid) {
    return true;
  }
  
  /* Removes data pointed to by EquipID from the list, lettting the garbage collector
   * scrub it from memory.
   */
  public void unload(EquipID eid) {
    this.list.remove(eid);
  }
  
  /* Removes data pointed to by a list of EquipIDs from the list of loaded data, letting the garbage
   * collector scrub it from memory.
   */
  public void unload(List<EquipID> eidlist) {
    for (EquipID e : eidlist)
      this.unload(e);
  }
  
  /* Empties the list of loaded equipment data, letting the garbage collector
   * scrub it all from memory. 
   */
  public void unloadAll() {
    // TODO Forget your birth
  }
  
  /* Requests that the data pointed to by the given EquipID object be given focus and made accessible.
   * If the given EquipID's data has not been loaded, it will be before being focused. 
   */
  public void using(EquipID eid) {
    // TODO
    // Find ID in list
      // Put in focus
    // Can't find? Find ID in database
      // Load & insert into list
      // Put in focus
    // Still can't find?
      // Throw exception
  }
  
  private void checkDataIsFocused() {
    // TODO If focus == null, throw exception.
    // If I try to .get() anything on a null object,
    // I think java will freak out for me, actually.
  }
  
  // #### All Getter Methods Written Below ####
  
  public boolean isPassive() {
    checkDataIsFocused();
    return this.focus.isPassive();
  }
  
  public String getName() {
    checkDataIsFocused();
    return this.focus.getName();
  }
  
  public String getDescription() {
    checkDataIsFocused();
    return this.focus.getDescription();
  }
  
  public String getShortDescription() {
    checkDataIsFocused();
    return this.focus.getShortDescription();
  }
  
  public int getValue() {
    checkDataIsFocused();
    return this.focus.getValue();
  }
  
  public TargetType getTargetType() {
    checkDataIsFocused();
    return this.focus.getTargetType();
  }
  
  public int getPOW() {
    checkDataIsFocused();
    return this.focus.getPOW();
  }
  
  public int getAP() {
    checkDataIsFocused();
    return this.focus.getAP();
  }
  
  public int getRange() {
    checkDataIsFocused();
    return this.focus.getRange();
  }
  
  public int getNullRange() {
    checkDataIsFocused();
    return this.focus.getNullRange();
  }
  
  public int getDEF() {
    checkDataIsFocused();
    return this.focus.getDEF();
  }
  
  public int getRES() {
    checkDataIsFocused();
    return this.focus.getRES();
  }
  
  public int getFireAffinity() {
    checkDataIsFocused();
    return this.focus.getFireAffinity();
  }
  
  public int getIceAffinity() {
    checkDataIsFocused();
    return this.focus.getIceAffinity();
  }
  
  public int getThunderAffinity() {
    checkDataIsFocused();
    return this.focus.getThunderAffinity();
  }
  
  public AreaEffectMap getAreaMap() {
    checkDataIsFocused();
    return this.focus.getAreaMap();
  }
  
  public AttributeTagList getAttributes() {
    checkDataIsFocused();
    return this.focus.getAttributes();
  }
  
  public boolean containsAttribute(Attribute a) {
    checkDataIsFocused();
    return this.focus.containsAttribute(a);
  }
  
  public List<StatVal> getStatReqs() {
    checkDataIsFocused();
    return this.focus.getStatReqs();
  }
  
  public List<StatVal> getStatPros() {
    checkDataIsFocused();
    return this.focus.getStatPros();
  }
  
  // #### All Getter Methods Written Above ####
  
  public EquipID waitCommand() {
    return new EquipID(cmdID_Wait);
  }
  
  /* Returns a random, valid, EquipID reference to some action or effect contained within the database.
   */
  public EquipID randomID() {
    String s = (Math.random() < 0.5) ? "A00" : "P00"; 
    s += Integer.toString((int)(Math.random()*10));
    return new EquipID(s);
  }
  
  /* Returns a random, valid, EquipID reference to some actionable weapon contained within the database.
   */
  public EquipID randomActionID() {
    String s = "A00" + Integer.toString((int)(Math.random()*10));
    return new EquipID(s);
  }
  
  /* Returns a random, valid, EquipID reference to some accessory contained within the database.
   */
  public EquipID randomPassiveID() {
    String s = "P00" + Integer.toString((int)(Math.random()*10));
    return new EquipID(s);
  }
}
