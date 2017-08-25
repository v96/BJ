package bj.sim.hands;

import bj.sim.Rules;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vasil.kuzevski
 */
class PlayerDoubledDown17To21 extends Player17To21 {
    
    public double compare(DealerFinalHand dealer) {
        return 2 * super.compare(dealer);
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof PlayerDoubledDown17To21 && ((PlayerDoubledDown17To21) obj).getTotal() == this.getTotal();
    }

    @Override
    public int hashCode() {
        return 426 * this.getTotal() + 347433;
    }
    
    PlayerDoubledDown17To21(Rules rules, int total) {
        super(rules, total);
    }
}
