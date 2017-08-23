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
public class PlayerSplitPair extends PlayerSplitInitial {
    
    protected final int card;
    
    public Action[] availableActions() {
        Action[] avail = new Action[3];
        avail[0] = Action.HIT;
        avail[1] = Action.STAND;
        avail[2] = Action.DOUBLEDOWN;
        //avail[3] = Action.SPLIT;
        return avail;
    }
    
    public Hand applyAction(Action action, int card) {
        switch(action) {
            case SPLIT:
                //return new PlayerSplitSingleCard(getRules(), this.card);
            case SURRENDER:
                throw new IllegalArgumentException();
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
