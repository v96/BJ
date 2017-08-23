/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bj.sim;

import bj.sim.hands.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author vasil.kuzevski
 */
public class BJ {

    /**
     * @param args the command line arguments
     */
    static int calls = 0, calculations = 0;

    static Map<MapKey, Double> map = new HashMap<>();

    public static double EV(PlayerHand hand, DealerHand dealer) {
        calls++;
        //if (calls % 100000 == 0) {
            System.out.println(calls + " " + calculations);
        //}

        MapKey key = new MapKey(hand, dealer);
        if (map.containsKey(key)) {
            return map.get(key);
        }
        calculations++;
        
        if (hand instanceof PlayerFinalHand && dealer instanceof DealerFinalHand) {
            map.put(key, ((PlayerFinalHand) hand).compare((DealerFinalHand) dealer));
            return map.get(key);
        }
        if (hand instanceof PlayerFinalHand) {
            double ev = 0;
            for (int i = 1; i <= 9; i++) {
                ev += (1 / (double) 13) * EV(hand, (DealerHand) dealer.applyAction(Action.HIT, i));
            }
            ev += (4 / (double) 13) * EV(hand, (DealerHand) dealer.applyAction(Action.HIT, 10));
            map.put(key, ev);
            return map.get(key);
        }
        double bestEV = Double.NEGATIVE_INFINITY;
        for (Action action : hand.availableActions()) {
            double ev = 0;
            if(action == Action.HIT || action == Action.DOUBLEDOWN) {
                for (int i = 1; i <= 9; i++) {
                    ev += (1 / (double) 13) * EV((PlayerHand) hand.applyAction(action, i), dealer);
                }
                ev += (4 / (double) 13) * EV((PlayerHand) hand.applyAction(action, 10), dealer);
            } else {
                ev = EV((PlayerHand) hand.applyAction(action, 10), dealer);
            }
            bestEV = Math.max(bestEV, ev);
        }
        map.put(key, bestEV);
        return map.get(key);
    }

    public static void main(String[] args) {

        Rules rules = new Rules();
        System.out.println(EV(new PlayerEmpty(rules), new DealerEmpty(rules)));
//        StrategyStats hit17 = new StrategyStats(Strategy.HIT_TO_17, CardDistribution.INFINITE_DECK, Rules.LE_GRAND);
//        StrategyStats alwaysStand = new StrategyStats(Strategy.ALWAYS_STAND, CardDistribution.INFINITE_DECK, Rules.LE_GRAND);
//        StrategyStats alwaysDD = new StrategyStats(Strategy.ALWAYS_DOUBLEDOWN, CardDistribution.INFINITE_DECK, Rules.LE_GRAND);
//        StrategyStats neverBust18 = new StrategyStats(Strategy.NEVER_BUST_18, CardDistribution.INFINITE_DECK, Rules.LE_GRAND);
//        
//        Strategy optimal = Strategy.generateOptimalStrategy(CardDistribution.INFINITE_DECK, Rules.LE_GRAND);
//        optimal.printStrategy();
//        System.out.println((new StrategyStats(optimal, CardDistribution.INFINITE_DECK, Rules.LE_GRAND)).getTotalEV());
//        System.out.println(hit17.getTotalEV());
//        System.out.println(alwaysStand.getTotalEV());
//        System.out.println(alwaysDD.getTotalEV());
//        System.out.println(neverBust18.getTotalEV());
    }

    private static class MapKey {

        private final PlayerHand hand;
        private final DealerHand dealer;

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 79 * hash + this.hand.hashCode();
            hash = 79 * hash + this.dealer.hashCode();
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final MapKey other = (MapKey) obj;
            if (!this.hand.equals(other.hand)) {
                return false;
            }
            if (!this.dealer.equals(other.dealer)) {
                return false;
            }
            return true;
        }
        
        public MapKey(PlayerHand hand, DealerHand dealer) {
            this.hand = hand;
            this.dealer = dealer;
        }
    }

}
