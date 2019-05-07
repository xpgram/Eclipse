/*
 * Contains a list of attributes along with useful functions for searching and cleaning them.
 * 
 * I'm sensing that this wrapper class didn't really need to exist. List<> handles it all pretty well as is.
 * Oh well, I already wrote it though. It has one unique feature, at least.
 * 
 * Also, I think I might have trouble maintaining consistent attribute names throughout the program without an enum.
 * Switching to Strings may have been a bad idea. I should fix that at some point.
 * TODO What I said.
 * 
 * Ah, problem. Well, sort of.
 * There's no easy toString method with enums.
 *  
 */

package battle.utils;

import java.util.ArrayList;
import java.util.List;

public class AttributeTagList {
  
  List<AttributeTag> attributes;
  
  
  public AttributeTagList() {
    attributes = new ArrayList<AttributeTag>();
  }
  
  public void updateTimers() {
    for (AttributeTag t : attributes) {
      t.tickTimer();
      if (t.isEnabled() != true)
        attributes.remove(t);
      // If I want to play an animation or something, send the message here, maybe.
    }
  }
  
  public void add(AttributeTag tag) {
    attributes.add(tag);
  }
  
  public void addAll(AttributeTagList tagList) {
    attributes.addAll(tagList.getCollection());
  }
  
  public void remove(AttributeTag tag) {
    attributes.remove(tag);
  }
  
  public void removeAll() {
    for (AttributeTag a : attributes) {
      attributes.remove(a);
    }
  }
  
  public boolean contains(Attribute a) {
    boolean result = false;
    for (AttributeTag t : attributes) {
      if (t.equalsType(a)) {
        result = true;
        break;
      }
    }
    return result;
  }
  
  /* Returns a list of all element-tags found in this list.
   */
  public List<Attribute> parseElementTags() {
    List<Attribute> list = new ArrayList<Attribute>();
    if (contains(Attribute.Fire)) list.add(Attribute.Fire);
    if (contains(Attribute.Volt)) list.add(Attribute.Volt);
    if (contains(Attribute.Frost)) list.add(Attribute.Frost);
    if (contains(Attribute.Water)) list.add(Attribute.Water);
    return list;
  }

  public void copy(AttributeTagList tagList) {
    attributes.clear();
    attributes.addAll(tagList.getCollection());  // getCollection() is private. Can I do this? Whoa! :D
  }
  
  private List<AttributeTag> getCollection() {
    return attributes;
  }
  
}
