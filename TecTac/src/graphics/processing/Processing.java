package graphics.processing;

import processing.core.PApplet;
import processing.core.PFont;
import input.VirtualController;
import input.VirtualButtonEvent;
import input.VirtualButton;

/* For future reference:
 * 
 * I think other games render at predefined aspect ratios.
 * Like, the UI guy makes sure the UI makes sense on 4:3, on 16:9, on 16:10, on... 21:3.
 * Then, sizes of things on-screen are determined by proportion.
 * That's how Google does things on android, anyway.
 * Everything is drawn at some rendering resolution,
 * which is then scaled up or down (down, hopefully) to fit the user's actual resolution.
 * (Unless the internal resolution is the same as screen resolution, which I would think is typical).
 * There might even be a minimum resolution set; system requirements, you know.
 * 
 * Proportions, I feel, are harder to work with than straight numbers.
 * I would bet they have a system wherein these numbers are easier to handle, and perhaps certain common ratios are easy to reference.
 * I would want one, anyway.
 * 
 * To clarify, 16:10 would have some size/placement/proportion settings specific to each UI element,
 * then those are translated via some scale-factor (resolution) into actual draw numbers (Position, width, height, etc.).
 * 
 * I'm learning~! Though!
 * So!
 * I'm just keeping things at 800x600 for easiness.
 * Processing couldn't handle much more, anyway. Probably.
 */

public class Processing extends PApplet {
  
  // References to other singletons
  VirtualController controller = VirtualController.instance();
  
  // Simply tells the Processing applet to work with my shit.
  public static void run(String[] args) {
    PApplet.main("graphics.processing.Processing");
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
    textFont(font,32);
    
    resetPen();
  }
  
  /* Not sure how useful this is (in this context), or if it's even useable. TODO Determine usefulness.
   * Resets all the different draw-type settings to default.
   */
  public void resetPen() {
    fill(255);
    noStroke();
    // Other
    
    // Text Alignment Settings, probably
    textAlign(LEFT, TOP);
  }
  
  /* This is the draw loop. Draw stuff here.
   */
  public void draw() {
    EnvironmentDrawmaster.draw(this);
    EntitiesDrawmaster.draw(this);
    BattleUIDrawmaster.draw(this);
    InfoWindowDrawmaster.draw(this);
    CommandMenuDrawmaster.draw(this);
    
    // Here's how this works.
    // There are several delegated drawmaster classes that handle the drawing of specific UI elements.
    // Each one implements an interface that demands a draw(PApplet applet) function.
    // Each call to dm.draw() must pass _this_ PApplet into it so that the dm's may reference the applet they're supposed to be drawing to.
    // If these DM's need supplemental data in their constructors, keep that info in Game or create a Settings class or something.
    // Otherwise, everything works as you'd expect it to.
    
    // Improvements..?
    // I'm not sure if any of these are worth it because at some point I plan on switching to a more nuanced/powerful graphics applet,
    // but...
    //  - Condense this applet to settings and setup. I'm using it solely to draw to the screen, so it doesn't need to do much.
    //  - Instantiate this object elsewhere, somehow... but as the first thing done. In Main or something.
    //  - Pass that reference around. Pass it everywhere. Then I can use it in constructors.
    // I'm not even sure this would work.
    // Here's a better improvement:
    //  - Read about how to create/use PApplet objects in Eclipse via tutorials or documentation, then do that.
  }
  
  
  public void keyPressed() {
    // I'm just gonna hardcode these here for now.
    // I'd rather not, but oh well.
    // Useful info for later: keyCode == UP is equivalent to keyCode == KeyEvent.VK_UP
    
    if (keyCode == UP)
      controller.addEvent(new VirtualButtonEvent(VirtualButton.Up, true));
    
    if (keyCode == DOWN)
      controller.addEvent(new VirtualButtonEvent(VirtualButton.Down, true));
    
    if (keyCode == LEFT)
      controller.addEvent(new VirtualButtonEvent(VirtualButton.Left, true));
    
    if (keyCode == RIGHT)
      controller.addEvent(new VirtualButtonEvent(VirtualButton.Right, true));
    
    if (key == 'z')
      controller.addEvent(new VirtualButtonEvent(VirtualButton.Accept, true));
    
    if (key == 'x')
      controller.addEvent(new VirtualButtonEvent(VirtualButton.Cancel, true));
    
    if (key == 'c')
      controller.addEvent(new VirtualButtonEvent(VirtualButton.Special, true));
    
    if (key == 's')
      controller.addEvent(new VirtualButtonEvent(VirtualButton.Menu, true));
    
    if (key == 'd')
      controller.addEvent(new VirtualButtonEvent(VirtualButton.Info, true));
    
    if (key == '`')
      controller.addEvent(new VirtualButtonEvent(VirtualButton.Debug, true));
  }
  
  public void keyReleased() {
    // I'm just gonna hardcode these here for now.
    // I'd rather not, but oh well.
    // Useful info for later: keyCode == UP is equivalent to keyCode == KeyEvent.VK_UP
    
    if (keyCode == UP)
      controller.addEvent(new VirtualButtonEvent(VirtualButton.Up, false));
    
    if (keyCode == DOWN)
      controller.addEvent(new VirtualButtonEvent(VirtualButton.Down, false));
    
    if (keyCode == LEFT)
      controller.addEvent(new VirtualButtonEvent(VirtualButton.Left, false));
    
    if (keyCode == RIGHT)
      controller.addEvent(new VirtualButtonEvent(VirtualButton.Right, false));
    
    if (key == 'z')
      controller.addEvent(new VirtualButtonEvent(VirtualButton.Accept, false));
    
    if (key == 'x')
      controller.addEvent(new VirtualButtonEvent(VirtualButton.Cancel, false));
    
    if (key == 'c')
      controller.addEvent(new VirtualButtonEvent(VirtualButton.Special, false));
    
    if (key == 's')
      controller.addEvent(new VirtualButtonEvent(VirtualButton.Menu, false));
    
    if (key == 'd')
      controller.addEvent(new VirtualButtonEvent(VirtualButton.Info, false));
    
    if (key == '`')
      controller.addEvent(new VirtualButtonEvent(VirtualButton.Debug, false));
  }
}
