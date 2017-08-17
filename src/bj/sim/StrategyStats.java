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
        return EV[(hand.isPair() ? 2 : (hand.isSoft() ? 1 : 0))][hand.getTotal()][dealer.getValue()];
    }

    StrategyStats(Strategy strategy, CardDistribution distribution, Rules rules) {
        EV = new double[3][23][12];
        var = new double[3][23][12];
        winRate = new double[3][23][12];
        pushRate = new double[3][23][12];
        lossRate = new double[3][23][12];
        double[][][] pDealer = new double[2][23][23]; //what is the probability of a dealer getting a certain total given his current total?

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 22; j++) {
                for (int k = 0; k < 23; k++) {
                    pDealer[i][j][k] = 0;
                }
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 17; j <= 22; j++) {
                pDealer[i][j][j] = 1;
            }
        }
        //pDealer[0] is the table for hard hands, pDealer[1] for soft hands
        for (int i = 16; i >= 11; i--) {
            for (int k = 17; k <= 22; k++) {
                for (int j = 1; j <= 10; j++) {
                    pDealer[0][i][k] += distribution.pCard(new Card(j)) * pDealer[0][Math.min(22, i + j)][k];
                }
            }
        }
        for (int i = 16; i >= 11; i--) {
            for (int k = 17; k <= 22; k++) {
                for (int j = 1; j <= 10; j++) {
                    pDealer[1][i][k] += distribution.pCard(new Card(j)) * pDealer[(i + j < 22 ? 1 : 0)][(i + j < 22 ? i + j : i + j - 10)][k];
                }
            }
        }
        for (int i = 10; i >= 2; i--) {
            for (int k = 17; k <= 22; k++) {
                pDealer[0][i][k] += distribution.pCard(new Card(1)) * pDealer[1][i + 11][k];
                for (int j = 2; j <= 10; j++) {
                    pDealer[0][i][k] += distribution.pCard(new Card(j)) * pDealer[0][i + j][k];
                }
            }
        }
        
        for(int i=0; i<3; i--)
            for(int j=2; j<=11; j++)
                EV[i][22][j] = -1;
        for(int i=0; i<3; i--)
            for(int j=)
        
    }
}
