package main;

import processing.core.PApplet;
import utils.GridPoint;

public class MinimapVal implements DataField {
  
  public static final int Map_Size = 5;
  public static final int Tile_Size = DisplaySettings.Pixel_Size * 3 / 2;  // About floor(1.5x)
  
  boolean[][] map = new boolean[Map_Size][Map_Size];
  
  public MinimapVal() {
    reset();
  }
  
  @Override
  public void reset() {
    for (int x = 0; x < map.length; x++)
      for (int y = 0; y < map.length; y++)
        map[x][y] = false;
  }

  @Override
  public void submit(String str) {
    int i = 0;
    if (valid(str)) {
      for (int y = 0; y < map.length; y++) {
        for (int x = 0; x < map.length; x++) {
          map[x][y] = (str.charAt(i) == '1');
          i++;
        }
      }
    }
  }

  @Override
  public boolean valid(String str) {
    boolean r = false;
    
    if (str.length() == Map_Size * Map_Size)
      r = true;
    
    return r;
  }

  @Override
  public String value() {
    return "";
  }

  @Override
  public void tap() { }

  @Override
  public void tapAlt() { }

  @Override
  public void del() {
    EquipData data = Main.program.getSelected();
    data.blastmap.reset();
  }

  @Override
  public void increment() { }

  @Override
  public void decrement() { }
  
  @Override
  public void incrementAlt() { }

  @Override
  public void decrementAlt() { }

  @Override
  public String displayString() {
    return value();
  }

  @Override
  public String displayHeader() {
    return "Map";
  }

  @Override
  public int displayWidth() {
    return DisplaySettings.Std_Chunk;
  }

  @Override
  public String contextEnter() {
    return "None";
  }

  @Override
  public String contextDel() {
    return "Reset";
  }

  @Override
  public String contextArrows() {
    return "None";
  }

  @Override
  public boolean editable() {
    return false;
  }

  @Override
  public boolean strictlyNumerical() {
    return false;
  }

  @Override
  public FieldType getType() {
    return FieldType.MiniMap;
  }

  public void drawMap(PApplet applet, GridPoint p) {
    applet.fill(0xFFFF6363);
    applet.stroke(0);
    
    for (int y = 0; y < map.length; y++) {
    for (int x = 0; x < map.length; x++) {
      if (map[x][y])
        applet.rect(p.x + x*Tile_Size, p.y + y*Tile_Size, Tile_Size, Tile_Size);
    }}
    
    applet.fill(255);
    applet.noStroke();
  }

}
