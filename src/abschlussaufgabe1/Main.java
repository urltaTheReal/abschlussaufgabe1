package abschlussaufgabe1;

import java.util.Random;
import java.util.Scanner;

import commandProcessing.ProcessCommands;

public class Main {
    /**
     * 
     * main method is responsible for running the game
     * @param args command line arguments
     */
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random random;
        Market market;
        Player[] player;
        int currentTurn = 0;
        boolean wasQuitDuringInizialisation = false;

        GameValues game = new GameValues(sc, commandArgsEmpty(args));
        random = new Random(game.getSeed());
        market = new Market(random, game.getPlayerAmount());
        player = new Player[game.getPlayerAmount()];
        player = initializePlayers(player, game);
        if (game.isQuit()) {
            wasQuitDuringInizialisation = true;
        }
        while (!game.isQuit()) {
            player[currentTurn].startRound();
            boolean turnEnded = false;
            int commandsDone = 0;
            while (!game.isQuit() && commandsDone < 2 && !turnEnded) {
                String currentCommand = sc.nextLine();
                ProcessCommands processer = new ProcessCommands(game, player[currentTurn], market, currentCommand);
                if (processer.isCommandCorrect()) {
                    processer.executeCommand();
                    game = processer.getGame();
                    market = processer.getMarket();
                    player[currentTurn] = processer.getPlayer();
                    turnEnded = processer.isTurnEnded();
                    if(!processer.isShowCommand()) {
                    commandsDone++;
                    }
                }
            }
            market.endTurn();
            currentTurn++;
            if (currentTurn == player.length) {
                for (int i = 0; i < player.length; i++) {
                    player[i].endRound();
                    if (player[i].getBarn().getGoldAmount() >= game.getWinningGold()) {
                        game.setQuit(true);
                    }
                }
                currentTurn = 0;
            }
        }
        if (!wasQuitDuringInizialisation) {
            PrintEndMessage gameOver = new PrintEndMessage(player, game);
            gameOver.endGame();
        }
    }
    
    private static boolean commandArgsEmpty(String[] args) {
        if (args.length != 0) {
            System.out.println("Error: Command line is not Empty, please restart without arguments");
            return true;
        } else {
            return false;
        }
    }

    private static Player[] initializePlayers(Player[] players, GameValues game) {
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(game.getPlayerNames()[i], game.getStartingGold());
        }
        return players;
    }


}
