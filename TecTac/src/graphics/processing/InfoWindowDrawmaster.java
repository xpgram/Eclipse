package graphics.processing;

import battle.ui.InfoWindow;
import processing.core.PApplet;
import utils.GridPoint;

public class InfoWindowDrawmaster {

  public static InfoWindow iw = InfoWindow.instance();
  
  // Variables
  private static GridPoint screenPos;     // Not currently used, but would determine which corner of the screen to draw to.
  
  // Constants
  private static int Scale = 4;     // TODO Should this become UI_Scale in DisplaySettings?   Answer: Yes. It is already used in two places.
  
  //Box dimensions in pixels
  private static int Text_Height = 8 * Scale;           // Size of my font, basically.
  private static int Box_Height = 28 * Scale;           // Arbitrarily chosen. Shouldn't change size, but should be smartly sized.
  private static int Box_Width = 48 * Scale;            // Arbitrarily chosen. Shouldn't change size, but should be smartly sized.
  private static int Edge_Border = 2 * Scale;           // Border between the window edge and the details held within.
  private static int Border = 2 * Scale;                // Border used between elements.
 
  //Color Constants
  private static int Color_Box = 0x88222222;
  private static int Color_Border = 0xFFFFFFFF;
  private static int Color_Text = 0xFFFFFFFF;

  // TODO Move Color Constants to a central place for uniformity of style.
  
  public static void draw(PApplet applet) {
    if (iw.hidden())
      return;
    
    // Determine On-Screen Location and Other Variables
    GridPoint p = new GridPoint();
    p.x = DisplaySettings.Screen_Edge_Border;
    p.y = applet.height - DisplaySettings.Screen_Edge_Border - Box_Height;    // From the bottom edge
    
    applet.fill(Color_Box);
    //applet.stroke(Color_Border);
    applet.noStroke();
    applet.rect(p.x, p.y, Box_Width, Box_Height);
    
    applet.fill(Color_Text);
    applet.textAlign(applet.LEFT, applet.TOP);
    applet.textSize(Text_Height);
    applet.text(iw.getUnit().getInfo().name() + "\nHP |||||||||||||||\nConfused", p.x + Border, p.y + Border);
  }

}
