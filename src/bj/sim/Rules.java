/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bj.sim;

/**
 *
 * @author vasil.kuzevski
 */
public class Rules {

    public static Rules LE_GRAND;

    static {
        LE_GRAND = new Rules();
        LE_GRAND.hasHoleCard = false;
        LE_GRAND.dealerStandsOnSoft17 = true;
        LE_GRAND.doubleAfterSplitAllowed = true;
        LE_GRAND.resplitAcesAllowed = false;
        LE_GRAND.earlySurrender = true;
        LE_GRAND.maxSplitHands = 4;
        LE_GRAND.blackjackPayout = 1.5;
        LE_GRAND.numberOfDecks = 6;
        LE_GRAND.shufflePoint = 215;
        LE_GRAND.numberOfBurnedCards = 5;
    }
    
    private boolean hasHoleCard;
    private boolean dealerStandsOnSoft17;
    private boolean doubleAfterSplitAllowed;
    private boolean resplitAcesAllowed;
    private boolean earlySurrender;
    private boolean insurance;
    private int maxSplitHands;
    private double blackjackPayout;
    private int numberOfDecks;
    private int shufflePoint;
    private int numberOfBurnedCards;

    public boolean dealerShouldDraw(Hand hand) {
        return hand.getTotal() < 17 || (hand.getTotal() == 17 && hand.isSoft() && !dealerStandsOnSoft17);
    }
    
    public double blackjackPayout() {
        return blackjackPayout;
    }
    
    public int numberOfDecks() {
        return numberOfDecks;
    }

    public int getShufflePoint() {
        return shufflePoint;
    }

    public int getNumberOfBurnedCards() {
        return numberOfBurnedCards;
    }
}
