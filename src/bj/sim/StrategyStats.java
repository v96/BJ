/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bj.sim;

import java.text.DecimalFormat;

/**
 *
 * @author Vasil
 */
public class StrategyStats {
    
    Strategy strategy;
    CardDistribution distribution;
    private double[][] standEV, standVar; //indexed by own total and dealer's upcard
    private double[][][] hitOrStandEV, hitOrStandVar; //indexed by hard or soft (0 and 1 resp.), own total, and dealer's card
    private double[][][] handEV, handVar; //indexed by own card #1, own card #2, and dealer's upcard
    private double totalEV, totalVar;
    private double[][][] pDealer; //what is the probability of a dealer getting a certain total given his current total? 0 = hard, 1 = soft, 22 = bust

    public double getTotalEV() {
        return totalEV;
    }

    public double getTotalVar() {
        return totalVar;
    }

    private double calculatePDealerSingleField(int soft, int curr, int target) {
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
        double pd = 0;
        for (int i = 1; i <= 10; i++) {
            Hand_2 newHand = new Hand_2(new Hand_2(curr, (soft == 1)), new Card(i));
            pd += distribution.pCard(new Card(i)) * calculatePDealerSingleField(newHand.isSoft() ? 1 : 0, newHand.getTotal(), target);
        }
        pDealer[soft][curr][target] = pd;
        return pDealer[soft][curr][target];
    }

