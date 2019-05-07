package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/* Handles loading of data and displaying of said data.
 * This is your view-window into the spreadsheet, basically.
 * This does not draw anything.
 */

public class DataBook {

  ArrayList<EquipData> equips;  // List of all loaded equipment objects.
  int page;                     // Where in the list of equips we are.
  
  public static final int Page_Size = 25;  // The number of equips to draw on-screen.
  
  public DataBook() {
    this.equips = new ArrayList<EquipData>();
    this.page = 0;
  }
  
  // Loads written data into memory, if the working file exists.
  public void loadData(String file) {
    EquipData data;
    
    try {
      Scanner in = new Scanner(new File(file));
      in.useDelimiter("\n");
      
      // If the file exists already, then this line rids us of the version number.
      if (in.hasNext()) in.next();    // TODO Compare version number for real
      
      // Read each line, give line to new equip data object, add object to global list of objects.
      while (in.hasNext()) {
        data = new EquipData();
        data.read(in.next());
        this.equips.add(data);
      }
      
      in.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    
    reassignIDs();
    
    // If no equips could be loaded, create one new one to kickstart the software.
    if (this.equips.size() == 0)
      addEquipment(new EquipData());
  }
  
  // Writes equip objects in memory to ~the~ file.
  public void saveData(String file) {
    reassignIDs();
    sortList();
    
    try {
      PrintWriter out = new PrintWriter(new File(file));
      
      out.println(Main.version);    // This is important so the actual game knows it can load the file.
      
      // Write 'em
      for (EquipData data : this.equips)
        out.println(data.write());
      
      out.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  // Preserves order within the list, but moves all accessory items to the end, after all the weapons.
  public void sortList() {
    int i, lastIdx;
    EquipData data;
    EquipData comp;
    
    i = 1;
    lastIdx = i;
    while (i < this.equips.size()) {
      // Can't sort downwards if there's nowhere to go.
      if (i == 0) {
        i++;
        continue;
      }
      
      // Acquire data
      data = this.equips.get(i);
      comp = this.equips.get(i - 1);
      
      // Compare data
      if (data.EID.compareTo(comp.EID) == -1) {
        this.equips.remove(i);
        this.equips.add(i - 1, data);
        i--;    // Follow the data all the way back to it's natural resting place.
      } else {
        // If i moved back a bunch, teleport back to lastIdx
        if (i < lastIdx)
          i = lastIdx;
        // Regardless, increase i and make sure lastIdx is keeping pace.
        i++;
        lastIdx = i;
      }
    }
  }
  
  // Iterates through the entire list, giving each one a unique ID number. Does not affect the sorting type.
  public void reassignIDs() {
    int id;
    
    for (EIDVal.Type t : EIDVal.Type.values()) {
      id = 1;
      for (EquipData data : equips) {
        if (data.EID.etype == t) {
          data.EID.reassignID(id);
          id++;
        }
      }
    }
  }
  
  // Adds a new equipment piece to the end of the global list.
  public void addEquipment(EquipData data) {
    this.equips.add(data);
    reassignIDs();
  }
  
  // Adds a new equipment piece to the global list, inserting it at the specified location.
  public void addEquipment(EquipData data, int index) {
    this.equips.add(index, data);
    reassignIDs();
  }
  
  // Takes in an EID, finds it, removes it from the list, then adds it again at a new location.
  public void moveEquipment(EIDVal EID, EIDVal newEID) {
    int dir = newEID.compareTo(EID);
    if (newEID.isNull()) return;
    
    int idx = getIndex(EID);
    int newIdx = getIndex(newEID);
    EquipData data;
    
    if (newIdx == -1) {
      if (dir == -1)
        newIdx = 0;
      if (dir == 1)
        newIdx = this.equips.size() - 1;
    }
    
    // EID must be referring to a valid entry in the list. Otherwise, ignore the request to move.
    if (idx != -1) {
      data = this.equips.get(idx);
      this.equips.remove(idx);
      this.equips.add(newIdx, data);
    }
    
    EID.clone(newEID);
    reassignIDs();
    sortList();
  }
  
  // Takes an EID, finds it and removes it from the list to be lost forever.
  public void deleteEquipment(EIDVal EID) {
    int idx = getIndex(EID);
    if (idx != -1)
      this.equips.remove(idx);
    
    reassignIDs();
    
    if (this.equips.size() == 0)
      addEquipment(new EquipData());
  }
  
  // Takes an EID and, assuming it's referring to a valid option, returns the index of said valid option.
  public int getIndex(EIDVal EID) {
    int idx = 0;
    boolean found = false;
    
    for (EquipData data : this.equips) {
      if (data.EID.compareTo(EID) == 0) {
        found = true;
        break;
      }
      idx++;
    }
    
    if (!found)
      idx = -1;
    
    return idx;
  }
  
  // Returns the index of the last weapon equip in the global list of equips.
  public int getLastIndex(EIDVal.Type t) {
    int i;
    boolean found = false;
    
    for (i = equips.size() - 1; i > 0; i--) {
      if (equips.get(i).EID.etype == t) {
        found = true;
        break;
      }
    }
    
    if (!found)
      i = -1;
    
    return i;
  }
  
  // Searches the databook for an EID and, finding one, returns its object.
  public EquipData getData(EIDVal EID) {
    int idx = getIndex(EID);
    if (idx != -1)
      return this.equips.get(idx);
    else
      return null;
  }
  
  public ArrayList<EquipData> getList() {
    return this.equips;
  }
  
  // Returns a segment list of all the "viewable" equips on the currently open page. If the page is beyond the global list, returns an empty list.
  public ArrayList<EquipData> getPage() {
    ArrayList<EquipData> result = new ArrayList<EquipData>();
    int n = this.page * Page_Size;
    
    for (int i = 0; i < Page_Size; i++) {
      if (n+i < this.equips.size())
        result.add(this.equips.get(n+i));
      else
        break;
    }
    
    return result;
  }
  
  // Shifts the view up one page, toward the beginning of the list. Loops to end if shifted past the beginning.
  public void pageup() {
    this.page--;
    if (this.page < 0) this.page = maxPageNumber();    // .size() should return an int, so no decimals need be worried about.
  }
  
  // Shifts the view down one page, toward the end of the list. Loops to beginning if shifted past the end.
  public void pagedown() {
    this.page++;
    if (this.page > maxPageNumber()) this.page = 0;
  }
  
  // Sets the page number to a particular page, so long as it exists.
  public void setPageNumber(int page) {
    if (page >= 0 && page <= maxPageNumber())
      this.page = page;
  }
  
  // Returns the page number currently being viewed.
  public int pageNumber() {
    return this.page;
  }
  
  // Returns the number of the last page in the databook.
  public int maxPageNumber() {
    return this.equips.size() / Page_Size;
  }
}
