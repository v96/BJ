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
public class PlayerSplitInitial extends PlayerInitial {

    public Action[] availableActions() {
        Action[] avail = new Action[3];
        avail[0] = Action.HIT;
        avail[1] = Action.STAND;
        avail[2] = Action.DOUBLEDOWN;
        return avail;
    }

    public Hand applyAction(Action action, int card) {
        switch (action) {
            case SURRENDER:
                throw new IllegalArgumentException();
            default:
                return super.applyAction(action, card);
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof PlayerSplitInitial && this.soft == ((PlayerSplitInitial) obj).soft && this.total == ((PlayerSplitInitial) obj).total;
    }

    @Override
    public int hashCode() {
        return 52522 + 19 * this.total + 37 * (this.soft ? 1 : 0);
    }

    PlayerSplitInitial(Rules rules, int total, boolean soft) {
        super(rules, total, soft);
    }
}
