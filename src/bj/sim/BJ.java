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
        System.out.println();
        Hand hand = new Hand(5, false);
        
        StrategyStats strsts = new StrategyStats(Strategy.HIT_TO_17, CardDistribution.INFINITE_DECK, Rules.LE_GRAND);
    }

}
