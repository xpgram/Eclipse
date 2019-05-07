package graphics.processing;

import battle.map.Grid;

public class DisplaySettings {

  public static final int Screen_Width = 800;
  public static final int Screen_Height = 600;
  
  public static final int Scale_Factor = 4;     // TODO This should derive from width/height and is used to determine Cell_Size and other things.
  
  public static final int Screen_Edge_Border = 32;    // How far from the edge of the screen the UI sits, in pixels.
  
  public static final int Cell_Size = Screen_Width / Grid.instance().boardWidth();
  
  public static final String Typeface_32 = "TecTacP3-48.vlw";
}
