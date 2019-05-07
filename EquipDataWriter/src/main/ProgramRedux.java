package main;

import java.util.ArrayList;

import processing.core.PApplet;
import utils.DeltaTime;
import utils.GridPoint;
import utils.Timer;

public class ProgramRedux {

  GridPoint cursor;     // What row/col we are currently hovering over.
  GridPoint AoEcursor;  // What row/col we are currently hovering over (specific to AoEMap editor).
  int maxRow, maxCol;   // Used during update to keep the cursor on screen and relevant.
  String input;         // A temporary place for input before it has been properly submitted.
  EquipData viewFocus;  // For AoE view: which equip data to draw in the view screen.
  State state;          // Essentially, whether or not the user is using the inputbar.
  View view;            // What kind of information is displayed (and editable) on screen.
  InputType inputType;  // What kind of input the inputbar is looking for. Input, after submission, is vetted before being accepted.
  
  ArrayList<EquipData> equipsPage;
  
  DeltaTime dt;
  int attrDisplay;                      // In CoreStats view, which attribute (of 5) to display on screen.
  Timer attrSwitchTimer;                // The time buffer between cycle-states of attrDisplay.
  final double Timer_Length = 0.75;     // 
  boolean cursorBlink;                  // Whether or not to draw the cursor (particularly for the inputbar)
  Timer cursorBlinkTimer;               // The time buffer between blinks of the cursor.
  final double Blink_Length = 0.35;     //
  Timer autoSaveTimer;                  //
  final double AutoSave_Length = 300.0; // 5 minutes
  Timer saveNotificationTimer;          // How long to let the user know the program had just saved.
  final double SaveNotify_Length = 5.0; //
  
  
  public ProgramRedux() {
    cursor = new GridPoint();
    AoEcursor = new GridPoint();
    input = "";
    viewFocus = null;
    state = State.Browsing;
    view = View.CoreStats;
    inputType = InputType.None;
    
    dt = DeltaTime.instance();
    attrDisplay = 0;
    attrSwitchTimer = new Timer(Timer_Length, -1);
    cursorBlink = true;
    cursorBlinkTimer = new Timer(Blink_Length, -1);
    autoSaveTimer = new Timer(AutoSave_Length, -1);
    saveNotificationTimer = new Timer(SaveNotify_Length, 1);
    saveNotificationTimer.finish();
  }
  
  public void draw(PApplet applet) {
    updateTime();
    
    equipsPage = Main.databook.getPage();
    updateMaxCursorValues();
    drawControlsUI(applet);
    drawPage(applet);
    if (view == View.AoEMap)
      drawAoEPage(applet);
    
    // The controls section in processing should be able to handle the rest.
    // Though, I may want to give it handles to use here in this class.
    // Yeah, okay: This class will have controls-focused methods, and Processing simply calls them when the appropriate button has been pressed.
  }
  
  // Updates all timers, executing their effects if their time has elapsed.
  public void updateTime() {
    dt.updateDelta();
    
    // Controls which numbered attribute is displayed in CoreStats view.
    attrSwitchTimer.update();
    if (attrSwitchTimer.pulse()) {
      attrDisplay++;
      if (attrDisplay > 3)
        attrDisplay = 0;
    }
    
    // Controls the blink of the text cursor in Editing mode.
    cursorBlinkTimer.update();
    if (cursorBlinkTimer.pulse())
      cursorBlink = !cursorBlink;
    
    // Controls the autosave feature.
    autoSaveTimer.update();
    if (autoSaveTimer.pulse())
      save();
    
    // Controls how long save notification lasts for.
    saveNotificationTimer.update();
  }
  
  // Uses view and the current page to determine where the cursor is allowed to be. Auto-fixes the cursor's pos if outside legal range.
  public void updateMaxCursorValues() {
    maxRow = equipsPage.size();
    maxCol = this.equipsPage.get(0).getFields().size();
    
    // -1, because the maximum value is not the number of columns which exist (for 8 cols, 0-7 are used).
    maxCol--;
    maxRow--;
    
    cursor.x = utils.Common.constrain(cursor.x, 0, maxCol);
    cursor.y = utils.Common.constrain(cursor.y, 0, maxRow);
  }
  
