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
public abstract class PlayerHand extends Hand {
    
    public static PlayerHand handWith2CardTotal(int total, boolean soft, Rules rules) {
        if((soft && (total < 12 || total > 21)) || (!soft && (total < 4 || total > 20)))
            throw new IllegalArgumentException();
        PlayerHand hand = new PlayerEmpty(rules);
        if(soft) {
            hand = hand.hit(1).hit(total - 11);
        } else {
            if(total < 12) {
                hand = hand.hit(2).hit(total - 2);
            }
            else {
                hand = hand.hit(10).hit(total - 10);
            }
        }
        return hand;
    }
    
    public static PlayerHand handWith2CardTotal(int total, boolean soft) {
        return handWith2CardTotal(total, soft, new Rules());
    }
    
    public abstract PlayerHand applyAction(Action action, int card);
    
    public PlayerHand applyAction(Action action) {
        if(action == Action.HIT || action == Action.DOUBLEDOWN) {
            throw new IllegalArgumentException();
        }
        return applyAction(action, 0);
    }
    
    public PlayerHand hit(int card) {
        return applyAction(Action.HIT, card);
    }
    
    public PlayerHand stand() {
        return applyAction(Action.STAND);
    }
    
    public PlayerHand doubleDown(int card) {
        return applyAction(Action.DOUBLEDOWN, card);
    }
    
    public PlayerHand surrender() {
        return applyAction(Action.SURRENDER);
    }
    
    public PlayerHand split() {
        return applyAction(Action.SPLIT);
    }
    
    public boolean isFinal() {
        return false;
    }
    
    public boolean isBlackjack() {
        return this instanceof PlayerBlackjack;
    }
    
    public boolean isEmpty() {
        return this instanceof PlayerEmpty;
    }
    
    public double compare(DealerHand dealer) {
        if(this instanceof PlayerFinalHand && dealer instanceof DealerFinalHand)
            return ((PlayerFinalHand) this).compare((DealerFinalHand) dealer);
        throw new IllegalArgumentException();
    }
    
    PlayerHand(Rules rules) {
        super(rules);
    }
}
