/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bj.sim;

import bj.sim.hands.*;

/**
 *
 * @author vasil.kuzevski
 */
public class BJ {

    /**
     * @param args the command line arguments
     */
    static int calls = 0, calculations = 0;

    static HandMap<Double> map;

    public static double EV(PlayerHand hand, DealerHand dealer) {
        calls++;
        //if (calls % 100000 == 0) {
        System.out.println(calls + " " + calculations);
        //}

        if (map.contains(hand, dealer)) {
            return map.get(hand, dealer);
        }
        calculations++;

        if (hand instanceof PlayerFinalHand && dealer instanceof DealerFinalHand) {
            map.put(hand, dealer, ((PlayerFinalHand) hand).compare((DealerFinalHand) dealer));
            return map.get(hand, dealer);
        }
        if (hand instanceof PlayerFinalHand) {
            double ev = 0;
            for (int i = 1; i <= 9; i++) {
                ev += (1 / (double) 13) * EV(hand, (DealerHand) dealer.applyAction(Action.HIT, i));
            }
            ev += (4 / (double) 13) * EV(hand, (DealerHand) dealer.applyAction(Action.HIT, 10));
            map.put(hand, dealer, ev);
            return map.get(hand, dealer);
        }
        double bestEV = Double.NEGATIVE_INFINITY;
        for (Action action : hand.availableActions()) {
            double ev = 0;
            if (action == Action.HIT || action == Action.DOUBLEDOWN) {
                for (int i = 1; i <= 9; i++) {
                    ev += (1 / (double) 13) * EV((PlayerHand) hand.applyAction(action, i), dealer);
                }
                ev += (4 / (double) 13) * EV((PlayerHand) hand.applyAction(action, 10), dealer);
            } else {
                ev = EV((PlayerHand) hand.applyAction(action, 10), dealer);
            }
            bestEV = Math.max(bestEV, ev);
        }
        map.put(hand, dealer, bestEV);
        return map.get(hand, dealer);
    }

    public static void main(String[] args) {
        map = new HandMap();
        Rules rules = new Rules();
        System.out.println(EV((PlayerHand) (new PlayerEmpty(rules)).applyAction(Action.HIT, 10),
                (DealerHand) (((new DealerEmpty(rules)).applyAction(Action.HIT, 6)).applyAction(Action.HIT, 1)).applyAction(Action.HIT, 2)));
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
}
