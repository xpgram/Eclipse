package graphics.processing;

import battle.ui.CommandMenu;
import processing.core.PApplet;
import utils.GridPoint;

// Resolution of game is 800x600 until further notice

public class CommandMenuDrawmaster {

  private static CommandMenu cm = CommandMenu.instance();
  
  private static GridPoint screenPos;     // Not currently used, but would determine which corner of the screen to draw to.
  
  // Constants
  private static int Scale = 4;     // TODO Should this become UI_Scale in DisplaySettings?
  
  // Box dimensions in pixels
  private static int TEXT_HEIGHT = 8 * Scale;            // Size of my font, basically.
  private static int NAME_AMMO_SEPARATION = 10 * Scale;  // Distance between the end of the longest command name and the AP numbers associated with them.
  private static int BOX_WIDTH = 80 * Scale;             // Arbitrarily chosen. Should auto-adjust to the amount of text contained within. 
  private static int EDGE_BORDER = 2 * Scale;            // Border between the menu and the options held within.
  private static int BORDER = 1 * Scale;                 // Border used between elements.
  private static int DETAIL_HEIGHT = 6 * Scale;          // Height of the smaller detail info drawn above the command menu.
  private static int DETAIL_NOTCH_HEIGHT = 1 * Scale;    // The height of the notches drawn to separate each independent detail of the hovered-over action.
  
  private static int COLOR_BOX = 0xFF888888;
  private static int COLOR_BORDER = 0xFFFFFFFF;
  
  public static void draw(PApplet applet) {
    // If the menu is hiding, don't bother...
    if (cm.hidden()) return;
    
    // Draw 
    int b_height = TEXT_HEIGHT * 3 - EDGE_BORDER * 2 - BORDER * 2;
    GridPoint p = new GridPoint();
    p.x = applet.width - 32 - BOX_WIDTH;
    p.y = applet.height - 32 - b_height;
    
    applet.fill(COLOR_BOX);
    applet.stroke(COLOR_BORDER);
    applet.rect(p.x, p.y, BOX_WIDTH, b_height);
  }
}
