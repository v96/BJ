/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bj.sim.hands;

import bj.sim.Rules;

/**
 *
 * @author vasil.kuzevski
 */
public class PlayerBlackjack extends PlayerFinalHand {
    
    public double compare(DealerFinalHand dealer) {
        if(dealer instanceof DealerBlackjack)
            return 0;
        return getRules().blackjackPayout;
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof PlayerBlackjack;
    }

    @Override
    public int hashCode() {
        return 876543456;
    }
    
    PlayerBlackjack(Rules rules) {
        super(rules);
    }
}
