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
    
    protected PlayerFinalHand(Rules rules) {
        super(rules);
    }
    
    public double compareAgainstDealer(DealerFinalHand dealer) {
        if (this instanceof SurrenderedHand) 
            return -0.5;
        else if (this instanceof PlayerBustedHand) 
            return -1;
        else if (this instanceof PlayerBlackjack) {
            if(!(dealer instanceof DealerBlackjack))
                return getRules().blackjackPayout();
            else
                return 0;
        } else if (this instanceof DoubledDown) {
            
        }
        throw new IllegalStateException();
    }
}
