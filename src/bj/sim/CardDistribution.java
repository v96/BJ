/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bj.sim;

/**
 *
 * @author vasil.kuzevski
 */
public class CardDistribution {
    
    public static CardDistribution INFINITE_DECK;

    static {
        INFINITE_DECK = new CardDistribution();
        
        for(int i=1; i<=9; i++)
            INFINITE_DECK.pOfCard[i] = 1 / (double) 13;  
        INFINITE_DECK.pOfCard[10] = 4 / (double) 13;
    }


    private double[] pOfCard;

    public double pCard(Card c) {
        return pOfCard[c.getValue()];
    }
    
    CardDistribution() {
        pOfCard = new double[11];
    }
}