  public void drawPage(PApplet applet) {
    GridPoint p = new GridPoint();          // Used for drawing
    EquipData data;
    ArrayList<DataField> fields;
    int[] widths;
    String[] headerLabels;
    
    // Set pen
    applet.fill(255);
    applet.noStroke();
    
    // Build width map
    fields = this.equipsPage.get(0).getFields();
    widths = new int[fields.size()];
    headerLabels = new String[fields.size()];
    for (int i = 0; i < fields.size(); i++) {
      widths[i] = fields.get(i).displayWidth();
      headerLabels[i] = fields.get(i).displayHeader();
    }
    
    // Draw Header
    p.setCoords(DisplaySettings.Border, DisplaySettings.Border);
    for (int i = 0; i < widths.length; i++) {
      applet.text(headerLabels[i], p.x, p.y);
      p.x += widths[i];
    }
    if (view == View.CoreStats)
      applet.text("Attributes", p.x, p.y);
    
    // Draw line separator
    p.setCoords(DisplaySettings.Border, DisplaySettings.Border + DisplaySettings.Newline);
    applet.rect(p.x, p.y, applet.width - p.x*2, DisplaySettings.Pixel_Size);
    
    // Draw the data page
    p.setCoords(DisplaySettings.Border, DisplaySettings.Border + DisplaySettings.Newline*2);
    for (int y = 0; y < this.equipsPage.size(); y++) {
      data = this.equipsPage.get(y);
      p.x = DisplaySettings.Border;
      fields = data.getFields();
      
      // Draw a helpful row-line across the screen, unless in map view.
      if (view != View.AoEMap && y == cursor.y) {
        applet.fill(32);
        applet.rect(0, p.y - DisplaySettings.Pixel_Size, applet.width, DisplaySettings.Newline);
        applet.fill(255);
      }
      
      // Draw main, interactable fields
      for (int x = 0; x < fields.size(); x++) {
        // Draw the column as-is if the cursor is not hovering over it
        if (y != cursor.y || x != cursor.x) {
          applet.text(fields.get(x).displayString(), p.x, p.y);
        // But if the cursor is hovering...
        } else {
          // Draw cursor as a box surrounding the list element
          applet.rect(p.x - DisplaySettings.Pixel_Size, p.y - DisplaySettings.Pixel_Size, fields.get(x).displayWidth(), DisplaySettings.Newline);
          
          // Black on white text
          applet.fill(0);
          
          // Draw the text normally if not being edited
          if (state != State.Editing) {
            applet.text(fields.get(x).displayString(), p.x, p.y);
          // Otherwise, draw it slightly indented, use text from the inputbar, and draw a cursor blink, too.
          } else {
            applet.text(Main.inputbar.getString(), p.x + DisplaySettings.Border, p.y);
            if (cursorBlink)
              applet.rect(p.x + DisplaySettings.Pixel_Size + applet.textWidth(Main.inputbar.getString().substring(0,Main.inputbar.getCursorPos())), p.y - DisplaySettings.Pixel_Size, DisplaySettings.Pixel_Size, DisplaySettings.Newline);
          }
          
          // Undo pen changes
          applet.fill(255);
        }
        
        // Draw the minimap in its own special column
        if (fields.get(x).getType() == DataField.FieldType.MiniMap)
          ((MinimapVal)fields.get(x)).drawMap(applet, p);
        
        // This line pushes the "pen" further to the right, ready to draw the next column's data
        p.x += fields.get(x).displayWidth();
      }
      
      // Draw special attribute column in CoreStats view
      if (view == View.CoreStats)
        applet.text(data.attributes[attrDisplay].displayString(), p.x, p.y);
      
      p.y += DisplaySettings.Newline;
    }
  }
  
