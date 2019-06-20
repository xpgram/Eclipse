# OldEclipseWork

This primarly shows off an early, architectural test-edition of Armed Revolt that I wrote as a summer project in 2018.
It's written in Java, uses Processing as a graphics API for ease of visuals, and nothing else other than built-in Java libraries. Virtually every bit of it is written from scratch.

A few features, that I remember:
 - A turn-based game engine which stitches together "different" programs via polymorphism and interfaces as separate game-states.
 - An equipment system that uses codes to identify items and textfiles with compiled data about each coded item. This works *a little* like a database. It's only half-implemented, I think, and in the future I might use an actual database for this. SQLite, maybe. I'm not sure how that would work yet.
   - There is a separate project included here: a spreadsheet-like program that allows fast and easy creation of equipment items and handles all the "database" writing for you. Quite proud of it, honestly.
 - A grid-based map system which keeps track of soldier entities, obstacles and terrain types (though those weren't implemented).
 - Detailed Unit class, which represents soldiers, keeping track of virtually all a soldier's information (their position on the map, their hard-stats, their soft-stats such as their current HP and status effects, their equipment setup, etc.) across several sub-classes.
 - An attribute system which uses strings as flags to simply mark something as affected by said attribute; meaning, attributes, temporary or otherwise, are easy to implement as they just need to be added to and checked for in separate parts of the program. The Attribute class has auto-purify features built in, and will do so when its 'turn timer' runs out.
 
There are more details to this, I put a lot of time into it, but I'll have to remember them later. A boy's gotta get to work, yo.
