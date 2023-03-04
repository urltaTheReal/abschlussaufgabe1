package abschlussaufgabe1;

import Barn.Barn;
import Board.Board;

/**
 * @author urlta
 * 
 *         this class is responsible for managing one individual player
 */
public class Player {
    private final String playerName;
    private Barn barn;
    private Board playerBoard;

    public Player(String playerName, int startingGold) {
        this.playerName = playerName;
        barn = new Barn(startingGold);
        playerBoard = new Board();
    }

    /**
     * end round executes all actions to be taken for a player when a round ends
     */
    public void endRound() {
        barn.endRound();
        playerBoard.endRound();
    }

    /**
     * startRound is responsible for printing the Players Message when their turn
     * starts
     */
    public void startRound() {
        System.out.println("");
        System.out.println("It is " + playerName + "'s turn!");
        playerBoard.startRound();
        barn.startRound();
    }

    /**
     * @return Name of player
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * @return barn of the player
     */
    public Barn getBarn() {
        return barn;
    }

    /**
     * replaces barn with new barn
     * @param barn barn with updated values to replace the players barn
     */
    public void setBarn(Barn barn) {
        this.barn = barn;
    }

    /**
     * @return board of the player
     */
    public Board getPlayerBoard() {
        return playerBoard;
    }

    /**
     * replaces board with new board
     * @param playerBoard board with updated values to replace the players board
     */
    public void setPlayerBoard(Board playerBoard) {
        this.playerBoard = playerBoard;
    }

}
