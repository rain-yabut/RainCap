package com.kenzie.app;

import java.util.Scanner;

public class Player {
    private int score, wrongAns;
    private String name;
    public Player(String name){
        this.name = name;
        score = 0;
    }
    public Player(){
        name = "you";
        score = 0;
    }

    public String answerQuestion(Clues clue) {
        Scanner scan = new Scanner(System.in);
        Play.printQuestion(clue);
        System.out.print("Please enter your answer: ");
        String response = scan.nextLine();
        return response;

    }

    public int getWrongAns() {
        return wrongAns;
    }

    public void setWrongAns(int wrongAns) {
        this.wrongAns = wrongAns;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
