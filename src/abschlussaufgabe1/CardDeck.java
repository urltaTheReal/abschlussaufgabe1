package abschlussaufgabe1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import plantSpaces.*;

/**
 * @author urlta
 * 
 *         this class is responsible for generating and managing the deck of
 *         Cards used to buy land
 *
 */
public class CardDeck {
    private ArrayList<PlantSpace> cards = new ArrayList<>();

    /**
     * Constructor fills the deck with cards and shuffles it, as described in the
     * game description
     * 
     * @param random       the random object initialized with seed at the beginning
     *                     of the game
     * @param playerAmount the amount of players needed to determine how many cards
     *                     are needed
     */
    public CardDeck(Random random, int playerAmount) {
        addPlantSpace(new Garden(), 2 * playerAmount);
        addPlantSpace(new Field(), 3 * playerAmount);
        addPlantSpace(new LargeField(), 2 * playerAmount);
        addPlantSpace(new Forest(), 2 * playerAmount);
        addPlantSpace(new LargeForest(), playerAmount);
        Collections.shuffle(cards, random);
    }

    /**
     * @return first card of the deck
     */
    public PlantSpace getTopCard() {
        return cards.get(0);
    }

    /**
     * deletes first card of the deck
     */
    public void removeTopCardFromDeck() {
        cards.remove(0);
    }

    /**
     * @return returns the cards left in the deck
     */
    public int getCardsLeftInDeck() {
        return cards.size();
    }

    private void addPlantSpace(PlantSpace cardToAdd, int amountToAdd) {
        for (int i = 0; i < amountToAdd; i++) {
            PlantSpace newCard = new PlantSpace(cardToAdd);
            cards.add(newCard);
        }
    }

}
