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
        // ANSI esc codes
        String reset = "\u001B[0m";
        String defaultColor = reset;  
        String blue = "\u001B[34m";  
        String green = "\u001B[32m";  
        String red = "\u001B[31m";  
        String purple = "\u001B[35m"; 


        System.out.println(reset + "Current Grid:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (revealed[i][j] || showAll) {
                    if (grid[i][j] == GridElement.BOMB) {
                        System.out.print(red + grid[i][j].getSymbol() + reset + " ");
                    } else if (grid[i][j] == GridElement.EMPTY) {
                        System.out.print(defaultColor + grid[i][j].getSymbol() + reset + " ");
                    } else if (grid[i][j] == GridElement.ONE || grid[i][j] == GridElement.FIVE) {
                        System.out.print(blue + grid[i][j].getSymbol() + reset + " ");
                    } else if (grid[i][j] == GridElement.TWO || grid[i][j] == GridElement.FOUR) {
                        System.out.print(green + grid[i][j].getSymbol() + reset + " ");
                    } else if (grid[i][j] == GridElement.THREE || grid[i][j] == GridElement.SIX) {
                        System.out.print(red + grid[i][j].getSymbol() + reset + " ");
                    } else if (grid[i][j] == GridElement.FOUR || grid[i][j] == GridElement.EIGHT) {
                        System.out.print(purple + grid[i][j].getSymbol() + reset + " ");
                    }
                } else {
                    System.out.print(GridElement.HIDDEN.getSymbol() + " ");
                }
            }
            System.out.println();
        }
    }

    public void revealCell(int row, int col) {
        String red = "\u001B[31m";
        if (row < 0 || row >= rows || col < 0 || col >= columns || revealed[row][col]) {
            System.out.println(red + "Invalid coordinate, please try again.");
            return;
        }

        revealed[row][col] = true;

        for (int i = Math.max(row - 1, 0); i <= Math.min(row + 1, rows - 1); i++) {
            for (int j = Math.max(col - 1, 0); j <= Math.min(col + 1, columns - 1); j++) {
                if (!revealed[i][j] && grid[i][j] != GridElement.BOMB) {
                    revealed[i][j] = true;
                    if (grid[i][j] == GridElement.EMPTY) {
                        revealCell(i, j);
                    }
                }
            }
        }

        printGrid(false);
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
