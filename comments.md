Pros: 
The solution has tests and there is an attempt for a proper concern separation (decouple board, console interaction, and presentation). 
Also, somewhere good idiomatic scala code is used. 
The game console interaction is nice. 
MoveDirection and Mode(expect the name) are good abstractions  

Cons: 
There are a lot of places with mutable states 
Abstractions are very leaky (e.g. controller.Controller declares def processInput(s: Char): Mode but no-one except Controller itself uses it )  
Composition for game.MoveControl and game. Board is done by inheritance and as a result, the board leaks a lot of implementation details so it is impossible to create another type of board. 
Moving empty tile is done by in-place array update 
No check that randomly generated board is valid  
*IO monads or Streams weren't used 

The problem is almost solved but design, abstraction, and coding aren't good enough 🙁  
