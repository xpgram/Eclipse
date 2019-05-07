/* Defines the terrain characteristics of a tile.
 * This is the essential map data.
 * In fact, this is what would determine "walls" and "not walls," rather than the cell itself.
 * 
 * This class should have the following features:
 *  - Terrain Type ID   (Specifies which data to pull from file - except graphics as that can vary. A tileset should specify what "forests" look like and this would point to it.)
 *  - Read from string
 *  - Write to string
 *  - Know terrain stats: DEF boost, EVA boost, MOV hinder, etc. 
 *  - Know terrain attributes: burnable, electrifiable, breakable, openable (doors), etc.
 *  - Know terrain condition (probably through tags .?): burning, frozen, mine-placed, foggy, etc.
 *  - Know if terrain is unit-inhabitable or unit-permeable (is there a difference? There might be if vehicles/boats can't exist on certain tiles.)
 *  - Know which tileset group to use (I'll talk about this a bit more below) and which member should represent itself.
 *        ^ For now this may just be a color.
 *  
 * Tileset Group (I need a more clear name)
 * This will probably need to be another class that this one references, and it will probably need to be a part of a larger
 * tileset whole. Here's what I'm thinking:
 *  - A tileset will define the visual mood (no "one tileset to rule them all," I suppose)
 *  - Each tileset will have different groups, serving the same function tileset to tileset, hopefully.
 *  - Each group is a tile type, representing very specific terrain features, but contains multiple graphics.
 *  - These graphic sets come in different types as well:
 *     - Single: Only one graphic ever.   (Only works if tile is an "object" sitting on land or something)
 *     - Box: Solitary graphic + box graphics (Box example: me + east + south + SE Corner = a box with SE corner. e.g. AW Forests)
 *     - Pipe: Roads + Rivers -- Solitary graphic + pipes + corner pipes + t-ways + 4-ways. 
 *     - Land: Uses the bits in a byte to measure which neighbors are similar (present) or disimilar (void)
 *          Includes solitary, middle, edge, corner, inside corner, pipe, corner pipe, pinch, t-way and 4-way
 *          Also (ugh, shit) touching corners, touching corner pipes, um...
 *          Man, land vs ocean was really complex graphically in Advance Wars. I don't think there is another example
 *          of something that needed so much definition. Forests only changed if they made a box with a corner,
 *          rivers made islands out of 4-ways if they made boxes (I think). They're much less defined. 
 * [Sheesh, you'd think this auto AW-style stuff would be easier done by hand. Probably, actually.]
 * 
 * I'm going to bed...
 */

package battle.map;

public class Terrain {

  int DEFboost;   // A boost to DEF during all unit-to-unit interactions.
  int EVAboost;   // A boost to AGI during all unit-to-unit interactions.   (I don't know that I need this. I'll have to play test.)
  //AttributeTagList attributes;
  // TODO Add attributes functionality. (Details in header)
  
}
