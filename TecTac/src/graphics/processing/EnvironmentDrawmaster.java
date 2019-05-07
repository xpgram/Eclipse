package graphics.processing;

import battle.map.Cell;

/* I don't think this actually needs to be a class object.
 * I could get away with static code.
 * But, uh... 
 */

import battle.map.Grid;
import processing.core.PApplet;
import utils.GridPoint;

public class EnvironmentDrawmaster {
  
  Grid grid = Grid.instance();
  
  // Constants relevant to game-board UI
  public static final int Color_Background = 0xFF636363;
  public static final int Color_GridLines = 0x40FFFFFF;
  public static final int Color_Wall = 0xFFE0E0E0;
  public static final int Color_MoveTile = 0x777777FF;
  public static final int Color_TargTile = 0x77FF7777;
  public static final int Color_AidTile = 0x7777FF77;
 
  /* Draws the game-board to the screen.
   * (non-Javadoc)
   * @see graphics.processing.Drawmaster#draw()
   */
  public static void draw(PApplet applet) {
    // Initiate useful variables
    Grid grid = Grid.instance();
    int Cell_Size = DisplaySettings.Cell_Size;
    
    // Draw the background
    applet.background(Color_Background);
    
    // Draw grid lines for all available board tiles.
    applet.stroke(Color_GridLines);
    for (int x=0; x < grid.boardWidth() + 1; x++) {
      applet.line(x*Cell_Size, 0, x*Cell_Size, grid.boardHeight()*Cell_Size);
    }
    for (int y=0; y < grid.boardHeight() + 1; y++) {
      applet.line(0, y*Cell_Size, grid.boardWidth()*Cell_Size, y*Cell_Size);
    }
    
    // Draw all walls, move tiles, etc.
    applet.noStroke();
    for (int x = 0; x < grid.boardWidth(); x++) {
    for (int y = 0; y < grid.boardHeight(); y++) {
      Cell cell = grid.getCell(new GridPoint(x,y));
      
      if (cell.wall) {
        applet.fill(Color_Wall);
        applet.rect(x*Cell_Size, y*Cell_Size, Cell_Size, Cell_Size);
      }
      
      if (cell.moveTile) {
        applet.fill(Color_MoveTile);
        applet.rect(x*Cell_Size, y*Cell_Size, Cell_Size, Cell_Size);
      }
    }} // End of nested for-loop
  }
  
}