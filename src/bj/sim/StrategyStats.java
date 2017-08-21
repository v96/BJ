/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bj.sim;

/**
 *
 * @author Vasil
 */
public class StrategyStats {

    Strategy strategy;
    CardDistribution distribution;
    private double[][][] hitOrStandEV, hitOrStandVar; //indexed by hard, soft, or pair (0, 1, 2 respectively), own total, and dealer's card
    private double[][][] handEV, handVar; //indexed by own card #1, own card #2, and dealer's upcard
    private double totalEV, totalVar;
    private double[][][] pDealer; //what is the probability of a dealer getting a certain total given his current total? 0 = hard, 1 = soft, 22 = bust

    public double getTotalEV() {
        return totalEV;
    }

    public double getTotalVar() {
        return totalVar;
    }

    private double calculatePDealerSingleField(int soft, int curr, int target, CardDistribution distribution) {
        if (soft < 0 || soft > 1 || curr < 2 || curr > 22 || target < 2 || target > 22) {
            throw new IndexOutOfBoundsException();
        }
        if (pDealer[soft][curr][target] != -1) {
            return pDealer[soft][curr][target];
        }
        if (curr >= 17) {
            if (target == curr) {
                pDealer[soft][curr][target] = 1;
                return pDealer[soft][curr][target];
            } else {
                pDealer[soft][curr][target] = 0;
                return pDealer[soft][curr][target];
            }
        }
        if (soft == 0 && curr > target || soft == 1 && curr < 11) {
            pDealer[soft][curr][target] = 0;
            return pDealer[soft][curr][target];
        }
        double pdealer = 0;
        for (int i = 1; i <= 10; i++) {
            Hand newHand = new Hand(new Hand(curr, (soft == 1)), new Card(i));
            pdealer += distribution.pCard(new Card(i)) * calculatePDealerSingleField(newHand.isSoft() ? 1 : 0, newHand.getTotal(), target, distribution);
        }
        pDealer[soft][curr][target] = pdealer;
        return pDealer[soft][curr][target];
    }

