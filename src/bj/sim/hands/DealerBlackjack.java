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
class DealerBlackjack extends DealerFinalHand {

    @Override
    public boolean equals(Object obj) {
        return obj instanceof DealerBlackjack;
    }

    @Override
    public int hashCode() {
        return 27425733;
    }
    
    DealerBlackjack(Rules rules) {
        super(rules);
    }
}

