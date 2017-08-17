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
public class BJ {

    /**
     * @param args the command line arguments
     */
    private static Card nextCard(Deck deck) {
        if (deck.hasNext()) {
            return deck.next();
        } else {
            deck.shuffle();
            deck.burn();
            return deck.next();
        }
    }

    public static void main(String[] args) {
        System.out.println();
        StrategyStats strsts = new StrategyStats(Strategy.HIT_TO_17, CardDistribution.INFINITE_DECK, Rules.LE_GRAND);
    }

}