  // Draws the AoE view of the equipment list.
  public void drawAoEPage(PApplet applet) {
    if (viewFocus == null) return;
    
    GridPoint p = new GridPoint();          // Used for drawing
    GridPoint tile = new GridPoint();       // Used for examining specific grid tiles
    GridPoint zero = new GridPoint();       // Used for finding out taxiCabDistance
    String tmp = "";                        // Also also used for drawing
    DataField field = null;
    AoEMap map = viewFocus.blastmap;
    int rangeMapColumns = 6;                // Blast map takes up most of the screen space.
    
    applet.fill(255);
    applet.noStroke();
    
    // Draw the header details.
    p.y = DisplaySettings.Border + DisplaySettings.Newline*2;
    p.x = DisplaySettings.Col_BlastMap;
    applet.text("Range: " + viewFocus.Range.displayString(), p.x, p.y);
    p.x = DisplaySettings.Col_RadiusVal;
    applet.text("Radius: " + EquipData.Radius.displayString(), p.x, p.y);
    
    // Draw box around header, maybe
    if (AoEcursor.y == 0 && state == State.AoEMapEdit) {
      if (AoEcursor.x == 0) { p.x = DisplaySettings.Col_BlastMap; field = viewFocus.Range; tmp = "Range: "; }
      if (AoEcursor.x == 1) { p.x = DisplaySettings.Col_RadiusVal; field = EquipData.Radius; tmp = "Radius: "; }
      
      // Draw the box
      applet.rect(p.x - DisplaySettings.Pixel_Size, p.y - DisplaySettings.Pixel_Size, DisplaySettings.Big_Indent, DisplaySettings.Newline);
      applet.fill(0);
      if (field != null)
        applet.text(tmp + field.displayString(), p.x, p.y);
      applet.fill(255);
    }
    
    // Move y to map parts. Should not change again.
    p.y += DisplaySettings.Newline;
    
    int pix_size = DisplaySettings.Pixel_Size;
    int boxSize = DisplaySettings.MapCell_Chunk;
    int mapSize = DisplaySettings.Map_Chunk;
    int border = DisplaySettings.Border;
    int boxDrawSize = boxSize - border*2;
    
    // Draw the map outline: Blast Map
    applet.stroke(255);
    p.x = DisplaySettings.Col_BlastMap;
    for (int x = 0; x < AoEMap.Map_Size + 1; x++) {
      applet.line(p.x + boxSize*x, p.y, p.x + boxSize*x, p.y + mapSize);
    }
    for (int y = 0; y < AoEMap.Map_Size + 1; y++) {
      applet.line(p.x, p.y + boxSize*y, p.x + mapSize, p.y + boxSize*y);
    }
    applet.noStroke();
    
    // Draw an indicator for the blast radius center tile, where the unit would be in a Dir-style move
    applet.noStroke();
    applet.fill(255);
    applet.rect(p.x + AoEMap.Map_Radius*boxSize + border, p.y + AoEMap.Map_Radius*boxSize + border, boxSize - border*2, boxSize - border*2);
    
    // Draw map elements: Blast Map
    applet.textSize(DisplaySettings.Text_Size_Map);
    p.x = DisplaySettings.Col_BlastMap;
    for (int y = 0; y < AoEMap.Map_Size; y++) {
    for (int x = 0; x < AoEMap.Map_Size; x++) {
      if (map.getCell(x,y).priority > 0) {
        tmp = map.getCell(x,y).displayString();
        applet.fill(0xFFFF6363 - (0x0A0000 * map.getCell(x,y).priority));
        applet.rect(p.x + border/2 + x*boxSize, p.y + border/2 + y*boxSize, boxDrawSize + border, boxDrawSize + border);
        applet.fill(255);
        applet.text(tmp.charAt(0), p.x + border + x*boxSize, p.y + border/2 + y*boxSize);
        applet.text(tmp.substring(2,4), p.x + border + x*boxSize, p.y + border/2 + y*boxSize + DisplaySettings.Map_Newline);
      }
    }}
    applet.textSize(DisplaySettings.Text_Size);
    
    // Draw the map cursor if needed
    if (AoEcursor.y > 0 && state == State.AoEMapEdit) {
      applet.noFill();
      applet.stroke(0xFF31FF63);
      applet.rect(p.x + AoEcursor.x*boxSize - pix_size, p.y + (AoEcursor.y-1)*boxSize - pix_size, boxSize + pix_size*2, boxSize + pix_size*2);
    }
    
    // Reset the pen
    applet.fill(255);
    applet.noStroke();
    
    // Draw the map outline: Range Map
    applet.stroke(255);
    p.x = DisplaySettings.Col_RangeMap;
    for (int x = 0; x < rangeMapColumns + 1; x++) {
      applet.line(p.x + boxSize*x, p.y, p.x + boxSize*x, p.y + mapSize);
    }
    for (int y = 0; y < AoEMap.Map_Size + 1; y++) {
      applet.line(p.x, p.y + boxSize*y, p.x + boxSize*rangeMapColumns, p.y + boxSize*y);
    }
    applet.noStroke();
    
    // Draw map elements: Range Map
    applet.rect(p.x + border, p.y + border, boxDrawSize, boxDrawSize);
    applet.fill(0xFF6363FF);
    for (tile.y = 0; tile.y < AoEMap.Map_Size; tile.y++) {
    for (tile.x = 0; tile.x < rangeMapColumns; tile.x++) {
      if (tile.x == 0 && tile.y == 0)
        continue;
      
      if (viewFocus.Range.isLine() && (tile.x != 0 && tile.y != 0))
        continue;
      
      if (tile.taxiCabDistance(zero) > viewFocus.Range.getNullRange() - 1 &&
          tile.taxiCabDistance(zero) <= viewFocus.Range.getRange())
        applet.rect(p.x + border + tile.x*boxSize, p.y + border + tile.y*boxSize, boxDrawSize, boxDrawSize);
    }}
    applet.fill(255);
  }
  
