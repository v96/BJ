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
abstract class PlayerFinalHand extends PlayerHand {
    
    public Action[] availableActions() {
        return new Action[0];
    }
    
    public PlayerHand applyAction(Action action, int card) {
        throw new IllegalArgumentException();
    }
    
    public abstract double compare(DealerFinalHand dealer);
    
    public boolean isFinal() {
        return true;
    }
    
    PlayerFinalHand(Rules rules) {
        super(rules);
    }
}
