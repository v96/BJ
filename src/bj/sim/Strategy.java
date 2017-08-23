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
                    ALWAYS_STAND.hitOrStand[i][j][k] = Decision.HIT;
                    NEVER_BUST_18.hitOrStand[i][j][k] = (i == 1 ? (j >= 18 ? Decision.STAND : Decision.HIT) : (j >= 12 ? Decision.STAND : Decision.HIT));
                }
            }
        }
    }

    public static Strategy generateOptimalStrategy(CardDistribution distribution, Rules rules) {
        Strategy optimal = HIT_TO_17;

        for (int i = 22; i >= 4; i--) {
            for (int j = 1; j <= 10; j++) {
                Strategy optimalHit = new Strategy(optimal);
                optimalHit.setHitOrStandDecision(0, i, new Card(j), Decision.HIT);
                double hitEV = (new StrategyStats(optimalHit, distribution, rules)).getTotalEV();
                Strategy optimalStand = new Strategy(optimal);
                optimalStand.setHitOrStandDecision(0, i, new Card(j), Decision.STAND);
                double standEV = (new StrategyStats(optimalStand, distribution, rules)).getTotalEV();

                if (hitEV > standEV) {
                    optimal = optimalHit;
                } else {
                    optimal = optimalStand;
                }
            }
        }
        for (int i = 22; i >= 11; i--) {
            for (int j = 1; j <= 10; j++) {
                Strategy optimalHit = new Strategy(optimal);
                optimalHit.setHitOrStandDecision(1, i, new Card(j), Decision.HIT);
                double hitEV = (new StrategyStats(optimalHit, distribution, rules)).getTotalEV();
                Strategy optimalStand = new Strategy(optimal);
                optimalStand.setHitOrStandDecision(1, i, new Card(j), Decision.STAND);
                double standEV = (new StrategyStats(optimalStand, distribution, rules)).getTotalEV();

                if (hitEV > standEV) {
                    optimal = optimalHit;
                } else {
                    optimal = optimalStand;
                }
            }
        }
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                for (int k = 1; k <= 10; k++) {
                    Decision bestDecision = Decision.STAND;
                    double bestEV = Double.NEGATIVE_INFINITY;
                    for (Decision dec : Decision.values()) {
                        if (i != j && dec.equals(Decision.SPLIT)) {
                            continue;
                        }
                        optimal.setIniHandDecision(new Card(i), new Card(j), new Card(k), dec);
                        StrategyStats stats = new StrategyStats(optimal, distribution, rules);
                        if (stats.getTotalEV() > bestEV) {
                            bestDecision = dec;
                            bestEV = stats.getTotalEV();
                        }
                    }
                    optimal.setIniHandDecision(new Card(i), new Card(j), new Card(k), bestDecision);
                }
            }
        }
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

    public void setHitOrStandDecision(int soft, int total, Card dealersCard, Decision decision) {
        if ((soft == 1) && total < 11 || total < 4 || total > 22 || !(decision.equals(Decision.HIT) || decision.equals(Decision.STAND))) {
            throw new IllegalArgumentException();
        }
        hitOrStand[soft][total][dealersCard.getValue()] = decision;
    }

    public void setIniHandDecision(Card ownCard1, Card ownCard2, Card dealersCard, Decision decision) {
        iniHand[ownCard1.getValue()][ownCard2.getValue()][dealersCard.getValue()] = decision;
    }

    public void printStrategy() {
        System.out.println("Hit or stand for hard totals:");
        for (int i = 22; i >= 4; i--) {
            System.out.print(i + ": ");
            for (int j = 2; j <= 10; j++) {
                System.out.print((hitOrStand[0][i][j] == Decision.HIT ? "H" : "S") + " ");
            }
            System.out.println(hitOrStand[0][i][1] == Decision.HIT ? "H" : "S");
        }
        System.out.println();
        System.out.println("Hit or stand for soft totals:");
        for (int i = 22; i >= 11; i--) {
            System.out.print(i + ": ");
            for (int j = 1; j <= 10; j++) {
                System.out.print((hitOrStand[1][i][j] == Decision.HIT ? "H" : "S") + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Decisions for each hand:");
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                System.out.print(i + " " + j + ": ");
                for (int k = 2; k <= 10; k++) {
                    System.out.print((iniHand[i][j][k] == Decision.HIT ? "H" : iniHand[i][j][k] == Decision.STAND ? "S"
                            : iniHand[i][j][k] == Decision.DOUBLEDOWN ? "Dd" : iniHand[i][j][k] == Decision.SPLIT ? "Sp" : "Sr") + " ");
                }
                System.out.print((iniHand[i][j][1] == Decision.HIT ? "H" : iniHand[i][j][1] == Decision.STAND ? "S"
                            : iniHand[i][j][1] == Decision.DOUBLEDOWN ? "Dd" : iniHand[i][j][1] == Decision.SPLIT ? "Sp" : "Sr") + " ");
                System.out.println();
            }
        }
    }

    Strategy(Strategy base) {
        iniHand = new Decision[11][11][11];
        hitOrStand = new Decision[2][23][11];

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
