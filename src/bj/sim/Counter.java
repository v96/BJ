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
public class Counter {

    public static Counter HILO;

    static {
        HILO.value = new int[11];
        HILO.value[1] = -1;
        for (int i = 2; i <= 6; i++) {
            HILO.value[i] = 1;
        }
        for (int i = 7; i <= 9; i++) {
            HILO.value[i] = 0;
        }
        HILO.value[10] = -1;
    }

    private int runningCount;
    private int[] value;

    public void count(Card c) {
        runningCount += value[c.getValue()];
    }

    public int getRunningCount() {
        return runningCount;
    }

    public double getTrueCount(double decksRemaining) {
        return runningCount / decksRemaining;
    }

    public void reset() {
        runningCount = 0;
    }
}
