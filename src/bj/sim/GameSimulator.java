/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bj.sim;

import bj.sim.hands.Hand;

/**
 *
 * @author vasil.kuzevski
 */
public class GameSimulator {

    private int numberOfPlayers; //including yourself
    private int positionOnTable; //start from 0
    private Rules rules;
    private Strategy[] strategies;
    private Counter counter;
    private BettingSystem bettingSystem;
    private Deck deck;
    private int bankroll;
    private int goal;
    private int maxHands;
    //session stops when you either reach the goal, lose the whole bankroll, or play maxHands hands

    public void setNumberOfPlayers(int numberOfPlayers) {
        if (numberOfPlayers < 0 || numberOfPlayers > 10) {
            throw new IllegalArgumentException();
        }
        this.numberOfPlayers = numberOfPlayers;
    }

    public void setPositionOnTable(int positionOnTable) {
        if (positionOnTable < 0 || positionOnTable > numberOfPlayers) {
            throw new IllegalArgumentException();
        }
        this.positionOnTable = positionOnTable;
    }

    public void setRules(Rules rules) {
        this.rules = rules;
    }

    public void setOwnStrategy(Strategy ownStrategy) {
        this.strategies[positionOnTable] = ownStrategy;
    }

    public void setStrategies(Strategy[] strategies) {
        this.strategies = strategies;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }

    public void setBettingSystem(BettingSystem bettingSystem) {
        this.bettingSystem = bettingSystem;
    }

    public void setBankroll(int bankroll) {
        this.bankroll = bankroll;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public void setMaxHands(int maxHands) {
        this.maxHands = maxHands;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public Rules getRules() {
        return rules;
    }

    public Strategy getOwnStrategy() {
        return strategies[positionOnTable];
    }

    public Strategy[] getStrategies() {
        return strategies;
    }

    public Counter getCounter() {
        return counter;
    }

    public BettingSystem getBettingSystem() {
        return bettingSystem;
    }

    public int getBankroll() {
        return bankroll;
    }

    public int getGoal() {
        return goal;
    }

    public int getMaxHands() {
        return maxHands;
    }

    private Card nextCard() {
        if (deck.hasNext()) {
            return deck.next();
        } else {
            deck.shuffle();
            deck.cut();
            for (int i = 0; i < rules.getNumberOfBurnedCards(); i++) {
                deck.burn();
            }
            counter.reset();
            return deck.next();
        }
    }
    
    public GameSimulation run() {
        GameSimulation simulation = new GameSimulation();
        Card c;
        
        int handsPlayed = 0;
        while (bankroll > 0 && bankroll < goal && handsPlayed < maxHands) {
            int betSize = bettingSystem.getBetSize(bankroll, counter);
            
            Hand dealersHand;
            Hand[] playersHands = new Hand[numberOfPlayers];
            
            c = nextCard();
            dealersHand = new Hand(c);
            counter.count(c);    
            for(int i=0; i<numberOfPlayers; i++) {
                c = nextCard();
                playersHands[i] = new Hand(c);
                counter.count(c);
            }
            for(int i=0; i<numberOfPlayers; i++) {
                c = nextCard();
                playersHands[i].hit(c);
                counter.count(c);
            }
            
            for(int i=0; i<numberOfPlayers; i++) {
                //do the strategy thing here
            }
            while(rules.dealerShouldDraw(dealersHand)) {
                c = nextCard();
                dealersHand.hit(c);
                counter.count(c);
            }
            //adjust bankroll
            //also  record stuff in simulation
        }

        return simulation;
    }
    
    GameSimulator(int bankroll, int goal, int maxHands) {
        numberOfPlayers = 1;
        positionOnTable = 0;
        rules = Rules.LE_GRAND;
        strategies = new Strategy[numberOfPlayers];
        for(int i=0; i<numberOfPlayers; i++)
            strategies[i] = Strategy.HIT_TO_17;
        counter = Counter.HILO;
        bettingSystem = BettingSystem.constantBet(2);
        deck = new Deck(rules.numberOfDecks(), rules.getShufflePoint());
        deck.shuffle();
        deck.cut();
        for (int i = 0; i < rules.getNumberOfBurnedCards(); i++) {
            deck.burn();
        }
        this.bankroll = bankroll;
        this.goal = goal;
        this.maxHands = maxHands;
    }
}
