package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/* This class helps make reading asset data from the [project's] various files easier.
 * Everything is a static function.
 * You simply tell it what line you want and it returns a String.
 * Generally.
 * 
 * If I have further use-cases then their solutions will probably go in here too.
 * 
 * Implement these methods:
 *  x Read nth line in file
 *  x Count total lines in file
 *  x Sort all lines alphabetically
 *  x Erase/Forget empty lines                                Sort of inferred by sort logic
 *  x Search for line beginning with string s
 *  x Append line (string) to file                            Seems dangerous. The game should never be writing to it's hard-coded assets files.
 *  - Add line (string) to file, sorted alphabetically        If file is not already sorted, this couldn't sensibly do its job.
 */

public class FileScanner {

  /* Retrieves the nth line from the given file. Uses \n as a delimiter.
   * If this line is beyond the contents of the file, simply returns an empty String.
   * TODO Throw an exception instead? Yes, probably. I'd be trying to access something that doesn't exist. That's always bad.
   */
  public static String readLineNumber(String filename, int n) {
    String result = "";
    
    try {
      Scanner s = new Scanner(new File(filename));
      s.useDelimiter("\n");
    
      // Iterate through the input stream until we get to our desired line.
      for (int i = 0; i < n; i++) {
        if (s.hasNext() == false)
          result = "Error: Line " + n + " does not exist."; // TODO Throw exception
        s.next();
      }
      
      // Make sure another line exists, then read the string into memory.
      if (s.hasNext())
        result = s.next();
      
      s.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  
    return result;
  }
  
  /* Given a filename and a target string label for a particular line, finds said line in file and returns it.
   * This is most useful when using ID codes to locate enemy or equipment data, or other such kinds of data.
   * If label is not found, returns an empty string.
   * TODO Should NotFound return null or something else? Should it throw an exception because I should always know which IDs are valid beforehand?
   */
  public static String readLineLabel(String filename, String label) {
    String result = "";
    boolean found = false;
    
    try {
      Scanner s = new Scanner(new File(filename));
      s.useDelimiter("\n");
      
      // Iterate through the entire file, stopping only when EoF or a line with a beginning matching 'label' is encountered.
      while (s.hasNext() && !found) {
        result = s.next();
        if ( result.substring(0, Math.min(label.length(), result.length())).equals(label) )
          found = true;
      }
      
      s.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    
    if (!found)
      result = "";
    
    return result;
  }
  
  /* Counts the number of lines in the given file, delimited by the standard newline character.
   */
  public static int countLines(String filename) {
    int count = 0;
    
    try {
      Scanner s = new Scanner(new File(filename));
      s.useDelimiter("\n");
      
      // Iterate through all lines in the file.
      while (s.hasNext()) {
        s.next();
        count++;
      }
      
      s.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    
    return count;
  }
  
  /* Given a file, reads all lines (delimited by newline characters) into memory and sorts them alphabetically, writing them into a new file
   * under the same name.
   */
  public static void sortLines(String filename) {
    ArrayList<String> lines = new ArrayList<String>();
    
    // Read into memory
    // TODO Answer this question: what if the file is really, super big? Like, too big for memory?
    try {
      Scanner s = new Scanner(new File(filename));
      s.useDelimiter("\n");
      while (s.hasNext())
        lines.add(s.next());
      s.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    
    // Sort the list.
    lines.sort(null);
    
    // Write the list to a new file (the same file, but not while testing).
    try {
      PrintWriter w = new PrintWriter(new File(filename));
      for (String str : lines)
        w.write(str + "\n");
      w.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  /* Appends a String to the end of the given file, separated from pre-existing contents by a newline character.
   */
  public static void appendLine(String filename, String str) {
    try {
      // I'm so young and small, I don't even know what this daisy chain is for!
      FileWriter fw = new FileWriter(filename, true);
      BufferedWriter bw = new BufferedWriter(fw);
      PrintWriter out = new PrintWriter(bw);
      out.println(str);
      out.close(); bw.close(); fw.close();
      // I'm such a young, firm little man that I don't know if I need all these closes.
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
