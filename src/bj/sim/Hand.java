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

    private boolean isSoft;
    private boolean isPair;
    private Card pairOf;
    private int nCards;
    private int total;

    public int getTotal() { //22 = bust
        return total;
    }

    public boolean isSoft() {
        return isSoft;
    }

    public boolean isPair() {
        return isPair;
    }

    public Card pairOf() {
        if (!isPair()) {
            throw new UnsupportedOperationException();
        }
        return pairOf;
    }

    Hand(Card c) {
        isSoft = (c.getValue() == 1);
        isPair = false;
        pairOf = null;
        nCards = 1;
        total = (c.getValue() == 1 ? 11 : c.getValue());
    }

    Hand(Hand h, Card c) {
        isPair = (h.nCards == 1 && (h.getTotal() == c.getValue() || (h.getTotal() == 11 && c.getValue() == 1)));
        pairOf = (isPair ? c : null);
        if (h.getTotal() <= 10 && c.getValue() == 1) {
            isSoft = true;
            total = h.getTotal() + 11;
        } else {
            total = h.getTotal() + c.getValue();
            if (h.isSoft()) {
                if (total >= 22) {
                    total -= 10;
                    isSoft = false;
                } else {
                    isSoft = true;
                }
            } else {
                isSoft = false;
            }
        }
        if (total >= 22) { //bust
            total = 22;
        }
        nCards = h.nCards + 1;
    }

    Hand(int total, boolean isSoft) {
        if (isSoft && total < 11) {
            throw new IllegalArgumentException();
        }
        this.isSoft = isSoft;
        this.total = total;
        if (this.total >= 22) {
            this.total = 22;
        }
        this.isPair = false;
        if (total <= 3 || (total == 11 && isSoft)) {
            this.nCards = 1;
        } else {
            this.nCards = 2;
        }
    }
}
