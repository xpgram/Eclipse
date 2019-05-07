/* A string reference to an abstract game effect, or terrain or equipment quality.
 * Used like dynamic booleans to simply declare an effect present.
 * 
 * This is the type for an individual member.
 * 
 * Features:
 *  - Legitimacy check
 *      Tag contents are enums, which via syntax, automatically correct me or halt the program for using
 *      an incorrect one. This is much more maintainable than strings, I think.
 *  - Contextual Interpretation
 *      To keep enum types down (this is explained in the enum file Attribute, also), tags mean
 *      different things depending on what they are tagged to. For weapons this is usually an aggressive
 *      interpretation, for armor a defensive one, and for UnitCondition they are thought to be an ailment.
 *  - Values
 *      Values are used for any variable attribute. For instance, "Poison=3" could inflict poison but also
 *      specify how strong that poison is.
 *  - Built-In Timer
 *      For turns. Optionally used to set a definite end condition for any temporary effects, or none at all.
 *      When the timer has fully elapsed, the tag nullifies itself.
 */

package battle.utils;

public class AttributeTag {
  
  Attribute type;   // The primary contents of the tag itself. An enum.
  short value;      // Value associated with the tag.
  short timer;      // Time limit associated with the tag. Set to -1 if irrelevant.
  boolean enabled;
  

  /* Creates a new attribute with the name given by _type.
   * Halts creation if _type is not a valid attribute.
   */
  public AttributeTag(Attribute _type) {
    this.type = _type;
  }
  
  /* Returns the enum contents of the tag itself.
   */
  public Attribute getType() {
    return type;
  }
  
  /* Retrieves the tag's associated value.
   */
  public int getValue() {
    return value;
  }
  
  /* Retrieves the turns/time-units left on the tag's timer.
   */
  public int getTimer() {
    return timer;
  }
  
  /* Ticks this tag's timer down by one, unless timer = -1 or "Permanent" flag is set.
   */
  public void tickTimer() {
    if (enabled == false) return;
    if (timer < 0) return;  // If timer = -1, do nothing.
    
    timer--;
    if (timer <= 0) disable();
  }
  
  /* @return: True if the attribute is in effect, false if not.
   */
  public boolean isEnabled() {
    return enabled;
  }
  
  /* Enables the attribute's effect.
   */
  public void enable() {
    enabled = true;
  }
  
  /* Disables the attribute's effect.
   */
  public void disable() {
    enabled = false;
  }
  
  /* Signals the AttributeTagList object to remove this attribute from its list.
   */
  public void erase() {
    this.type = Attribute.None;
  }
  
  /* Writes the contents of this attribute to a string which may then be
   * saved to a file, displayed, or whatever else you need.
   */
  public String write() {
    // TODO Come up with a way to convert a billion enums to strings.
    return value + "," + timer + ";";
  }
  
  /* Reads the contents of a string into this attribute.
   * This could be used to load data from a file, or whatever else. 
   */
  public void read(String str) {
    // TODO Even better, come up with a way to convert from strings to enums.
    String val = str.substring(0, str.indexOf(','));
    String tim = str.substring(0, str.indexOf(';'));
    // TODO value and timer
    // TODO Finish the thing
  }
  
  /* Compares this tag to another one, returning true only if the tag contents are the same,
   * and if both this and the other tag are enabled.
   */
  public boolean equals(AttributeTag tag) {
    return (type == tag.getType() && isEnabled() && tag.isEnabled());
  }
  
  /* Compares this tag to an Attribute (a name), returning true only if the tag contents are the same,
   * and if this attribute is enabled.
   */
  public boolean equalsType(Attribute a) {
    return (type == a && isEnabled());
  }
  
}
