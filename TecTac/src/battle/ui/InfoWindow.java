package battle.ui;

import battle.entities.unit.Unit;
import battle.map.Grid;
import battle.utils.Attribute;
import utils.GridPoint;

/* This class may be given a reference to a battle unit, or said reference may be taken away.
 * Doesn't do too much else, tbh.
 * 
 * Knows when it should be shown. (Aside of the global activate/deactivate setting)
 * Knows whether it's set to Unit/Terrain Info mode.
 * Knows (somehow) if a unit was 'Scanned' and should reveal additional info.
 */

public class InfoWindow {

  private static InfoWindow iw;
  
  private GridPoint focusPos;
  private Unit displayUnit;
  //private Terrain displayTerrain;
  
  private boolean active;       // Whether the InfoWindow is relevant.
  private boolean visible;      // Whether the InfoWindow should be on-screen.
  private boolean showTerrain;  // Whether to display Terrain info regardless of the presence of a focusable unit.
  private boolean unitScanned;  // "Scanned" might be a status effect applied to UnitCondition. That would make the most sense, I think.
  
  // Private singleton constructor
  private InfoWindow() {
    this.focusPos = new GridPoint();
    this.displayUnit = null;
    this.active = false;
    this.visible = false;
    this.showTerrain = false;
    this.unitScanned = false;
  }
  
  /* Returns ~the~ singleton instance of the InfoWindow class. 
   */
  public static InfoWindow instance() {
    if (iw == null)
      iw = new InfoWindow();
    return iw;
  }
  
  /* Tells the Info Window to display itself, so long as there is anything to display.
   */
  public void show() {
    this.active = true;
  }
  
  /* Sets the Info Window to hide from view/use.
   */
  public void hide() {
    this.active = false;
  }
  
  /* Returns true if the window should be drawn, that it isn't hidden from view, false if not.
   */
  public boolean hidden() {
    return (!this.active || !this.visible);
  }
  
  /* Sets the location, on the GridMap, from which any and all information displayed will be pulled from.
   */
  public void setFocusPos(GridPoint p) {
    if (this.focusPos.equals(p) == false && Grid.instance().validPoint(p)) {
      this.focusPos.clone(p);
      updateSelf();
    } else
      {} // TODO Throw an out of bounds exception. Unless moving it out of bounds is a useful "be quiet" statement.
  }
  
  /* Flushes and re-reads the data associated with the focusPos location.
   */
  public void updateSelf() {
    // Reset
    this.visible = false;
    this.unitScanned = false;
    
    // Get data
    Grid grid = Grid.instance();
    this.displayUnit = grid.getUnit(this.focusPos);
    // TODO retrieve terrain type, if one exists.
    
    // Determine extra info availability
    if (this.displayUnit != null) {
      this.visible = true;
      this.unitScanned = this.displayUnit.getCondition().getAttributes().contains(Attribute.Scanned);
    }
    
    // Set a flag indicating whether the InfoWindow has anything neat to show at all.
    //if (this.displayTerrain != null) {}
  }
  
  /* Swaps between forcing Terrain Info into the window at all times, and letting Unit Info take over when unit information is present.
   */
  public void toggleTerrainView() {
    this.showTerrain = !this.showTerrain;
  }
  
  /* Returns true if Terrain should take precedent over Unit info when displaying metrics.
   */
  public boolean terrainView() {
    return this.showTerrain;
  }
  
  /* Returns true if this "container" has references to any Unit data that is displayable.
   */
  public boolean unitExists() {
    return (this.displayUnit != null);
  }
  
  /* Returns a reference to the unit located at the grid location the InfoWindow is currently focused on.
   * If no such unit exists, returns null.
   */
  public Unit getUnit() {
    return this.displayUnit;
  }
  
  /* Returns true if this "container" has references to any (literally any) terrain data that is displayable.
   */
  public boolean terrainExists() {
    return false; // (this.displayTerrain != null);
  }
  
  /* Returns a reference to the terrain-type object located wherever the InfoWindow is currently focused.
   * If no such object exists, returns null.
   */
  //public Terrain getTerrain() {
  //  return this.displayTerrain;
  //}
}
