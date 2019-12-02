package Trees;

/** An instance represents an expression tree. */
public abstract class Expression {

    /** Return the value of this tree */
    abstract double eval();

    /** Return the in-order of this tree, with binary operations parenthesized. */
    abstract String inOrder();

    /** Returns the derivative of this tree with respect to variable v. */
    abstract Expression differentiate(Variable v);

    /** Represents this expression tree-- its in-order. */
    public @Override String toString() {
        return inOrder();
    }

    /** parses an Expression Tree from String Input. */
    static Expression parseExpression(String str) {
        str= noWhiteSpace(str);

        /**********************************************************************/
        // Binary Operations
        /**********************************************************************/
        // Add and Subtract
        int plsmns= indexOfPlsMns(str);
        if (plsmns != -1) {

            Character op= str.charAt(plsmns);
            if (op == '+')
                return new Add(parseExpression(str.substring(0, plsmns)),
                    parseExpression(str.substring(plsmns + 1, str.length())));

            // differentiate between negative and minus
            if (plsmns != 0)
                return new Sub(parseExpression(str.substring(0, plsmns)),
                    parseExpression(str.substring(plsmns + 1, str.length())));
        }

        // Multiply and Divide
        int muldiv= indexOfMulDiv(str);
        if (muldiv != -1) {

            Character op= str.charAt(muldiv);
            if (op == '*')
                return new Mul(parseExpression(str.substring(0, muldiv)),
                    parseExpression(str.substring(muldiv + 1, str.length())));

            return new Div(parseExpression(str.substring(0, muldiv)),
                parseExpression(str.substring(muldiv + 1, str.length())));
        }

        // Exponentials
        int exp= indexOfExp(str);
        if (exp != -1) {

            Character op= str.charAt(exp);
            if (op == '^')
                return new Pow(parseExpression(str.substring(0, exp)),
                    parseExpression(str.substring(exp + 1, str.length())));
        }

        // Parenthesis
        if (str.charAt(0) == '(' && str.charAt(str.length() - 1) == ')')
            return parseExpression(str.substring(1, str.length() - 1));

        /**********************************************************************/
        // Uniary Operations
        /**********************************************************************/
        if (str.substring(0, 1).equals("-"))
            return new Neg(parseExpression(str.substring(1)));

        if (str.length() > 3) {
            if (str.substring(0, 3).equals("abs"))
                return new Abs(parseExpression(str.substring(3)));

            if (str.substring(0, 3).equals("log"))
                return new Log(parseExpression(str.substring(3)));

            if (str.substring(0, 3).equals("exp"))
                return new Exp(parseExpression(str.substring(3)));

            if (str.substring(0, 3).equals("sin"))
                return new Sin(parseExpression(str.substring(3)));

            if (str.substring(0, 3).equals("cos"))
                return new Cos(parseExpression(str.substring(3)));

            if (str.substring(0, 3).equals("tan"))
                return new Tan(parseExpression(str.substring(3)));
            if (str.substring(0, 3).equals("csc"))
                return new Csc(parseExpression(str.substring(3)));

            if (str.substring(0, 3).equals("sec"))
                return new Sec(parseExpression(str.substring(3)));

            if (str.substring(0, 3).equals("cot"))
                return new Cot(parseExpression(str.substring(3)));

        }

        if (str.length() > 6) {
            if (str.substring(0, 6).equals("arcsin"))
                return new ArcSin(parseExpression(str.substring(6)));
            if (str.substring(0, 6).equals("arccos"))
                return new ArcCos(parseExpression(str.substring(6)));

            if (str.substring(0, 6).equals("arctan"))
                return new ArcTan(parseExpression(str.substring(6)));

            if (str.substring(0, 6).equals("arccsc"))
                return new ArcCsc(parseExpression(str.substring(6)));

            if (str.substring(0, 6).equals("arcsec"))
                return new ArcSec(parseExpression(str.substring(6)));

            if (str.substring(0, 6).equals("arccot"))
                return new ArcCot(parseExpression(str.substring(6)));

        }

        /*********************************************************************/
        // Doubles
        /**********************************************************************/
        try {
            double value= Double.parseDouble(str);
            return new DblLeaf(value);
        } catch (NumberFormatException e) {

        }

        /**********************************************************************/
        // Variables
        /**********************************************************************/
        Variable var= new Variable(str);
        return new VarLeaf(var);

    }

    /** Removes all whitespace */
    private static String noWhiteSpace(String str) {
        String ret= "";

        for (int i= 0; i < str.length(); i++ ) {
            String s= str.substring(i, i + 1);
            if (!s.equals(" ")) ret+= s;
        }

        return ret;
    }

    /** Returns the index of the last '+' or '-' outside parenthesis. If these characters are not
     * present, returns -1 */
    private static int indexOfPlsMns(String str) {

        int countParens= 0;

        for (int i= str.length() - 1; i >= 0; i-- ) {
            Character s= str.charAt(i);
            if (s.equals(')')) countParens++ ;
            if (s.equals('(')) countParens-- ;
            if ((s == '+' || s == '-') && countParens == 0) return i;
        }

        return -1;
    }

    /** Returns the index of the last '*' or '/' outside parenthesis. If these characters are not
     * present, returns -1 */
    private static int indexOfMulDiv(String str) {

        int countParens= 0;

        for (int i= str.length() - 1; i >= 0; i-- ) {
            Character s= str.charAt(i);
            if (s.equals(')')) countParens++ ;
            if (s.equals('(')) countParens-- ;
            if ((s == '*' || s == '/') && countParens == 0) return i;
        }

        return -1;
    }

    /** Returns the index of the last '^' outside parenthesis. If this character is not present,
     * returns -1 */
    private static int indexOfExp(String str) {

        int countParens= 0;

        for (int i= str.length() - 1; i >= 0; i-- ) {
            Character s= str.charAt(i);
            if (s.equals(')')) countParens++ ;
            if (s.equals('(')) countParens-- ;
            if (s == '^' && countParens == 0) return i;
        }

        return -1;
    }

}