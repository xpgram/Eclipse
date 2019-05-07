package main;

public interface ICyclerEnum {
  
  // Implementee Methods
  ICyclerEnum[] getList();
  int ordinal();
  String toString();
  String displayString();
  String displayHeader();
  
  ICyclerEnum next();
  ICyclerEnum prev();
  
  public class Utils {
    
    // Retrieves the enum associated with the given string.
    // Less "me" focused and more utilitarian for the benefit of muy dificil generic enum cyclers
    public static ICyclerEnum valueOf(ICyclerEnum[] list, String str) {
      ICyclerEnum r = null;
      
      for (ICyclerEnum e : list) {
        if (e.toString().equals(str) ||
            e.displayString().equals(str)) {
          r = e;
          break;
        }
      }
      
      return r;
    }
    
    // Retrieve next enum in sequence
    public static ICyclerEnum next(ICyclerEnum enumItem) {
      int index = enumItem.ordinal();
      ICyclerEnum[] list = enumItem.getList();
      ICyclerEnum next;
      
      if (index + 1 < list.length)
        next = list[index + 1];
      else
        next = list[0];
      
      return next;
    }
    
    // Retrieve previous enum in sequence
    public static ICyclerEnum previous(ICyclerEnum enumItem) {
      int index = enumItem.ordinal();
      ICyclerEnum[] list = enumItem.getList();
      ICyclerEnum previous;
      
      if (index - 1 >= 0)
        previous = list[index - 1];
      else
        previous = list[list.length - 1];
      
      return previous;
    }
    
  }
  
}
