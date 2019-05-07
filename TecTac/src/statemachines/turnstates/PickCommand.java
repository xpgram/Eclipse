/*
 * If TurnMachine shouldn't have functions for inserting ... well, it'll have to.
 * But maybe they should be confined to being used by its "arms" (these state classes).
 * Anyway, if that's the case, I could pass a reference to self to cmdMenu to collect the player's
 * input (or FieldCursor, or PauseMenu, etc.). next() could pass it along to TurnMachine.
 * 
 * TODO Pass reference to self to cmdMenu to collect player input?
 */

package statemachines.turnstates;

import battle.ui.CommandMenu;
import input.VirtualButton;
import input.VirtualController;
import statemachines.TurnMachine;

public class PickCommand extends TurnState {

  private static CommandMenu cmdMenu = CommandMenu.instance();
  private static VirtualController controller = VirtualController.instance();
  
  @Override
  public TurnState getNextState() { return new PickTarget(); }

  @Override
  public TurnState getPrevState() { return new PickMoveTile(); }

  @Override
  public void init() {
    cmdMenu.setCommands(tm.getActingUnit().getEquipment().getCommands());
    cmdMenu.show();
  }

  @Override
  public void update() {
    // TODO Watch for (tm.command != null)
    
    // Player presses Accept:
    
    // Player presses Cancel:
    //else if
    if (controller.buttonPressed(VirtualButton.Cancel))
      tm.setPrevStateFlag();
  }
  
  @Override
  public void next() {
    
  }
  
  @Override
  public void prev() {
    tm.getActingUnit().setPos(tm.getOldUnitLoc());
  }
  
  @Override
  public void close() {
    cmdMenu.hide();
  }

}
