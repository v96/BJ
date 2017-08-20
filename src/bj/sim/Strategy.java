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
public class Strategy {

    public static Strategy HIT_TO_17;
    public static Strategy ALWAYS_DOUBLEDOWN;
    public static Strategy ALWAYS_STAND;
    public static Strategy NEVER_BUST;
    public static Strategy DRUNK_MONKEY;

    static {
        HIT_TO_17 = new Strategy();
        ALWAYS_DOUBLEDOWN = new Strategy();
        ALWAYS_STAND = new Strategy();
        NEVER_BUST = new Strategy();
        DRUNK_MONKEY = new Strategy();
        
        for (int i = 0; i < 3; i++) {
            for (int j = 4; j <= 21; j++) {
                for (int k = 1; k <= 10; k++) {
                    HIT_TO_17.lookup[i][j][k] = (j < 17 ? Decision.HIT : Decision.STAND);
                    ALWAYS_DOUBLEDOWN.lookup[i][j][k] = Decision.DOUBLEDOWN;
                    ALWAYS_STAND.lookup[i][j][k] = Decision.STAND;
                    NEVER_BUST.lookup[i][j][k] = (j <= 11 ? Decision.HIT : Decision.STAND);
                }
            }
        }        
    }
    
    public static Strategy generateOptimalStrategy(CardDistribution distribution, Rules rules) {
        Strategy optimal = new Strategy();
        return optimal;
    }
    
    private Decision[][][] lookup; //indexed by hard or soft or pair (0, 1, and 2 respectively), own total, and dealer's card value 

    public void setDecision(Hand hand, Card dealersCard, Decision decision) {
        if (hand.isPair()) {
            lookup[2][hand.getTotal()][dealersCard.getValue()] = decision;
        } else {
            lookup[(hand.isSoft() ? 1 : 0)][hand.getTotal()][dealersCard.getValue()] = decision;
        }
    }

    public Decision decide(Hand ownHand, Card dealersCard) {
        if (ownHand.isBlackjack() || ownHand.isBusted()) {
            throw new UnsupportedOperationException();
        }
        if (ownHand.isPair()) {
            return lookup[2][ownHand.getTotal()][dealersCard.getValue()];
        } else {
            return lookup[(ownHand.isSoft() ? 1 : 0)][ownHand.getTotal()][dealersCard.getValue()];
        }
    }

    Strategy(Strategy base) {
        for (int i = 0; i < 3; i++) {
            for (int j = 3; j <= 21; j++) {
                for (int k = 1; k <= 10; k++) {
                    lookup[i][j][k] = base.lookup[i][j][k];
                }
            }
        }
    }
    
    Strategy() {
        lookup = new Decision[3][22][11];
    }
}
