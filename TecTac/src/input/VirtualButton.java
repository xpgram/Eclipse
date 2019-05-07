package input;

/* Provides a level of abstraction to button definitions.
 * Keys or joystick buttons are filtered through these enums by user settings, and these enums are essentially the
 * buttons that the game actually interprets. Or, the names for them, anyway.
 */
public enum VirtualButton {
  Accept,         // Used to select things, advance text boxes, open the menu when pressed over an empty tile
  Cancel,         // Used to back up, cancel actions, display range info on enemy units, speed up camera (mod: hold over empty space)
  Special,        // Used in special circumstances to change pages on info, add an enemy's range to the danger range, toggle danger range over open tiles, "switch firing modes" whatever that means
  Menu,           // Opens the menu at any time, or otherwise pauses.
  Info,           // If 'Menu' is Start, then 'Info' is Select. Displays the map? Is there a map?
  Debug,          // Special developer key! Toggles debug info, like FPS and whatever else there might be.
  
  // Everyone's favorite movement keys.
  Up,
  Down, 
  Left, 
  Right
}