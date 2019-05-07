/*
 * TurnState describes the methods each state must have to be usable by TurnMachine.  
 */

package statemachines.turnstates;

import statemachines.TurnMachine;

public abstract class TurnState {
  
  // Provides access to protected methods for inserting Unit and ActionData objects.
  protected static TurnMachine tm;
  
  // Constructor used to initialize common fields.
  protected TurnState() {
    this.tm = TurnMachine.instance();
  }
  
  // Defined by the inheritor, specifies which states come before and after in the sequence.
  public TurnState getNextState() { return null; }
  public TurnState getPrevState() { return null; }

  public void init()   {} // Called once only; used to set up field cursors, menus, info screens, etc.
  public void update() {} // Called once per frame; watches for changes to relevant TurnMachine fields.
  public void next()   {} // Called once only; calls close(), sets up necessary details, and returns which state should be switched to.
  public void prev()   {} // Called once only; calls close(), and undoes any changes to tm it may have done.
  public void close()  {} // Called whenever the state is being switched from. Frees up memory, closes cursors and menus, etc.
  
  // I think next() and prev() might be unnecessary.
  
}
