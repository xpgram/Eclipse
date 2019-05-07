package graphics.processing;

import battle.entities.EntitiesList;
import battle.entities.unit.Unit;
import battle.map.Grid;
import processing.core.PApplet;
import utils.GridPoint;

public class EntitiesDrawmaster {
  
  // Constants
  private static final int Ally_Color = 0xFF0077FF;
  private static final int Foe_Color = 0xFFFF3700;
  private static final int Support_Color = 0xFF37FF37;
  private static int Cell_Size;
  private static final double Cell_Border = 0.20;
  
  // References to other managers.
  private static Grid grid;
  private static EntitiesList entities;
  
  
  public static void draw(PApplet applet) {
    // Initiate useful vars
    Cell_Size = DisplaySettings.Cell_Size;
    grid = Grid.instance();
    entities = EntitiesList.instance();
    
    int color = 0;
    GridPoint p;
    int unitBorder = (int)(Cell_Size*Cell_Border);
    int unitSize = (int)(Cell_Size - unitBorder*2);
    Unit[] units = entities.getAllUnits();
    
    applet.noStroke();
    for (int i = 0; i < units.length; i++) {
      switch (units[i].getTeam()) {
        case Allies:
          color = Ally_Color;
          break;
        case Foes:
          color = Foe_Color;
          break;
        case Support:
          color = Support_Color;
          break;
      }
      
      p = units[i].getPos();
      if (grid.validPoint(p)) {       // Later, add: && PointOnScreen(p)

        // Draw the soldier's body
        applet.fill(color);
        applet.rect(p.x*Cell_Size + unitBorder, p.y*Cell_Size + unitBorder, unitSize, unitSize);
        
        // If deactivated, draw them dulled.      TODO Unless it isn't even their turn.
        if (units[i].active() == false) {
          applet.fill(0x77000000);
          applet.rect(p.x*Cell_Size + unitBorder, p.y*Cell_Size + unitBorder, unitSize, unitSize);
        }
        
        // Draw a letter-label
        applet.fill(0);
        applet.textAlign(applet.CENTER, applet.CENTER);
        applet.textSize(unitSize - unitSize/5);
        applet.text(units[i].getLabel(), p.x*Cell_Size + Cell_Size/2 + 2, p.y*Cell_Size + Cell_Size/2);
        
        // Draw a health bar
        //if (TurnMachine.showUnitUI() == true) { ...
        applet.fill(0xFF00FF00);
        applet.rect(p.x*Cell_Size + 5, (p.y+1)*Cell_Size - 4, (int)((Cell_Size - 10)*(units[i].getCondition().getHP() / (double)units[i].getCondition().getMaxHP())), 4);
      }
    }
  }
}
