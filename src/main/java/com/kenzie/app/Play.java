package com.kenzie.app;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Scanner;

public class Play {
    private Player p1;

    public Play(){
        p1 = new Player();
    }

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

    public static Clues getRandomClue(List<Clues> list){
        //get a random number and then use that as the integer to get a random question in the list;
        int randomNumber = (int) (Math.random()*100);
        Clues randomClue = list.get(randomNumber);
        return randomClue;
    }

    public static void printQuestion(Clues clue){
        String category = clue.getCategory().getTitle();
        String question = clue.getQuestion();
        System.out.println("Category: " + category);
        System.out.println("Question: " + question);
    }

    public static boolean gradeAnswer(String response, Clues question){
        if (response.equalsIgnoreCase(question.getAnswer())){
            return true;
        } else {
            return false;
        }
    }

    public static void runGame()  {
        //Write main execution code here
        String json = CustomHttpClient.sendGET("https://jservice.kenzie.academy/api/clues");
        DTO dto = convert(json);
        List<Clues> cluesList = dto.getClues();
        Player p1 = new Player();

            System.out.println("\n \nWelcome to Rain's Capstone Game. \nToday, we'll be playing some trivia. For every question" +
                    " you get correct, you will receive 1 point.\nAfter you answer 10 questions, your results will be displayed.\nIf at any time you want to STOP playing, please press" +
                    " 'Q' to quit.\n Your progress will not be saved.\n" +
                    "- - - - - - - - - - - - - - -\n");


            //TODO: CHANGES TO BE MADE
            /*
            TODO: HANDLE EDGE CASES FOR THE SCANNER WHEN ASKING THE QUESTIONS
                CAPITALS
                BLANK/WHITESPACE
                JUST HITTING "ENTER" >> EMPTY STRING
            TODO: CHANGE THE WHILE LOOP TO AN IF STATEMENT

            TODO: make it so that if the question has been asked, then it can't be asked again
            USE A FOR LOOP -- ONLY ASK 10 QUESTIONS IN TOTAL, THEN RETURN THE SCORE AT THE END
            STILL USE THE 'QUIT' FEATURE, TO SHOW THEM THEIR RESULTS IF THEY QUIT OR THEY DON'T
             */
            //while (!(quitString.equalsIgnoreCase("q")) || ) {
            for (int i = 1; i <= 10; i++) {

                Clues q1 = getRandomClue(cluesList);
                System.out.println(i + ". ");
                String response = p1.answerQuestion(q1);
                if (gradeAnswer(response, q1)) {
                    p1.setScore(p1.getScore()+1);
                    System.out.println("You got it right! Nice.");
                    System.out.println("Your new score: " + p1.getScore());
                    System.out.println();
                } else {
                    p1.setScore(p1.getWrongAns()+1);
                    System.out.println("Incorrect! Try again.");
                    System.out.println("CORRECT ANSWER: " + q1.getAnswer());
                    System.out.println("Your current score: " + p1.getScore());
                    System.out.println();
                }
                }
            //ends for loop
            // }//ends while loop
            System.out.println("You have now reached the end of the game.\n" +
                    "-~-~-~-~-~-~-~-~-~-~-~-~-~\n" +
                    "Here's your results:\n" +
                    "You got " + p1.getWrongAns() + " questions incorrect " +
                    "\nand got " + p1.getScore() + " questions right.");
            //TODO: make it so that depending on how many they get right, it changes what you tell
            //TODO: at the end.
    }//ends main

}//ends play
