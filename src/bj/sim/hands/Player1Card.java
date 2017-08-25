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
class Player1Card extends PlayerHand {

    private final int card;

    public Action[] availableActions() {
        return new Action[] {Action.HIT};
    }
    
    protected int getCard() {
        return card;
    }

    public PlayerHand applyAction(Action action, int card) {
        switch (action) {
            case HIT:
                checkCard(card);
                if((this.card == 1 && card == 10) || (this.card == 10 && card == 1))
                    return new PlayerBlackjack(getRules());
                if(this.card == card) 
                    return new PlayerInitialPair(getRules(), card);
                int newTotal = newTotal(this.card == 1 ? 11 : this.card, this.card == 1, card);
                boolean newSoft = newSoft(this.card == 1 ? 11 : this.card, this.card == 1, card);
                return new Player2Cards(getRules(), newTotal, newSoft);
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
        final Player1Card other = (Player1Card) obj;
        if (this.card != other.card) {
            return false;
        }
        return true;
    }

    Player1Card(Rules rules, int card) {
        super(rules);
        this.card = card;
    }
}
