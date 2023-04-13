package com.kenzie.app;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Play {
    //properties
    private Player p1;

    //constructor
    public Play(){
        p1 = new Player();
    }

    //methods

    //goal: write convert method that takes in a json String and returns a DTO
    public static DTO convert(String json){
        ObjectMapper mapper = new ObjectMapper();
        try {
            DTO dto = mapper.readValue(json,DTO.class);
            return dto;
        } catch (Exception e){
            System.out.println("something went wrong. " + e.getMessage());
            return null;
        }
    }

    //goal: write a method that takes in a list of Clues and a player and returns one singular clue that
        //isn't already in the player's questions list
    public static Clues getRandomClue(List<Clues> list, Player p1){
        //get a random number and then use that as the integer to get a random question in the list;
        int randomNumber = (int) (Math.random()*100);
        Clues randomClue = list.get(randomNumber);
        //while the clue's question is already in the player's list of questions, get a different clue
        while (p1.getQuestionsList().contains(randomClue.getQuestion())){
            randomClue = list.get(randomNumber);
        }
        return randomClue;
    }

    //GOAL: write a method that displays the category and the question of a specific clue that is given
    public static void printQuestion(Clues clue){
        String category = clue.getCategory().getTitle();
        String question = clue.getQuestion();
        System.out.println("Category: " + category);
        System.out.println("Question: " + question);
    }

    //GOAL: write a method that takes in a user's response and a clue that compares the
        //answers >> return true if the answer is correct and return false if the answer is wrong
    public static boolean gradeAnswer(String response, Clues question){
        if (response.equalsIgnoreCase(question.getAnswer())){
            return true;
        } else {
            return false;
        }
    }

    //GOAL: write a method that takes in a player, and uses the getter methods to display their results
    public static void printResults(Player p1){
        System.out.println("Loading up your results.\n");
        System.out.println("Name: " + p1.getName());
        System.out.println("Total questions: " + p1.getQuestionsList().size());
        if (p1.getQuestionsList().size() < 10) {
            System.out.println("You should play all the way through next time!\n");
        } else {
            System.out.println("You made it so far! I'm glad you're here.\n");
        }
                //add commentary for if they get a certain amount of answers right
        System.out.println("Correct Answers: " + p1.getScore());
        System.out.println("Incorrect Answers: " + p1.getWrongAns());
        if (p1.getScore() > p1.getWrongAns()) {
            System.out.println("(/^-^)/ Great job.");
            System.out.println("Quite lemon if you ask me!");
        } else if (p1.getScore() < p1.getWrongAns()) {
            System.out.println("Yikes. (o_o)");
            System.out.println("I bet if you try again, you'll do better.");
        } else {
            System.out.println("Solid. I guess.");
        }
        System.out.println("\n# - YOUR ANSWER | CORRECT ANSWER");
        for (int i = 0 ; i < p1.getQuestionsList().size(); i++){
            System.out.print((i+1 + " "));
            if (p1.getAnsList().get(i).equalsIgnoreCase("")) {
                System.out.print("*SKIPPED* ");
            } else {
                System.out.print(p1.getAnsList().get(i));
            }
            System.out.println("\t| " + p1.getCorrectAnsList().get(i));
        }
    }

    //GOAL: write a displayIntro method that takes in a Player and returns a personalized welcome message
        //and then asks if the player is ready to play
    public static void displayIntro(Player p1){
        Scanner scan = new Scanner(System.in);
        System.out.print("Hello. What is your name? ");
        p1.setName(scan.nextLine());
        System.out.println("Thanks, " + p1.getName());
        System.out.print("Would you like to play a game? Press 'Y' for yes or 'N' for no: " );
    }

    //GOAL: write a method that runs the game
    public static void runGame()  {
        //GOAL: runGame using the methods
        String json = CustomHttpClient.sendGET("https://jservice.kenzie.academy/api/clues");
        DTO dto = convert(json);
        List<Clues> cluesList = dto.getClues();
        Scanner scan = new Scanner(System.in);
        Player p1 = new Player();
        displayIntro(p1);
        String beginString = scan.nextLine();
        while (beginString.equals("") || (!beginString.equalsIgnoreCase("y") && !beginString.equalsIgnoreCase("n"))) {
                System.out.print("Hmm. That wasn't a yes or a no. \nPress 'Y' for yes or 'N' for no: ");
                beginString = scan.nextLine();
        }
        if (beginString.equalsIgnoreCase("y")) {
            System.out.println("\nAwesome " + p1.getName() + "! Let's play.");
            System.out.println("\n \nWelcome to RainCap! \nToday, we'll be playing some trivia. For every question" +
                    " you get correct, you will receive 1 point.\nAfter you answer 10 questions, your results will be displayed.\nIf at any time you want to STOP playing, please press" +
                    " 'Q' to quit.\n Your progress will not be saved.\n" +
                    "- - - - - - - - - - - - - - -\n");
            for (int i = 1; i <= 10; i++) {

                Clues q1 = getRandomClue(cluesList, p1);
                p1.addQuestionToList(q1.getQuestion());
                p1.addCorrectAns(q1.getAnswer());
                System.out.println(i + ". ");
                String response = p1.answerQuestion(q1);
                //if the response is "", quit the game
                //set i to 10
                if (response.equalsIgnoreCase("q")) {
                    System.out.println("************\nEnding game. Your progress will not be saved!");
                    i = 10;
                } else if(response.equalsIgnoreCase("")) {
                    System.out.println("\nQuestion skipped.\n");
                } else {
                    if (gradeAnswer(response, q1)) {
                        p1.setScore(p1.getScore() + 1);
                        System.out.println("\nYou got it right! Nice.");
                        System.out.println("Your new score: " + p1.getScore());
                        System.out.println();
                    } else {
                        p1.setWrongAns(p1.getWrongAns() + 1);
                        System.out.println("\nIncorrect! Try again.");
                        System.out.println("CORRECT ANSWER: " + q1.getAnswer());
                        System.out.println("Your current score: " + p1.getScore());
                        System.out.println();
                    }
                } //ends else
            } //ends for loop

            if (p1.getAnsList().size() < 10) {
                System.out.println("I see you have decided to quit. Sorry to see you go.");
                System.out.println("-~-~-~-~-~-~-~-~-~-~-~-~-~");
            }
        } else { //BIG else
            System.out.println("\nOh, okay.");
        }
            //create method that prints out the results of the game for the player
            printResults(p1);
            System.out.println("Thanks for playing RainCap, " + p1.getName() + "!");
    }//ends main

}//ends play
