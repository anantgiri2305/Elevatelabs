package task8;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class QuizApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Define quiz questions
        List<QuizQuestion> questions = Arrays.asList(
            new QuizQuestion("What is the capital of France?",
                Arrays.asList("1. Berlin", "2. Madrid", "3. Paris", "4. Rome"), 3),

            new QuizQuestion("Which planet is known as the Red Planet?",
                Arrays.asList("1. Earth", "2. Mars", "3. Jupiter", "4. Venus"), 2),

            new QuizQuestion("Who wrote 'Romeo and Juliet'?",
                Arrays.asList("1. Charles Dickens", "2. William Shakespeare", "3. Mark Twain", "4. Jane Austen"), 2)
        );

        int score = 0;

        System.out.println("Welcome to the Quiz!");
        System.out.println("------------------------\n");

        // Loop through each question
        for (int i = 0; i < questions.size(); i++) {
            QuizQuestion q = questions.get(i);
            System.out.println("Q" + (i + 1) + ": " + q.getQuestion());

            for (String option : q.getOptions()) {
                System.out.println(option);
            }

            System.out.print("Your answer (1-4): ");
            int answer = scanner.nextInt();

            if (answer == q.getCorrectOption()) {
                System.out.println("Correct!\n");
                score++;
            } else {
                System.out.println("Wrong! Correct answer is: " + q.getCorrectOption() + "\n");
            }
        }

        System.out.println("Quiz Complete!");
        System.out.println("Your Score: " + score + "/" + questions.size());

        scanner.close();
    }
}
