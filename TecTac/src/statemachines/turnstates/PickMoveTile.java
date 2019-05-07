package statemachines.turnstates;

import battle.entities.unit.Unit;
import battle.map.Grid;
import battle.ui.GridCursor;
import battle.ui.InfoWindow;
import input.VirtualButton;
import input.VirtualController;
import statemachines.TurnMachine;

public class PickMoveTile extends TurnState {

  private final GridCursor cursor = GridCursor.instance();
  private final InfoWindow infoW = InfoWindow.instance();
  private final Grid board = Grid.instance();
  private final VirtualController controller = VirtualController.instance();
  
  @Override
  public TurnState getNextState() { return new PickCommand(); }  // Why would these be instances? Shouldn't these states be static? No. Okay, singletons, then. They really should be static, though. TurnMachine would need to be updated a little for that to happen.

  @Override
  public TurnState getPrevState() { return new PickUnit(); }  // new PickUnit() ... seems silly.

  @Override
  public void init() {
    board.updateSnapshot();
    board.generateMoveTiles(tm.getActingUnit().getPos(), tm.getActingUnit().getStats().getMOV());
    
    cursor.show();
    infoW.show();
  }

  @Override
  public void update() {
    // TODO Watch for tm.newLoc != null;
    // Maybe call?
    //   if (tm.locationPicked()) {}
    
    // Allows the cursor we've invoked to "live"
    cursor.update();
    
    // Use the accept button to pick a move-tile and add it to TurnMachine's internals.
    if (controller.buttonPressed(VirtualButton.Accept)) {
      // Check the grid cell for a valid move-tile.
      if (board.getCell(cursor.location()).moveTile) {
        tm.setMoveLocation(cursor.location());
        tm.setNextStateFlag();
      }
    }
    
    if (controller.buttonPressed(VirtualButton.Cancel)) {
      tm.setPrevStateFlag();
    }
  }

  @Override
  public void next() {
    tm.getActingUnit().setPos(tm.getNewUnitLoc());
  }

  @Override
  public void prev() {
    cursor.setPos(tm.getActingUnit().getPos());
  }

  @Override
  public void close() {
    cursor.hide();
    infoW.hide();
    board.eraseMoveTiles();
  }

}
