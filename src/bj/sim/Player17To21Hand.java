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
public class Player17To21Hand extends PlayerFinalHand {
    
    private final int total;
    
    public int getTotal() {
        return total;
    }
    
    Player17To21Hand(Rules rules, int total) {
        super(rules);
        this.total = total;
    }
    
}
