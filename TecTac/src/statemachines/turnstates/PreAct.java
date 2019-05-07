/*
 * This step is handled once between each unit order given.
 * PreTurnSetup is called once per turn, so anything that needs
 * to be reset between each individual unit order is handled here.
 * 
 * Because this is a "line up the pins," pass-through kind of class,
 * init() is the only relevant method here.
 */

package statemachines.turnstates;

public class PreAct extends TurnState {
  
  @Override
  public TurnState getNextState() { return new PickUnit(); }

  @Override
  public void init() {
    // PreAct will at least send the final TurnIsOver message.
    // Unless auto is off, in which case the PauseMenu will send it, I guess.
    
    tm.setNextStateFlag();
  }

}
