package main;

public class DisplaySettings {

  public static final int Screen_Width = 1280;    // Currently, the board fills up the entire screen.
  public static final int Screen_Height = 800;    // This can be adjusted by just fiddling with the numbers here.
  
  // Text and UI
  public static final String Typeface = "TecTacP3-48.vlw";
  public static final int Text_Size = 24;
  public static final int Text_Newline = 32;
  public static final int Text_Indent = 128;
  
  // Board Dimensions
  public static final int Board_Width = 80;     // Note: Values are mathed out, but 1280x800 and 80x50 are a naturally neat and tidy combo.
  public static final int Board_Height = 50;
  public static final int Cell_Size = Screen_Width / Board_Width;   // If the board isn't to be the entire screen, change this. 
  public static final int Pixil_Size = Cell_Size * 3 / 4;
  public static final int Cell_Content_Border = (Cell_Size - Pixil_Size) / 2;
  
}

