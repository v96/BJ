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
public class PlayerDoubledDownBusted extends PlayerBusted {
    
    public double compare(DealerFinalHand dealer) {
        return 2 * super.compare(dealer);
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof PlayerDoubledDownBusted;
    }

    @Override
    public int hashCode() {
        return 664442227;
    }
    
    PlayerDoubledDownBusted(Rules rules) {
        super(rules);
    } 
}
