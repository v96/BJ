/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bj.sim;

import java.util.Scanner;

/**
 *
 * @author vasil.kuzevski
 */
public class BJ {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        StrategyStats hit17 = new StrategyStats(Strategy.HIT_TO_17, CardDistribution.INFINITE_DECK, Rules.LE_GRAND);
        StrategyStats alwaysStand = new StrategyStats(Strategy.ALWAYS_STAND, CardDistribution.INFINITE_DECK, Rules.LE_GRAND);
        StrategyStats alwaysDD = new StrategyStats(Strategy.ALWAYS_DOUBLEDOWN, CardDistribution.INFINITE_DECK, Rules.LE_GRAND);
        StrategyStats neverBust18 = new StrategyStats(Strategy.NEVER_BUST_18, CardDistribution.INFINITE_DECK, Rules.LE_GRAND);
        
        Strategy optimal = Strategy.generateOptimalStrategy(CardDistribution.INFINITE_DECK, Rules.LE_GRAND);
        optimal.printStrategy();
        
        System.out.println(hit17.getTotalEV());
        System.out.println(alwaysStand.getTotalEV());
        System.out.println(alwaysDD.getTotalEV());
        System.out.println(neverBust18.getTotalEV());
    }

}
