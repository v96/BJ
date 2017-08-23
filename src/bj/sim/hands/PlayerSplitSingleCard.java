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
public class PlayerSplitSingleCard extends PlayerSingleCard {
    
    public Hand applyAction(Action action, int card) {
        switch (action) {
            case HIT:
                checkCard(card);
                if(this.card == card) 
                    return new PlayerSplitPair(getRules(), card);
                int newTotal = getTotal(this.card == 1 ? 11 : this.card, this.card == 1, card);
                boolean newSoft = isSoft(this.card == 1 ? 11 : this.card, this.card == 1, card);
                return new PlayerSplitInitial(getRules(), newTotal, newSoft);
            default:
                throw new IllegalArgumentException();
        }
    }
    
    @Override
    public int hashCode() {
        return 21163 * this.card;
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
        final PlayerSplitSingleCard other = (PlayerSplitSingleCard) obj;
        if (this.card != other.card) {
            return false;
        }
        return true;
    }
    
    PlayerSplitSingleCard(Rules rules, int card) {
        super(rules, card);
    }
}
