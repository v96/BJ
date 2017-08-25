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
class PlayerSplit2Cards extends Player2Cards {

    public Action[] availableActions() {
        if(getRules().doubleAfterSplitAllowed) {
            return new Action[] {Action.HIT, Action.STAND, Action.DOUBLEDOWN};
        } else {
            return new Action[] {Action.HIT, Action.STAND};
        }
    }

    public PlayerHand applyAction(Action action, int card) {
        switch (action) {
            case SPLIT:
            case SURRENDER:
                throw new IllegalArgumentException();
            case DOUBLEDOWN:
                if(getRules().doubleAfterSplitAllowed) {
                    return super.applyAction(Action.DOUBLEDOWN, card);
                } else {
                    throw new IllegalArgumentException();
                }
            default:
                return super.applyAction(action, card);
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof PlayerSplit2Cards && isSoft() == ((PlayerSplit2Cards) obj).isSoft() && getTotal() == ((PlayerSplit2Cards) obj).getTotal();
    }

    @Override
    public int hashCode() {
        return 52522 + 19 * getTotal() + 37 * (isSoft() ? 1 : 0);
    }

    PlayerSplit2Cards(Rules rules, int total, boolean soft) {
        super(rules, total, soft);
    }
}
