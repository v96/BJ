/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bj.sim.hands;

import bj.sim.Action;
import bj.sim.Rules;

/**
 *
 * @author vasil.kuzevski
 */
public class DealerUnder17 extends DealerMustHit {

    private final int total;
    private final boolean soft;

    public Hand applyAction(Action action, int card) {
        switch (action) {
            case HIT:
                checkCard(card);
                int newTotal = getTotal(total, soft, card);
                boolean newSoft = isSoft(total, soft, card);
                if (newTotal >= 22) {
                    return new DealerBusted(getRules());
                }
                if (newTotal >= 17) {
                    return new Dealer17To21(getRules(), newTotal);
                }
//                if (newTotal == 17) {
//                    if (newSoft && !getRules().dealerStandsOnSoft17) {
//                        return new DealerUnder17(getRules(), newTotal, newSoft);
//                    } else {
//                        return new Dealer17To21(getRules(), newTotal);
//                    }
//                }
                return new DealerUnder17(getRules(), newTotal, newSoft);
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.total;
        hash = 53 * hash + (this.soft ? 1 : 0);
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
        final DealerUnder17 other = (DealerUnder17) obj;
        if (this.total != other.total) {
            return false;
        }
        if (this.soft != other.soft) {
            return false;
        }
        return true;
    }
    
    DealerUnder17(Rules rules, int total, boolean soft) {
        super(rules);
        this.total = total;
        this.soft = soft;
    }
}
