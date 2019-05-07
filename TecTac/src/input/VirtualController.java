/*
 * Collects input from Processing and disperses it throughout the program.
 * Made to make using a different library later on easy.
 * 
 * The way I've written this to work is thus:
 *  - This "controller" watches for input from the player.
 *  - It then "translates" this input into state-flags describing a virtual button machine.
 *  - This "button machine" is what the player is actually using to play the game.
 *  
 * Press 'Z' -> keyPressed('Z')
 *           -> button[Accept] = Pressed
 *           -> isButtonPressed('Accept') returns true
 *           -> Game does something 'Accept'-like.
 * 
 * These flags the button-machine uses are set per-frame.
 * Whereas most input managers, I believe, operate by calling some input(arg0) method the instant a button is actually pressed,
 * mine is interpreting that information and letting the rest of the program ask it what its state is.
 * Which is, at the very least, easy for me to understand logically.
 * 
 * This does mean keypressed events only exist for a single frame, which could (..?) cause problems if we miss that event somehow..?
 * I dunno. I'm thinking of timing issues, mostly.
 * 
 * If the player somehow (during a framerate slowdown, maybe) presses and releases a key during a single frame, that key event
 * will be missed entirely.
 * 
 * To be fair, I see myself relying on released() more than pressed(), it has a nicer play feel; but still, it's an issue.
 * A possible remedy would be adding a PressedReleased state, or call it Tapped, that would represent a single frame of both happening.
 * Pressed() and Released() would both return true when asked. I only wonder if this would cause logistical problems.
 * Press functionality would/should always be handled before Release in that case, as a convention, which is do-able, but easily fuck-up-able.
 */

package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import utils.Timer;

public class VirtualController {
  
  /* TODO Make dis boy work it
   * I think I've written something that should mostly work, but it doesn't.
   * It doesn't because keyPressed(KeyEvent e) is never called, it seems.
   * I'll have to figure that out.
   */
  
  // Singleton
  private static VirtualController im;
  
  // Constants
  private static final double MAX_HOLD_LENGTH = Double.MAX_VALUE;   // The time, in seconds, before a hold timer stops keeping track.
  private static int NUM_BUTTONS;
  
  // Button State Vars - Used to indicate what each button is doing.
  private VirtualButtonState[] buttons;
  private Timer[] buttonTimers;
  
  // Defines keyboard key relationships with in-game buttons.
  // TODO Build this into an array somehow. I'm using Buttons.[Element].ordinal() to index, so the struggle is merely in hardcoding values.
  private int accept_keyboardID  = 90;  // 'Z'
  private int cancel_keyboardID  = 88;  // 'X'
  private int special_keyboardID = 67;  // 'C'
  private int menu_keyboardID    = 83;  // 'S'  'Shift' = 16
  private int info_keyboardID    = 68;  // 'D'  'Ctrl'  = 17
  private int debug_keyboardID   = 81;  // 'Q'
  
  // Defines joystick button relationships with in-game buttons.
  // TODO Same as above. But it's for joysticks so much less time sensitive.
  private int accept_joystickID  = 0;
  private int cancel_joystickID  = 0;
  private int special_joystickID = 0;
  private int menu_joystickID    = 0;
  private int info_joystickID    = 0;
  private int debug_joystickID   = 0;
  
  // This list carries with it the changes to the controller's state per-frame. Input must be filtered and directed here.
  private List<VirtualButtonEvent> eventlist;
  
  
  // Methods
  
  
  // Inaccessible constructor
  private VirtualController() {
    // Finalize Constants
    NUM_BUTTONS = VirtualButton.values().length;
    
    // Instantiate lists
    this.buttons = new VirtualButtonState[NUM_BUTTONS];
    this.buttonTimers = new Timer[NUM_BUTTONS];
    
    // Instantiate list elements
    for (int i = 0; i < NUM_BUTTONS; i++) {
      this.buttons[i] = VirtualButtonState.Up;
      this.buttonTimers[i] = new Timer(MAX_HOLD_LENGTH);
    }
    
    // Instantiate EventsList
    this.eventlist = new ArrayList<VirtualButtonEvent>();
  }
  
  /* Retrieves the reference to ~the~ InputMaster object.
   */
  public static VirtualController instance() {
    if (VirtualController.im == null)
      VirtualController.im = new VirtualController();
    return VirtualController.im;
  }

