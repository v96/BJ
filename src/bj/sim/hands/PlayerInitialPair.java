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
public class PlayerInitialPair extends PlayerInitial {
    
    private final int card;
    
    public Action[] availableActions() {
        Action[] avail = new Action[5];
        System.arraycopy(Action.values(), 0, avail, 0, 5);
        return avail;
    }
    
    public Hand applyAction(Action action, int card) {
        switch (action) {
            case SPLIT:
                return new PlayerSplitSingleCard(getRules(), card);
            default:
                return super.applyAction(action, card);
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
        final PlayerInitialPair other = (PlayerInitialPair) obj;
        if (this.card != other.card) {
            return false;
        }
        return true;
    }
    
    PlayerInitialPair(Rules rules, int card) {
        super(rules, card == 1 ? 12 : 2 * card, card == 1);
        this.card = card;
    }
}
