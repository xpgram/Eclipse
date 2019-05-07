package statemachines.turnstates;

import battle.constants.Team;
import battle.entities.BattleParty;
import battle.entities.EntitiesList;
import battle.map.Grid;
import battle.ui.GridCursor;
import statemachines.TurnMachine;
import utils.GridPoint;

public class MatchSetup extends TurnState {

  private final Grid board = Grid.instance();
  private final GridCursor cursor = GridCursor.instance();
  private final EntitiesList entities = EntitiesList.instance();
  
  @Override
  public TurnState getNextState() { return new PreTurnSetup(); }

  @Override
  public void init() {
    // TODO Shape the battlefield with which the player shall play on.
    // - TurnMachine will have a rudimentary mapID system such that I can load specific scenarios. 
    // - MatchSetup will ask Grid to build the map (a loaded one, if possible)
    // - MatchSetup will ask EntitiesList to build allies and enemies (loading them, if possible).
    // - MatchSetup will call SpawnUnits(), which places all units onto the gamemap.
    // - MatchSetup sets|determines whose turn it is to start with (probably the player's always).
    // - Other various details, probably.
    
    // In the future:
    // - TurnMachine should hold some kind of mapID value that MatchSetup may reference.
    // - MatchSetup will ask Grid to load that mapID's details
    // - MatchSetup will ask EntitiesList to load enemies from that mapID as well.
    // - MatchSetup will ask EntitiesList to load allied data from the players save.
    // - Grid should keep track of spawn points, tiles which are allowed to start with units on them.
    // - There will be a pre-battle phase where the player will place their favorite units onto these tiles.
    // - The enemy's spawn tiles will spawn specific enemies, or randomly generate ones based on the environment/mapID
    //    - I might want a special class for this; spawning random enemies could be meticulous work to get balanced.
    //    - Maybe call it EnemyLoader or something.
    // - 
    
    // Create a (mock-up) battlefield to play on.
    int boardW = 15;
    int boardH = 10;
    GridPoint allySpawnTL = new GridPoint((int)(boardW*.1), (int)(boardH*.2));
    GridPoint allySpawnBR = new GridPoint((int)(boardW*.3), (int)(boardH*.8));
    GridPoint foeSpawnTL = new GridPoint((int)(boardW*.7), (int)(boardH*.2));
    GridPoint foeSpawnBR = new GridPoint((int)(boardW*.95), (int)(boardH*.8));
    
    board.generateNewBoard(boardW, boardH);
    cursor.constrain(boardW, boardH);
    
    // Create (mock-up) units to play with.
    BattleParty allies = new BattleParty(Team.Allies, 3);
    BattleParty foes = new BattleParty(Team.Foes, 3);
    //BattleParty support = new BattleParty(Team.Support, 2);
    entities.setTeams(allies, foes);
    
    // Spawn each team's units onto battlefield.
    for (int i = 0; i < allies.teamSize(); i++) {
      board.updateSnapshot();
      allies.getMember(i).setPos(board.generateUniquePoint(allySpawnTL, allySpawnBR));
    }
    for (int i = 0; i < foes.teamSize(); i++) {
      board.updateSnapshot();
      foes.getMember(i).setPos(board.generateUniquePoint(foeSpawnTL, foeSpawnBR));
    }
    
    // Oh, I'm dumb. I gotta do this one last time.
    board.updateSnapshot();
    
    // Set|Determine Intitial Battle Conditions
    //TurnMachine.activeTeam = Team.Allies;
    //TurnMachine.turnNumber = 0;
    //TurnMachine.fieldCursorLastPos = allies.getMember(0).getPos();        // What if [allies] is null?
        // TODO The above two lines are how this should work, but I need to set up some way for TurnMachine and each TurnState to access
        // all of these fields. I think I didn't want them to be public because "then any old fuck could change the team on a whim," but
        // I'm not sure how much it matters. They'll be public to TurnStates, anyway.
        // Best intuitive solution I have right now: require that each setter method in TurnMachine receives a TurnState-type argument.
        // MatchSetup would then call TurnMachine.setActiveTeam(this, Team.Allies) and the lock and key would work kinda, I guess.
    
    // Tell the battle-mode's engine that we are finished setting up and are ready to progress to the next step.
    tm.setNextStateFlag();
    
    //super.tm.setNextStateFlag();    // This creates a null pointer exception, I think because super is referring to an abstract class and tm = TurnMachine.instance() is never actually initialized.
    //Ohh... the constructor for any TurnState-inherited classes must call super() as their first line. Maybe super() should have the instance() call.
  }

}