    private double[][][] calculatePDealer(CardDistribution distribution) {
        pDealer = new double[2][23][23];

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 23; j++) {
                for (int k = 0; k < 23; k++) {
                    pDealer[i][j][k] = -1;
                }
            }
        }
        for (int j = 2; j <= 22; j++) {
            for (int k = 17; k <= 22; k++) {
                calculatePDealerSingleField(0, j, k, distribution);
            }
        }
        for (int j = 11; j <= 22; j++) {
            for (int k = 17; k <= 22; k++) {
                calculatePDealerSingleField(1, j, k, distribution);
            }
        }

        return pDealer;
    }

    private void calculateHandEV(double[][][] pDealer, CardDistribution distribution) {
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                for (int k = 1; k <= 10; k++) {
                    Hand ownHand = new Hand(new Hand(new Card(i)), new Card(j));
                    Card dealerCard = new Card(k);
                    Hand dealerHand = new Hand(dealerCard);
                    Decision decision = strategy.decide(ownHand, dealerCard);
                    switch (decision) {
                        case STAND:
                            handEV[i][j][k] = 0;
                            for (int m = 17; m <= 22; m++) {
                                if (ownHand.getTotal() == 22 || m > ownHand.getTotal()) {
                                    handEV[i][j][k] += -1.0 * pDealer[dealerHand.isSoft() ? 1 : 0][dealerHand.getTotal()][m];
                                } else if (ownHand.getTotal() > m || m == 22) {
                                    handEV[i][j][k] += 1.0 * pDealer[dealerHand.isSoft() ? 1 : 0][dealerHand.getTotal()][m];
                                }
                            }
                            break;
                        case HIT:
                            handEV[i][j][k] = 0;
                            for (int l = 1; l <= 10; l++) {
                                Card newCard = new Card(l);
                                Hand newHand = new Hand(ownHand, newCard);
                                handEV[i][j][k] += hitOrStandEV[newHand.isSoft() ? 1 : 0][newHand.getTotal()][dealerCard.getValue()] * distribution.pCard(newCard);
                            }
                            break;
                        case DOUBLEDOWN:
                            handEV[i][j][k] = 0;
                            for (int l = 1; l <= 10; l++) {
                                Card newCard = new Card(l);
                                Hand newHand = new Hand(ownHand, newCard);
                                for (int m = 17; m <= 22; m++) {
                                    if (ownHand.getTotal() == 22 || m > ownHand.getTotal()) {
                                        handEV[i][j][k] += -2.0 * distribution.pCard(newCard) * pDealer[dealerHand.isSoft() ? 1 : 0][dealerHand.getTotal()][m];
                                    } else if (ownHand.getTotal() > m || m == 22) {
                                        handEV[i][j][k] += 2.0 * distribution.pCard(newCard) * pDealer[dealerHand.isSoft() ? 1 : 0][dealerHand.getTotal()][m];
                                    }
                                }
                            }
                            break;
                        case SURRENDER:
                            handEV[i][j][k] = -0.5;
                            break;
                        case SPLIT:
                            if (i != j) {
                                throw new IllegalStateException();
                            }
                            handEV[i][j][k] = 0;
                            if (i == 1) { //special case for splitting aces
                                //assuming that resplitting aces is not allowed
                            } else {
                                for (int l = 1; l <= 10; l++) {
                                    for (int n = 1; n <= 10; n++) {
                                        Hand[] newHand = new Hand[2];
                                        Card[] newCard = new Card[2];
                                        newCard[0] = new Card(l);
                                        newCard[1] = new Card(n);
                                        newHand[0] = new Hand(new Hand(new Card(i)), newCard[0]);
                                        newHand[1] = new Hand(new Hand(new Card(j)), newCard[1]);
                                        
                                        handEV[i][j][k] += (hitOrStandEV[newHand[0].isSoft() ? 1 : 0][newHand[0].getTotal()][dealerCard.getValue()] + 
                                                            hitOrStandEV[newHand[1].isSoft() ? 1 : 0][newHand[1].getTotal()][dealerCard.getValue()]) * distribution.pCard(newCard[0]) *
                                                                                                                                                       distribution.pCard(newCard[1]);
                                    }
                                }
                            }
                            break;
                    }
                }
            }
        }
    }

    /*
    private double[][][] calculateEVStand(double[][][] pDealer) {
        double[][][] EVStand = new double[2][23][12];

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 23; j++) {
                for (int k = 0; k < 12; k++) {
                    EVStand[i][j][k] = 0;
                }
            }
        }
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                for (int k = 1; k <= 10; k++) {
                    for (int l = 1; l <= 10; l++) {
                        Hand own = new Hand(new Hand(new Hand(new Card(i)), new Card(j)), new Card(l));
                        Hand dealer = new Hand(new Card(k));
                        if (EVStand[own.isSoft() ? 1 : 0][own.getTotal()][dealer.getTotal()] == 0) {
                            for (int m = 17; m <= 22; m++) {
                                if (own.getTotal() != 22 && (own.getTotal() > m || m == 22)) {
                                    EVStand[own.isSoft() ? 1 : 0][own.getTotal()][dealer.getTotal()] += 1.0 * pDealer[dealer.isSoft() ? 1 : 0][dealer.getTotal()][m];
                                } else if (own.getTotal() == 22 || m > own.getTotal()) {
                                    EVStand[own.isSoft() ? 1 : 0][own.getTotal()][dealer.getTotal()] += -1.0 * pDealer[dealer.isSoft() ? 1 : 0][dealer.getTotal()][m];
                                }
                            }
                        }
                    }
                }
            }
        }

        return EVStand;
    }
     */
    StrategyStats(Strategy strategy, CardDistribution distribution, Rules rules) {
        this.strategy = strategy;
        this.distribution = distribution;

        pDealer = calculatePDealer(distribution);
        calculateHandEV(pDealer, distribution);

        /*
        double[][][] EVStand = calculateEVStand(pDealer);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j <= 11; j++) {
                System.out.print(j + " ");
            }
            System.out.println();
            for (int j = 0; j <= 22; j++) {
                System.out.print(j + " ");
                for (int k = 0; k <= 11; k++) {
                    System.out.print(EVStand[i][j][k] + " ");
                }
                System.out.println();
            }
            System.out.println();
            System.out.println();
        }
         */
    }
}
