INTRODUCTION
------------

 * This program designs and implements a custom data structure called an MDeque. 
   The structure operates like a doubly-linked list implementation of a queue, 
   but it keeps track of the middle element in addition to a head and tail. 
   In the case of an even length, the middle is considered to be at 
   position size/2 + 1. 
   
 * MAIN METHOD: Decode.Java
   MDEQUE CLASS: MDeque.java

 * The program's main method creates a new MDeque and prompts 
   the user to enter a "sequence" to add to the MDeque. It them prompts the user
   for a "decoding key" which will be used to operate on the MDeque. 
   (F = remove front element, B = remove back element, R = reverse the elements)
   
 * The sequence must consist of only integers separated by commas and spaces, 
   and the key must consist of only "F", "B", or "R" characters.
   
   Sample user input - 
   Sequence: 
   12, 6, 9, 20, 17, -8, 19, 22
   Decoding key:
   FBRFBR

 * An explanation for each class can be found at the top of each file  
   Main method: NYCStreetTrees.java
   Parsing method: CSV.java
   Abstraction methods: Tree.java and TreeSpecies.java
   Data Structure methods: TreeList.java and TreeSpeciesList.java;
   
   
REQUIREMENTS
------------
 
 * If running in an IDE, you must create a package to store all the
   files.
 
CONFIGURATION
-------------
 
 * N/A


MAINTAINERS
-----------

Current maintainers:
 * Finn Fetherstonhaugh - fkf2005@nyu.edu
