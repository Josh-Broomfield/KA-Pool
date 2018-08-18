package com.b.m.josh.keithdrewpool;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class KALogic {
    private ArrayList<Player> players = new ArrayList<>();
    private List<Integer> usedBalls = new ArrayList<>();
    private final int minBall = 1;
    private final int maxBall = 15;

    public KALogic() {

    }

    public void resetGame() {
        players.clear();
        usedBalls.clear();
    }

    public void getNewBall(int id) throws Exception {
        Player p = findPlayer(id);
        p.setCurrentBall(getNewBall());
        updatePlayer(p);
    }

    public Player getNewBall(Player p) throws Exception {
        p.setCurrentBall(getNewBall());

        return p;
    }

    //Grabs an unused ball within min-max and adds it to the list of used balls
    public int getNewBall() throws Exception {
        Random r = new Random();
        int newBall = 0;

        if(usedBalls.size() >= maxBall - minBall + 1) {
            throw new Exception("No more balls!");
        }

        do {
            newBall = r.nextInt(maxBall - minBall + 1) + minBall;
        } while(usedBalls.contains(newBall));
        usedBalls.add(newBall);

        return newBall;
    }

    public void updatePlayer(Player p) {
        players.set(findIndex( p.getId() ), p);
    }

    private int findIndex(int id) {
        int pPos = -1;
        for(int i = 0; i < players.size(); ++i) {
            if(players.get(i).getId() == id) {
                pPos = i;
                break;
            }
        }
        return pPos;
    }

    public Player findPlayer(int id) {
        int i = findIndex(id);
        Player p = i >= 0 ? players.get(i) : null;

        return p;
    }

    public void addPlayer(Player newPlayer) {
        players.add(newPlayer);
    }
}