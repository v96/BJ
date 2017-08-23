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
public class PlayerDoubledDownUnder17 extends PlayerUnder17 {
    
    public double compare(DealerFinalHand dealer) {
        return 2 * super.compare(dealer);
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof PlayerDoubledDownUnder17;
    }

    @Override
    public int hashCode() {
        return 78543333;
    }
    
    PlayerDoubledDownUnder17(Rules rules) {
        super(rules);
    }
}
