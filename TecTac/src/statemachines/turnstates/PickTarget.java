/*
 * Keep in mind, most games like this usually have an alternate set of controls for picking targets.
 * 
 * AW and FE let you move the cursor (I think ... sometimes) but they usually switch to an auto-target-anyone-in-range
 * style system. Hitting a directional button just moves the cursor to the next target, assuming there is one.
 * 
 * Disgaea might let you move the cursor (most times), but they have another system for lines: you pick a direction.
 * 
 * My point is, PickTarget, eventually, will need more sophistication than just using the FieldCursor.
 * 
 * TODO Implement alternate target-picking control systems.
 */

package statemachines.turnstates;

public class PickTarget extends TurnState {

  //private final FieldCursor cursor = FieldCursor.getInstance();
  //private final Grid board = Grid.getInstance();
  
  @Override
  public TurnState getNextState() { return new ConfirmChoices(); }

  @Override
  public TurnState getPrevState() { return new PickCommand(); }

  @Override
  public void init() {
    //board.buildNew();
    //board.generateAttackTiles(tm.activeUnit.pos, tm.command.range);
    
    //cursor.wake();
    //cursor.setTarget(tm.command.targetType);
  }

  @Override
  public void update() {
    // TODO Watch for (tm.targetLoc != null)
  }

  @Override
  public void prev() {
    //cursor.move(tm.activeUnit.pos);
  }

  @Override
  public void close() {
    //cursor.sleep();
    //board.resetSpecials();
  }

}
