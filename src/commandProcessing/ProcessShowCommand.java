package commandProcessing;

import abschlussaufgabe1.Market;
import abschlussaufgabe1.Player;

/**
 * @author urlta
 * 
 * this class implements and executes the show command
 *
 */
public class ProcessShowCommand implements Processing {
    private Player player;
    private Market market;
    private String[] command;

    /**
     * @param player  player who entered the command
     * @param market  current state of the market
     * @param command the full line of text entered in the console split after each
     *                space
     */
    public ProcessShowCommand(Player player, Market market, String[] command) {
        this.player = player;
        this.market = market;
        this.command = command;
    }

    /**
     * executeCommand can only be successfully executed, if isCommandCorrect returns
     * true for the same input
     *
     */
    public void executeCommand() {

        if (command[1].equals("barn")) {
            player.getBarn().show();
        } else if (command[1].equals("board")) {
            player.getPlayerBoard().showBoard(player.getBarn());
        } else {
            market.show();
        }

    }

    /**
     * isCommandCorrect checks the entered command for semantical and logical
     * Correctness. returns true if the command was entered correctly and works
     * logically
     * 
     * @return returns true if execute command can be executed without error
     */
    public boolean isCommandCorrect() {
        if (!isCommandSemanticallyCorrect()) {
            return false;
        }

        if (!isCommandLogicallyCorrect()) {
            return false;
        }
        return true;
    }

    private boolean isCommandRightSice() {
        if (command.length > 2) {
            System.out.println("Error: command too long, please only add the thing you want to see as Argument and please seperate with only 1 space");
            return false;
        }
        if (command.length < 2) {
            System.out.println(
                    "Error: missing argument after the show command. Please choose between: board, market or barn");
            return false;
        }
        return true;
    }

    private boolean isCommandSemanticallyCorrect() {
        if (!isCommandRightSice()) {
            return false;
        }
        return true;
    }

    private boolean isCommandLogicallyCorrect() {
        if (!command[1].equals("barn") && !command[1].equals("board") && !command[1].equals("market")) {
            System.out.println(
                    "Error: argument after show is not supported. Please choose between: board, market or barn");
            return false;
        }
        return true;
    }

}
