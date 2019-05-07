package statemachines.turnstates;

public class ConfirmChoices extends TurnState {

  //private final ConfirmDialog dialog = ConfirmDialog.getInstance();
  
  @Override
  public TurnState getNextState() { return new Finalize(); }

  @Override
  public TurnState getPrevState() { return new PickTarget(); }

  @Override
  public void init() {
    // TODO Setup Confirm Window
    //dialog.wake();
  }

  @Override
  public void update() {
    // TODO Wait for accept. tm.commandConfirmed == true, maybe.
  }

  @Override
  public void close() {
    //dialog.sleep();
  }

}