  // Draws the controls UI at the bottom of the screen. Controls are context sensitive, so this readout is important.
  public void drawControlsUI(PApplet applet) {
    GridPoint p = new GridPoint();
    p.x = DisplaySettings.Border;
    p.y = applet.height - DisplaySettings.Border - DisplaySettings.Newline;
    DataField field = equipsPage.get(cursor.y).getCol(cursor.x);
    String contextEnter;
    String contextDel;
    String contextArrows;
    
    if (field == null) {
      contextEnter = "None";
      contextDel = "None";
      contextArrows = "None";
    }
    else {
      contextEnter = field.contextEnter();
      contextDel = field.contextDel();
      contextArrows = field.contextArrows();
    }
    
    if (state == State.AoEMapEdit) {
      if (viewFocus == null) {
        contextEnter = "None";
        contextDel = "None";
        contextArrows = "None";
      }
      else {
        field = viewFocus.getAoEData(AoEcursor.x, AoEcursor.y);
        contextEnter = field.contextEnter();
        contextDel = field.contextDel();
        contextArrows = field.contextArrows();
      }
    }
    
    // Point p is used like a pen, moved relative to where it used to be.
    applet.text("Enter:" + contextEnter, p.x, p.y);
    p.y -= DisplaySettings.Newline*2;
    if (!saveNotificationTimer.finished())
      applet.text("Saved!", p.x, p.y);
    p.x += DisplaySettings.Big_Indent; p.y += DisplaySettings.Newline*2;
    applet.text("Del:" + contextDel, p.x, p.y);
    p.x += DisplaySettings.Big_Indent;
    applet.text("Tab:View", p.x, p.y);
    p.x += DisplaySettings.Big_Indent;
    applet.text("Ctrl+S:Save", p.x, p.y);
    p.y -= DisplaySettings.Newline;
    applet.text("Ctrl+N:InsertNew", p.x, p.y);
    p.x += DisplaySettings.Big_Indent + DisplaySettings.Small_Indent;
    applet.text("Page U/D:PageNav  " + Main.databook.pageNumber() + "/" + Main.databook.maxPageNumber(), p.x, p.y);
    p.y += DisplaySettings.Newline;
    applet.text("Ctrl+U/D:" + contextArrows, p.x, p.y);
    
    // Draw View labels
    p.y -= DisplaySettings.Newline*2;
    p.x = DisplaySettings.Border + DisplaySettings.Big_Indent*3;
    applet.text("Core", p.x, p.y);
    p.x += DisplaySettings.Value_Chunk;
    applet.text("Prop", p.x, p.y);
    p.x += DisplaySettings.Value_Chunk;
    applet.text("Attr", p.x, p.y);
    p.x += DisplaySettings.Value_Chunk;
    applet.text("Reqs", p.x, p.y);
    p.x += DisplaySettings.Value_Chunk;
    applet.text("Desc", p.x, p.y);
    p.x += DisplaySettings.Value_Chunk;
    applet.text("Dumb", p.x, p.y);
    p.x += DisplaySettings.Value_Chunk;
    applet.text("AoE", p.x, p.y);
    
    // Draw View labels' tab indicator
    String text = "Core"; int indent = 0;
    if (view == View.Properties) { text = "Prop"; indent = 1; }
    if (view == View.Attributes) { text = "Attr"; indent = 2; }
    if (view == View.Requirements) { text = "Reqs"; indent = 3; }
    if (view == View.Description) { text = "Desc"; indent = 4; }
    if (view == View.Description2) { text = "Dumb"; indent = 5; }
    if (view == View.AoEMap) { text = "AoE"; indent = 6; }
    p.x = DisplaySettings.Border + DisplaySettings.Big_Indent*3 + DisplaySettings.Value_Chunk*indent;
    applet.rect(p.x - DisplaySettings.Pixel_Size, p.y - DisplaySettings.Pixel_Size, DisplaySettings.Std_Chunk, DisplaySettings.Newline);
    applet.fill(0);
    applet.text(text, p.x, p.y);
    applet.fill(255);
  }
  
