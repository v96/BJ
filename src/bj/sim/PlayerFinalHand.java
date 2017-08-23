/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bj.sim;

/**
 *
 * @author Vasil
 */
public abstract class PlayerFinalHand extends PlayerHand {
    
    public Action[] availableActions() {
        return new Action[0];
    }
    
    public Hand applyAction(Action action, int card) {
        throw new IllegalArgumentException();
    }
    
    public abstract double compareAgainstDealer(DealerFinalHand dealer);
    
    protected PlayerFinalHand(Rules rules) {
        super(rules);
    }
}
