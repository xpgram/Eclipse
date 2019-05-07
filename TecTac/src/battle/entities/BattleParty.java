/*
 * This class represents a team of units.
 * It holds references to each member, and has methods for determining the overall state of
 * the team itself. 
 */

package battle.entities;

import java.util.ArrayList;
import java.util.List;
import battle.constants.Team;
import battle.entities.unit.Unit;

public class BattleParty {
  
  List<Unit> members;
  Team team;
  
  
  /* Constructs a BattleParty object.
   * @param team: A Team type this party is associated with.
   * @param units: A list of units to include as members of the team.
   */
  public BattleParty(Team team, List<Unit> units) {
    this.members = units;
    this.team = team;
  }
  
  /* Constructs a mock BattleParty object for testing purposes.
   */
  public BattleParty(Team team, int numMembers) {
    this.team = team;
    char label = 'A';
    members = new ArrayList<Unit>();
    for (int i = 0; i < numMembers; i++) {
      this.members.add(new Unit());
      this.members.get(i).generateFalseData(team, label);
      label += 1;
    }
  }
  
  /* Returns the length of the list of team members.
   * @return: An int
   */
  public int teamSize() {
    return members.size();
  }
  
  /* Returns the unit held at index idx in the list.
   * @param idx: The index of the team member to retrieve.
   * @return: A Unit object.
   */
  public Unit getMember(int idx) {
    if (idx < 0 || idx > members.size())
      return null;
    
    return members.get(idx);
  }
  
  /* Activates all unit members of this team for use on the battlefield. 
   */
  public void activateAllMembers() {
    for (Unit unit : members) {
      unit.activate();
    }
  }
  
  /* Checks all units and returns true if all have been HP KOed.
   */
  public boolean teamDefeated() {
    boolean result = true;
    for (Unit unit : members) {
      if (unit.active()) {
        result = false;
        break;
      }
    }
    
    return result;
  }
  
}
