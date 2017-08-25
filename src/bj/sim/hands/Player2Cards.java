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
class Player2Cards extends PlayerHand {

    private final int total;
    private final boolean soft;

    public Action[] availableActions() {
        return new Action[] {Action.HIT, Action.STAND, Action.DOUBLEDOWN, Action.SURRENDER};
    }

    public PlayerHand applyAction(Action action, int card) {
        switch (action) {
            case HIT: {
                checkCard(card);
                int newTotal = newTotal(total, soft, card);
                boolean newSoft = newSoft(total, soft, card);
                if (newTotal < 22)
                    return new PlayerHitOrStand(getRules(), newTotal, newSoft);
                return new PlayerBusted(getRules());
            }
            case STAND:
                if (total < 17) {
                    return new PlayerUnder17(getRules());
                }
                return new Player17To21(getRules(), total);
            case DOUBLEDOWN: {
                checkCard(card);
                int newTotal = newTotal(total, soft, card);
                if (newTotal < 17) {
                    return new PlayerDoubledDownUnder17(getRules());
                } else if (newTotal >= 17 && newTotal <= 21) {
                    return new PlayerDoubledDown17To21(getRules(), newTotal);
                }
                return new PlayerDoubledDownBusted(getRules());
            }
            case SURRENDER:
                return new PlayerSurrendered(getRules());
            default:
                throw new IllegalArgumentException();
        }
    }
    
    protected int getTotal(){
        return total;
    }
    
    protected boolean isSoft() {
        return soft;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.total;
        hash = 97 * hash + (this.soft ? 1 : 0);
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
        final Player2Cards other = (Player2Cards) obj;
        if (this.total != other.total) {
            return false;
        }
        if (this.soft != other.soft) {
            return false;
        }
        return true;
    }

    Player2Cards(Rules rules, int total, boolean soft) {
        super(rules);
        this.total = total;
        this.soft = soft;
    }
}
