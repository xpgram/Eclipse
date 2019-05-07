package battle.constants;

public enum TargetType {
  None,
  Self,
  Enemy,
  Ally,
  Any,
  AnyButSelf,
  Location,
  EmptyLocation
  
  /* I've repurposed these a bit, so I want to explain what exactly they're for.
   * These are strictly relevant to the ActionDatabase.
   * They describe what the action is able to target.
   * These are, however, only one half of the total qualifier; all of them have a range restriction.
   * "Location," for instance, accepts any location at all, but only if it is within range. This means that "Location"
   * could select the ground beneath the acting's feet, but only if the action they're performing has range 0.
   * 
   * These were previously used by the FieldCursor as well, but that was making things too complicated.
   */
}
