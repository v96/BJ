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
public class DealerEmpty extends DealerMustHit {
    
    public Hand applyAction(Action action, int card) {
        switch(action) {
            case HIT:
                checkCard(card);
                return new DealerSingleCard(getRules(), card);
            default:
                throw new IllegalArgumentException();
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof DealerEmpty;
    }

    @Override
    public int hashCode() {
        return 97642224;
    }
    
    public DealerEmpty(Rules rules) {
        super(rules);
    }
}
