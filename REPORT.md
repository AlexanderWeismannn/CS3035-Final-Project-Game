CS 3035 Programming Project
December 10th, 2020
Joshua Roberts,  Anton Benfey,  Alexander Weismann

OVERVIEW
This paper covers the development and implementation of “Goblin: Shadow Legends”, our tower defence game for the Building User Interfaces Final project.

GOALS

Create an endless wave based tower defence game
Create enemy pathing 
Create a money and health system
Create an original main menu with non standard buttons
Stick to the principles of MVC as much as possible during the development
Implement a settings template
Implement overlay menus for the main menu and game

IMPLEMENTATION
Our implementation of this application draws heavily from the MVC principles discussed in class. For each view that we had (Overlay View, Main Menu View, Overlay Menu View, and Pause Menu View) we attempted to create custom models of the view when necessary and controllers to handle inputs or events. This was mostly successful, however there is some “bad” MVC design throughout our code that could be improved.

The basic idea of our game was to have a wave based / endless tower defence game. The player would be able to drag and drop different towers onto the board to prepare for the incoming wave of goblins. The player would gain money for killing the goblins and be able to use this money on upgrades or to buy more towers. There would also be a health meter where every time a goblin got past your defences you would lose a heart. 

In terms of the implementation, we wanted to make sure components were both scalable as well as efficient. Given our project needed game logic, there were going to be calls to methods every frame. These calls dealt with animating the enemies and tower projectiles, as well as handling the backend procedures for the waves. Some of these components did not fit well in MVC, and had to be incorporated into their associated widgets.

CONCLUSION AND POTENTIAL CHANGES
Fortunately we were able to get the majority of these features and goals implemented. We have a Fade In effect with a splash screen of our names and company logo, a main menu with a help screen, settings screen, play button, and quit button. The game itself while fairly simplistic accomplishes what we set out to do. With more time we would have liked to flesh out the complexity of it. This would be done by adding more tower types, more enemies, an upgrade system to the towers themselves, and a wave based mode instead of an endless survival one. Audio was also a consideration for the game. Given more time we would have liked to add sound effects and music, and we would have liked to sync up the audio to the placeholder settings slider. Overall all this was a very enjoyable project and a great way to end off the class.

