import java.util.Random;

public class Grid {
    private int rows;
    private int columns;
    private int totalBombs;
    private GridElement[][] grid;
    private boolean[][] revealed;

    public Grid(int rows, int columns, int totalBombs) {
        this.rows = rows;
        this.columns = columns;
        this.totalBombs = totalBombs;
        this.grid = new GridElement[rows][columns];
        this.revealed = new boolean[rows][columns];
        initializeGrid();
    }

    private void initializeGrid() {
        Random random = new Random();

        // empty grid
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = GridElement.EMPTY;
            }
        }

        // random bombs
        int bombsPlaced = 0;
        while (bombsPlaced < totalBombs) {
            int row = random.nextInt(rows);
            int col = random.nextInt(columns);
            if (grid[row][col] != GridElement.BOMB) {
                grid[row][col] = GridElement.BOMB;
                bombsPlaced++;
            }
        }
        // calculate and set adjacent bombs count for safe cells
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (grid[i][j] != GridElement.BOMB) {
                    int bombsCount = countAdjacentBombs(i, j);
                    grid[i][j] = GridElement.fromInt(bombsCount);
                }
            }
        }
    }

    // counts adjacent bombs for a cell
    private int countAdjacentBombs(int row, int col) {
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < rows && j >= 0 && j < columns && grid[i][j] == GridElement.BOMB) {
                    count++;
                }
            }
        }
        return count;
    }

    
    public void printGrid(boolean showAll) {
        System.out.println("Current Grid:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (revealed[i][j] || showAll) {
                    System.out.print(grid[i][j].getSymbol() + " ");
                } else {
                    System.out.print(GridElement.HIDDEN.getSymbol() + " ");
                }
            }
            System.out.println();
        }
    }

    public void revealCell(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= columns || revealed[row][col]) {
            System.out.println("Invalid co-ordinate, please try again.");
            return;
        }

        revealed[row][col] = true;

        if (grid[row][col] == GridElement.BOMB) {
            System.out.println("Game Over! You hit a bomb.");
            printGrid(true);
        } else {
            printGrid(false);
        }
    }

    public boolean isGameWon() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (!revealed[i][j] && grid[i][j] != GridElement.BOMB) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isGameOver() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (revealed[i][j] && grid[i][j] == GridElement.BOMB) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}
