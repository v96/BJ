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
class Player17To21 extends PlayerFinalHand {

    private final int total;

    public int getTotal() {
        return total;
    }

    public double compare(DealerFinalHand dealer) {
        if (dealer instanceof DealerBusted) {
            return 1;
        } else if (dealer.isBlackjack()) {
            return -1;
        } else {
            if(total > ((Dealer17To21) dealer).getTotal())
                return 1;
            else if(total == ((Dealer17To21) dealer).getTotal())
                return 0;
            else
                return -1;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.total;
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
        final Player17To21 other = (Player17To21) obj;
        if (this.total != other.total) {
            return false;
        }
        return true;
    }

    Player17To21(Rules rules, int total) {
        super(rules);
        this.total = total;
    }
}
