/*
 * Name: William Holt
 * Project: CMSC 315 - Binary Search Tree Implementation
 * Date: 24-SEP-2024
 * Description: This class defines a custom checked exception that handles syntax errors
 * in the preorder representation of a binary tree, including missing parentheses and non-integer values.
 */


public class SyntaxException extends Exception {
    public SyntaxException(String message) {
        super(message);
    }
}
