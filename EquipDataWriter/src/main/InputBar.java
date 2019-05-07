package main;

public class InputBar {
  
  private String str;               // The value currently being edited.
  private int cursor;               // The position of the editing cursor.
  
  // Constructor
  public InputBar() {
    defaultState();
  }
  
  // Resets the input bar to default settings: empty and looking for generic text.
  public void defaultState() {
    this.str = "";
    this.cursor = 0;
  }
  
  // Moves the cursor along the field string at the rate of int i, constrained to never go beyond the limits of the string.
  public void moveCursor(int i) {
    this.cursor += i;
    this.cursor = utils.Common.constrain(this.cursor, 0, this.str.length());
  }
  
  // Moves the cursor towards the beginning of the string value.
  public void moveCursorLeft() {
    moveCursor(-1);
  }
  
  // Moves the cursor towards the end of the string value.
  public void moveCursorRight() {
    moveCursor(1);
  }
  
  // Moves the cursor to the end of the string.
  public void moveCursorToEnd() {
    this.cursor = this.str.length();
  }
  
  // Moves the cursor to the beginning of the string.
  public void moveCursorToHome() {
    this.cursor = 0;
  }
  
  // Adds a new char to the string in a place dependent on where the cursor is located.
  public void addChar(char c) {
    this.str = this.str.substring(0, cursor) + c + str.substring(cursor, str.length());
    moveCursorRight();
  }
  
  // Deletes a char from the string where the cursor is located.
  public void backspace() {
    if (cursor == 0)
      return;
    
    this.str = this.str.substring(0, cursor - 1) + this.str.substring(cursor, str.length());
    moveCursorLeft();
  }
  
  // Deletes a char from the string where the cursor is located, but in the forward direction.
  public void delete() {
    if (cursor == str.length())
      return;
    
    this.str = this.str.substring(0, cursor) + this.str.substring(cursor + 1, str.length());
  }
  
  // Clears the entire field.
  public void clearString() {
    this.str = "";
    this.cursor = 0;
  }
    
  // Returns the String as it is.
  public String getString() {
    return this.str;
  }
  
  // Returns the position of the string currently being edited.
  public int getCursorPos() {
    return this.cursor;
  }
  
  // Sets the string to something pre-written
  public void setString(String str) {
    this.str = str;
    this.cursor = this.str.length();
  }
}
