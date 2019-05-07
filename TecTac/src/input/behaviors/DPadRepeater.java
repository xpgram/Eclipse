package input.behaviors;

import input.VirtualButton;
import input.VirtualController;
import utils.GridPoint;
import utils.Timer;

public class DPadRepeater {

  private static VirtualController controller = VirtualController.instance();
  
  private static final GridPoint DIR_UP    = new GridPoint( 0,-1);
  private static final GridPoint DIR_DOWN  = new GridPoint( 0, 1);
  private static final GridPoint DIR_LEFT  = new GridPoint(-1, 0);
  private static final GridPoint DIR_RIGHT = new GridPoint( 1, 0);
  
  private ButtonRepeater dpad;
  private int initial;
  private int interval;
  private Timer timer;
  private GridPoint vector;
  
  /* Watches the directional buttons for input and, using vector(), produces a GridPoint pointing in the aggregate direction
   * of desired movement. This vector is produced in pulses whose interval is determined by: 
   * @initialWait: The amount of time in milliseconds that the first pulse should wait before the seconds pulse.
   * @intervalWait: The amount of time in milliseconds that the second pulse onward should wait before the next pulse.
   */
  public DPadRepeater(int initialWait, int intervalWait) {
    //VirtualButton[] blist = {VirtualButton.Up, VirtualButton.Down, VirtualButton.Left, VirtualButton.Right};
    //this.dpad = new ButtonRepeater(blist, initialWait, intervalWait);
    this.initial = initialWait;
    this.interval = intervalWait;
    this.timer = new Timer(initialWait);
    this.vector = new GridPoint(); 
  }
  
  /* Watches the directional buttons for input and returns a GridPoint vector congruent with the final direction being output.
   * If no directional buttons are being held, returns a GridPoint vector of (0,0).
   * 
   * This is essentially the D-Pad Repeater's "Update method."
   * It should thus be checked every frame for any change in player input.
   * 
   * Note: The implementation feels a little clunky, with all the controller.buttonCheck()'s, but this works far better
   * than what I was using.
   * I would like to TODO Clean up this method, at some point.
   */
  public GridPoint vector() {
    GridPoint p = new GridPoint();      // This is the return value of the function.
    
    // If any button is down, advance the pulse timer. 
    if (controller.buttonDown(VirtualButton.Up) ||
        controller.buttonDown(VirtualButton.Down) ||
        controller.buttonDown(VirtualButton.Left) ||
        controller.buttonDown(VirtualButton.Right))
      this.timer.update();
    
    // If any button was pressed, move in that direction, so long as we are not in "Repeat" mode.
    if (this.timer.limitMillis() == this.initial) {
      if (controller.buttonPressed(VirtualButton.Up))    { p.add(DIR_UP); }
      if (controller.buttonPressed(VirtualButton.Down))  { p.add(DIR_DOWN); }
      if (controller.buttonPressed(VirtualButton.Left))  { p.add(DIR_LEFT); }
      if (controller.buttonPressed(VirtualButton.Right)) { p.add(DIR_RIGHT); }
      
      // While in non-repeat-mode, if any button was released, restart the wait until repeat-mode starts.
      if (controller.buttonReleased(VirtualButton.Up) ||
          controller.buttonReleased(VirtualButton.Down) ||
          controller.buttonReleased(VirtualButton.Left) ||
          controller.buttonReleased(VirtualButton.Right))
        this.timer.reset();
    }
    
    // If the timer is pulsing, then assign p an aggregate vector.
    if (timer.pulse()) {
      p = new GridPoint();    // Erase p in case it was affected by the above block.
      if (controller.buttonDown(VirtualButton.Up))    { p.add(DIR_UP); }
      if (controller.buttonDown(VirtualButton.Down))  { p.add(DIR_DOWN); }
      if (controller.buttonDown(VirtualButton.Left))  { p.add(DIR_LEFT); }
      if (controller.buttonDown(VirtualButton.Right)) { p.add(DIR_RIGHT); }
    }
    
    // If the timer has finished(), that means we are using the initial timer. Set up the subsequent interval timer.
    if (this.timer.finished())
      this.timer = new Timer(this.interval, -1);
    
    // If we were in repeat mode, and no buttons are down, then reset the timer to initial conditions.
    if (this.timer.limitMillis() == this.interval &&
        controller.buttonUp(VirtualButton.Up) &&
        controller.buttonUp(VirtualButton.Down) &&
        controller.buttonUp(VirtualButton.Left) &&
        controller.buttonUp(VirtualButton.Right))
      this.timer = new Timer(this.initial);
    
    return p;
  }
  
}
