package abschlussaufgabe1;

/**
 * @author urlta
 * 
 *         This class is responsible for the calculation and printing of the
 *         final score of the game
 *
 */
public class PrintEndMessage {

    Player[] players;
    int goldToWin;

    /**
     * players and gold to win is needed for the calculation who is to be printed,
     * and which player(s) won
     * 
     * @param players all players who participated in the bame
     * @param game    The starting values of the finished game
     */
    public PrintEndMessage(Player[] players, GameValues game) {
        this.players = players;
        goldToWin = game.getWinningGold();
    }

    private boolean playerWonByWinningGold(Player player) {
        return player.getBarn().getGoldAmount() >= goldToWin;
    }

    private boolean wasWinningGoldReached() {
        for (int i = 0; i < players.length; i++) {
            if (playerWonByWinningGold(players[i])) {
                return true;
            }
        }
        return false;
    }

    private String addPlayerToWinningMessage(String playerName, int amountLeft, String currentLine) {
        if (currentLine.length() == 0) {
            return playerName;
        }
        if (amountLeft == 1) {
            currentLine += " and " + playerName;
        } else {
            currentLine += ", " + playerName;
        }
        return currentLine;
    }

    private String calculateWinningPlayersByWinningGold() {
        String winners = "";
        int winnerAmount = 0;
        for (int i = 0; i < players.length; i++) {
            if (playerWonByWinningGold(players[i])) {
                winnerAmount++;
            }

        }
        for (int i = 0; i < players.length; i++) {
            if (playerWonByWinningGold(players[i])) {
                winners = addPlayerToWinningMessage(players[i].getPlayerName(), winnerAmount, winners);
            }
        }
        return winners;
    }

    private int calculateWinningByDefaultPlayerAmount() {
        int winnerAmount = 1;
        Player winningPlayer = players[0];
        for (int i = 1; i < players.length; i++) {
            if (players[i].getBarn().getGoldAmount() > winningPlayer.getBarn().getGoldAmount()) {
                winnerAmount = 1;
            } else if (players[i].getBarn().getGoldAmount() == winningPlayer.getBarn().getGoldAmount()) {
                winnerAmount++;
            }
        }
        return winnerAmount;
    }

    private int calculateGoldAmountToWinByDefault() {
        int goldAmount = 0;
        for (int i = 0; i < players.length; i++) {
            if (players[i].getBarn().getGoldAmount() > goldAmount) {
                goldAmount = players[i].getBarn().getGoldAmount();
            }
        }
        return goldAmount;
    }

    private String calculateWinningPlayersByDefault() {
        int winnerAmount = calculateWinningByDefaultPlayerAmount();
        int winningGoldAmount = calculateGoldAmountToWinByDefault();
        String winners = "";

        for (int i = 0; i < players.length; i++) {
            if (players[i].getBarn().getGoldAmount() == winningGoldAmount) {
                winners = addPlayerToWinningMessage(players[i].getPlayerName(), winnerAmount, winners);
                winnerAmount--;
            }

        }
        return winners;
    }

    /**
     * this class is the one that prints the resulting score of the game
     */
    public void endGame() {
        String winners = "";
        for (int i = 0; i < players.length; i++) {
            System.out.println(
                    "Player " + i + " (" + players[i].getPlayerName() + "): " + players[i].getBarn().getGoldAmount());
        }
        if (wasWinningGoldReached()) {
            winners = calculateWinningPlayersByWinningGold();
        } else {
            winners = calculateWinningPlayersByDefault();
        }
        if (winners.split(" ").length == 1) {
            System.out.println(winners + " has won!");
        } else {
            System.out.println(winners + " have won!");
        }
    }

}
