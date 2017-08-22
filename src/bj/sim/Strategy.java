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

    public enum Decision {
        HIT,
        STAND,
        DOUBLEDOWN,
        SPLIT,
        SURRENDER
    }

    public static Strategy HIT_TO_17;
    public static Strategy ALWAYS_DOUBLEDOWN;
    public static Strategy ALWAYS_STAND;
    public static Strategy NEVER_BUST_18;
    public static Strategy DRUNK_MONKEY;

    static {
        HIT_TO_17 = new Strategy();
        ALWAYS_DOUBLEDOWN = new Strategy();
        ALWAYS_STAND = new Strategy();
        NEVER_BUST_18 = new Strategy();
        DRUNK_MONKEY = new Strategy(); //to implement

        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                for (int k = 1; k <= 10; k++) {
                    Hand ownHand = new Hand(new Hand(new Card(i)), new Card(j));
                    HIT_TO_17.iniHand[i][j][k] = ownHand.getTotal() >= 17 ? Decision.STAND : Decision.HIT;
                    ALWAYS_DOUBLEDOWN.iniHand[i][j][k] = Decision.DOUBLEDOWN;
                    ALWAYS_STAND.iniHand[i][j][k] = Decision.STAND;
                    NEVER_BUST_18.iniHand[i][j][k] = (ownHand.isSoft() ? (ownHand.getTotal() >= 18 ? Decision.STAND : Decision.HIT) : (ownHand.getTotal() >= 12 ? Decision.STAND : Decision.HIT));
                }
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 4; j <= 22; j++) {
                for (int k = 1; k <= 10; k++) {
                    HIT_TO_17.hitOrStand[i][j][k] = (j >= 17 ? Decision.STAND : Decision.HIT);
                    ALWAYS_DOUBLEDOWN.hitOrStand[i][j][k] = Decision.STAND;
                    ALWAYS_STAND.hitOrStand[i][j][k] = Decision.STAND;
                    NEVER_BUST_18.hitOrStand[i][j][k] = (i == 1 ? (j >= 18 ? Decision.STAND : Decision.HIT) : (j >= 12 ? Decision.STAND : Decision.HIT));
                }
            }
        }
    }

    public static Strategy generateOptimalStrategy(CardDistribution distribution, Rules rules) {
        Strategy optimal = new Strategy();
        return optimal;
    }

    private Decision[][][] hitOrStand; //indexed by hard or soft (0, 1 resp.), own total, and dealer's card value 
    private Decision[][][] iniHand; //indexed  by own card #1, own card #2, and dealer's card

    public Decision decideInitialHand(Card first, Card second, Card dealersCard) {
        return iniHand[first.getValue()][second.getValue()][dealersCard.getValue()];
    }

    public Decision decideHitOrStand(Hand ownHand, Card dealersCard) {
        return hitOrStand[ownHand.isSoft() ? 1 : 0][ownHand.getTotal()][dealersCard.getValue()];
    }

    Strategy(Strategy base) {
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                for (int k = 1; k <= 10; k++) {
                    iniHand[i][j][k] = base.iniHand[i][j][k];
                }
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 4; j <= 22; j++) {
                for (int k = 1; k <= 10; k++) {
                    hitOrStand[i][j][k] = base.hitOrStand[i][j][k];
                }
            }
        }
    }

    Strategy() {
        iniHand = new Decision[11][11][11];
        hitOrStand = new Decision[2][23][11];
    }
}
