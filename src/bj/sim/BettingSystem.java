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
public class BettingSystem {
    
    public static BettingSystem constantBet(int bet) {
        BettingSystem cb = new BettingSystem();
        return cb;
    }
    
    public int getBetSize(int bankroll, Counter counter) {
        return 2;
    }
     
}
