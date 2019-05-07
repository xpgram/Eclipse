/*
 * A container class for each team and all their members.
 * If more than units are allowed to exist on the map, this class will hold references to them as well.
 * 
 * TODO Add support for treasure and other kinds of map entities.
 */

package battle.entities;

import battle.constants.Team;
import battle.entities.unit.Unit;
import battle.map.Grid;
import utils.GridPoint;

public class EntitiesList {

  // Singleton reference
  private static EntitiesList instance;
  
  // Constants for mock team setup.
  final int NUM_ALLIES = 3;
  final int NUM_FOES = 3;
  final int NUM_SUPPORT = 0;
  
  // Team Objects
  BattleParty allies;
  BattleParty foes;
  BattleParty support;
  //List<Treasure> treasures;       // I believe these are Treasure entities which exist on the field, not drop prizes held by units.
  
  // Other fields
  boolean initialized = false;
  
  // Singleton references
  private static Grid grid;
  
  
  /* Constructor. Tsh.
   */
  private EntitiesList() {
  }
  
  /* Retrieves instance of singleton.
   */
  public static EntitiesList instance() {
    if (instance == null)
      instance = new EntitiesList();
    return instance;
  }
  
  public void init() {
    grid = Grid.instance();
  }
  
  /* Accepts references to BattleParties constructed (or loaded) elsewhere.
   * @param allyTeam: BattleParty object containing allied team members.
   * @param enemyTeam: BattleParty object containing enemy team members.
   * @param supportTeam: BattleParty object containing support team members.
   */
  public void setTeams(BattleParty allyTeam, BattleParty enemyTeam) {
    this.setTeams(allyTeam, enemyTeam, new BattleParty(Team.Support, 0));
  }
  
  /* Accepts references to BattleParties constructed (or loaded) elsewhere.
   * @param allyTeam: BattleParty object containing allied team members.
   * @param enemyTeam: BattleParty object containing enemy team members.
   * @param supportTeam: BattleParty object containing support team members.
   */
  public void setTeams(BattleParty allyTeam, BattleParty enemyTeam, BattleParty supportTeam) {
    this.allies = allyTeam;
    this.foes = enemyTeam;
    this.support = supportTeam;
    this.initialized = true;
  }
  
  /* Builds and returns a list of references for all units currently in play.
   * @return: An array Unit[] of Units.
   */
  public Unit[] getAllUnits() {
    if (this.initialized == false)
      return null;    // TODO Throw exception: Trying to access battle units, but no such objects exist.
    
    int totalUnits = allies.teamSize() + foes.teamSize() + support.teamSize();
    Unit[] list = new Unit[totalUnits];
    int base; // Where idx is counting from in the list of all units.
    
    // Add allied team units to list.
    base = 0;
    for (int i = 0; i < allies.teamSize(); i++) {
      list[i] = allies.getMember(i);
    }
    
    // Add enemy team units to list.
    base = allies.teamSize();
    for (int i = 0; i < foes.teamSize(); i++) {
      list[i + base] = foes.getMember(i);
    }
    
    // Add support team units to list.
    base += foes.teamSize();
    for (int i = 0; i < support.teamSize(); i++) {
      list[i + base] = support.getMember(i);
    }
    
    return list;
  }
  
  /* Retrieves the BattleParty object for the allied team.
   * @return: A BattleParty object. Null if none exists yet.
   */
  public BattleParty getAllyTeam() {
    return allies;
  }
  
  /* Retrieves the BattleParty object for the enemy team.
   * @return: A BattleParty object. Null if none exists yet.
   */
  public BattleParty getEnemyTeam() {
    return foes;
  }
    
  /* Retrieves the BattleParty object for the support team.
   * @return: A BattleParty object. Null if none exists yet.
   */
  public BattleParty getSupportTeam() {
    return support;
  }
  
  /* Retrieves the BattleParty object for the specified team.
   * @return: A BattleParty object. Null if none exists yet.
   */
  public BattleParty getParty(Team team) {
    BattleParty r = null;
    switch (team) {
      case Allies:
        r = getAllyTeam();
        break;
      case Foes:
        r = getEnemyTeam();
        break;
      case Support:
        r = getSupportTeam();
        break;
    }
    return r;
  }
  
}
