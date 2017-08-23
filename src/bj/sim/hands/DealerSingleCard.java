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
public class DealerSingleCard extends DealerMustHit {
    
    private final int card;
    
    public Hand applyAction(Action action, int card) {
        switch (action) {
            case HIT:
                checkCard(card);
                if((this.card == 1 && card == 10) || (this.card == 10 && card == 1))
                    return new DealerBlackjack(getRules());
                int newTotal = getTotal(this.card == 1 ? 11 : this.card, this.card == 1, card);
                boolean newSoft = isSoft(this.card == 1 ? 11 : this.card, this.card == 1, card);
                return new DealerNonBlackjack(getRules(), newTotal, newSoft);
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.card;
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
        final DealerSingleCard other = (DealerSingleCard) obj;
        if (this.card != other.card) {
            return false;
        }
        return true;
    }
    
    DealerSingleCard(Rules rules, int card) {
        super(rules);
        this.card = card;
    }
}
