package Logic;

public class Game {

    private static int blockColumns = 3;
    // the wall are used to hold the blocks
    private boolean[][] wall;
    private int rows;
    private int columns;
    //the currently moving block
    private Block currentBlock;
    private Block nextBlock = Block.nextRandomBlock();
    //thr predicted row that currentBlock will reach if it falls directly
    private int fakeX;
    // the abscissa position of cells[0][0] of currentBlock
    private int curX;
    // the ordinate position of cells[0][0] of currentBlock
    private int curY;
    // if a row is eliminated, the score will increase by 10 points
    private UserState userState;
    // create an instance of Game
    private static Game game = new Game();

    public final int getFakeX(){return fakeX;}
    public final int getCurX(){return curX;}
    public final int getCurY(){return curY;}
    public final UserState getUserState(){return userState;}
    public final UserState getRecordState(){return new UserState(userState.getName(), userState.getScore());}
    public final boolean[][] getWall(){return wall;}
    public final boolean[][] getCurrentBlockCells(){return currentBlock.getCells();}
    public final boolean[][] getNextBlockCells(){return nextBlock.getCells();}

    public void dudang(int fakeX, int curX, int curY, UserState userState, boolean[][] wall, boolean[][] current, boolean[][] next){
        this.fakeX = fakeX;
        this.curX = curX;
        this.curY = curY;
        this.userState = userState;
        this.wall = wall;
        this.currentBlock.setBlock(current);
        this.nextBlock.setBlock(next);
    }

    private Game() {}

/*
    get the single instance
    @return the single instance of Game.
 */
    public static Game getInstance() {
        return game;
    }

/*
 * initialize the wall with height and width, and assign all wall
 with "false".
 * YOU NEED TO:
 * assign rows and columns with height and width
 * initialize the wall and assign theDDDm with "false".
 * @param height: the rows of wall
 * @param width: the columns of wall
 */
    public void initial(int height, int width) {
        rows = height;
        columns = width;
        wall = new boolean[rows][columns];
        userState = new UserState();
        for(int i = 0; i < height; i++)
            for(int j = 0; j < width; j++)
                wall[i][j] = false;
        nextBlock();
        fakeImitate();
    }

/*
 * Get next block, invoked when the game started or the last block has
 reached the bottom.
 * YOU NEED TO:
 * assign curX and curY with 0 and columns / 2 - blockColumns /2.
 * assign currentBlock with a new instance of ZBlock.
 */
    public void nextBlock() {
        curX = 0;
        curY = columns / 2 - blockColumns / 2;
        currentBlock = nextBlock;
        nextBlock = Block.nextRandomBlock();
        if(checkHit(curX, curY)) loadBlock();
    }

/*
since curX, curY, currentBlock will never collide with game
Homework1.wall except when nextBlock appeared.
we can invoke "collide()" below to check whether the game is over.
@return: whether the game is over
*/
    public boolean isOver() {
        return  collide(curX, curY, currentBlock.getCells());
    }

/*
 * execute next command, you may need to invoke collide() to check collision before rotating or moving.
 * when the command is "s" and the block has reached to the bottom,
 * loadBlock(), eliminate(), nextBlock() will be invoked sequentially.
 * @param command: the next command {"w","s","a","d"}
 */
    public void executeCommand(String command) {
        switch (command.toLowerCase().charAt(0)){
            case 'a':
                if(!collide(curX, curY - 1, currentBlock.getCells()))
                    curY--;
                execute();
                break;
            case 'd':
                if(!collide(curX + 1, curY + 1, currentBlock.getCells()))
                    curY++;
                execute();
                break;
            case 'w':
                if(!collide(curX + 1, curY, currentBlock.nextRotatedCells()))
                    currentBlock.rotate();
                execute();
                break;
            case 's':
                execute();
                break;
            case 'x':
                while(!checkHit(curX, curY))
                    curX++;
                execute();
                break;
        }
    }

    public void execute() {
        if (checkHit(curX, curY)) {
            loadBlock();
            eliminate();
            nextBlock();
        }
        else
            curX++;
        fakeImitate();
    }

    public void fakeImitate(){
        fakeX = curX;
        while(!checkHit(fakeX, curY))
            fakeX++;
    }

    /* judge whether blockwall at position(nextRow, nextColumn) collide
    with game wall.
    * @param nextcurX: the next curX after rotating or moving.
    * @param nextcurY: the next curY after rotating or moving.
    * @param nextBlockwall: the next blockwall after rotating or moving.
    * @return: true if collided, false otherwise.
    */
    private boolean collide(int nextcurX, int nextcurY, boolean [][] nextBlockwall){
        for(int i = currentBlock.getRows() - 1 ; i >= 0 ; i--)
            for(int j = currentBlock.getColumns() - 1 ; j >= 0; j--)
                if(nextBlockwall[i][j])
                    if(j + nextcurY < 0 || j + nextcurY >= columns || i + nextcurX >= rows || wall[i + nextcurX][j + nextcurY])
                        return true;
        return false;
    }

    public boolean checkHit(int curX, int curY){
        boolean[][] temp = currentBlock.getCells();
        for(int i = currentBlock.getRows() - 1; i >= 0 ; i--)
            for(int j = currentBlock.getColumns()-1; j >= 0; j--)
                if(temp[i][j])
                    if(i + curX == rows - 1 || wall[i + curX + 1][j + curY])
                        return true;
        return false;
    }

    /*assign "true" to the game wall occupied by currentBlock.*/
    private void loadBlock(){
        boolean[][] temp = currentBlock.getCells();
        for(int i = 0; i < currentBlock.getRows(); i++)
           for(int j = 0; j < currentBlock.getColumns(); j++)
               if(temp[i][j])
                   wall[i + curX][j + curY] = temp[i][j];
    }

    /*eliminate all the "full" rows. "full" means all true.*/
    private void eliminate() {
        int removeTimes = 0;
        for(int i = curX; i < rows; i++){
            int checkID = 0;
            while(checkID < columns && wall[i][checkID])
                checkID++;
            if(checkID == columns){
                eliminateOneRow(i);
                removeTimes++;
            }
        }
        if(removeTimes != 0)
            userState.setScore(userState.getScore() + 10 * removeTimes);
    }

    /*Force to eliminate one row, and all the wall above it will drop by one row.
    param row: the index of the eliminated row.*/
    private void eliminateOneRow(int row) {
        for(int i = row; i > 0; i--)
            for(int j = 0; j < columns; j++)
                wall[i][j] = wall[i - 1][j];
    }
}
