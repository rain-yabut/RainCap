package com.kenzie.app;

// import necessary libraries


import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Scanner;

public class Main {
    /* Java Fundamentals Capstone project:
       - Define as many variables, properties, and methods as you decide are necessary to
       solve the program requirements.
       - You are not limited to only the class files included here
       - You must write the HTTP GET call inside the CustomHttpClient.sendGET(String URL) method
         definition provided
       - Your program execution must run from the main() method in Main.java
       - The rest is up to you. Good luck and happy coding!

     */

    /*
    THE PLAN:

    1. Use an HTTP GET request to pull questions from the API
        need to work with the API
            need to get the json
            need DTO to create a list of questions
            question is its own object?
                Properties: category, answer?
    2. Present a single question to the user. For each question, also display the category.
        need variables for question, category, and for the answer
        askQuestion method?
    3. Allow the user to respond to the question
        need scanner
    4. Determine if the user's answer was correct
        compare to answer
        MUST HANDLE THE EDGE CASES
            what if they use spaces or something?
            what if they capitalize weird letters
    5. A correct answer awards one (1) point and continues the game
        int score
      - An incorrect answer does not update the score. The game continues.
      - Display a message indicating whether the answer was correct or incorrect.
      - If the answer is incorrect, display the correct answer.
    9. Keep track of and display a user's score
     */
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
        System.out.print("Please Enter Answer: ");
    }

    public static boolean gradeAnswer(String response, Clues question){
        if (response.equalsIgnoreCase(question.getAnswer())){
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args)  {
        //Write main execution code here
        int score = 0;
        int wrongAns = 0;
        String quitString = "";
        String json = CustomHttpClient.sendGET("https://jservice.kenzie.academy/api/clues");
        Scanner scan = new Scanner(System.in);
        String start = "";
        DTO dto = convert(json);
        List<Clues> cluesList = dto.getClues();
        System.out.print("Hello. \nWould you like to play a game? Please enter 'Y' for yes or 'N' for no: ");
        start = scan.nextLine();
        while (!(start.equalsIgnoreCase("y")) && !(start.equalsIgnoreCase("n"))) {
            System.out.print("That is not a valid response. Please enter 'Y' for yes or 'N' for no: ");
            start = scan.nextLine();
        }


        if (start.equalsIgnoreCase("y")) {
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
                printQuestion(q1);
                String response = scan.nextLine();
                if (response.equalsIgnoreCase("q")) {
                    quitString = "q";
                } else {
                    if (gradeAnswer(response, q1)) {
                        score++;
                        System.out.println("You got it right! Nice.");
                        System.out.println("Your new score: " + score);
                        System.out.println();
                    } else {
                        wrongAns++;
                        System.out.println("Incorrect! Try again.");
                        System.out.println("CORRECT ANSWER: " + q1.getAnswer());
                        System.out.println("Your current score: " + score);
                        System.out.println();
                    }
                }
            }//ends for loop
       // }//ends while loop
        if (quitString.equalsIgnoreCase("q")) {
            System.out.println("Sorry to see you go.");
        }
            System.out.println("You have now reached the end of the game.\n" +
                    "-~-~-~-~-~-~-~-~-~-~-~-~-~\n" +
                    "Here's your results:\n" +
                    "You got " + wrongAns + " questions incorrect " +
                    "\nand got " + score + " questions right. Thanks for playing.");
        //TODO: make it so that depending on how many they get right, it changes what you tell
            //TODO: at the end.

        } //ends your BIG if statement
        else {
            System.out.println("Too bad. It's a fun game! Run the program again to play.");
        } //ends if they say 'N' for no
    }//ends main



}

