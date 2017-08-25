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
class PlayerSplitPair extends PlayerSplit2Cards {
    
    private final int card;
    
    public Action[] availableActions() {
        if(getRules().doubleAfterSplitAllowed) {
            return new Action[] {Action.HIT, Action.STAND, Action.DOUBLEDOWN};//, Action.SPLIT};
        } else {
            return new Action[] {Action.HIT, Action.STAND}; //,Action.SPLIT};
        }
    }
    
    public PlayerHand applyAction(Action action, int card) {
        switch(action) {
            case SPLIT:
                //return new PlayerSplitSingleCard(getRules(), this.card);
            case SURRENDER:
                throw new IllegalArgumentException();
            case DOUBLEDOWN:
                if(getRules().doubleAfterSplitAllowed) {
                    return super.doubleDown(card);
                } else {
                    throw new IllegalArgumentException();
                }
            default:
                return super.applyAction(action, card);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.card;
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
        final PlayerSplitPair other = (PlayerSplitPair) obj;
        if (this.card != other.card) {
            return false;
        }
        return true;
    }
    
    PlayerSplitPair(Rules rules, int card) {
        super(rules, card == 1 ? 12 : 2 * card, card == 1);
        this.card = card;
    }
}
