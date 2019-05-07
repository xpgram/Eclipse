package input.behaviors;

import input.VirtualButton;
import input.VirtualController;
import utils.Timer;

/* A class whose objects describe a repeating pulse with an initial wait time and an interval wait time.
 * 
 * Should this class also take a VirtualButton?
 * I could only send pulses if the button is down.
 * This class would only ever be used in conjunction with a button anyway, and its purpose is to describe a repeat interval for said button.
 * Tying them together would only make it easier to know when to pulse and when not to.
 * 
 * TODO The first interval timer, the initial one, should only complete if one ~particular~ button is held down for the entire duration.
 * That is, holding left, holding right, and releasing left should set the timer to extend from the ~right button,~ not the left one.
 * This change would prevent the d-pad (which uses this class, provided the four directional buttons) from zipping off into outer
 * space while you're trying to make fine lateral movements. 
 * 
 * 
 * I need to rethink how the d-pad functions a little.
 * Pulsing with the 
 */
public class ButtonRepeater {

  private static VirtualController controller;
  
  private boolean pulse;            // Whether or not this object is sending a pulse signal. Pulses are not missable, and are only consumed once observed.
  private int initial;              // The time interval between the first and second pulse exclusively, in milliseconds.
  private int interval;             // The interval time between pulses in milliseconds, excluding the first and second pulse interval.
  private Timer timer;              // The clock keeping track of the interval between pulses.
  private VirtualButton[] buttons;  // The button on the VirtualController that is being watched and pulsed.
  
  
  
  /* @b: A VirtualButton that will sustain the pulse interval while it is being held down.
   * @initialWait: A double equal to the wait time after the first pulse in seconds.
   * @intervalWait: A double equal to the wait time between pulses, sans the first pulse interval, in seconds.
   */
  public ButtonRepeater(VirtualButton b, double initialWait, double intervalWait) {
    construct(new VirtualButton[]{b}, (int)(initialWait * 1000), (int)(intervalWait * 1000));
  }
  
  /* @b: A VirtualButton that will sustain the pulse interval while it is being held down.
   * @initialWait: An int equal to the wait time after the first pulse in milliseconds.
   * @intervalWait: An int equal to the wait time between pulses, sans the first pulse interval, in milliseconds.
   */
  public ButtonRepeater(VirtualButton b, int initialWait, int intervalWait) {
    construct(new VirtualButton[]{b}, initialWait, intervalWait);
  }
  
  /* @bl: An array list of buttons to consider sustaining the pulse while at least one of them is being held down.
   * @initialWait: A double equal to the wait time after the first pulse in seconds.
   * @intervalWait: A double equal to the wait time between pulses, sans the first pulse interval, in seconds.
   */
  public ButtonRepeater(VirtualButton[] bl, double initialWait, double intervalWait) {
    construct(bl, (int)(initialWait * 1000), (int)(intervalWait * 1000));
  }
  
  /* @bl: An array list of buttons to consider sustaining the pulse while at least one of them is being held down.
   * @initialWait: An int equal to the wait time after the first pulse in milliseconds.
   * @intervalWait: An int equal to the wait time between pulses, sans the first pulse interval, in milliseconds.
   */
  public ButtonRepeater(VirtualButton[] bl, int initialWait, int intervalWait) {
    construct(bl, initialWait, intervalWait);
  }
  
  // Constructor helper method.
  private void construct(VirtualButton[] b, int initialWait, int intervalWait) {
    if (controller == null)
      controller = VirtualController.instance();
    
    this.buttons = b;
    this.initial = initialWait;
    this.interval = intervalWait;
    
    reset();
  }
  
  /* Resets the object to initial conditions.
   * Used to begin a new repeat behavior pattern if one has ended. More specifically, this is called between controller.buttonUp() and .buttonDown()
   */
  private void reset() {
    this.pulse = true;
    this.timer = new Timer(this.initial);
  }
  
  /* Update the object per frame, allowing it to generate pulses according to some interval.
   */
  private void update() {
    boolean firstSignal = true;
    
    // Verify that no buttons in the list are being held down from the last frame.
    for (int i = 0; i < this.buttons.length; i++) {
      if (controller.buttonDownExclusive(this.buttons[i]))
        firstSignal = false;
    }
    
    // If this is the fist frame to output a signal, reset all timers.
    if (firstSignal) {
      for (int i = 0; i < this.buttons.length; i++) {
        if (controller.buttonPressed(this.buttons[i])) {
          reset();
          break;                // Exit the for-loop; we only needed to find one pressed button.
        }
      }
    }
    
    // If any button is down, update timers and the pulse signal.
    if (signalPositive()) {
      this.timer.update();
      boolean p;  // Container for timer.pulse()
    
      if (this.timer.finished()) {            // If the timer has finished, we have necessarily been using the initial wait timer.
        this.pulse = true;                    // This pulse is the 2nd pulse, standing in as the first pulse of the new timer.
        this.timer = new Timer(interval, -1); // Transition to the interval wait timer, using an infinite cycle.
      } else {
        p = this.timer.pulse();               // This step prevents timer.pulse() from erasing this object's pulse.
        if (p)
          this.pulse = p;                     // Only relevant if timer is cycling endlessly: use timer's pulse as this object's pulse.
      }
    }
  }
  
  /* Returns true if a pulse signal was sent. If one was, that pulse is "consumed" internally;
   * subsequent calls will return false unless another pulse has been generated.
   */
  public boolean pulse() {
    update();
    
    boolean r = false;
    if (this.pulse) {
      r = true;
      this.pulse = false;
    }
    return r;
  }
  
  /* Returns true if any of the buttons in the list of those to watch are down.
   * Returns false if none are.
   */
  public boolean signalPositive() {
    boolean r = false;
    for (int i = 0; i < this.buttons.length; i++) {
      if (controller.buttonDown(this.buttons[i]))
        r = true;
    }
    return r;
  }
}
