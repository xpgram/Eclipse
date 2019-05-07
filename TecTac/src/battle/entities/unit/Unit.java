package battle.entities.unit;

import battle.constants.Team;
import utils.GridPoint;

public class Unit {
  
  GridPoint pos;
  //Vector2 tileDisplace;     // Either: ranges between -1,1, displaces from pos; or is the sprites actual pos, which moves toward pos*cellSize.
  
  Team team;                  // Which team this unit is a part of. Should this be ~here~?
  char label;                 // A 'name' for this unit. Especially useful for enemies.
  boolean active;             // Whether or not the unit is still allowed to act during the current turn.
  boolean enabled;            // Whether or not the unit is still a present entity on the battlefield.
  
  UnitInfo info;              // Contains unchanging ID-type information.
  UnitCondition condition;    // Keeps track of HP, KO status, status effects like blind, etc.
  UnitStats stats;            // Keeps track of unit's core stats: max HP, PHY, INT, MOV, level up and growth functions, etc.
  UnitEquipment equipment;    // Keeps track of unit's equipment, command list, passive effects, etc.
  UnitVictoryCache prize;     // Keeps track of--and unloads--the unit's drop or pilfer prize when appropriate.
  //Sprite sprite;            // Knows what to draw, yo. In the future, because Unit's will have bodies with variable heads, I guess, I may need a special UnitGraphics type.
  
  
  public Unit() {
    pos = new GridPoint(-1, -1);
    team = null;
    label = ' ';
    deactivate();
    disable();
  }
  
  public void generateFalseData(Team team, char label) {
    this.team = team;
    this.label = label;
    
    info = new UnitInfo(this);
    stats = new UnitStats(this);
    condition = new UnitCondition(this);
    equipment = new UnitEquipment(this);
    prize = new UnitVictoryCache(this);
    
    enable();
  }
  
  public Team getTeam() {
    return team;
  }
  
  public char getLabel() {
    return label;
  }
  
  public GridPoint getPos() {
    return pos;
  }
  
  public void setPos(GridPoint p) {
    pos.clone(p);
  }
  
  public UnitInfo getInfo() {
    return info;
  }
  
  public UnitStats getStats() {
    return stats;
  }
  
  public UnitCondition getCondition() {
    return condition;
  }
  
  public UnitEquipment getEquipment() {
    return equipment;
  }
  
  public void activate() {
    this.active = true;
  }
  
  public void deactivate() {
    this.active = false;
  }
  
  /* Returns true if the unit can still act this turn.
   */
  public boolean active() {
    return this.active;
  }
  
  /* Enables the unit, signaling that it should resume being relevant.
   * Used generally to revive fallen units. (That is, unless, UnitCondition could be better used for the same purpose.)
   */
  public void enable() {
    this.enabled = true;
  }
  
  /* Disables the unit, signaling that it is no longer relevant to anything.
   * Used generally to exclude a dead/KOed unit. (That is, unless, UnitCondition could be better used for the same purpose.)
   */
  public void disable() {
    deactivate();
    this.enabled = false;
  }
  
  // Returns true if the unit is enabled for processing/use.
  public boolean enabled() {
    return this.enabled;
  }
  
  // Put in UnitCondition! Or.. Well, I don't know. Maybe.
  public void death() {
    disable();
    setPos(new GridPoint(-1, -1));
  }
  
}
