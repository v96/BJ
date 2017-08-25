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
class PlayerSplit1Card extends Player1Card {
    
    public PlayerHand applyAction(Action action, int card) {
        switch (action) {
            case HIT:
                checkCard(card);
//                if(getCard() == card) 
//                    return new PlayerSplitPair(getRules(), card);
                int newTotal = newTotal(getCard() == 1 ? 11 : getCard(), getCard() == 1, card);
                if(getCard() == 1 && !getRules().canHitSplitAces) {
                    if (newTotal < 17) {
                        return new PlayerUnder17(getRules());
                    }
                    return new Player17To21(getRules(), newTotal);
                }
                boolean newSoft = newSoft(getCard() == 1 ? 11 : getCard(), getCard() == 1, card);
                return new PlayerSplit2Cards(getRules(), newTotal, newSoft);
            default:
                throw new IllegalArgumentException();
        }
    }
    
    @Override
    public int hashCode() {
        return 21163 * getCard();
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
        final PlayerSplit1Card other = (PlayerSplit1Card) obj;
        if (getCard() != other.getCard()) {
            return false;
        }
        return true;
    }
    
    PlayerSplit1Card(Rules rules, int card) {
        super(rules, card);
    }
}
