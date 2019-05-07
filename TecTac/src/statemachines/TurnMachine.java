/* Keeps track of battle/turn/command state.
 * 
 * I think ... some of these public methods should actually be protected.
 * TurnState needs to be able to access them (hence they're public), but I don't need some outside source
 * grabbing the static tm and fucking up the whole kebab.
 * TODO Use "protected" to protect methods like cleanPreActValues from outside meddling.
 * 
 * TODO I should (I think) make all states singleton objects *OR*
 * Restructure this class slightly to use an enum and static calls. Oh wait. That means I would need
 * a switch statement to interpret enums instead of my nifty "state=whatever just do it" methodology.
 * Okay. Singletons it is, then.
 * 
 * I suppose TODO Make all states complete.
 * That's a tall order, but I also just mean relative to what I have working in Processing. This
 * program isn't runnable yet. As of writing, I don't even have the Processing libraries in place.
 */

package statemachines;

import utils.GridPoint;
import statemachines.turnstates.TurnState;
import statemachines.turnstates.MatchSetup;
import battle.actions.AreaEffectMap;
import battle.actions.EquipID;
import battle.actions.EquipmentDatabaseManager;
import battle.constants.TargetType;
import battle.constants.Team;
import battle.entities.EntitiesList;
import battle.entities.unit.Unit;
import battle.map.Grid;

public class TurnMachine {
  private static TurnMachine tm;          // The handle for the TurnMachine singleton.
  
  private static Grid grid;               // The handle for the grid and map data. (Used for validPoint(p) only).
  private static EntitiesList entities;   // The handle for the list of all units and other things. (Not sure if used other than to initialize.)
  
  private static TurnState state;         // Reference to the object which is managing the battle mode's current state.
  private static ToState toState;         // An enum flag which tells us which direction to move the machine in.
  
  private static int turnNumber;          // The number of turns taken since the start of the match.
  
  private static Team activeTeam;         // The team currently giving orders.
  private static Unit activeUnit;         // The unit currently being ordered.
  private static GridPoint oldLoc;        // Where the unit was located before being ordered.
  private static GridPoint newLoc;        // Where the unit is being moved to post-orders.
  private static EquipID command;         // The command to be issued to the active unit on State.Act
  private static TargetType targetType;   // Supplementary to targetLoc; tells us which kind of target the point is referring to.
  private static AreaEffectMap targetMap; // Supplementary to targetLoc; tells us if the action is an AoE effect.
  private static GridPoint targetLoc;     // This might be the location of an enemy, an ally, or just a destination tile.
	
  
	private TurnMachine() {
	  // Setup|construct singleton references.
	  grid = Grid.instance();
	  entities = EntitiesList.instance();
	  
	  grid.init();
	  entities.init();
	}
	
	// Returns a reference to the singleton instance for TurnMachine. 
	public static TurnMachine instance() {
	  if (tm == null)
	    tm = new TurnMachine();
	  return tm;
	}
	
	/* The constructor constructs itself, this links other things together.
	 * It's phase 2 in the construction process and must be called explicitly.
	 */
	public void init() {
	  // Initiate first state.
	  changeState(new MatchSetup());
	  
    // Set up basic match information.
    turnNumber = 1;
    activeTeam = Team.Allies;    // This might be variable. ...Or better yet, should match setup do this? It does other things.
    cleanPreActValues();
	}
	
	
	public void update() {
	  state.update();
	  
	  switch (toState) {
	    case Next:
	      state.next();
	      changeState(state.getNextState());
	      break;
	    case Prev:
	      state.prev();
	      changeState(state.getPrevState());
	      break;
	    default:
	      break;
	  }
	}
	
