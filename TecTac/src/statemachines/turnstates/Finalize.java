package statemachines.turnstates;

public class Finalize extends TurnState {

  //private final BattleManager bm = BattleManager.getInstance();
  
  @Override
  public TurnState getNextState() { return new PreAct(); }

  @Override
  public void init() {
    // TODO All the work.
    // All the work can be done in this step: pass-through state.
    
    //bm.applyEffects(ActionData command, Unit[] list, Point loc);
  }
  
}
