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
public abstract class DealerMustHit extends DealerHand {
    
    public Action[] availableActions() {
        Action[] avail = new Action[1];
        avail[0] = Action.HIT;
        return avail;
    }
    
    public abstract Hand applyAction(Action action, int card);
    
    protected DealerMustHit(Rules rules) {
        super(rules);
    }
    
}
