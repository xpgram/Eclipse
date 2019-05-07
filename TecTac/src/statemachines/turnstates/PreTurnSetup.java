package statemachines.turnstates;

import battle.entities.BattleParty;
import battle.entities.EntitiesList;
import battle.ui.GridCursor;
import statemachines.TurnMachine;
import utils.GridPoint;

public class PreTurnSetup extends TurnState {
  
  private final TurnMachine tm = TurnMachine.instance();
  private final GridCursor cursor = GridCursor.instance();
  private final EntitiesList entities = EntitiesList.instance();

  @Override
  public TurnState getNextState() { return new PreAct(); }

  @Override
  public void init() {
    
    // Activate all allied members.
    BattleParty party = entities.getParty(TurnMachine.instance().getActiveTeam());
    for (int i = 0; i < party.teamSize(); i++) {
      party.getMember(i).activate();
    }
    
    // Move FieldCursor into place.
    GridPoint p = entities.getParty(tm.getActiveTeam()).getMember(0).getPos();
    cursor.setPos(p);
    
    tm.setNextStateFlag();
  }
  
}
