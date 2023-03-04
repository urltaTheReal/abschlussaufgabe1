package commandProcessing;

import abschlussaufgabe1.GameValues;
import abschlussaufgabe1.Market;
import abschlussaufgabe1.Player;

/**
 * @author urlta
 * 
 *         This class is responsible for reading the command entered and
 *         executing it
 *
 */
public class ProcessCommands implements Processing {
    private GameValues game;
    private Player player;
    private Market market;
    private final String[] command;
    private boolean turnEnded = false;
    private boolean isShowCommand = false;
    private final String commandString;

    /**
     * @param game    game values entered at the start of the game, used for quit
     *                command
     * @param player  player who entered the command
     * @param market  current state of the market
     * @param command undivided string of player input to be processed
     */
    public ProcessCommands(GameValues game, Player player, Market market, String command) {
        this.game = game;
        this.player = player;
        this.market = market;
        this.command = command.split(" ");
        this.commandString = command;
    }

    public boolean isTurnEnded() {
        return turnEnded;
    }

    public GameValues getGame() {
        return game;
    }

    public Player getPlayer() {
        return player;
    }

    public Market getMarket() {
        return market;
    }

    public boolean isShowCommand() {
        return isShowCommand;
    }

    /**
     * executeCommand can only be successfully executed, if isCommandCorrect returns
     * true for the same input
     *
     */
    public void executeCommand() {
        switch (command[0]) {
        case "quit":
            game.setQuit(true);
            break;
        case "show":
            ProcessShowCommand show = new ProcessShowCommand(player, market, command);
            show.executeCommand();
            isShowCommand = true;
            break;

        case "buy":
            ProcessBuyCommand buy = new ProcessBuyCommand(player, market, command);
            buy.executeCommand();
            this.player = buy.getPlayer();
            this.market = buy.getMarket();
            break;

        case "sell":
            ProcessSellCommand sell = new ProcessSellCommand(player, market, command);
            sell.executeCommand();
            this.player = sell.getPlayer();
            this.market = sell.getMarket();
            break;

        case "harvest":
            ProcessHarvestCommand harvest = new ProcessHarvestCommand(player, command);
            harvest.executeCommand();
            this.player = harvest.getPlayer();
            break;

        case "plant":
            ProcessPlantCommand plant = new ProcessPlantCommand(player, command);
            plant.executeCommand();
            this.player = plant.getPlayer();
            break;

        case "end":
            ProcessEndCommand end = new ProcessEndCommand(command);
            end.executeCommand();
            turnEnded = true;
            break;

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
        if (isEndedWithSpace(commandString)) {
            System.out.println(
                    "Error: Command can´t be ended with a space, please remove spaces at the end or add arguments");
            return false;
        }
        switch (command[0]) {
        case "quit":
            return true;
        case "show":
            ProcessShowCommand show = new ProcessShowCommand(player, market, command);
            return show.isCommandCorrect();

        case "buy":
            ProcessBuyCommand buy = new ProcessBuyCommand(player, market, command);
            return buy.isCommandCorrect();

        case "sell":
            ProcessSellCommand sell = new ProcessSellCommand(player, market, command);
            return sell.isCommandCorrect();
        case "harvest":
            ProcessHarvestCommand harvest = new ProcessHarvestCommand(player, command);
            return harvest.isCommandCorrect();
        case "plant":
            ProcessPlantCommand plant = new ProcessPlantCommand(player, command);
            return plant.isCommandCorrect();
        case "end":
            ProcessEndCommand end = new ProcessEndCommand(command);
            return end.isCommandCorrect();
        default:
            System.out.println(
                    "Error: unknown command entered, please enter one of the commands and seperate the arguments with spaces");
            return false;
        }
    }

    private boolean isEndedWithSpace(String command) {
        try {
            return command.charAt(command.length() - 1) == ' ';
        } catch (StringIndexOutOfBoundsException sioobe) {
            return false;
        }
    }

}
