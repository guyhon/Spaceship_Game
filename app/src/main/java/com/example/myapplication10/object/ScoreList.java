package com.example.myapplication10.object;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreList {
    private String name = "";

    private ArrayList<Score> scores = new ArrayList<>();

    public ScoreList() {    }

    public String getName() {
        return name;
    }

    public ScoreList setName(String name) {
        this.name = name;
        return this;
    }

    public ArrayList<Score> getScores() {
        return scores;
    }

    public ScoreList setScores(ArrayList<Score> scores) {
        this.scores = scores;
        return this;
    }

    public void sortScores() {
        Collections.sort(scores);
    }

    public void addScore(Score score){
        this.scores.add(score);
        this.sortScores();
        if(this.scores.size() > 10){
            this.scores.remove(scores.size() - 1);
        }
    }

    @Override
    public String toString() {
        return "MovieList{" +
                "name='" + name + '\'' +
                ", movies=" + scores.toString() +
                '}';
    }
}
