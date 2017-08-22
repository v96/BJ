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
public class BJ {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        
        StrategyStats strsts = new StrategyStats(Strategy.ALWAYS_STAND, CardDistribution.INFINITE_DECK, Rules.LE_GRAND);
        System.out.println(strsts.getTotalEV());
        
    }

}
