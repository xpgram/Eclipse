package input;

/* This is a container class which simply names a virtual button and whether or not it is "down" on the keyboard/joystick.
 * The virtual controller has the element of time, and will separate this single quality into Pressed/Down/Held/etc.
 * 
 * This class represents a single event, so pressed=false means the key was released.
 * The only reason I'm not directly using VirtualButtonState is because it has more options than are really needed here.
 */
public class VirtualButtonEvent {

  public VirtualButton button;
  public boolean pressed;
  
  /* Creates a new button event which can be inserted into the VirtualController's list of events.
   * @VirtualButton b: This is the specific button being referenced.
   * @boolean down: Whether or not the key is down in this event. It should be "true" if just pressed, or "false" if just released.
   */
  public VirtualButtonEvent(VirtualButton b, boolean down) {
    this.button = b;
    this.pressed = down;
  }
  
}