    private void calculatePDealer() {
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
                calculatePDealerSingleField(0, j, k);
            }
        }
        for (int j = 11; j <= 22; j++) {
            for (int k = 17; k <= 22; k++) {
                calculatePDealerSingleField(1, j, k);
            }
        }
    }

    private void calculateStandEV() {
        standEV = new double[23][11];

        for (int i = 4; i <= 22; i++) {
            for (int j = 1; j <= 10; j++) {
                Hand_2 dealerHand = new Hand_2(new Card(j));
                standEV[i][j] = 0;
                for (int m = 17; m <= 22; m++) {
                    if (i == 22 || (m > i && m < 22)) {
                        standEV[i][j] += -1.0 * pDealer[dealerHand.isSoft() ? 1 : 0][dealerHand.getTotal()][m];
                    } else if (i > m || m == 22) {
                        standEV[i][j] += 1.0 * pDealer[dealerHand.isSoft() ? 1 : 0][dealerHand.getTotal()][m];
                    }
                }
            }
        }
    }

    private double calculateHitOrStandEVSingleField(int soft, int total, Card dealersCard) {
        if (hitOrStandEV[soft][total][dealersCard.getValue()] != 0) {
            return hitOrStandEV[soft][total][dealersCard.getValue()];
        }
        if(total == 22) {
            hitOrStandEV[soft][total][dealersCard.getValue()] = -1;
            return hitOrStandEV[soft][total][dealersCard.getValue()];
        }
        switch (strategy.decideHitOrStand(new Hand_2(total, (soft == 1)), dealersCard)) {
            case HIT:
                for (int i = 1; i <= 10; i++) {
                    Card newCard = new Card(i);
                    Hand_2 newHand = new Hand_2(new Hand_2(total, (soft == 1)), newCard);
                    hitOrStandEV[soft][total][dealersCard.getValue()] += calculateHitOrStandEVSingleField(newHand.isSoft() ? 1 : 0, newHand.getTotal(), dealersCard) * distribution.pCard(newCard);
                }
                break;
            case STAND:
                hitOrStandEV[soft][total][dealersCard.getValue()] = standEV[total][dealersCard.getValue()];
                break;
            default:
                throw new IllegalStateException();
        }
        return hitOrStandEV[soft][total][dealersCard.getValue()];
    }

    private void calculateHitOrStandEV() {
        hitOrStandEV = new double[2][23][11];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 23; j++) {
                for (int k = 0; k < 11; k++) {
                    hitOrStandEV[i][j][k] = 0;
                }
            }
        }

        for (int i = 4; i <= 22; i++) { //hard hands
            for (int j = 1; j <= 10; j++) {
                calculateHitOrStandEVSingleField(0, i, new Card(j));
            }
        }
        for (int i = 11; i <= 21; i++) { //soft hands
            for (int j = 1; j <= 10; j++) {
                calculateHitOrStandEVSingleField(1, i, new Card(j));
            }
        }
    }

    private void calculateHandEV() {
        handEV = new double[11][11][11];

        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                for (int k = 1; k <= 10; k++) {
                    Hand_2 ownHand = new Hand_2(new Hand_2(new Card(i)), new Card(j));
                    Card dealerCard = new Card(k);
                    switch (strategy.decideInitialHand(new Card(i), new Card(j), dealerCard)) {
                        case STAND:
                            handEV[i][j][k] = standEV[ownHand.getTotal()][k];
                            break;
                        case HIT:
                            handEV[i][j][k] = 0;
                            for (int l = 1; l <= 10; l++) {
                                Card newCard = new Card(l);
                                Hand_2 newHand = new Hand_2(ownHand, newCard);
                                handEV[i][j][k] += hitOrStandEV[newHand.isSoft() ? 1 : 0][newHand.getTotal()][dealerCard.getValue()] * distribution.pCard(newCard);
                            }
                            break;
                        case DOUBLEDOWN:
                            handEV[i][j][k] = 0;
                            for (int l = 1; l <= 10; l++) {
                                Card newCard = new Card(l);
                                Hand_2 newHand = new Hand_2(ownHand, newCard);
                                handEV[i][j][k] += 2 * standEV[newHand.getTotal()][dealerCard.getValue()] * distribution.pCard(newCard);
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
                                for (int l = 1; l <= 10; l++) {
                                    for (int n = 1; n <= 10; n++) {
                                        Hand_2[] newHand = new Hand_2[2];
                                        Card[] newCard = new Card[2];
                                        newCard[0] = new Card(l);
                                        newCard[1] = new Card(n);
                                        newHand[0] = new Hand_2(new Hand_2(new Card(i)), newCard[0]);
                                        newHand[1] = new Hand_2(new Hand_2(new Card(j)), newCard[1]);

                                        handEV[i][j][k] += (standEV[newHand[0].getTotal()][dealerCard.getValue()]
                                                + standEV[newHand[1].getTotal()][dealerCard.getValue()]) * distribution.pCard(newCard[0])
                                                * distribution.pCard(newCard[1]);
                                    }
                                }
                            } else {
                                for (int l = 1; l <= 10; l++) {
                                    for (int n = 1; n <= 10; n++) {
                                        Hand_2[] newHand = new Hand_2[2];
                                        Card[] newCard = new Card[2];
                                        newCard[0] = new Card(l);
                                        newCard[1] = new Card(n);
                                        newHand[0] = new Hand_2(new Hand_2(new Card(i)), newCard[0]);
                                        newHand[1] = new Hand_2(new Hand_2(new Card(j)), newCard[1]);

                                        handEV[i][j][k] += (hitOrStandEV[newHand[0].isSoft() ? 1 : 0][newHand[0].getTotal()][dealerCard.getValue()]
                                                + hitOrStandEV[newHand[1].isSoft() ? 1 : 0][newHand[1].getTotal()][dealerCard.getValue()]) * distribution.pCard(newCard[0])
                                                * distribution.pCard(newCard[1]);
                                    }
                                }
                            }
                            break;
                    }
                }
            }
        }
    }
    
    private void calculateTotalEV() {
        totalEV = 0;
        for(int i=1; i<=10; i++) {
            for(int j=1; j<=10; j++) {
                for(int k=1; k<=10; k++) {
                    totalEV += handEV[i][j][k] * distribution.pCard(new Card(i)) * distribution.pCard(new Card(j)) * distribution.pCard(new Card(k));
                }
            }
        }
    }
    
    private void calculateTotalEV() {
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                for (int k = 1; k <= 10; k++) {
                    totalEV += handEV[i][j][k] * distribution.pCard(new Card(i)) * distribution.pCard(new Card(j)) * distribution.pCard(new Card(k));
                }
            }
        }
    }

    StrategyStats(Strategy strategy, CardDistribution distribution, Rules rules) {
        this.strategy = strategy;
        this.distribution = distribution;

        calculatePDealer();
        calculateStandEV();
        calculateHitOrStandEV();
        calculateHandEV();
        calculateTotalEV();

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(3);
        /*
        System.out.print("     ");
        for (int i = 1; i <= 10; i++) {
            System.out.print(i + "      ");
        }
        System.out.println();
        for (int j = 1; j <= 10; j++) {
            for (int k = 1; k <= 10; k++) {
                System.out.print(j + " " + k + ": ");
                for(int i=1; i<=10; i++) {
                    System.out.print(df.format(handEV[j][k][i]) + " ");
                }
                System.out.println();
            }
        }
        
        System.out.println();
        System.out.println();
        */
        /*
        for(int i=4; i<=22; i++) {
            for(int j=1; j<=10; j++) {
                System.out.print(df.format(standEV[i][j]) + " ");
            }
            System.out.println();
        }
        */
        /*
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
