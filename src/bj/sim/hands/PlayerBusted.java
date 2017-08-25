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
class PlayerBusted extends PlayerFinalHand {
    
    public double compare(DealerFinalHand dealer) {
        return -1;
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof PlayerBusted;
    }

    @Override
    public int hashCode() {
        return 112284446;
    }
    
    PlayerBusted(Rules rules) {
        super(rules);
    }
}
