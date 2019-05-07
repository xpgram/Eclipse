package utils;

public class Common {

  public static int constrain(int val, int min, int max) {
    // Anal-retentive paranoia.
    if (min > max) {
      int n = min;
      min = max;
      max = n;
    }
    
    if (val < min) val = min;
    if (val > max) val = max;
    return val;
  }
  
  /* Given two ints, returns the largest one as a new int.
   * I think the Math library already has this, but uh... whatever.
   */
  public static int max(int val, int max) {
    if (val < max) val = max;
    return val;
  }
  
  /* Given a list of ints, returns the largest int contained in the array.
   */
  public static int max(int[] values) {
    int r = values[0];
    for (int i = 1; i < values.length; i++) {
      r = max(r, values[i]);
    }
    return r;
  }
  
  /* Given two ints, returns the smallest one as a new int.
   * I think the Math library already has this, but uh... whatever.
   */
  public static int min(int val, int min) {
    if (val > min) val = min;
    return val;
  }
  
  /* Given a list of ints, returns the smallest int contained in the array.
   */
  public static int min(int[] values) {
    int r = values[0];
    for (int i = 1; i < values.length; i++) {
      r = min(r, values[i]);
    }
    return r;
  }
  
  /* Determines whether a given char is one of ~the~ ten digit numbers.
   */
  public static boolean charIsDigit(char c) {
    boolean r = false;
    if (c >= '0' && c <= '9') r = true;
    return r;
  }
  
  /* If the given char is a numerical digit, returns that digit.
   * Otherwise, returns -1.
   */
  public static int charToDigit(char c) {
    int n = -1;
    if (charIsDigit(c))
      n = c - '0';
    return n;
  }
  
  /* Determines whether the given String is strictly numerical. Returns true if so.  
   */
  public static boolean strIsNumber(String str) {
    boolean r = false;
    
    try {
      Integer.valueOf(str);
      r = true;
    } catch (NumberFormatException e) { }
    
    return r;
  }
}
