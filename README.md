# Othello-AI-in-Java
The game Othello except this time it's human vs computer. 

**How the algorithm works:**
Something to clear up, this is an algorithm and not AI. AI needs to learn, and this rather computes. 
  
It works off a minimax algorithm with the pointage system looking at two things: The number of black pieces minus the number of white pieces and who holds important areas. For example, corners are really important and are worth three points in the pointage system each. Other important parts of the board are are the 4 center pieces and all the edge pieces. 

The algorithm also takes advantage of alpha beta pruning to become more effecient. It is hence able to look 8 moves ahead.

Here is a link explaining the minimax algorithm: https://en.wikipedia.org/wiki/Minimax
Here is a link explaining alpha beta pruning: https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning

**Resaults**

In the end the algorithm could effortlssly beat me. It could also beat more simple algorithms that just look at the number of black pieces minus the number of white pieces up to a depth of 11+. It is hence very succesful as it is powerfull yet very time effecient. 
