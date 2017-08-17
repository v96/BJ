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
public class Card {
    private int value;

    public int getValue() {
        return value;
    }

    Card(int value) {
        if(value >= 1 && value <= 10)
            this.value = value;
        else
            throw new IllegalArgumentException();
    }
}
