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
abstract class Hand {
    
    private final Rules rules;
    
    protected static void checkCard(int card) {
        if (card < 1 || card > 10) {
            throw new IllegalArgumentException();
        }
    }
    
    protected static int newTotal(int total, boolean soft, int card) {
        checkCard(card);
        
        if(card == 1 && total <= 10) {
            return total + 11;
        } else {
            int newTotal = total + card;
            if(soft && newTotal > 21) {
                newTotal -= 10;
            }
            return newTotal;
        }
    }
    
    protected static boolean newSoft(int total, boolean soft, int card) {
        checkCard(card);
        
        if(card == 1 && total <= 10)
            return true;
        if(soft)
            return (total + card <= 21);
        return false;
    }

    public abstract Action[] availableActions();
    
    public abstract boolean isFinal();
    
    public abstract boolean isBlackjack();
    
    public abstract boolean isEmpty();
    
    public Rules getRules() {
        return rules;
    }

    protected Hand(Rules rules) {
        this.rules = rules;
    }
}
