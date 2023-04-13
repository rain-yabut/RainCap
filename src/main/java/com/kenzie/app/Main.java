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



    public static void main(String[] args) {
        //Write main execution code here
        Play.runGame();
    }

}

