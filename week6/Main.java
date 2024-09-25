/*
 * Name: William Holt
 * Project: CMSC 315 - Binary Search Tree Implementation
 * Date: 24-SEP-2024
 * Description: This class handles the main user interaction. It takes user input to create
 * a binary tree from a preorder string, checks if it's a balanced binary search tree,
 * and constructs a balanced tree if necessary. It also handles error checking for input syntax.
 */


import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;
        boolean moreTrees = true;

        while (moreTrees) {
            try {
                System.out.print("Enter a binary tree: ");
                input = scanner.nextLine();
                BinaryTree tree = new BinaryTree(input);

                tree.printIndentedTree();

                if (!tree.isBST()) {
                    System.out.println("It is not a binary search tree");
                } else if (tree.isBalanced()) {
                    System.out.println("It is a balanced binary search tree");
                } else {
                    System.out.println("It is a binary search tree but it is not balanced");
                    ArrayList<Integer> values = tree.getValues();
                    BinaryTree balancedTree = new BinaryTree(values);
                    balancedTree.printIndentedTree();
                    System.out.println("Original tree has height " + tree.getHeight());
                    System.out.println("Balanced tree has height " + balancedTree.getHeight());
                }
            } catch (SyntaxException e) {
                System.out.println("Syntax Error: " + e.getMessage());
            }

            System.out.print("More trees? Y or N: ");
            moreTrees = scanner.nextLine().equalsIgnoreCase("Y");
        }

        scanner.close();
    }
}
