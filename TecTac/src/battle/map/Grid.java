/*
 * This class is the interface for all map-related information requests.
 * I chose to build it like a picture machine instead of a chess board: meaning you don't have to remove and replace
 * pieces from the board, it simply erases and re-inserts references to entities as needed.
 * This does mean you have to remember to call updatePicture() before doing anything.
 * 
 * Anyway, this biggest problem here, besides making sure all my class calls are linked properly,
 * is that I want to add a movable camera--and, I think drawing the grid tiles possibly shouldn't
 * be handled by the Grid? I dunno. Let's go jerk off to Jessica instead.
 */

package battle.map;

import battle.actions.EquipID;
import battle.actions.EquipmentDatabaseManager;
import battle.constants.TargetType;
import battle.entities.EntitiesList;
import battle.entities.unit.Unit;
import statemachines.TurnMachine;
import utils.GridPoint;

public class Grid {
  
  // Singleton handle
  private static Grid self;
  
  // Singleton references
  private EntitiesList entities;
  
  // Fields
  private int boardWidth;
  private int boardHeight;
  Cell[] board;
  boolean initialized = false;
  
  
  private Grid() {
  }
  
  // Singleton accessor.
  public static Grid instance() {
    if (self == null)
      self = new Grid();
    return self;
  }
  
  
  // Used for linking singletons, among other things if necessary.
  public void init() {
    entities = EntitiesList.instance();
  }
  
  
  private boolean boardInitialized() {
    if (this.initialized == false) {
      // TODO Throw exception: Trying to access game board, but no such board exists.
      return false;
    }
    
    return true;  // Grid has been initialized.
  }
  
  
 /* I changed this from private to public because it is called by MatchSetup to build the game's play area.
  * However, I believe it was private to begin with because it isn't meant to be called once the map is in play.
  * Either I'll need a buffer method that calls this one after it has verified the current map isn't being used:
  *     - Call createNewBoard(w,h);
  *     - Use board
  *     - Call destroyBoard();        Sets some boolean, initialized probably, to false, allowing:
  *     - Call createNewBoard(w,h);
  * Or... some other idea, I don't know.
  * TODO Add protections against recreating the game board while it is being used by the game.
  */
  public void generateNewBoard(int width, int height) {
    this.boardWidth = width;
    this.boardHeight = height;
    
    board = new Cell[boardWidth * boardHeight];
    for (int x = 0; x < boardWidth; x++) {
      for (int y = 0; y < boardHeight; y++) {
        board[x + y*boardWidth] = new Cell();
      }
    }
    this.initialized = true;
    
    generateWalls(20);
  }
  
  
  public void clean() {
    for (int i = 0; i < board.length; i++) {
      board[i].resetSnapshotData();
    }
  }
  
  public int boardWidth() {
    return this.boardWidth;
  }
  
  public int boardHeight() {
    return this.boardHeight;
  }
  
  // TODO Raise exception if invalid.
  public Cell getCell(GridPoint p) {
    if (!boardInitialized()) return null;
    if (!validPoint(p)) return null;
    return board[p.x + p.y * boardWidth];
  }
  