  /* This method iterates through all in-game controller buttons, changing their status according to what the player is doing with
   * whatever physical device it is that they're using.
   * Put this in your game loop somewhere. Top-level.
   */
  public void update() {
    
    // Update all button states per frame
    for (int i = 0; i < NUM_BUTTONS; i++) {
      buttonTimers[i].update();
      
      // If the button is pressed, transition to down.
      if (buttons[i] == VirtualButtonState.Pressed) {
        buttons[i] = VirtualButtonState.Down;
      }
      
      // If the button was released, transition to up.
      if (buttons[i] == VirtualButtonState.Released) {
        buttons[i] = VirtualButtonState.Up;
      }
    }
    
    // Update button state information from eventslist.
    // It is important this happens after updating Pressed->Down and Released->Up; otherwise, Pressed and Released will never exist for an entire frame.
    VirtualButtonEvent e;   // The button event container
    int buttonID;           // The ordinal ID of the button in question within the virtual controller's arrays of state and property data.
    for (int i = 0; i < eventlist.size();) {
      // Pop the event from the beginning of the events list.
      e = eventlist.remove(i);
      if (e == null) return;      // TODO For some reason, we sometimes have null events. I don't know how.
      
      buttonID = e.button.ordinal();
      
      // Interpret
      
      // If button is in unpressed state, switch to pressed state and begin hold length timer.
      if ((buttons[buttonID] == VirtualButtonState.Up || buttons[buttonID] == VirtualButtonState.Released) && e.pressed) {
        buttons[buttonID] = VirtualButtonState.Pressed;
        buttonTimers[buttonID].start();
      }
      
      // If button is in pressed state, switch to unpressed state.
      else if ((buttons[buttonID] == VirtualButtonState.Down || buttons[buttonID] == VirtualButtonState.Pressed) && !e.pressed) {
        buttons[buttonID] = VirtualButtonState.Released;
        buttonTimers[buttonID].reset();
        buttonTimers[buttonID].stop();
      }
    }
  }
  
  /* Adds an event to the eventlist. It will be interpretted during the next frame.
   */
  public void addEvent(VirtualButtonEvent e) {
    this.eventlist.add(e);
  }
  
  /* Takes in a key and returns a VirtualButton if one is applicable.
   */
  public VirtualButton getVirtualButton(char c) {
    return null;
  }
  
  /* Returns true if the button b was pressed this frame, false if not.
   */
  public boolean buttonPressed(VirtualButton b) {
    return (buttons[b.ordinal()] == VirtualButtonState.Pressed);
  }
  
  /* Returns true if the button b was released this frame, false if not.
   */
  public boolean buttonReleased(VirtualButton b) {
    return (buttons[b.ordinal()] == VirtualButtonState.Released);
  }
  
  /* Returns true if the button b is currently being held down, false if it is up instead.
   */
  public boolean buttonDown(VirtualButton b) {
    return (buttons[b.ordinal()] == VirtualButtonState.Pressed ||
            buttons[b.ordinal()] == VirtualButtonState.Down);
  }
  
  /* Returns true if the button b is currently in the down state, irrespective of its relationship with the button socket.
   * I.e., will return false if the button is Pressed.
   */
  public boolean buttonDownExclusive(VirtualButton b) {
    return (buttons[b.ordinal()] == VirtualButtonState.Down);
  }
  
  /* Returns true if the button b is currently up, false if it is down instead.
   */
  public boolean buttonUp(VirtualButton b) {
    return (buttons[b.ordinal()] == VirtualButtonState.Released ||
            buttons[b.ordinal()] == VirtualButtonState.Up);
  }
  
  /* Returns true if the button b is currently in the up state, irrespective of its relationship with the button socket.
   * I.e., will return false if the button is Released.
   */
  public boolean buttonUpExclusive(VirtualButton b) {
    return (buttons[b.ordinal()] == VirtualButtonState.Up);
  }
  
  /* Returns a double equal to the time in seconds that the button b has been held down, up to Double.MAX_VALUE seconds.
   * If the button is not being held down, this method will return 0. (It should, anyway).
   */
  public double buttonHoldTime(VirtualButton b) {
    return buttonTimers[b.ordinal()].time();
  }
}
