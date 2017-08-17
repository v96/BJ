/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bj.sim;

import java.util.Random;

/**
 *
 * @author vasil.kuzevski
 */
public class Shuffler {

    private static void fisherYatesShuffle(Card[] cards) {
        int index;
        Card temp;
        Random random = new Random();
        for (int i = cards.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            temp = cards[index];
            cards[index] = cards[i];
            cards[i] = temp;
        }
    }

    public void shuffle(Card[] cards) {
        fisherYatesShuffle(cards);
    }
}
