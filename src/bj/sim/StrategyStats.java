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

    private double[][][] EV, var, winRate, pushRate, lossRate; //indexed by hard, soft, or pair (0, 1, 2 respectively), own total, and dealer's total
    private double totalEV, totalVar;

    public double getTotalEV() {
        return totalEV;
    }

    public double getTotalVar() {
        return totalVar;
    }

    public double getHandEV(Hand hand, Card dealer) {
        return 0;//EV
    }

    private double calculatePDealerSingleField(double[][][] pDealer, int soft, int curr, int target, CardDistribution distribution) {
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
            pdealer += distribution.pCard(new Card(i)) * calculatePDealerSingleField(pDealer, newHand.isSoft() ? 1 : 0, newHand.getTotal(), target, distribution);
        }
        pDealer[soft][curr][target] = pdealer;
        return pDealer[soft][curr][target];
    }

    private double[][][] calculatePDealer(CardDistribution distribution) {
        double[][][] pDealer = new double[2][23][23]; //what is the probability of a dealer getting a certain total given his current total? 0 = hard, 1 = soft, 22 = bust

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 23; j++) {
                for (int k = 0; k < 23; k++) {
                    pDealer[i][j][k] = -1;
                }
            }
        }
        for (int j = 2; j <= 22; j++) {
            for (int k = 17; k <= 22; k++) {
                calculatePDealerSingleField(pDealer, 0, j, k, distribution);
            }
        }
        for (int j = 11; j <= 22; j++) {
            for (int k = 17; k <= 22; k++) {
                calculatePDealerSingleField(pDealer, 1, j, k, distribution);
            }
        }

        return pDealer;
    }

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
    
    StrategyStats(Strategy strategy, CardDistribution distribution, Rules rules) {
        EV = new double[3][23][12];
        var = new double[3][23][12];

        double[][][] EVHit = new double[3][23][12];
        double[][][] EVDoubledown = new double[3][23][12];

        double[][][] pDealer = calculatePDealer(distribution);
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
    }
}
