public class GameOfLife
{
    private int[][] board; // 0 = dead; 1 = alive
    private int[][] boardAlt; // the alternate board
    private int x; // x size
    private int y; // y size
    private int animationDelay; // millisecond delay for animation.
    
    // create a blank board of specified dimension.
    public GameOfLife(int xDim, int yDim, int delay)
    {
        this.x = xDim;
        this.y = yDim;
        board = new int[y][x];
        boardAlt = new int[y][x];
        for (int i = 0; i < y; i++)
        {
            for (int j = 0; j < x; j++)
            {
                board[i][j] = 0;
            }
        }
        this.animationDelay = delay;
        StdDraw.show(animationDelay);
    }
    
    // create a board from the given matrix.
    public GameOfLife(int[][] start, int delay)
    {
        this.x = start[0].length;
        this.y = start.length;
        board = new int[y][x];
        boardAlt = new int[y][x];
        for (int i = 0; i < y; i++)
        {
            for (int j = 0; j < x; j++) board[i][j] = start[i][j];
        }
        StdDraw.setXscale(-.5, x-.5);
        StdDraw.setYscale(-.5, y-.5);
        this.animationDelay = delay;
        StdDraw.show(animationDelay);
    }
    
    // create a board from the given matrix, padding with empty cells
    // to the specified dimensions.
    public GameOfLife(int[][] start, int xDim, int yDim, int delay)
    {
        this.x = xDim;
        this.y = yDim;
        int width = start[0].length;
        int height = start.length;
        if (x < width || y < height)
            throw new RuntimeException("X and Y dimensions must be at least"
                                           +" as large as the input matrix.");
        int startX = (x - width)/2;
        int startY = (y - height)/2;
        board = new int[y][x];
        boardAlt = new int[y][x];
        
        // initialize board to zero
        for (int i = 0; i < y; i++)
        {
            for (int j = 0; j < x; j++)
            {
                board[i][j] = 0;
            }
        }
        // initialize center squares to contents of matrix
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
                board[i + startY][j + startX] = start[i][j];
        }
        StdDraw.setXscale(-.5, x-.5);
        StdDraw.setYscale(-.5, y-.5);
        this.animationDelay = delay;
        StdDraw.show(animationDelay);
    }
    
    public void step()
    {
        for (int i = 0; i < y; i++)
        {
            for (int j = 0; j < x; j++)
            {
                int iB = i-1, iA = i+1, jB = j-1, jA = j+1;
                if (i == 0) iB = y - 1;
                if (i == y-1) iA = 0;
                if (j == 0) jB = x - 1;
                if (j == x-1) jA = 0;
                int sum = board[iA][jA] + board[iA][j] + board[iA][jB]
                    + board[i][jA] + board[i][jB]
                    + board[iB][jA] + board[iB][j] + board[iB][jB];
                
                if ((board[i][j] == 1 && sum == 2) || sum == 3)
                    boardAlt[i][j] = 1;
                else boardAlt[i][j] = 0;
                
            }
        }
        
        int[][] a = board;
        board = boardAlt;
        boardAlt = a;
    }
    
    public void show()
    {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledRectangle(x/2 - .5, y/2 - .5, x/2, y/2);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < y; i++)
        {
            for (int j = 0; j < x; j++)
            {
                if (board[i][j] == 1)
                    StdDraw.filledSquare(j, y - 1 - i, .5);
            }
        }
        StdDraw.show(animationDelay);
    }
    
    public static void main(String[] args)
    {
        int x = StdIn.readInt();
        int y = StdIn.readInt();
        int[][] startingBoard = new int[y][x];
        for (int i = 0; i < y; i++)
        {
            for (int j = 0; j < x; j++)
            {
                startingBoard[i][j] = StdIn.readInt();
            }
        }
        
        GameOfLife game = new GameOfLife(startingBoard, 100, 100, 10);
        while (true)
        {
            game.show();
            game.step();
        }
    }
}