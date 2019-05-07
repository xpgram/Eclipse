package battle.ui;

import java.util.ArrayList;

/* Okay...
 * I've had myself a long day, even though I haven't done anything, so I'm not going to work on this now.
 * But, I've come up with a new visual style for the Command Menu.
 * 
 * 
 *  | Broad Sword  |-5a-|-3d-|  |-1g-|
 *  | Flameka      |-3a-|-4d-|1-3r|  |Fire|
 * >| Wait         |
 * 
 * We no longer have giant description boxes, they're just really long now.
 * Any relevant effects (and there should be at least one, otherwise what does it do?) are listed to the right, and a spacer
 * about half the width of the attribute box itself separates core attributes from additional attributes.
 * 
 * "Core" includes:
 *  - AP, or Ammo
 *  - Power, or Damage
 *  - Range
 *  
 * "Additional" is literally anything else.
 * Examples:
 *  - Extra defense applied when attacked
 *  - The element of the attack
 *  - The attack inflicts Poison
 *  - The action heals HP
 *  - The action summons a copy of yourself
 *  Etc.
 *  
 * Letter symbols for all/most of these is the easiest thing to do ~now~, so that may be how I operate,
 * but I conceived this menu with handy pictures in mind. Either, these pictures occupy the whole box with the value (if necessary)
 * overlaid on top; or, these pictures will be centered on the bottom 1/3rd of the box, while the value is centered on the top 2/3rds.
 * Like this:
 *  _____
 * | 1-3 |    <-- But with this as 2/3rds, obviously.
 * |__R__|
 * 
 * This style probably wouldn't make publication, but it's neat.
 * It can be a bit cluttered, though; it works best with about 2 choices max. 3 is okay, kinda, so 3 really has to be enforced as the maximum
 * number of equip-able actions. If a greatsword, say, adds an additional command [Defend] to the list, it must be two-handed. This will ensure
 * "adding extra commands" never brings the list above 3 items.
 * 
 * Another note:
 * 
 *  | Padded Armor |  |-1g-|-1b-|-2hp|
 * 
 * Passive items are not selectable, but I would like to have them present just to show off what effects they have.
 * Um... actually, I think the effects were supposed to be aggregated and added to an Info Window, so maybe not.
 * 
 * Anyway, one more thing:
 * If an action has 0 AP left, just grey it out a little. I shouldn't have to tell you (me), but it helps communicate the idea a little better.
 */

import java.util.List;

import battle.actions.EquipID;
import battle.actions.EquipmentDatabaseManager;
import input.behaviors.DPadRepeater;
import utils.GridPoint;

public class CommandMenu {

  // Constants
  public static final int NUMBER_OF_STATIC_COMMANDS = 1;
  
  // Singleton handle
  private static CommandMenu cm;
  
  boolean active = false;       // Whether or not the menu should be open & listening to player input.
  List<EquipID> commands;       // The list of actionable commands (excluding "Wait")
  int cursorPos;                // Which menu item is currently being hovered over.
  int endOfList;                // Represents the end of the selectable items. (Includes "Wait")
  
  DPadRepeater dpad;            // Used to move the menu's cursor. (Up and Down only).
  
  
  // Exists to stop instantiation.
  private CommandMenu() {
    this.commands = new ArrayList<EquipID>();
    this.cursorPos = 0;
    this.endOfList = NUMBER_OF_STATIC_COMMANDS;
  }
  
  // Singleton Accessor
  public static CommandMenu instance() {
    if (cm == null) cm = new CommandMenu();
    return cm;
  }
  
  /* Updates cursor with D-Pad input.
   * TODO My Dad is talking, I can't think enough to solve this right now.
   * Currently, the cursor, if held down, will wrap around the menu, but will not auto-stop if reaching the end of the menu.
   * Wrapping is good, but dpad.vector()'s pulses or whatever need to be examined: only wrap if this is the first pulse of a new keypress.
   */
  public void update() {
    GridPoint p = dpad.vector();
    if (p.equals(GridPoint.unitVectorUp()) || p.equals(GridPoint.unitVectorDown())) {   // This prevents vectors (-1, -1) from doing anything. Useful?
      moveCursor(p);
    }
  }
  
  // Sets up the menu for player interactivity.
  public void show() {
    this.active = true;
  }
  
  // Closes the menu from player interactivity (doesn't free memory).
  public void hide() {
    this.active = false;
  }
  
  // Returns whether or not the menu is currently hidden from view/input.
  public boolean hidden() {
    return (this.active == false);
  }
  
  /* Gives the command menu a new set of commands to display.
   * CommandMenu will parse the list of given EquipIDs for choosable actions,
   * and will only display those.
   */
  public void setCommands(List<EquipID> commandsList) {
    this.commands = commandsList;
    this.endOfList = this.commands.size() + NUMBER_OF_STATIC_COMMANDS;
    resetCursor();
  }
  
  /* Can be called indiscriminately, thus preserving "player selection memory" depending on the situation.
   * [Ideal: after player selects a unit, selection is preserved until the player select a ~different~ unit.
   * [  So, if the player moves forward and cancels, or even if the player cancels the unit and reselects them,
   * [  the player's cursor position is remembered.
   * [  TODO TurnMachine should probably handle when resetCursor() is called, then. Maybe. 
   */
  public void resetCursor() {
    this.cursorPos = 0;
  }
  
  // Moves the cursor up and down the menu, wrapping if the cursor moves beyond.
  public void moveCursor(GridPoint p) {
    this.cursorPos += p.y;
    if (this.cursorPos < 0) this.cursorPos = this.endOfList - 1;
    if (this.cursorPos >= this.endOfList) this.cursorPos = 0;
  }
  
  /* Returns whichever command is being hovered over by the selection cursor.
   * Currently, this includes all submitted actionable commands plus the special "Wait" command.
   * The structure of this method does not allow for more special functions than just the "Wait" command.
   */
  public EquipID getSelection() {
    EquipID r = EquipmentDatabaseManager.instance().waitCommand();
    if (this.cursorPos >= 0 && this.cursorPos < this.endOfList - NUMBER_OF_STATIC_COMMANDS) {   // If cursor is hovering over any command excluding "Wait"
      r = this.commands.get(this.cursorPos);
    }
    
    return r;
  }
  
  /* Returns a list of all commands implied by the given list.
   * "Implied" includes the "Wait" command, which is appended to the list before the list is returned to the method-caller.
   */
  public List<EquipID> getCommandList() {
    ArrayList<EquipID> list = new ArrayList<EquipID>();
    list.addAll(this.commands);
    list.add(EquipmentDatabaseManager.instance().waitCommand());
    return list;
  }
}
