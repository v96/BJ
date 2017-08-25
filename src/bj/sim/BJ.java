/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bj.sim;

import bj.sim.hands.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 *
 * @author vasil.kuzevski
 */
public class BJ {

    private static class MapKey {

        private final PlayerHand hand;
        private final DealerHand dealer;

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 79 * hash + Objects.hashCode(this.hand);
            hash = 79 * hash + Objects.hashCode(this.dealer);
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
            if (!Objects.equals(this.hand, other.hand)) {
                return false;
            }
            if (!Objects.equals(this.dealer, other.dealer)) {
                return false;
            }
            return true;
        }

        public String toString() {
            return hand.toString() + " " + dealer.toString();
        }

        public MapKey(PlayerHand hand, DealerHand dealer) {
            this.hand = hand;
            this.dealer = dealer;
        }
    }

    static int calls = 0, calculations = 0;
    static int stackDepth = 0;
    static Map<MapKey, Double> cachedEV;
    static Map<MapKey, Action> basicStrategy;

    public static double EV(PlayerHand hand, DealerHand dealer) {
        MapKey key = new MapKey(hand, dealer);
        if (cachedEV.containsKey(key)) {
            return cachedEV.get(key);
        }

        if (hand.isFinal() && dealer.isFinal()) {
            cachedEV.put(key, hand.compare(dealer));
            return hand.compare(dealer);
        }
        if (dealer.isEmpty() || hand.isFinal()) {
            double ev = 0;
            if (dealer.hit(1).isBlackjack()) {
                for (int i = 2; i <= 9; i++) {
                    ev += (1 / (double) 12) * EV(hand, dealer.hit(i));
                }
                ev += (4 / (double) 12) * EV(hand, dealer.hit(10));
                ev = (12 / (double) 13) * ev + (1 / (double) 13) * hand.compare(dealer.hit(1)); //adjust for dealer blackjack
            } else if (dealer.hit(10).isBlackjack()) {
                for (int i = 1; i <= 9; i++) {
                    ev += (1 / (double) 9) * EV(hand, dealer.hit(i));
                }
                ev = (9 / (double) 13) * ev + (4 / (double) 13) * hand.compare(dealer.hit(10)); //adjust for dealer blackjack
            } else {
                for (int i = 1; i <= 9; i++) {
                    ev += (1 / (double) 13) * EV(hand, dealer.hit(i));
                }
                ev += (4 / (double) 13) * EV(hand, dealer.hit(10));
            }
            cachedEV.put(key, ev);
            return ev;
        }
        double bestEV = Double.NEGATIVE_INFINITY;
        Action bestAction = Action.STAND;
        for (Action action : hand.availableActions()) {
            double ev = 0;
            switch (action) {
                case HIT:
                case DOUBLEDOWN:
                    for (int i = 1; i <= 9; i++) {
                        ev += (1 / (double) 13) * EV((PlayerHand) hand.applyAction(action, i), dealer);
                    }
                    ev += (4 / (double) 13) * EV((PlayerHand) hand.applyAction(action, 10), dealer);
                    break;
                case SPLIT:
                    ev = 2 * EV((PlayerHand) hand.applyAction(action), dealer);
                    break;
                default:
                    ev = EV((PlayerHand) hand.applyAction(action), dealer);
                    break;
            }
            if (ev > bestEV) {
                bestEV = ev;
                bestAction = action;
            }
        }
        basicStrategy.put(key, bestAction);
        cachedEV.put(key, bestEV);
        return bestEV;
    }

    private static String niceStr(Action action) {
        switch (action) {
            case HIT:
                return "H ";
            case STAND:
                return "S ";
            case DOUBLEDOWN:
                return "Dd";
            case SPLIT:
                return "Sp";
            case SURRENDER:
                return "Sr";
            default:
                throw new IllegalArgumentException();
        }
    }

    private static final Random random = new Random();

    public static int nextCard() {
        return Math.min(random.nextInt(13) + 1, 10);
    }

    public static Action bestAction(PlayerHand hand, DealerHand dealer) {
        EV(hand, dealer);
        return basicStrategy.get(new MapKey(hand, dealer));
    }

    public static void sim() {
        PlayerHand handEmpty = new PlayerEmpty();
        DealerHand dealerEmpty = new DealerEmpty();

        int bankroll = 0;
        for (int i = 1; i <= 100000000; i++) {
            if (i != 0 && i % 100000 == 0) {
                DecimalFormat df = new DecimalFormat();
                df.setMinimumFractionDigits(10);
                df.setMaximumFractionDigits(10);
                System.out.println(df.format(bankroll / (double) i));
            }

            PlayerHand hand = handEmpty;
            DealerHand dealer = dealerEmpty;
            boolean split = false;
            PlayerHand secondHand = null;

            dealer = dealer.hit(nextCard());
            while (!hand.isFinal()) {
                if (!split && bestAction(hand, dealer) == Action.SPLIT) {
                    secondHand = hand.split();
                    while (!secondHand.isFinal()) {
                        secondHand = secondHand.applyAction(bestAction(secondHand, dealer), nextCard());
                    }
                    split = true;
                }
                hand = hand.applyAction(bestAction(hand, dealer), nextCard());
            }
            while (!dealer.isFinal()) {
                dealer = dealer.hit(nextCard());
            }
            if (split) {
                bankroll += secondHand.compare(dealer);
            }
            bankroll += hand.compare(dealer);
        }
    }

    public static void main(String[] args) {
        cachedEV = new HashMap();
        basicStrategy = new HashMap();

        PlayerHand emptyHand = new PlayerEmpty();
        DealerHand emptyDealer = new DealerEmpty();
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(14);
        df.setMaximumFractionDigits(14);
        
        System.out.println("Soft hands:");
        for(int i=13; i<=21; i++) {
            System.out.print(i + ": ");
            for(int j=2; j<=10; j++) {
                System.out.print(df.format(EV(PlayerHand.handWith2CardTotal(i, true), emptyDealer.hit(j))) + " ");
            }
            System.out.print(df.format(EV(PlayerHand.handWith2CardTotal(i, true), emptyDealer.hit(1))) + " ");
            System.out.println();
        }
        System.out.println();
        System.out.println("Hard hands:");
        for(int i=5; i<=19; i++) {
            System.out.print(i + ": ");
            for(int j=2; j<=10; j++) {
                System.out.print(df.format(EV(PlayerHand.handWith2CardTotal(i, false), emptyDealer.hit(j))) + " ");
            }
            System.out.print(df.format(EV(PlayerHand.handWith2CardTotal(i, false), emptyDealer.hit(1))) + " ");
            System.out.println();
        }
        System.out.println();
        System.out.println("Pairs:");
        for (int i = 1; i <= 10; i++) {
            PlayerHand hand = emptyHand.hit(i).hit(i);
            System.out.print(i + ": ");
            for (int j = 2; j <= 10; j++) {
                System.out.print(df.format(EV(hand, emptyDealer.hit(j))) + " ");
//                System.out.print(niceStr(bestAction(hand, emptyDealer.hit(j))) + " ");
            }
            System.out.print(df.format(EV(hand, emptyDealer.hit(1))) + " ");
            System.out.println();
        }
        System.out.println();
        System.out.println("Soft hands:");
        for(int i=13; i<=20; i++) {
            System.out.print(i + ": ");
            for(int j=2; j<=10; j++) {
                System.out.print(niceStr(bestAction(PlayerHand.handWith2CardTotal(i, true), emptyDealer.hit(j))) + " ");
            }
            System.out.print(niceStr(bestAction(PlayerHand.handWith2CardTotal(i, true), emptyDealer.hit(1))) + " ");
            System.out.println();
        }
        System.out.println();
        System.out.println();
        System.out.println("Hard hands:");
        for(int i=5; i<=19; i++) {
            System.out.print(i + ": ");
            for(int j=2; j<=10; j++) {
                System.out.print(niceStr(bestAction(PlayerHand.handWith2CardTotal(i, false), emptyDealer.hit(j))) + " ");
            }
            System.out.print(niceStr(bestAction(PlayerHand.handWith2CardTotal(i, false), emptyDealer.hit(1))) + " ");
            System.out.println();
        }
        System.out.println();
        System.out.println("Pairs:");
        for (int i = 1; i <= 10; i++) {
            PlayerHand hand = emptyHand.hit(i).hit(i);
            System.out.print(i + ": ");
            for (int j = 2; j <= 10; j++) {
                System.out.print(niceStr(bestAction(hand, emptyDealer.hit(j))) + " ");
//                System.out.print(niceStr(bestAction(hand, emptyDealer.hit(j))) + " ");
            }
            System.out.print(niceStr(bestAction(hand, emptyDealer.hit(1))));
            System.out.println();
        }
        System.out.println();
        System.out.println();
        double sum = 0;
        int n = 0;
        PlayerHand hand = emptyHand;
        DealerHand dealer = emptyDealer;
        for(int i=1; i<=10; i++) {
            for(int j=1; j<=10; j++) {
                for(int k=1; k<=10; k++) {
                    sum += (i == 10 ? 4 : 1) * (j == 10 ? 4 : 1) * (k == 10 ? 4 : 1) *
                            EV(hand.hit(i).hit(j), dealer.hit(k)) / ((double) 13 * 13 * 13);
                }
            }
        }
        System.out.println(df.format(sum));
        System.out.println(EV(hand.hit(1).hit(10), dealer.hit(10).hit(10)));
        
//        for(int i=1; i<=10; i++) {
//            System.out.print(i + ": ");
//            for(int j=1; j<=10; j++) {
//                PlayerHand inihand = hand.hit(i).hit(j);
//                if(i == j || inihand.isBlackjack())
//                    System.out.print("xx ");
//                else {
//                    for(int k=1; k<=10; k++) {
//                        if (EV(PlayerHand.handWith2CardTotal(((Player2Cards) inihand).getTotal(), ((Player2Cards) inihand).isSoft()), dealer.hit(k)) != 
//                            EV(inihand, dealer.hit(k))) {
//                            System.out.println("BUG!");
//                        }
//                    }
//                }
//            }
//            System.out.println();
//        }
//        System.out.println();
        
        System.out.println(df.format(EV(hand, dealer)));
        System.out.println(cachedEV.size());
    }
}
