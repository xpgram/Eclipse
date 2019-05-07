/*
 * Used to represent (and build) target AoE maps.
 * Simple ones: single, radius and line are easy to construct.
 * More complex ones will have to be loaded from a file, probably.
 * I think I might only use those three though, anyway.
 * 
 * TODO Advanced AoE Instructions
 * Complex instructions may need they're own list + points.
 * If I want to have a hypothetical attack that damages and knocks back 3 target units 1 tile,
 * I would need a doubled list of from points and to points in addition to the attack map.
 * Either or, I would need a second map of points (same size as AoE map) indicating which tiles
 * "chain" to other tiles. After damage has been applied, any units in a non-void point-tile would
 * be moved to wherever the tile's Point object is pointing to.  
 */

package battle.actions;

public class AreaEffectMap {
  
  int width, height;
  MapTileType[] map;    // Currently, only two values are used, None and PrimaryTarget, but I'm using Enums in case I want to complexify later.
  
  
  // Constructor: Creates single-target
  public AreaEffectMap() {
    createSingle();
  }
  
  // Constructor String: Build map from string (load from file)
  public AreaEffectMap(String str) {
    readString(str);
  }
  
  // Returns true if an action would include these coordinates as part of its effect.
  public boolean isAffected(int x, int y) {
    if (x < 0 || x > width || y < 0 || y > height)
      return false;
    
    return (map[x*y] == MapTileType.PrimaryTarget);
  }
  
  /* Returns true if an action would include these coordinates (relative to center) as part of its effect.
   * If isAffected(x,y) is useless, this may replace it entirely.
   * @param x, y: integers ranging from -radius to +radius, "radius" being of the AreaEffectMap size.
   */
  public boolean isAffectedRelativeToCenter(int x, int y) {
    int displace = (int)(width / 2);
    return isAffected(x + displace, y + displace);
  }
  
  /* Generates an effect map of size (1,1) with one target affected.
   * @return This object modifies and returns itself in case we are constructing, building and assigning a handle in one line.
   */
  public AreaEffectMap createSingle() {
    generateEmptyMap(1, 1);
    map[0] = MapTileType.PrimaryTarget;
    return this;
  }
  
  /* Generates an effect map of size (radius*2 + 1)^2 with a TaxiCab-style "circle" of affected targets within.
   * @param radius: The distance range of the effect from the center. radius=0 would produce a map equal to createSingle().
   * @return This object modifies and returns itself in case we are constructing, building and assigning a handle in one line. 
   */
  public AreaEffectMap createCircle(int radius) {
    int diameter = radius * 2 + 1;  // 0 = 1, 1 = 3, 2 = 5; Always a center tile.
    generateEmptyMap(diameter, diameter);
    
    // Create the circle by scanning/building horizontal lines.
    int xdist;
    for (int y = -radius; y <= radius; y++) {
      xdist = radius - Math.abs(y);
      for (int x = -xdist; x <= xdist; x++) {
        // We displaced by -radius so we have to +radius to bring it back
        map[(x+radius) * (y+radius)] = MapTileType.PrimaryTarget;
      }
    }
    
    return this;
  }
  
  /* Generates an effect map of size (radius*2 + 1)^2 with all coordinates being affected targets.
   * @param radius: The distance range of the effect from the center. radius=0 would produce a map equal to createSingle(). 
   * @return This object modifies and returns itself in case we are constructing, building and assigning a handle in one line.
   */
  public AreaEffectMap createSquare(int radius) {
    int diameter = radius * 2 + 1;    // 0 = 1, 1 = 3, 2 = 5; Always a center tile.
    generateEmptyMap(diameter, diameter);
    for (int i = 0; i < map.length; i++) {
      map[i] = MapTileType.PrimaryTarget;
    }
    
    return this;
  }
  
  /* Generates an effect map of size (1, length) with all coordinates being affected targets.
   * @param length: The metric size of the line in map tiles. 
   * @return This object modifies and returns itself in case we are constructing, building and assigning a handle in one line.
   */
  public AreaEffectMap createLine(int length) {
    generateEmptyMap(length, 1);
    for (int i = 0; i < map.length; i++) {
      map[i] = MapTileType.PrimaryTarget;
    }
    
    return this;
  }
  
  /* Changes the center tile of the map to "not affected." Useful if the map is to surround the acting unit
   * instead of being directed by the FieldCursor.
   * Probably more important for display purposes: I don't imagine games like mine let you area-effect your own team mates.
   */
  public void removeCenter() {
    int center = (int)(width / 2 + 1);
    map[center*center] = MapTileType.None;
  }
  
  /* Builds the square-map infrastructure.
   * All tiles/cells initiate as non-valid, they get filled in with shapes elsewhere.
   */
  private void generateEmptyMap(int w, int h) {
    width = w;
    height = h;
    map = new MapTileType[w*h];
    for (int i = 0; i < map.length; i++) {
      map[i] = MapTileType.None;
    }
  }
  
  /* After constructing a shape from an outside source (a file), this method
   * confirms that the shape isn't completely broken.
   */
  private void validate() {
    short moveActingUnit = 0;
    short moveTargetUnit = 0;
    for (int i = 0; i < map.length; i++) {
      if (map[i] == MapTileType.MoveActingUnit) moveActingUnit++;
      if (map[i] == MapTileType.MoveActingUnit) moveTargetUnit++;
    }
    
    if (moveActingUnit > 1) ; // TODO Throw exception!
    if (moveTargetUnit > 1) ; // TODO Throw exception!.
  }
  
  
  public void readString(String str) {
    // TODO This defined process:
    // Read width and height of map from string.
    // Create an empty map of that size.
    // For each tile in the map, read back a code value instructing what to build:
    //    NO    None
    //    PT    PrimaryTarget
    //    ST    SecondaryTarget
    //    MU1:1 Move Unit in this location to x:y coordinates relative to this map.
    // Confirm that data isn't corrupt => confirm read back tiles equal area of map size
    validate();
  }
  
  
  public String writeString() {
    String str = "";
    // TODO The reverse of readString():
    // Write width and height of map to string.
    // For each tile in the map, write a code value instructing what to build when read + a delimiter ++ eol:
    //    NO    None
    //    PT    PrimaryTarget
    //    ST    SecondaryTarget
    //    MU1:1 Move Unit in this location to x:y coordinates relative to this map.
    return str;
  }
  
  
  public enum MapTileType {
    None,
    PrimaryTarget,    // Used to declare a unit (tile) is affected by the action.
    SecondaryTarget,  // Used to implement secondary effects to different units. (... really?)
    MoveActingUnit,   // Only one may be present -- marks where the acting unit will move upon action completion.
    MoveTargetUnit    // Basic implementation: move target[0] only. Obviously doesn't work for target.length > 1.
    //MoveTargetUnit2
    //MoveTargetUnit3  etc.
  }
}
