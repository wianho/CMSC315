/*
 * Student Name: William Holt
 * Project: Matching Delimiters in Java Source Code
 * Date: 02SEP2024
 * Description: This class contains the main method that checks for matching delimiters 
 *              in a Java source file by using a stack. It reads characters from 
 *              JavaFileAnalyzer and ensures all delimiters are properly matched.
 */


import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class DelimiterMatcher {
    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        JavaFileAnalyzer fileAnalyzer = null;

        // Keep asking for a valid file name until one is provided
        while (fileAnalyzer == null) {
            System.out.print("Enter the Java source file name: ");
            String fileName = inputScanner.nextLine();
            try {
                fileAnalyzer = new JavaFileAnalyzer(fileName);
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }

        Stack<Character> delimiterStack = new Stack<>();
        char currentChar;

        // Process the file character by character
        while ((currentChar = fileAnalyzer.nextChar()) != '\0') {
            if (isLeftDelimiter(currentChar)) {
                delimiterStack.push(currentChar);
            } else if (isRightDelimiter(currentChar)) {
                if (delimiterStack.isEmpty()) {
                    System.out.println("Unmatched " + currentChar + " at " + fileAnalyzer.getPosition());
                    return;
                }
                char lastDelimiter = delimiterStack.pop();
                if (!isMatchingPair(lastDelimiter, currentChar)) {
                    System.out.println("Mismatched " + lastDelimiter + " and " + currentChar + " at " + fileAnalyzer.getPosition());
                    return;
                }
            }
        }

        // Check if there are any unmatched left delimiters at the end
        if (!delimiterStack.isEmpty()) {
            System.out.println("Unmatched " + delimiterStack.pop() + " at end of file");
        } else {
            System.out.println("All delimiters matched correctly.");
        }
    }

    // Helper method to check if a character is a left delimiter
    private static boolean isLeftDelimiter(char ch) {
        return ch == '(' || ch == '{' || ch == '[';
    }

    // Helper method to check if a character is a right delimiter
    private static boolean isRightDelimiter(char ch) {
        return ch == ')' || ch == '}' || ch == ']';
    }

    // Helper method to check if a pair of delimiters match
    private static boolean isMatchingPair(char left, char right) {
        return (left == '(' && right == ')') || 
               (left == '{' && right == '}') || 
               (left == '[' && right == ']');
    }
}
