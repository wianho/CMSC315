/*
 * Name: William Holt
 * Project: CMSC 315 - Binary Search Tree Implementation
 * Date: 24-SEP-2024
 * Description: This class defines a binary tree with methods to parse a preorder representation
 * of a tree, check if it's a binary search tree, check if it's balanced, print the tree in indented format,
 * and construct a balanced binary search tree from a set of values.
 */


import java.util.ArrayList;
import java.util.Stack;

public class BinaryTree {
    private Node root;

    // Node class representing a tree node
    private static class Node {
        int data;
        Node left, right;

        Node(int data) {
            this.data = data;
            left = right = null;
        }
    }

    // Constructor: accepts preorder string, parses, and builds the tree
    public BinaryTree(String preorderRepresentation) throws SyntaxException {
        root = parseTree(preorderRepresentation);
    }

    // Constructor: builds a balanced binary search tree from an array list of values
    public BinaryTree(ArrayList<Integer> values) {
        root = buildBalancedBST(values, 0, values.size() - 1);
    }

    // Parses the input string and constructs the binary tree
    private Node parseTree(String input) throws SyntaxException {
        if (input.isEmpty()) {
            throw new SyntaxException("Empty input string");
        }

        // Remove all spaces from the input for easier processing
        input = input.replaceAll("\\s+", "");

        // Call the recursive parsing function
        int[] index = {0};  // index array to maintain position during recursion
        return parseNode(input, index);
    }

    // Recursive function to parse a node
    private Node parseNode(String input, int[] index) throws SyntaxException {
        if (index[0] >= input.length()) {
            throw new SyntaxException("Incomplete tree");
        }

        // Expecting an opening parenthesis '(' or a number at the beginning
        if (input.charAt(index[0]) == '(') {
            index[0]++;  // move past '('
        } else {
            throw new SyntaxException("Missing left parenthesis");
        }

        // Get the number for the current node
        int numStart = index[0];
        while (index[0] < input.length() && Character.isDigit(input.charAt(index[0]))) {
            index[0]++;
        }
        if (numStart == index[0]) {
            throw new SyntaxException("Data is not an integer");
        }

        // Create a node with the parsed integer
        int value = Integer.parseInt(input.substring(numStart, index[0]));
        Node currentNode = new Node(value);

        // Parse left child
        if (input.charAt(index[0]) == '(' || input.charAt(index[0]) == '*') {
            if (input.charAt(index[0]) == '*') {
                index[0]++;  // move past '*'
                currentNode.left = null;
            } else {
                currentNode.left = parseNode(input, index);  // recurse for left child
            }
        } else {
            throw new SyntaxException("Invalid character or missing left child");
        }

        // Parse right child
        if (input.charAt(index[0]) == '(' || input.charAt(index[0]) == '*') {
            if (input.charAt(index[0]) == '*') {
                index[0]++;  // move past '*'
                currentNode.right = null;
            } else {
                currentNode.right = parseNode(input, index);  // recurse for right child
            }
        } else {
            throw new SyntaxException("Invalid character or missing right child");
        }

        // Expecting a closing parenthesis ')'
        if (input.charAt(index[0]) == ')') {
            index[0]++;  // move past ')'
        } else {
            throw new SyntaxException("Missing right parenthesis");
        }

        return currentNode;
    }

    // Builds a balanced BST from a sorted list of integers
    private Node buildBalancedBST(ArrayList<Integer> values, int start, int end) {
        if (start > end) return null;

        int mid = (start + end) / 2;
        Node node = new Node(values.get(mid));

        node.left = buildBalancedBST(values, start, mid - 1);
        node.right = buildBalancedBST(values, mid + 1, end);

        return node;
    }

    // Print tree in indented form
    public void printIndentedTree() {
        printIndentedTree(root, 0);
    }

    private void printIndentedTree(Node node, int depth) {
        if (node == null) return;

        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }
        System.out.println(node.data);
        printIndentedTree(node.left, depth + 1);
        printIndentedTree(node.right, depth + 1);
    }

    // Check if the tree is a binary search tree (BST)
    public boolean isBST() {
        return isBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean isBST(Node node, int min, int max) {
        if (node == null) return true;
        if (node.data <= min || node.data >= max) return false;
        return isBST(node.left, min, node.data) && isBST(node.right, node.data, max);
    }

    // Check if the tree is balanced
    public boolean isBalanced() {
        return isBalanced(root) != -1;
    }

    private int isBalanced(Node node) {
        if (node == null) return 0;

        int leftHeight = isBalanced(node.left);
        int rightHeight = isBalanced(node.right);

        if (leftHeight == -1 || rightHeight == -1 || Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }

        return Math.max(leftHeight, rightHeight) + 1;
    }

    // Returns the height of the tree
    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(Node node) {
        if (node == null) return 0;
        return Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    // Returns an array list of the tree's values (in-order traversal)
    public ArrayList<Integer> getValues() {
        ArrayList<Integer> values = new ArrayList<>();
        inOrderTraversal(root, values);
        return values;
    }

    private void inOrderTraversal(Node node, ArrayList<Integer> values) {
        if (node == null) return;
        inOrderTraversal(node.left, values);
        values.add(node.data);
        inOrderTraversal(node.right, values);
    }
}
