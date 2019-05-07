/*
 * This is an information container, essentially.
 * For an individual tile on the map, keeps track of map data, inhabitants, iteration data, etc.
 */

package battle.map;

import battle.constants.Team;
import battle.entities.unit.Unit;

public class Cell {

  public boolean wall = false;
  public boolean spawnPoint = false;
  public Team spawnTeam;       // I think.. there should be a list..? Hm.
  
  public Unit unit;
  //Treasure treasure;
  
  // UI Tile-Highlight values
  public boolean moveTile = false;   // These three can't really be active at the same time.
  public boolean attackTile = false; // v    TODO [Optimization: truncate down to one]
  public boolean aidTile = false;    // v
  public boolean dangerTile = false;     // The difference is this one is for specific units, 
  public boolean teamDangerTile = false; // and this one is for the whole team.
  
  public int val = 0;
  
  
  public Cell() {
    unit = null;  // Doesn't it already?
  }
  
  
  public void resetSnapshotData() {
    this.moveTile = false;
    this.attackTile = false;
    this.aidTile = false;
    this.dangerTile = false;
    this.teamDangerTile = false;
    this.unit = null;
    //this.treasure = null;
    
    this.val = 0;
  }
  
}
