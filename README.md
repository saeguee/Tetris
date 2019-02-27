# Tetris
## Game logic
### 1 Initialization & End
At the beginning of the game, the player prints the game start interface, and can choose to start the game and exit the game. Select to start the game, break into the game, print the game process interface (including maps and operation prompts), and choose to exit the game to end the program.</br>

### 2 Game Operations
The player enters a (representing the left-shifting square), d (representing the right-moving square), w (representing the clockwise falling square), s (representing no action), and x (representing direct drop to A symbol in the bottommost) as a single operation.</br>

After each operation, the square should automatically fall to a square.</br>

The left and right shifts are moved one frame at a time, and the rotation is rotated 90 degrees clockwise each time.</br>

Of course, you can also use other reasonable keys instead.</br>

Each time the input ends, such as after entering a on the command line, click Enter to execute the command.</br>

### 3 Game Rules
Each unit time (each operation, that is, each input is counted as one unit time) is dropped into a square. When the square falls to the end or touches the already stacked square, the next square begins to fall.</br>

The initial position of the drop of the square is the top of the game field (ie, the small square at the bottom of the square is at the top of the game field).</br>

After the square moves or rotates, it cannot exceed the left and right borders and the lower boundary or re-enter with other squares (can exceed the upper boundary). If this happens after a move or rotation, then this move or rotation is invalid, this unit time is equivalent to the selection. "Do not operate", the effect is equivalent to the "s" of the above key.</br>

When a horizontal grid of a row in a region is filled with all squares, the row disappears and becomes the player's score. The more rows that are eliminated at the same time, the more points are scored. If a line is eliminated, the squares that have been stacked above the line are all dropped by one line. That is to say, in one elimination process, the number of rows dropped by one square is equal to the number of rows of the rows that are eliminated below the row of the square.</br>

The game interface is output once per unit time (ie, each operation), and it is determined whether the game is over. If it is over, print the corresponding prompt and return to the initial game start screen for the player to start the game or exit the game. If not, wait for the player to continue typing.</br>

Game termination condition: When the fixed block is piled up to the top of the area and cannot be eliminated, the game ends.</br>

### 4 Scoring
Eliminate 10 points for one line. When eliminating multiple lines at the same time, each line is eliminated, and the score of each line is doubled. </br>

e.g. If 3 lines are eliminated at the same time, the score is 3*40 = 120, and the score is printed every time.</br>
