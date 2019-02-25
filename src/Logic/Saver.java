package Logic;

import java.io.*;

public class Saver {
    public Game game = Game.getInstance();
    public void save(String name) throws Exception{
       /* this.fakeX = fakeX;
        this.curX = curX;
        this.curY = curY;
        this.userState = userState;
        this.wall = wall;
        this.currentBlock.setBlock(current);
        this.nextBlock.setBlock(next);*/

       boolean[][] wall = game.getWall();
       boolean[][] current = game.getCurrentBlockCells();
       boolean[][] next = game.getNextBlockCells();
       int wallRow = wall.length;
       int wallColumn = wall[0].length;
       int blockWidth = current.length;
       File file = new File("/javafile/src/Logic/record"+"name");
        DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file));
        dataOutputStream.writeInt(game.getFakeX());
        dataOutputStream.writeInt(game.getCurX());
        dataOutputStream.writeInt(game.getCurY());
        dataOutputStream.writeInt(game.getUserState().getScore());
        dataOutputStream.writeUTF(game.getUserState().getName());
        for (int i = 0; i < wallRow; i++)
            for (int j = 0; j < wallColumn; j++)
                dataOutputStream.writeBoolean(wall[i][j]);
        for (int i = 0; i < blockWidth; i++)
            for (int j = 0; j < blockWidth; j++)
                dataOutputStream.writeBoolean(current[i][j]);
        for (int i = 0; i < blockWidth; i++)
            for (int j = 0; j < blockWidth; j++)
                dataOutputStream.writeBoolean(next[i][j]);
    }

    public void load(){

    }
}

