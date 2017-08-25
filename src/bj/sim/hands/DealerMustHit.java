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
abstract class DealerMustHit extends DealerHand {
    
    public Action[] availableActions() {
        return new Action[] {Action.HIT};
    }
    
    public abstract DealerHand applyAction(Action action, int card);
    
    DealerMustHit(Rules rules) {
        super(rules);
    }
    
}
