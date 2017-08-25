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
class PlayerSurrendered extends PlayerFinalHand {
    
    public double compare(DealerFinalHand dealer) {
        if(dealer.isBlackjack())
            return -1;
        return -0.5;
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof PlayerSurrendered;
    }

    @Override
    public int hashCode() {
        return 765422245;
    }
    
    PlayerSurrendered(Rules rules) {
        super(rules);
    }
}