  public View getView() {
    return this.view;
  }
  
  public EquipData getSelected() {
    return this.equipsPage.get(this.cursor.y);
  }
  
  // Moves the cursor to a particular EID, changing both the page and cursor.y value to get there.
  public void moveCursorToID(EIDVal EID) {
    int idx = Main.databook.getIndex(EID);
    if (idx != -1) {
      Main.databook.setPageNumber(idx / DataBook.Page_Size);
      this.cursor.y = idx % DataBook.Page_Size;
    }
  }
  
  // Whence the arrow keys are pressed, do them.
  public void arrowUp() { arrowKey(new GridPoint(0, -1)); }
  public void arrowDown() { arrowKey(new GridPoint(0, 1)); }
  public void arrowLeft() { arrowKey(new GridPoint(-1, 0)); }
  public void arrowRight() { arrowKey(new GridPoint(1, 0)); }
  // Meat of the arrow keys method
  public void arrowKey(GridPoint p) {
    if (state != State.Editing && state != State.AoEMapEdit) {
      this.cursor.add(p);
      this.cursor.x = utils.Common.constrain(this.cursor.x, 0, this.maxCol);
      
      // Y needs be limited, too, but it's special. It can also page change.
      if (this.view != View.AoEMap ||
          this.view == View.AoEMap && this.state == State.Browsing) {
        if (this.cursor.y < 0) {
          Main.databook.pageup();
          this.equipsPage = Main.databook.getPage();
          this.cursor.y = this.equipsPage.size() - 1;
        }
        else if (this.cursor.y > maxRow) {
          Main.databook.pagedown();
          this.cursor.y = 0;
        }
        
        if (this.view == View.AoEMap)
          this.viewFocus = equipsPage.get(this.cursor.y);
      }
    }
    else if (state == State.AoEMapEdit) {
      int Max_Row = AoEMap.Map_Size + 1 - 1;    // The -1's are here because of how constrain() enforces limits.
      int Max_Col = AoEMap.Map_Size - 1;        // The +1 above is because of the header controls row.
      
      this.AoEcursor.add(p);
      if (AoEcursor.y == 0) Max_Col = 1;
      this.AoEcursor.x = utils.Common.constrain(this.AoEcursor.x, 0, Max_Col);
      this.AoEcursor.y = utils.Common.constrain(this.AoEcursor.y, 0, Max_Row);
    }
    else {   // state ~is~ Editing
      Main.inputbar.moveCursor(p.x);
    }
  }
  
