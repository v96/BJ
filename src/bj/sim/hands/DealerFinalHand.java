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
public abstract class DealerFinalHand extends DealerHand {
    
    public Action[] availableActions() {
        Action[] avail = new Action[0];
        return avail;
    }
    
    public Hand applyAction(Action action, int card) {
        throw new IllegalArgumentException();
    }
    
    protected DealerFinalHand(Rules rules) {
        super(rules);
    }
}
