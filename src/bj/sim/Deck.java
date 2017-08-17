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
public class Deck {

    final private int numberOfSingleDecks; //deck originally contained 52*D cards
    private Card[] cards; //a representation of the full deck, the undealt cards are cards[n..cards.length)
    private int n;
    private int shufflePoint;
    private Shuffler shuffler;

    public void setShufflePoint(int shufflePoint) {
        if (shufflePoint <= 0 || shufflePoint > 52 * numberOfSingleDecks) {
            throw new IllegalArgumentException();
        }
        this.shufflePoint = shufflePoint;
    }

    public void setShuffler(Shuffler shuffler) {
        this.shuffler = shuffler;
    }

    public int getCardsRemaining() {
        return cards.length - n;
    }

    public int getNumberOfSingleDecks() {
        return numberOfSingleDecks;
    }

    public boolean hasNext() {
        return n < shufflePoint;
    }

    public Card next() {
        if (!hasNext()) {
            throw new UnsupportedOperationException();
        }
        return cards[n++];
    }

    public void burn() {
        n++;
    }

    public void shuffle() {
        shuffler.shuffle(cards);
        n = 0;
    }
    
    public void cut() {
        
    }
    
    public void cut(int cutPoint) {
        
    }

    Deck(int numberOfSingleDecks, int shufflePoint) {
        this.numberOfSingleDecks = numberOfSingleDecks;
        cards = new Card[52*numberOfSingleDecks];
        n = 0;
        this.shufflePoint = shufflePoint;
        shuffler = new Shuffler();
        
        for(int i=0; i<52*numberOfSingleDecks; i++) {
            cards[i] = new Card(i % 13 < 10 ? (i % 13) + 1 : 10);
        }
    }
}