  // Ctrl+Up Button Boy
  public void ctrlArrowVertical(int dir) {
    if (state == State.Editing) return;
    
    // Run whichever's increment/decrement function. Many will run an empty method.
    if (state == State.Browsing) {
      if (dir > 0)
        equipsPage.get(cursor.y).getCol(cursor.x).increment();
      else
        equipsPage.get(cursor.y).getCol(cursor.x).decrement();
    }
    else if (state == State.AoEMapEdit) {
      if (viewFocus != null) {
        if (dir > 0)
          viewFocus.getAoEData(AoEcursor.x, AoEcursor.y).increment();
        else
          viewFocus.getAoEData(AoEcursor.x, AoEcursor.y).decrement();
      }
    }
  }
  
  // I might need this in attribute viewing mode? I dunno.
  public void ctrlArrowHorizontal(int dir) {
    if (state == State.Editing) return;
    
    // Run whichever's alt. increment/decrement function. Many will run an empty method.
    if (state == State.Browsing) {
      if (dir > 0)
        equipsPage.get(cursor.y).getCol(cursor.x).incrementAlt();
      else
        equipsPage.get(cursor.y).getCol(cursor.x).decrementAlt();
    }
    else if (state == State.AoEMapEdit) {
      if (viewFocus != null) {
        if (dir > 0)
          viewFocus.getAoEData(AoEcursor.x, AoEcursor.y).incrementAlt();
        else
          viewFocus.getAoEData(AoEcursor.x, AoEcursor.y).decrementAlt();
      }
    }
  }
  
  // Enter key, usually, enters edit mode. Sometimes it toggles or cycles.
  public void enterKey() {
    DataField field = equipsPage.get(cursor.y).getCol(cursor.x);
    if (state == State.AoEMapEdit) {
      if (viewFocus == null)
        return;
      else
        field = viewFocus.getAoEData(AoEcursor.x, AoEcursor.y);
    }
    
    if (state == State.Browsing || state == State.AoEMapEdit) {
      if (field.editable()) {
        Main.inputbar.setString(field.displayString());
        if (Main.inputbar.getString().equals("???"))      // Bandaid fix for Names defaulting to "???"
          Main.inputbar.setString("");
        state = State.Editing;          // If any field in AoEMapEdit is ever editable (luckily not) this would probably break program flow.
      } else {
        field.tap();
      }
    }
    else if (state == State.Editing) {
      if (field.getType() != DataField.FieldType.EID)
        field.submit(Main.inputbar.getString());
      else {
        EIDVal newID = new EIDVal();
        newID.submit(Main.inputbar.getString());
        Main.databook.moveEquipment((EIDVal)field, newID);
        moveCursorToID((EIDVal)field);
      }
      state = State.Browsing;
    }
  }
  
  public void cancelKey() {
    if (state == State.Editing) {
      Main.inputbar.clearString();
      state = State.Browsing;
    }
  }
  
