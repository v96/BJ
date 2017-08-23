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
public class PlayerSingleCard extends PlayerHand {

    protected final int card;

    public Action[] availableActions() {
        Action[] avail = new Action[1];
        avail[0] = Action.HIT;
        return avail;
    }

    public Hand applyAction(Action action, int card) {
        switch (action) {
            case HIT:
                checkCard(card);
                if((this.card == 1 && card == 10) || (this.card == 10 && card == 1))
                    return new PlayerBlackjack(getRules());
                if(this.card == card) 
                    return new PlayerInitialPair(getRules(), card);
                int newTotal = getTotal(this.card == 1 ? 11 : this.card, this.card == 1, card);
                boolean newSoft = isSoft(this.card == 1 ? 11 : this.card, this.card == 1, card);
                return new PlayerInitial(getRules(), newTotal, newSoft);
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.card;
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
        final PlayerSingleCard other = (PlayerSingleCard) obj;
        if (this.card != other.card) {
            return false;
        }
        return true;
    }

    PlayerSingleCard(Rules rules, int card) {
        super(rules);
        this.card = card;
    }
}
