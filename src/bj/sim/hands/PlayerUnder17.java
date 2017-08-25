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
class PlayerUnder17 extends PlayerFinalHand {

    public double compare(DealerFinalHand dealer) {
        if (dealer instanceof DealerBusted) {
            return 1;
        }
        return -1;
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof PlayerUnder17;
    }

    @Override
    public int hashCode() {
        return 5333331;
    }

    PlayerUnder17(Rules rules) {
        super(rules);
    }
}
