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
public class PlayerInitialOneCardHand extends PlayerHand {
    private int card;
    
    public Action[] availableActions() {
        Action[] avail = new Action[1];
        avail[0] = Action.HIT;
        return avail;
    }
    
    public Hand applyAction(Action action, int card) {
        switch(action) {
            case HIT: 
                checkCard(card);
                if(this.card == 1 && card == 10 || this.card == 10 && card == 1) {
                    return new PlayerBlackjack(getRules());
                }
                return new PlayerInitialFullHand(getRules(), this.card, card);
            default:
                throw new IllegalArgumentException();
        }
    }
    
    PlayerInitialOneCardHand(Rules rules, int card) {
        super(rules);
        checkCard(card);
        this.card = card;
    }
}
