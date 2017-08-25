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
public abstract class DealerHand extends Hand {
    
    public abstract DealerHand applyAction(Action action, int card);
    
    public DealerHand applyAction(Action action) {
        if(action == Action.HIT || action == Action.DOUBLEDOWN) {
            throw new IllegalArgumentException();
        }
        return applyAction(action, 0);
    }
    
    public DealerHand hit(int card) {
        return applyAction(Action.HIT, card);
    }
    
    public boolean isFinal() {
        return false;
    }
    
    public boolean isBlackjack() {
        return this instanceof DealerBlackjack;
    }
    
    public boolean isEmpty() {
        return this instanceof DealerEmpty;
    }
    
    DealerHand(Rules rules) {
        super(rules);
    }
}
