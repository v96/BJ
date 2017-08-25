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
class Dealer17To21 extends DealerFinalHand {
    
    private final int total;
    
    public int getTotal() {
        return total;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + this.total;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Dealer17To21 other = (Dealer17To21) obj;
        if (this.total != other.total) {
            return false;
        }
        return true;
    }
    
    Dealer17To21(Rules rules, int total) {
        super(rules);
        this.total = total;
    }
}
