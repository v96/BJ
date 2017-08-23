/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bj.sim;

import bj.sim.hands.Hand;

/**
 *
 * @author vasil.kuzevski
 */
public class Rules {

//    public static Rules LE_GRAND;

//    static {
//        LE_GRAND = new Rules();
//        LE_GRAND.hasHoleCard = false;
//        LE_GRAND.dealerStandsOnSoft17 = true;
//        LE_GRAND.doubleAfterSplitAllowed = true;
//        LE_GRAND.resplitAcesAllowed = false;
//        LE_GRAND.earlySurrender = true;
//        LE_GRAND.insuranceOffered = true;
//        LE_GRAND.blackjackPayout = 1.5;
//        LE_GRAND.numberOfDecks = 6;
//        LE_GRAND.shufflePoint = 215;
//        LE_GRAND.numberOfBurnedCards = 5;
//    }
//    
//    public final boolean hasHoleCard;
//    public final boolean dealerStandsOnSoft17;
//    public final boolean doubleAfterSplitAllowed;
//    public final boolean resplitAcesAllowed;
//    public final boolean earlySurrender;
//    public final boolean insuranceOffered;
//    public final double blackjackPayout;
//    public final int numberOfDecks;
//    public final int shufflePoint;
//    public final int numberOfBurnedCards;
//
//    public boolean dealerShouldDraw(Hand hand) {
//        return hand.getTotal() < 17 || (hand.getTotal() == 17 && hand.isSoft() && !dealerStandsOnSoft17);
//    }
    public final boolean dealerStandsOnSoft17;
    public final double blackjackPayout;
    Rules() {
        dealerStandsOnSoft17 = true;
        blackjackPayout = 1.5;
    }
}