  // TODO Raise exception if invalid.
  public Unit getUnit(GridPoint p) {
    if (!boardInitialized()) return null;
    if (!validPoint(p)) return null;
    return board[p.x + p.y * boardWidth].unit;
  }
  
  
  public void updateSnapshot() {
    if (!boardInitialized()) return;
    
    clean();
    GridPoint pos;
    Unit[] list = entities.getAllUnits();
    
    // Add all character objects to the map.
    for (int i = 0; i < list.length; i++) {
      pos = list[i].getPos();
      if (validPoint(pos)) {
        board[pos.x + pos.y * boardWidth].unit = list[i];
      }
    }
    
    // Treasures?
    
    // Re-compose danger tiles and team danger tiles?
  }
  
  
  public void generateMoveTiles(GridPoint source, int range) {
    if (!boardInitialized()) return;
    
    updateSnapshot();  // Update our picture
    
    getCell(source).moveTile = true;  // Source can always stay at their current location.
    getCell(source).val = range;
    
    generateMoveTilesH(source.move(-1, 0), range);
    generateMoveTilesH(source.move( 1, 0), range);
    generateMoveTilesH(source.move( 0,-1), range);
    generateMoveTilesH(source.move( 0, 1), range);
  }
  
  
  private void generateMoveTilesH(GridPoint source, int range) {
    // Obtain a reference to the TurnState Engine
    TurnMachine engine = TurnMachine.instance();
    // TODO This happens every recursive iteration. That seems dumb. I would like to find a way to keep this statement in the object's fields, but eh... I dunno how.
    
    if (range < 0) return;                              // Halt if out of range.
    if (!validPoint(source)) return;                    // Stay inside grid[][] bounds.
    
    Cell cell = getCell(source);
    Unit unit = getUnit(source);
    
    if (cell.val >= range) return;                      // Halt if we've already fully explored this location's possibilities.
    if (cell.wall == true) return;                      // Halt if wall encountered.
    
    if (unit != null &&                                 // Halt if tile is not empty and
      engine.getActiveTeam() != unit.getTeam()) return; // non-empty tile contains an enemy.           [grid.passable(Point p) ?]
    
    if (unit == null) {                                 // Only enable tile if tile is inhabitable.    [grid.inhabitable(Point p) ?]
      cell.moveTile = true;   // This method should be adapted to also generate an enemy's danger range tiles.
      cell.val = range;
    }
    
    generateMoveTilesH(source.move(-1, 0), range - 1);
    generateMoveTilesH(source.move( 1, 0), range - 1);
    generateMoveTilesH(source.move( 0,-1), range - 1);
    generateMoveTilesH(source.move( 0, 1), range - 1);
  }
  
  
  public void generateActionTiles(GridPoint source, EquipID equip) {
    if (!boardInitialized()) return;
    
    if (equip.isAction() != true)
      return;
    
    // Given equip is confirmed to be a valid command.
    
    // Pass equip to equipment manager for use
    EquipmentDatabaseManager edm = EquipmentDatabaseManager.instance();
    edm.using(equip);
    
    // Also, get a reference to the TurnState engine
    TurnMachine engine = TurnMachine.instance();
    
    // Update our picture of the game map.
    updateSnapshot();
    
    // Relevant action data - called frequently in this method.
    TargetType targType = edm.getTargetType();
    int actRange        = edm.getRange();
    int actNullRange    = edm.getNullRange();
    
    int x = 0;
    int y = -actRange;
    int xdist = 1;
    GridPoint p = new GridPoint();
    Cell cell;
    
    //boolean targetTile = (action.targetType == TargetType.Tile);
    //boolean targetEmpty = (action.targetType == TargetType.EmptyTile);
    boolean targetAny = (targType == TargetType.Any);
    boolean targetAllies = (targType == TargetType.Ally);
    boolean sameTeam;
    
    for (y = -actRange; y <= actRange; y++) {
      xdist = actRange - Math.abs(y);
      for (x = -xdist; x <= xdist; x++) {
        p.x = source.x + x;
        p.y = source.y + y;
        
        // Skip points off the board
        if (!validPoint(p)) continue;
        
        cell = getCell(p);
        
        // Omit walls
        if (cell.wall == true) continue;
        
        // Omit null range
        if (source.taxiCabDistance(p) <= actNullRange) continue;
        
        // Omit invalid units
        if (cell.unit != null && !targetAny) {
          sameTeam = (cell.unit.getTeam() == engine.getActiveTeam());
          if (targetAllies != sameTeam) {
            continue;
          }
        }
        
        
        // Light up the tile properly
        if (targetAllies) {
          cell.aidTile = true;
        } else {
          cell.attackTile = true;
        }
        
      }
    }
  }
  
  
  public void eraseMoveTiles() {
    if (!boardInitialized()) return;
    for (int i = 0; i < board.length; i++) {
      board[i].moveTile = false;
    }
  }
  
  
  public void eraseActionTiles() {
    if (!boardInitialized()) return;
    for (int i = 0; i < board.length; i++) {
      board[i].attackTile = false;
      board[i].aidTile = false;
    }
  }
  
  /* Used to create valid spawn points within a range smaller than the entire battefield.
   * Only useful while spawn points haven't been implemented yet.
   * This method can be repurposed|expanded later if coming up with uninhabited points is still a useful concept.
   */
  public GridPoint generateUniquePoint(GridPoint a, GridPoint b) {
    // Given range on the battlefield must be a valid range.
    if (!validPoint(a) || !validPoint(b))
      return null;
    
    // TODO Break infinite loop located here.
    // Here's a scary problem: I'm not checking that there even are open points in this range.
    // This could be an infinite loop.
    
    // If I'm going to check every point for openness anyway, maybe it would make more sense to build a list
    // of all open points in the given range and then randomly choose from that.
    // Typical sacrifice of memory for efficiency thing.
    // These ranges shouldn't be huge, I'm not sure it's worth it.
    // But then again, they wouldn't consume much memory then, either.
    // Let's call it a moot point.
    
    GridPoint r = new GridPoint();
    do {
      r.x = (int)(Math.random()*(b.x - a.x) + a.x);
      r.y = (int)(Math.random()*(b.y - a.y) + a.y);
    } while (isCellOpen(r) == false);
    
    return r;
  }
  
  // TODO Raise exception if invalid?
  public boolean validPoint(GridPoint source) {
    if (!boardInitialized()) return false;
    if (source.x < 0 || source.x > boardWidth - 1 ||
        source.y < 0 || source.y > boardHeight - 1)
      return false;
    
    // Else
    return true;
  }
  
  
  public boolean isCellOpen(GridPoint p) {
    if (!boardInitialized()) return false;
    if (validPoint(p)) {
      Cell cell = board[p.x + p.y * boardWidth];
      if (cell.wall == false)
        if (cell.unit == null)
          return true;
    }
    
    // Else
    return false;
  }
  
  
  public boolean isCellOccupied(GridPoint p) {
    if (!boardInitialized()) return false;
    return (board[p.x + p.y * boardWidth].unit != null);
  }
  
  
  private void generateWalls(int n) {
    if (!boardInitialized()) return;
    for (int i = 0; i < n; i++) {
      GridPoint p = new GridPoint();
      
      do {
        p.x = (int)Math.floor(Math.random() * boardWidth);
        p.y = (int)Math.floor(Math.random() * boardHeight);
        //System.out.println("Generated point (" + p.x + "," + p.y + ")");
      } while (board[p.x + p.y * boardWidth].wall == true);
      
      board[p.x + p.y * boardWidth].wall = true;
    }
  }
  
}
