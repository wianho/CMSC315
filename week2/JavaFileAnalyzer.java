/*
 * Student Name: William Holt
 * Project: Matching Delimiters in Java Source Code
 * Date: 02SEP2024
 * Description: This class is responsible for reading a Java source file and providing 
 *              relevant characters, excluding those inside comments or string literals. 
 *              It also keeps track of the current line and character position.
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class JavaFileAnalyzer {
    private Scanner scanner;
    private String currentLine;
    private int lineNumber;
    private int charIndex;
    private boolean insideSingleLineComment;
    private boolean insideMultiLineComment;
    private boolean insideStringLiteral;

    // Constructor to initialize the file and Scanner
    public JavaFileAnalyzer(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + fileName);
        }
        scanner = new Scanner(file);
        lineNumber = 0;
        charIndex = -1;
        currentLine = "";
        insideSingleLineComment = false;
        insideMultiLineComment = false;
        insideStringLiteral = false;
    }

    // Method to get the next relevant character, skipping comments and string literals
    public char nextChar() {
        while (true) {
            if (charIndex == currentLine.length() - 1) {
                if (!scanner.hasNextLine()) {
                    return '\0'; // End of file
                }
                currentLine = scanner.nextLine();
                lineNumber++;
                charIndex = -1;
                insideSingleLineComment = false; // Reset single-line comment at the end of each line
            }
            charIndex++;

            char currentChar = currentLine.charAt(charIndex);

            // Update state based on the current character and context
            updateState(currentChar);

            // Skip over characters inside comments or string literals
            if (insideSingleLineComment || insideMultiLineComment || insideStringLiteral) {
                continue;
            }

            if (!Character.isWhitespace(currentChar)) {
                return currentChar;
            }
        }
    }

    // Method to return the current position in the file
    public String getPosition() {
        return "Line " + lineNumber + ", Char " + (charIndex + 1);
    }

    // Update the state to handle comments and string literals
    private void updateState(char currentChar) {
        // Check for entering or exiting a string literal
        if (currentChar == '"' && !insideSingleLineComment && !insideMultiLineComment) {
            insideStringLiteral = !insideStringLiteral;
            return;
        }

        // Check for entering a single-line comment
        if (currentChar == '/' && charIndex + 1 < currentLine.length() && currentLine.charAt(charIndex + 1) == '/' &&
                !insideStringLiteral && !insideMultiLineComment) {
            insideSingleLineComment = true;
            return;
        }

        // Check for entering or exiting a multi-line comment
        if (currentChar == '/' && charIndex + 1 < currentLine.length() && currentLine.charAt(charIndex + 1) == '*' &&
                !insideStringLiteral && !insideMultiLineComment) {
            insideMultiLineComment = true;
            return;
        }
        if (currentChar == '*' && charIndex + 1 < currentLine.length() && currentLine.charAt(charIndex + 1) == '/' &&
                insideMultiLineComment) {
            insideMultiLineComment = false;
            charIndex++; // Skip the closing '/' of the multi-line comment
            return;
        }
    }
}
