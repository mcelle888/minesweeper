import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String purple = "\u001B[35m";
        String reset = "\u001B[0m";
        String cyan = "\u001B[36m";

        String title = cyan +
        " __   __  ___   __    _  _______  _______  _     _  _______  _______  _______  _______  ______   \n" 
        +"|  |_|  ||   | |  |  | ||       ||       || | _ | ||       ||       ||       ||       ||    _ |\n"
        +"|       ||   | |   |_| ||    ___||  _____|| || || ||    ___||    ___||    _  ||    ___||   | ||  \n"
        +"|       ||   | |       ||   |___ | |_____ |       ||   |___ |   |___ |   |_| ||   |___ |   |_||_ \n"
        +"|       ||   | |  _    ||    ___||_____  ||       ||    ___||    ___||    ___||    ___||    __  |\n"
        +"| ||_|| ||   | | | |   ||   |___  _____| ||   _   ||   |___ |   |___ |   |    |   |___ |   |  | |\n"
        +"|_|   |_||___| |_|  |__||_______||_______||__| |__||_______||_______||___|    |_______||___|  |_|\n";

        System.out.println(title);
        System.out.println(purple + "Lets play Minesweeper! \n");
        System.out.println(cyan + "!Type '\\quit' to exit the game at any time \n");


        while (true) {
            playGame(scanner);
            System.out.println(reset + "Would you like to play again? (y/n)");
            String playAgain = scanner.next();
            if (!playAgain.equalsIgnoreCase("y")) {
                break;  
            }
        }

        System.out.println(purple + "Thanks for playing, see you next time :)");
        scanner.close();
    }

    private static void playGame(Scanner scanner) {
    String reset = "\u001B[0m";
    String cyan = "\u001B[36m";
    String red = "\u001B[31m";
    String green = "\u001B[32m";
    String yellow = "\u001B[33m";

    System.out.println(reset + "Choose the dimensions of the game and the number of bombs:");
    System.out.println(cyan + "Please enter number of rows:");
    int rows = getValidInput(scanner);
    System.out.println(cyan + "Please enter number of columns:");
    int columns = getValidInput(scanner);
    System.out.println(yellow + "Please enter number of bombs:");
    int totalBombs = getValidInput(scanner);

    Grid grid = new Grid(rows, columns, totalBombs);
    grid.printGrid(false);

    while (true) {
        System.out.println(cyan + "Enter row and column, separated by a space (e.g., '2 4'):");
        int row = getValidInput(scanner) - 1;
        int col = getValidInput(scanner) - 1;
        grid.revealCell(row, col);
        if (grid.isGameWon() || grid.isGameOver()) {
            break;
        }
    }

    if (grid.isGameWon()) {
        System.out.println(green + "Congratulations! You win :)");
    } else if (grid.isGameOver()) {
        System.out.println(red + "Game over! You've hit a bomb :(");
    }
}

private static int getValidInput(Scanner scanner) {
    String red = "\u001B[31m";
    String pink = "\u001b[35m";
    String quit = "\\quit";
    String reset = "\u001B[0m";
    int input;
    while (true) {
        try {
            String userInput = scanner.next();
            if (userInput.equalsIgnoreCase(quit)) {
                System.out.println(reset + "Exiting...");
                System.out.println(pink + "Thanks for playing, see you next time :)");
                System.exit(0);
            }
            input = Integer.parseInt(userInput);
            if (input <= 0) {
                System.out.println("Please enter a positive number or type '\\quit' to exit.");
                continue;
            }
            break;
        } catch (Exception e) {
            System.out.println(red + "Invalid input. Please enter a valid number or type '\\quit' to exit.");
        }
    }
    return input;
}
}
