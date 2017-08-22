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
public class PlayerInitialFullHand extends PlayerHand {

    private int card1, card2;

    public Action[] availableActions() {
        Action[] avail = new Action[5];

        return avail;
    }

    public Hand applyAction(Action action, int card) {
        switch (action) {
            case SURRENDER:
                return new SurrenderedHand(getRules());
            case DOUBLEDOWN:
                
            case SPLIT_1:
            case SPLIT_2:
            case HIT:
            case STAND:
                
            default:
                throw new IllegalArgumentException();
        }
    }

    PlayerInitialFullHand(Rules rules, int card1, int card2) {
        super(rules);
    }
}
