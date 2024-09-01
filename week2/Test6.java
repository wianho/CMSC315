public class Test6 {
    public static void main(String[] args) {
        // Comment with delimiters { ( [
        String text = "String with delimiters } ) ]";
        if (text.length() > 0) {
            System.out.println("Complex case with nested delimiters");
            if (true) {
                System.out.println("Nested if statement");
            }
        }
    }
}
