import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        

        while (true) {
            playGame(scanner);
            System.out.println("Would you like to play again? (y/n)");
            String playAgain = scanner.next();
            if (!playAgain.equalsIgnoreCase("y")) {
                break;  
            }
        }

        System.out.println("Thanks for playing, see you next time :)");
        scanner.close();
    }

    private static void playGame(Scanner scanner) {
        System.out.println("Please enter number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Please enter number of columns:");
        int columns = scanner.nextInt();
        System.out.println("Please enter number of bombs:");
        int totalBombs = scanner.nextInt();

        Grid grid = new Grid(rows, columns, totalBombs);
        grid.printGrid(false);

        while (true) {
            System.out.println("Enter row and column, separated by a space (e.g., '2 4'):");
            int row = scanner.nextInt() - 1;
            int col = scanner.nextInt() - 1;
            grid.revealCell(row, col);
            if (grid.isGameWon() || grid.isGameOver()) {
                break;
            }
        }

        if (grid.isGameWon()) {
            System.out.println("Congratulations! You win :)");
        } else if (grid.isGameOver()) {
            System.out.println("Game over! You've hit a bomb :(");
        }
    }
}
