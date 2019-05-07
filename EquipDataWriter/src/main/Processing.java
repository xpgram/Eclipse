package main;

import processing.core.PApplet;
import processing.core.PFont;

public class Processing extends PApplet {
  
  boolean shiftDown;
  boolean ctrlDown;
  
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
    font = loadFont(DisplaySettings.Typeface_32);
    textFont(font, DisplaySettings.Text_Size);
   
    textAlign(LEFT, TOP);
    
    this.shiftDown = false;
  }
   
   // This is the draw loop. Draw stuff here.
   public void draw() {
     background(0);
     Main.program.draw(this);
   }
   
   
   public void keyPressed() {
     
     if (keyCode == LEFT && !ctrlDown)
       Main.program.arrowLeft();
     
     if (keyCode == RIGHT && !ctrlDown)
       Main.program.arrowRight();
     
     if (keyCode == LEFT && ctrlDown) 
       Main.program.ctrlArrowHorizontal(-1);
     
     if (keyCode == RIGHT && ctrlDown)
       Main.program.ctrlArrowHorizontal(1);
     
     if (keyCode == UP && !ctrlDown)
       Main.program.arrowUp();
     
     if (keyCode == DOWN && !ctrlDown)
       Main.program.arrowDown();
     
     if (keyCode == UP && ctrlDown)
       Main.program.ctrlArrowVertical(1);
     
     if (keyCode == DOWN && ctrlDown)
       Main.program.ctrlArrowVertical(-1);
     
     if (key == ENTER)
       Main.program.enterKey();
     
     if (key == ' ')
       Main.program.spaceKey();
     
     if (key == TAB && !shiftDown)
       Main.program.tabKey();
     
     if (key == TAB && shiftDown)
       Main.program.shiftTabKey();
     
     if (key == BACKSPACE && !ctrlDown)
       Main.inputbar.backspace();
     
     if (key == BACKSPACE && ctrlDown) {
       Main.program.cancelKey();
       Main.inputbar.clearString();
     }
     
     if (key == DELETE && !ctrlDown) {
       Main.program.deleteKey();
       Main.inputbar.delete();
     }
     
     if (key == DELETE && ctrlDown) {
       Main.inputbar.clearString();
     }
     
     if (key == '')  // This is ctrl+N
       Main.program.insertNewData();
     
     if (key == '')  // This is ctrl+S
       Main.program.save();
     
     if (keyCode == 33)
       Main.databook.pageup();
     
     if (keyCode == 34)
       Main.databook.pagedown();
     
     if (keyCode == 36)  // This is 'Home'
       Main.inputbar.moveCursorToHome();
     
     if (keyCode == 35)  // This is 'End'
       Main.inputbar.moveCursorToEnd();
     
     if (keyCode == SHIFT)
       this.shiftDown = true;
     
     if (keyCode == CONTROL)
       this.ctrlDown = true;
   }
   
   public void keyReleased() {
     if (keyCode == SHIFT)
       this.shiftDown = false;
     if (keyCode == CONTROL)
       this.ctrlDown = false;
   }
   
   public void keyTyped() {
     if (key == BACKSPACE ||
         key == DELETE ||
         key == ENTER ||
         key == ESC)
       return;
     
     Main.program.typed(key);
     // TODO Ensure that ~only~ letters, numbers and specials get added to the input bar.
     // ctrl+s and the ascii code for backspace are ~not~ type-ables.
   }
}
