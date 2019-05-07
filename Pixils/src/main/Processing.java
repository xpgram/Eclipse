package main;

import processing.core.PApplet;
import processing.core.PFont;

public class Processing extends PApplet {
  
  // Simply tells the Processing applet to work with my shit.
  public static void run(String[] args) {
    PApplet.main("main.Processing");
  }
 
  /* Run once, can only contain size() I guess.  
   */
  public void settings() {
    size(DisplaySettings.Screen_Width, DisplaySettings.Screen_Height);
  }
 
  // Run once; use to initiate any graphics-related thingers.
  public void setup() {
    PFont font;
    font = loadFont(DisplaySettings.Typeface);
    textFont(font, DisplaySettings.Text_Size);
   
    textAlign(LEFT, TOP);
  }
   
   // This is the draw loop. Draw stuff here.
   public void draw() {
     background(0);
     Program.update();
     Program.draw(this);
   }
   
   // Handles input and passes it somewhere else
   public void keyPressed() { }
}
