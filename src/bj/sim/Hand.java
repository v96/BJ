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
public abstract class Hand {
    
    protected static void checkCard(int card) {
        if(card < 1 || card > 10)
            throw new IllegalArgumentException();
    }
    
    private final Rules rules;
    
    public Rules getRules() {
        return rules;
    }
    
    public abstract Action[] availableActions();
    
    public abstract Hand applyAction(Action action, int card); //use card = 0 for actions that don't require cards
    
    protected Hand(Rules rules) {
        this.rules = rules;
    }
}
