package statemachines.turnstates;

import battle.entities.unit.Unit;
import battle.map.Grid;
import battle.ui.GridCursor;
import battle.ui.InfoWindow;
import input.VirtualController;
import input.VirtualButton;

public class PickUnit extends TurnState {

  private final Grid grid = Grid.instance();
  private final GridCursor cursor = GridCursor.instance();
  private final InfoWindow infoW = InfoWindow.instance();
  private final VirtualController controller = VirtualController.instance();
  
  @Override
  public TurnState getNextState() { return new PickMoveTile(); }
  
  @Override
  public void init() {
    cursor.show();
    infoW.show();
  }
  
  @Override
  public void update() {
    // Here
    // Or somewhere
    // update checks for two things
    // Arrow Keys?
    // Or Select|Cancel?
    // If either are true, do something with whatever location the cursor is occupying.
    
    // - ArrowKeys      : Move
    // - Select         : If Ally   - Pick Unit
    //                    If Not    - Open Menu
    // - Cancel         : If Unit   - Show unit attack range, disable cursor movement.
    // - Mod            : If Foe    - Add unit attack range to global danger range.
    
    // I think I put these here... just to get something working. I don't remember where I planned on moving them.
    // I guess this should be in GridCursor; the behavior is a little more complex than what it simply written here.
    // The accept button would not be a part of grid cursor, though, which... seems weird.
    
    // Allows the cursor we've invoked to "live"
    cursor.update();
    
    // Use the accept button to pick a unit and add it to TurnMachine's internals.
    if (controller.buttonReleased(VirtualButton.Accept)) {
      // Check the grid cell for an allied unit.
      Unit u = grid.getUnit(cursor.location());
      if (u != null && u.getTeam() == tm.getActiveTeam()) {
        tm.setActingUnit(u);
        tm.setNextStateFlag();
      }
    }
    
    // TODO Use the cancel button to look at an enemy's attack range
    // // //
    
    // TODO Use the mod button to add an enemy's attack range to the "Danger Range"
    // // //
  }

  @Override
  public void close() {
    cursor.hide();
    infoW.hide();
  }

}
