package Logic;

import java.util.Random;
class Block{
    private static int[][] flag = new int[][]{{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
    private static Random rand = new Random();
    private int rows, columns, paneWidth, sum;
    private boolean[][] cells;

    public Block() {
        /*rows = 1 + rand.nextInt(3);
        columns = 3 + rand.nextInt(3);*/
        rows = 2;
        columns = 3;
        paneWidth = columns;
        cells = cellsFactory(rows, columns);
    }


    public static Block nextRandomBlock(){
        return new Block();
    }

    public void setBlock(boolean[][] cells){
        this.cells = cells;
    }

    public boolean[][] nextRotatedCells(){
        boolean[][] clone = new boolean[paneWidth][paneWidth];
        for (int i = 0; i < paneWidth; i++)
            for (int j = 0; j < paneWidth; j++)
                clone[i][j] = cells[i][j];

        boolean temp;
        for (int i = 0; i < paneWidth; i++)
            for (int j = i + 1; j < paneWidth; j++) {
                temp = clone[i][j];
                clone[i][j] = clone[j][i];
                clone[j][i] = temp;
            }

        for(int j = 0; j < paneWidth; j++)
            for(int i = 0; i < paneWidth/2; i++){
                temp = clone[i][j];
                clone[i][j] = clone[paneWidth - 1 - i][j];
                clone[paneWidth - 1 - i][j] = temp;
            }

        return clone;
    }

    public boolean[][] cellsFactory(int rows, int columns){
        boolean[][] newCells = new boolean[paneWidth][paneWidth];
        while(true){
            for (int i = 0; i < rows; i++)
                for (int j = 0; j < columns; j++)
                    if(newCells[i][j] = rand.nextInt() > 0.5) sum++;
            if(!newCells[0][0]){
                newCells[0][0] = true;
                sum++;
            }
            if(sum > 2 && bfs(newCells, rows, columns)) break;
                else sum = 0;
        }
        return newCells;
    }

    public boolean bfs(boolean[][] newCells, int rows, int columns){
        boolean[][] visit = new boolean[rows][columns];
        int[][] queue = new int[rows*columns][2];
        int first = 0, rear = 1, x = 0, y = 0;

        /*if(rows == 3)
            for(int j = 1; j < columns - 1; j++)
                if(!newCells[1][j] && newCells[1][j - 1] && newCells[1][j + 1] && newCells[0][j] && newCells[2][j])
                    return false;*/

        outer:
        for (int m = 0; m < rows; m++)
            for (int n = 0; n < columns; n++)
                if(newCells[m][n]){
                    queue[first][0] = m;
                    queue[first][1] = n;
                    visit[m][n] = true;
                    break outer;
                }

        while(first < rear){
            x = queue[first][0];
            y = queue[first++][1];
            for(int k = 0; k < 4; k++){
                int next_rows = x + flag[k][0], next_columns = y + flag[k][1];
                if(next_rows < 0 || next_columns < 0 ||
                    next_rows == rows || next_columns == columns ||
                    visit[next_rows][next_columns] || !newCells[next_rows][next_columns])
                    continue;
                queue[rear][0] = next_rows;
                queue[rear++][1] = next_columns;
                visit[next_rows][next_columns] = true;
            }
        }
        return first == sum;
    }

    public void rotate() {
        cells = nextRotatedCells();
    }

    public boolean[][] getCells(){
        return cells;
    }

    public  int getRows(){
        return paneWidth;
    }

    public int getColumns(){
        return paneWidth;
    }

    public int getPaneWidth(){
        return paneWidth;
    }
}