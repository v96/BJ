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
public class PlayerInitialEmptyHand extends PlayerHand {

    public Action[] availableActions() {
        Action[] avail = new Action[1];
        avail[0] = Action.HIT;
        return avail;
    }

    public Hand applyAction(Action action, int card) {
        switch (action) {
            case HIT:
                checkCard(card);
                return new PlayerInitialOneCardHand(this.getRules(), 1);
            default:
                throw new IllegalArgumentException();
        }
    }

    PlayerInitialEmptyHand(Rules rules) {
        super(rules);
    }
}
