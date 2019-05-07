package main;

import utils.GridPoint;

public class AoEMap { 
  
  BlastGridCellVal[][] map;
  MinimapVal minimap;         // A handle for the minimap object tied to the equipdata object this AoEMap object is tied to.
  
  public static final int Map_Radius = 5;                 // This "radius" refers to the size of a square, from center to edge midpoints.
  public static final int Map_Size = Map_Radius*2 + 1;    // The size of the map in memory. +1 adds a row/column for the castor to reach from.
  
  public AoEMap(MinimapVal minimap) {
    this.minimap = minimap;
    
    // Initialize the map itself.
    map = new BlastGridCellVal[Map_Size][Map_Size];
    for (int x = 0; x < map.length; x++) {
    for (int y = 0; y < map.length; y++) {
      map[x][y] = new BlastGridCellVal(this);
    }}
  }
  
  public void submit(String str) {
    int i = 0;
    int len = str.length();
    int width = 0;
    int min, max;
    int x, y;

    if (valid(str)) {
      
      // Get width, use to determine min and max coords.
      width = utils.Common.charToDigit(str.charAt(0));
      min = Map_Radius - width/2;
      max = min + width;
      
      i = str.indexOf(";") + 1;
      
      // iterate over the actual data
      x = 0;
      y = 0;
      while (str.indexOf(";", i) > -1) {
        map[min+x][min+y].submit(str.substring(i, str.indexOf(";", i)));
        
        x++;
        if (x >= width) {
          x = 0;
          y++;
        }
        i = str.indexOf(";", i) + 1;
      }
      
      updateMinimap();
    }
  }
  
  public boolean valid(String str) {
    boolean r = true;
    int i = 0;
    int len = str.length();
    int width = 0;
    
    // Check str meets basic requirements
    if (len == 0)
      r = false;
    
    // Check width value
    if (i+1 < len) {
      if (utils.Common.charIsDigit(str.charAt(i)) &&
          str.charAt(i+1) == ';')
        width = utils.Common.charToDigit(str.charAt(i));
      else
        r = false;
    }
    
    // Iterate through data, checking the format of each one. Set r to false if any of them are broken.
    i = str.indexOf(";") + 1;
    while (str.indexOf(";", i) > -1) {
      if (map[0][0].valid(str.substring(i, i+4)) )
        i = str.indexOf(";", i) + 1;
      else {
        r = false;
        break;
      }
    }
    
    return r;
  }
  
  public void reset() {
    for (int x = 0; x < map.length; x++) {
    for (int y = 0; y < map.length; y++) {
      map[x][y].reset();
    }}
    
    map[Map_Radius][Map_Radius].tap();
    updateMinimap();
  }
  
  public void emptyMap() {
    for (int x = 0; x < map.length; x++) {
    for (int y = 0; y < map.length; y++) {
      map[x][y].reset();
    }}
    updateMinimap();
  }
  
  public BlastGridCellVal getCell(GridPoint p) {
    return getCell(p.x, p.y);
  }
  
  public BlastGridCellVal getCell(int x, int y) {
    BlastGridCellVal r = null;     // TODO Throw an exception instead of returning null
    if (x >= 0 && x < Map_Size &&
        y >= 0 && y < Map_Size)
      r = map[x][y];
    return r;
  }
  
  public BlastGridCellVal getCellRelativeToCenter(int x, int y) {
    BlastGridCellVal r = null;      // TODO Throw an exception or something.
    if (x >= -Map_Radius && x < Map_Radius+1 &&   // The +1 should be here because of the center column.
        y >= -Map_Radius && y < Map_Radius+1 )
      r = map[x + Map_Radius][y + Map_Radius];
    return r;
  }
  
  public String valueString() {
    String str = "";
    int rad;
    int minCol, maxCol;
    int min, max, width;
    
    // Determine the smallest radius which contains data.
    for (rad = Map_Radius; rad > 0; rad--) {
      if (ringEmpty(rad) == false)
        break;
    }
    
    // Determine bounds of new map.
    width = rad*2 + 1;
    min = Map_Radius - width/2;
    max = min + width;
    
    // Begin writing the save string
    str = width + ";";                  // The save string is written in one line, this tells the interpreter when to "line-break."
    
    for (int y = 0; y < width; y++) {
    for (int x = 0; x < width; x++) {
      str += map[min + x][min + y].toString() + ";";    // Empty tiles have priority 0, their intensity doesn't matter, but should be 00.
    }}
    
    // The save string has this format:
    //     3;1:99;1:99;1:99;1:99;0:00;1:99;1:99;1:99;1:99;
    // or
    //     width;[data];special
    
        // TODO TODO
        // All blast maps are special-made.
        // The radius number is just a building tool; it auto-builds maps for me.
        // PickDir and LineStyle are important for range however, and should be toggleable and saveable.
        // Attaching that L to range is... fine, but that's really not where LineStyle should go.\
        // Leaving some kind of visual indicator on CoreStats view would be nice, though, to let me know at a glance a weapon has this line style.
    
    return str;
  }
  
  // Draws a small map of the blast shape wherever told to.
  public void updateMinimap() {
    int min = Map_Radius - 2; // This should set the stage for a 5x5 map.
    int max = Map_Size - min;
    int width = max - min;
    String str = "";
    
    for (int y = 0; y < width; y++) {
    for (int x = 0; x < width; x++) {
      if (map[min+x][min+y].priority > 0)
        str += "1";
      else
        str += "0";
    }}

    this.minimap.submit(str);
  }
  
  // Checks a square "ring" on the map for priority > 0 tiles. Returns true if none exist, false if at least one exists.
  private boolean ringEmpty(int radius) {
    if (radius > Map_Radius) return true;   // Or throw exception?
    
    boolean r = true;
    int width = radius * 2 + 1;
    int min = Map_Radius - width/2;
    int max = min + width - 1;
    
    // This should check all four edges of the square "ring" simultaneously. 
    for (int i = 0; i < width; i++) {
      if (map[min + i][min].priority > 0 ||
          map[min + i][max].priority > 0 ||
          map[min][min + i].priority > 0 ||
          map[max][min + i].priority > 0 )
        r = false;
    }
    
    return r;
  }
  
  public void buildMap(RangeVal range, BlastRadiusVal radius) {
    GridPoint tmp = new GridPoint();
    GridPoint p = new GridPoint(Map_Radius, Map_Radius);
    int rad = radius.getRadius();
    boolean lineStyle = range.isLine();
    boolean omitCenter = radius.omitCenter();
    
    for (tmp.x = 0; tmp.x < Map_Size; tmp.x++) {
    for (tmp.y = 0; tmp.y < Map_Size; tmp.y++) {
      map[tmp.x][tmp.y].reset();
      
      if (tmp.taxiCabDistance(p) < rad) {
        if (lineStyle && tmp.x != 0 && tmp.y != 0)
          continue;
        if (omitCenter && tmp.x == p.x && tmp.y == p.y)   // TODO Should this make rad=1 extend out like range=1 does? I guess it's not a game var.
          continue;
        
        map[tmp.x][tmp.y].tap();
      }
    }}
    
    updateMinimap();
  }
  
}
