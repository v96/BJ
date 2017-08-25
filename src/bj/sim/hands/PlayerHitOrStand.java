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
class PlayerHitOrStand extends PlayerHand {

    private final int total;
    private final boolean soft;

    public Action[] availableActions() {
        return new Action[] {Action.HIT, Action.STAND};
    }

    public PlayerHand applyAction(Action action, int card) {
        switch (action) {
            case HIT:
                checkCard(card);
                int newTotal = newTotal(total, soft, card);
                boolean newSoft = newSoft(total, soft, card);
                if (newTotal < 22)
                    return new PlayerHitOrStand(getRules(), newTotal, newSoft);
                return new PlayerBusted(getRules());
            case STAND:
                if (total < 17) {
                    return new PlayerUnder17(getRules());
                } else if (total <= 21) {
                    return new Player17To21(getRules(), total);
                } else {
                    throw new IllegalStateException();
                }
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.total;
        hash = 37 * hash + (this.soft ? 1 : 0);
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
        final PlayerHitOrStand other = (PlayerHitOrStand) obj;
        if (this.total != other.total) {
            return false;
        }
        if (this.soft != other.soft) {
            return false;
        }
        return true;
    }
    
    PlayerHitOrStand(Rules rules, int total, boolean soft) {
        super(rules);
        this.total = total;
        this.soft = soft;
    }
}
