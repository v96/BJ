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
public class Hand {

    final private boolean isBJ;
    private boolean isBusted;
    private boolean isSoft; //does the hand contain an ace?
    private boolean isPair;
    private Card pairOf;
    private int total;

    public boolean isBlackjack() {
        return isBJ;
    }

    public int getTotal() {
        return total;
    }

    public boolean isPair() {
        return isPair;
    }

    public boolean isBusted() {
        return isBusted;
    }
    
    public boolean isSoft() {
        return isSoft;
    }

    public Card getPairedCard() {
        if (isPair) {
            return pairOf;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public int compareAgainstDealer(Hand dealersHand) {
        //returns 1 if the hand beats the dealer, 0 if they're tied, and -1 otherwise
        if (isBJ) {
            return 1;
        }
        if (isBusted) {
            return -1;
        }
        if (dealersHand.isBusted()) {
            return 1;
        }
        if (total > dealersHand.getTotal()) {
            return 1;
        }
        if (total == dealersHand.getTotal()) {
            return 0;
        }
        return -1;
    }

    public void hit(Card c) {
        isPair = false;
        if (total <= 10 && c.getValue() == 1) {
            total += 11;
            isSoft = true;
        } else {
            total += c.getValue();
            if (isSoft == true && total > 21) {
                total -= 10;
                isSoft = false;
            }
        }
        if(total > 21)
            isBusted = true;
    }

    Hand(Card c) {
        isPair = false;
        isBJ = false;
        isBusted = false;
        if (c.getValue() == 1) {
            isSoft = true;
            total = 11;
        } else {
            isSoft = false;
            total = c.getValue();
        }
    }

    Hand(Card c1, Card c2) {
        isBusted = false;
        if (c1 == c2) {
            isPair = true;
            pairOf = c1;
        }
        if (c1.getValue() == 1 || c2.getValue() == 1) {
            isSoft = true;
            total = c1.getValue() + c2.getValue() + 10;
        } else {
            isSoft = false;
            total = c1.getValue() + c2.getValue();
        }
        isBJ = (total == 21);
    }
    
    Hand(int total, boolean isSoft) {
        this.total = total;
        this.isSoft = isSoft;
        isPair = false;
        isBusted = (total > 21);
        isBJ = (total == 21);
    }
}
