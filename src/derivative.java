
/** */
public class derivative {

    /** Parenthesis */
    public static String derive(String expression) {

        int len= expression.length();

        // SUBTRACTION AND ADDITION
        for (int i= 0; i < len; i++ ) {
            int parenthesisCount= 0;
            if (expression.charAt(i) == '(') parenthesisCount++ ;
            if (expression.charAt(i) == ')') parenthesisCount-- ;
            // subtraction
            if (parenthesisCount == 0 && expression.charAt(i) == '-') {
                return derive(expression.substring(0, i)) + "-" +
                    derive(expression.substring(i + 1, len));
            }
            // addition
            if (parenthesisCount == 0 && expression.charAt(i) == '+') {
                return derive(expression.substring(0, i)) + "+" +
                    derive(expression.substring(i + 1, len));
            }
        }
        // DIVISION AND MULTIPLIATION
        for (int i= 0; i < len; i++ ) {
            int parenthesisCount= 0;
            if (expression.charAt(i) == '(') parenthesisCount++ ;
            if (expression.charAt(i) == ')') parenthesisCount-- ;
            // division
            String one= expression.substring(0, i);
            String two= expression.substring(i + 1, len);
            if (parenthesisCount == 0 && expression.charAt(i) == '/') {
                return "((" + two + "*" + derive(one) + "+" + one + "*" + derive(two) + ")/(" +
                    two + ")^2)";
            }
            // multiplication
            if (parenthesisCount == 0 && expression.charAt(i) == '*') {
                return "(" + one + "*" + derive(two) + "+" + two + "*" + derive(one) + ")";
            }
        }

        return expression + "'";
    }
}
