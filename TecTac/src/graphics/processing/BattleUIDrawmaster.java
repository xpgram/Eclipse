package graphics.processing;

import battle.ui.GridCursor;
import processing.core.PApplet;
import utils.GridPoint;
import utils.Timer;

public class BattleUIDrawmaster {
  
  // Singleton Reference Handles
  private static GridCursor cursor = GridCursor.instance();
  
  // Color Constants
  private static final int Color_Cursor = 0xFFFFFFFF;
  
  public static void draw(PApplet applet) {

    if (cursor.hidden() == false) {
      
      // Initiate useful vars
      int Cell_Size = DisplaySettings.Cell_Size;

      // Draw the cursor according to its state.
      applet.stroke(Color_Cursor);
      applet.noFill();
      int border = Cell_Size/16 * cursor.animState;    // cursorState is 0 or 1, so state=0 reduces the border to nothing.
      int x = cursor.location().x * Cell_Size + border;   // The top-left origin of the whole shape.
      int y = cursor.location().y * Cell_Size + border;
      int len = Cell_Size / 4;                            // The length of the lines used to draw each corner.
      int box = Cell_Size - border*2;                     // Used to get to the other corners.
      
      applet.line(x, y, x+len, y);  // T-L corner
      applet.line(x, y, x, y+len);
      applet.line(x+box, y, x+box-len, y);  // T-R corner
      applet.line(x+box, y, x+box, y+len);
      applet.line(x, y+box, x+len, y+box);  // B-L corner
      applet.line(x, y+box, x, y+box-len);
      applet.line(x+box, y+box, x+box-len, y+box);  // B-R corner
      applet.line(x+box, y+box, x+box, y+box-len);
      
    }
    
  }
}