	/* I'm leaving this private for now. TurnState objects should just use setNextStateFlag()
	 * and submit their getNextState() value.
	 */
	private void changeState(TurnState s) {
	  toState = ToState.None;
	  
	  if (state != null)
	    state.close();
	  //if (s == null)
	    // TODO throw exception
	  
	  state = s;
	  state.init();
	}
	
	
	public void cleanPreActValues() {
    activeUnit = null;
    oldLoc = null;
    newLoc = null;
    command = null;
    targetLoc = null;
    targetType = TargetType.None;
    targetMap = new AreaEffectMap().createSingle();
	}
	
	
	public void setNextStateFlag() {
	  toState = ToState.Next;
	}
	
	
	public void setPrevStateFlag() {
	  toState = ToState.Prev;
	}
	
	
	public void advanceTurnNumber() {
   turnNumber++;
	}
	
	
	public int getTurnNumber() {
	  return turnNumber;
	}
	
	
	public Team getActiveTeam() {
	  return activeTeam;
	}
	
	
	public void alternateActiveTeam() {
	  switch (activeTeam) {
	    case Allies:
	      activeTeam = Team.Foes;
	      break;
	    case Foes:
	      activeTeam = Team.Support;
	      break;
	    case Support:
	      activeTeam = Team.Allies;
	      break;
	  }
	}
	
	
	public boolean isActingUnitChosen() {
	  return (activeUnit != null);
	}
	
	
	public Unit getActingUnit() {
	  return activeUnit;
	}
	
	
	public void setActingUnit(Unit u) {
	  activeUnit = u;
	}
	
	
	public boolean isLocationChosen() {
	  return (newLoc != null);
	}
	
	
	public void setMoveLocation(GridPoint p) {
	  if (grid.validPoint(p) != true)
	  {} // TODO throw exception
	  if (activeUnit == null)
	  {} // TODO throw exception
	  
	  newLoc = new GridPoint().clone(p);
	  oldLoc = new GridPoint().clone(activeUnit.getPos());
	}
	
	
	public GridPoint getOldUnitLoc() {
	  return oldLoc;
	}
	
	
	public GridPoint getNewUnitLoc() {
	  return newLoc;
	}
	
	
	public boolean isCommandChosen() {
	  return (command != null);
	}
	
	
	public void setCommand(EquipID equip) {
	  EquipmentDatabaseManager edm = EquipmentDatabaseManager.instance();
	  edm.using(equip);
	  
	  command = equip;
	  targetType = edm.getTargetType();
	  targetMap = edm.getAreaMap();
	}
	
	
	public EquipID getCommand() {
	  return command;
	}
	
	
	public boolean isTargetChosen() {
	  return (targetLoc != null);
	}
	
	
	// These, as it turns out, are not necessary. I am using them as a guide, however.
	enum State {
		MatchSetup,     // Instantiate ally/enemy unit objects, entity objects, etc.
		PreTurnSetup,   // Set all units to active, etc.
		Animate,        // Indicates something is being animated. This is a solitary step that will return to whatever was interrupted after animation is complete (ideally).
		PauseMenu,      // Switched to whenever the pause menu is opened. It can only be opened, I believe, from the PickUnit phase.
		                //    This is because PickUnit is also where your basic intel gathering happens. UnitInfoWindow, enemy attack ranges, etc.
		
		PreAct,         // The beginning of the unit-move-act loop. Sets up or checks anything which needs it, and exits the loop.
		PickUnit,       // PlayerObject uses FieldCursor to select an active unit.  If auto-end is off, PauseMenu will exit the loop.
		PickLocation,   // PlayerObject uses FieldCursor to select a valid (in range) tile to move to.
		PickCommand,    // PlayerObject uses CommandMenu to select and give the unit an order.
		PickTarget,     // PlayerObject uses FieldCursor to select a valid (qualifying) unit or tile to act on.
		ConfirmChoices, // PlayerObject (actual player only) must confirm before the command is given and made irreversible.
		Finalize,       // Plays out the orders given to the unit, inflicting damage / etc. May be interrupted by Animate step.
		
		ChangeTurn,     // Handles state changes between teams. ActiveTeam = next, etc.
		
		MatchEnd,       // Determines winner, handles uh... you know. Etc.	(Meant to combine Win and Lose from last TurnM iteration).
		VictoryScreen,  // Displays final match results, xp gained, etc.
		GameOver,       // Some of these I'm not sure if I need or not. We'll see. This would handle displaying GameOver graphic / returning the player to file select screen / etc.
		
		MatchClose,     // Free memory, return control to OverworldMachine, etc.			
	}
	
	
	private enum ToState {
	  None,
	  Next,
	  Prev
	}
	
}
