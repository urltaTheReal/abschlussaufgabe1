package abschlussaufgabe1;

import java.util.Scanner;

/**
 * @author urlta
 * 
 *         Game values is responsible for asking and registering all important
 *         values, which are required to be set at the start of the game Game
 *         values will stop asking and registering if the game is quit by the
 *         quit command, or isQuit was set to true initially
 *
 */
public class GameValues {
    private int startingGold;
    private int winningGold;
    private String[] playerNames;
    private int playerAmount;
    private long seed;
    private boolean isQuit = false;
    private boolean playerAmountCorrect = false;
    private int playerNamesCorrect = 0;
    private boolean startingGoldCorrect = false;
    private boolean winningGoldCorrect = false;
    private boolean seedCorrect = false;

    /**
     * If game values is initialized, the method game start will be executed. game
     * start is responsible for player interaction and to make sure, that all values
     * are set correctly by the player
     * 
     * @param sc     scanner used to read inputs
     * @param isQuit if is quit is true, nothing will happen in this class
     */
    public GameValues(Scanner sc, boolean isQuit) {
        this.isQuit = isQuit;
        if (!isQuit) {
            printPixelArt();
            gameStart(sc);
        }
    }


    public int getStartingGold() {
        return startingGold;
    }

    public int getWinningGold() {
        return winningGold;
    }

    public String[] getPlayerNames() {
        return playerNames;
    }

    public int getPlayerAmount() {
        return playerAmount;
    }

    public long getSeed() {
        return seed;
    }

    public boolean isQuit() {
        return isQuit;
    }

    public void setQuit(boolean isQuit) {
        this.isQuit = isQuit;
    }

    private void gameStart(Scanner sc) {

        askAndRegisterPlayerAmount(sc);

        playerNames = new String[playerAmount];

        askAndRegisterPlayersNames(sc);

        askAndRegisterStartingGold(sc);

        askAndRegisterWinningGold(sc);

        askAndRegisterSeed(sc);
    }

    private void askAndRegisterSeed(Scanner sc) {
        while (!isQuit && !seedCorrect) {
            System.out.println("Please enter the seed used to shuffle the tiles:");
            String seedInput = sc.nextLine();
            seedCorrect = isNumberInput(seedInput);
            if (seedCorrect) {
                seed = Integer.parseInt(seedInput);
            }
        }
    }

    private void askAndRegisterWinningGold(Scanner sc) {
        while (!isQuit && !winningGoldCorrect) {
            System.out.println("With how much gold should a player win?");
            String winningGoldInput = sc.nextLine();
            winningGoldCorrect = isGoldCorrect(winningGoldInput, 1);
            if (winningGoldCorrect) {
                winningGold = Integer.parseInt(winningGoldInput);
            }
        }
    }

    private void askAndRegisterStartingGold(Scanner sc) {
        while (!isQuit && !startingGoldCorrect) {
            System.out.println("With how much gold should each player start?");
            String startingGoldInput = sc.nextLine();
            startingGoldCorrect = isGoldCorrect(startingGoldInput, 0);
            if (startingGoldCorrect) {
                startingGold = Integer.parseInt(startingGoldInput);
            }
        }

    }

    private boolean isGoldCorrect(String input, int minimalValue) {
        isQuit = quitCommandEntered(input);
        int choosenAmount = 0;
        if (!isQuit && isNumberInput(input)) {
            choosenAmount = Integer.parseInt(input);
            if (choosenAmount > minimalValue) {
                return true;
            } else {
                System.out.println(
                        "Error: The choosen gold value is not high enough. It must be at least " + minimalValue);
                return false;
            }
        } else {
            return false;
        }

    }

    private void askAndRegisterPlayerAmount(Scanner sc) {
        while (!playerAmountCorrect && !isQuit) {
            System.out.println("How many players?");
            String playerAmountInput = sc.nextLine();
            playerAmountCorrect = isPlayerAmountCorrect(playerAmountInput);

            if (playerAmountCorrect) {
                playerAmount = Integer.parseInt(playerAmountInput);
            }
        }
    }

    private void askAndRegisterPlayersNames(Scanner sc) {
        while (playerNamesCorrect < playerAmount && !isQuit) {
            System.out.println("Enter the name of player " + (playerNamesCorrect + 1) + ":");
            controllAndSetPlayerNames(sc.nextLine(), playerNamesCorrect);
        }
    }

    /**
     * reads the value of input, if input is a number between 1 - 4 true will be
     * returned
     * 
     * @param input from player. quit command is supported
     * @return returns true if input was a number between 1 and 4
     */

    private boolean isPlayerAmountCorrect(String input) {
        isQuit = quitCommandEntered(input);
        int choosenAmount = 0;
        if (!isQuit && isNumberInput(input)) {
            choosenAmount = Integer.parseInt(input);
            if (isNumberInRange(choosenAmount, 1, 4)) {
                return true;
            } else {
                System.out.println(
                        "Error: the choosen player amount is not supported" + " please try a number between 1-4");
                return false;
            }
        } else {
            return false;
        }

    }

    private void controllAndSetPlayerNames(String input, int playerNumber) {

        boolean nameAllowed = false;

        isQuit = quitCommandEntered(input);

        nameAllowed = isNameAllowed(input);

        if (nameAllowed) {
            playerNames[playerNumber] = input;
            playerNamesCorrect++; // if everything is fine, the correctPlayer-name amount
            // rises
        } else {
            System.out.println("Error: The name must only consist of Letters please try again");
            // if an error occurs, nothing will be added to the correct Player-name amount
        }

    }

    private boolean isNameAllowed(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (!isCharInAlphabet(input.charAt(i))) {
                return false;
            }

        }
        return true;
    }

    private boolean isCharInAlphabet(char c) {
        return (c >= 65 && c <= 90) || (c >= 97 && c <= 122);
    }

    private void printPixelArt() {
        System.out.println("                           _.-^-._    .--.");
        System.out.println("                        .-\'   _   \'-. |__|");
        System.out.println("                       /     |_|     \\|  |");
        System.out.println("                      /               \\  |");
        System.out.println("                     /|     _____     |\\ |");
        System.out.println("                      |    |==|==|    |  |");
        System.out.println("  |---|---|---|---|---|    |--|--|    |  |");
        System.out.println("  |---|---|---|---|---|    |==|==|    |  |");
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        System.out.println("^^^^^^^^^^^^^^^ QUEENS FARMING ^^^^^^^^^^^^^^^");
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        System.out.println("");
    }

    /**
     * @param number the String which will be checked if it is a Number number
     *               anything that is not a number i the range of an int
     * @return true if it is a Number, false if it is not
     */
    private boolean isNumberInput(String number) {
        try {
            Integer.parseInt(number);
            return true;
        }

        catch (NumberFormatException nfe) {
            System.out.println("Error: input cant be read as a Number, "
                    + "please use a Number that is between  -2147483648 and 2147483647");
            return false;
        }

    }

    private boolean isNumberInRange(int number, int lowestPoint, int highestPoint) {
        return (number >= lowestPoint && number <= highestPoint);
    }

    private boolean quitCommandEntered(String command) {
        return command.equals("quit");
    }

}