  // Delete key: Deletes entries or resets fields
  public void deleteKey() {
    if (state == State.Browsing) {
      DataField field = equipsPage.get(cursor.y).getCol(cursor.x);
      field.del();
    }
    else if (state == State.AoEMapEdit && viewFocus != null) {
      DataField field = viewFocus.getAoEData(AoEcursor.x, AoEcursor.y);
      field.del();
    }
  }
  
  // Tab key: Changes view style
  public void tabKey() {
    if (state != State.Editing) {
      int i = view.ordinal();
      i++;
      if (i >= View.values().length)
        i = 0;
      view = View.values()[i];
      
      // Reset values relating to MapEdit mode if we're tabbing out of it.
      if (view != View.AoEMap && state == State.AoEMapEdit) {
        AoEcursor = new GridPoint();
        state = State.Browsing;
      }
      
      updateMaxCursorValues();
    }
    
    if (view == View.AoEMap)
      viewFocus = this.equipsPage.get(this.cursor.y);
  }
  
  //Tab key: Changes view style
  public void shiftTabKey() {
    if (state != State.Editing) {
      int i = view.ordinal();
      i--;
      if (i < 0)
        i = View.values().length - 1;
      view = View.values()[i];
     
      // If we're tabbing out of MapEdit mode, reset values related to said mode.
      if (view != View.AoEMap && state == State.AoEMapEdit) {
        AoEcursor = new GridPoint();
        state = State.Browsing;
      }
      
      updateMaxCursorValues();
    }
    
    if (view == View.AoEMap)
      viewFocus = this.equipsPage.get(this.cursor.y);
  }
  
  // Space key: Exclusively to swap between editing modes in AoE view.
  public void spaceKey() {
    if (view == View.AoEMap) {
      if (state == State.Browsing)
        state = State.AoEMapEdit;
      else if (state == State.AoEMapEdit) {
        AoEcursor = new GridPoint();
        state = State.Browsing;
      }
    }
  }
  
  // If in editing mode, sends chars to the inputbar for string processing.
  public void typed(char c) {
    if (state == State.Editing)
      Main.inputbar.addChar(c);
    else if (state == State.Browsing && view == View.CoreStats) {
      if (c >= '0' && c <= '9') { 
        DataField field = equipsPage.get(cursor.y).getCol(cursor.x);
        int maxDigits = (field.getType() == DataField.FieldType.Value) ? 5 : 2;
        String str;
        
        if (field.strictlyNumerical()) {
          str = field.displayString();
          if (str.length() == maxDigits)
            str = "";
          str += c;
          field.submit(str);
        }
      }
    }
  }
  
  // Save
  public void save() {
    Main.databook.saveData(Main.filename);
    Main.databook.saveData(Main.bckfilename);
    saveNotificationTimer.reset();
    saveNotificationTimer.start();
    autoSaveTimer.reset();
    autoSaveTimer.start();
  }
  
  // Insert new data at the current cursor position.
  public void insertNewData() {
    EIDVal destID = new EIDVal();
    destID.clone(this.equipsPage.get(cursor.y).EID);
    destID.value++;
    EquipData data = new EquipData();
    data.EID.clone(destID);              // This is done so that it carries the correct letter assignment (weapon vs accessory)
    Main.databook.addEquipment(data);
    Main.databook.moveEquipment(data.EID, destID);
    
    if (data.EID.isWeapon() == false)
      data.blastmap.emptyMap();
    
    // Update cursor boundaries to account for new data, then move cursor to meet new data.
    this.equipsPage = Main.databook.getPage();
    updateMaxCursorValues();
    moveCursorToID(data.EID);
  }
  
  public static enum State {
    Browsing,
    Editing,
    AoEMapEdit,
    GroupSelect
  }
  
  public static enum View {
    CoreStats,
    Properties,
    Attributes,
    Requirements,
    Description,
    Description2,
    AoEMap
  }
  
  public static enum InputType {
    None,
    Text,
    StatNumber,
    LargeNumber,
    Attribute,
    Requirement
  }
  
}
