package battle.ui;

/* The FieldCursor simply "returns" a location upon request.
 * What happens with the data underneath it depends on what is currently manipulating it.
 */

import battle.map.Grid;
import input.VirtualButton;
import input.VirtualController;
import input.behaviors.DPadRepeater;
import utils.GridPoint;
import utils.Timer;

public class GridCursor {
  
  private static GridCursor gc;
  
  // Singleton Handles
  private Grid grid = Grid.instance();
  private VirtualController controller = VirtualController.instance();
  private InfoWindow infoW = InfoWindow.instance();
  
  // General Fields
  GridPoint pos;            // The location of the cursor on the grid map.
  GridPoint rangeSource;    // The origin-point location of the restricted range, assuming such a thing is in effect.
  int range;                // The defined range surrounding the rangeSource point. TODO Why is this here?
  int width;                // The width constraint for the cursor's maximum movement.
  int height;               // The height constraint for the cursor's maximum movement.
  boolean hidden;           // Whether or not the cursor should be drawn on screen.
  
  // TODO Public? This is for granting access to the graphics API.
  public Timer animTimer = new Timer(0.5);
  public int animState = 0;
  
  // Button Controls
  private DPadRepeater dpad = new DPadRepeater(400, 50);
  
  
  private GridCursor() {
    this.pos = new GridPoint();
    this.rangeSource = new GridPoint();
    this.hidden = true;
  }
  
  public static GridCursor instance() {
    if (gc == null)
      gc = new GridCursor();
    return gc;
  }
  
  /* Include this in your update loop.
   * This step allows the cursor to move across the "grid" it is layered over.
   */
  public void update() {
    GridPoint p = dpad.vector();
    moveVector(p);
    infoW.setFocusPos(this.pos);    // Update the info window, which is more than likely always relevant when the gridcursor is, with new info.
  }
  
  public void show() {
    this.hidden = false;
  }
  
  public void hide() {
    this.hidden = true;
  }
  
  public boolean hidden() {
    return this.hidden;
  }
  
  public void constrain(int width, int height) {
    // TODO Throw exception when width|height < 0
    
    this.width = width;
    this.height = height;
  }
  
  public void move(int x, int y) {
    moveVector(new GridPoint(x,y));
  }
  
  /* Adds a rigid grid-style vector to the cursor's current location, moving it relative to wherever it is now.
   * TODO This method doesn't do anything if cursor wants to move outside of the defined range.
   * Not that I'm sure why it might need to.
   */
  public void moveVector(GridPoint p) {
    GridPoint q = new GridPoint(this.pos.x + p.x, this.pos.y + p.y);
    if (this.pos.equals(q) == false)
      setPos(q);
    
    /*
    if (grid.validPoint(q))           // This was removed to remove dependency on the Grid class. Cursor should be useable anywhere now.
      setPos(q);
    */
  }
  
  public GridPoint location() {
    return this.pos;
  }
  
  public void setPos(GridPoint p) {
    if (pointWithinBounds(p))
      this.pos.clone(p);
  }
  
  public boolean pointWithinBounds(GridPoint p) {
    return (p.x >= 0 && p.x < this.width && p.y >= 0 && p.y < this.height);
  }
  
  public void setRange(GridPoint p, int range) {
    this.rangeSource.clone(p);
    this.range = range;
  }
  
  public boolean inRange(GridPoint p) {
    if (this.range < 0) return true;
    return (this.rangeSource.taxiCabDistance(p) <= this.range);
  }
  
  public void updateAnimationState() {
    this.animTimer.update();
    if (this.animTimer.finished()) {
      if (this.animState == 0) this.animState = 1;
      else if (this.animState == 1) this.animState = 0;
      this.animTimer.reset();
    }
  }
}
