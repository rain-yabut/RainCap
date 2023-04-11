package com.kenzie.app;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    private int score, wrongAns;
    private String name;
    private ArrayList<String> questionsList, correctAnsList;
    private ArrayList<String> ansList;
    public Player(String name){
        this.name = name;
        score = 0;
        ansList = new ArrayList<String>();
        questionsList = new ArrayList<String>();
        correctAnsList = new ArrayList<String>();
    }
    public Player(){
        name = "you";
        score = 0;
        ansList = new ArrayList<String>();
        questionsList = new ArrayList<String>();
        correctAnsList = new ArrayList<String>();
    }

    public String answerQuestion(Clues clue) {
        Scanner scan = new Scanner(System.in);
        Play.printQuestion(clue);
        System.out.print("Please enter your answer: ");
        String response = scan.nextLine();
        ansList.add(response);
        return response;

    }

    public ArrayList<String> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(ArrayList<String> questionsList) {
        this.questionsList = questionsList;
    }

    public void addQuestionToList(String question){
        questionsList.add(question);
    }

    public void addCorrectAns(String ans){
        correctAnsList.add(ans);
    }

    public ArrayList<String> getCorrectAnsList() {
        return correctAnsList;
    }

    public void setCorrectAnsList(ArrayList<String> correctAnsList) {
        this.correctAnsList = correctAnsList;
    }

    public ArrayList<String> getAnsList() {
        return ansList;
    }

    public void setAnsList(ArrayList<String> ansList) {
        this.ansList = ansList;
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
