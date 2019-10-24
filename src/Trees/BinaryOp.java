package Trees;

/** Represents an instance with operators: + - * / ^ */
class BinaryOp extends ExpressionTree {

    /** Represents an instance of one of the operators: + - * / ^ */
    enum Operator {
        ADD("+"), SUBTRACT("-"), MULTIPLY("*"), DIVIDE("/"), EXPONENTIATE("^");

        private String value; // String representation of operation

        /** Constructor: an instance with the value v */
        Operator(String v) {
            value= v;
        }

        /** Returns the operation represented by this operator. */
        @Override
        public String toString() {
            return value;
        }
    }

    Operator op; // the operator
    ExpressionTree leftOp; // the left operand
    ExpressionTree rightOp; // the right operand

    /** Constructor: an instance with left operand left, operator op, and right operand right. */
    BinaryOp(ExpressionTree leftOp, Operator op, ExpressionTree rightOp) {
        this.leftOp= leftOp;
        this.op= op;
        this.rightOp= rightOp;
    }

    /** Returns the value of this tree */
    public @Override double eval() {
        if (op == Operator.ADD) return leftOp.eval() + rightOp.eval();
        if (op == Operator.SUBTRACT) return leftOp.eval() - rightOp.eval();
        if (op == Operator.MULTIPLY) return leftOp.eval() * rightOp.eval();
        if (op == Operator.DIVIDE) return leftOp.eval() / rightOp.eval();
        if (op == Operator.EXPONENTIATE) return Math.pow(leftOp.eval(), rightOp.eval());

        throw new RuntimeException("Invalid Operator: " + op.toString());
    }

    /** Returns the in-order version of this expression, parenthesized. */
    public @Override String inOrder() {
        return "(" + leftOp.toString() + " " + op.toString() + " " + rightOp.toString() + ")";
    }

    /** returns the derivative of this tree with respect to v. */
    public @Override ExpressionTree differentiate(Variable v) {

        ExpressionTree deriv= new BinaryOp(leftOp, op, rightOp);

        if (op == Operator.ADD)
            deriv= new BinaryOp(leftOp.differentiate(v), Operator.ADD, rightOp.differentiate(v));

        if (op == Operator.SUBTRACT) deriv= new BinaryOp(leftOp.differentiate(v), Operator.SUBTRACT,
            rightOp.differentiate(v));

        if (op == Operator.MULTIPLY)
            deriv= new BinaryOp(new BinaryOp(leftOp, Operator.MULTIPLY, rightOp.differentiate(v)),
                Operator.ADD, new BinaryOp(leftOp.differentiate(v), Operator.MULTIPLY, rightOp));

        if (op == Operator.DIVIDE) return new BinaryOp(
            deriv= new BinaryOp(new BinaryOp(leftOp.differentiate(v), Operator.MULTIPLY, rightOp),
                Operator.SUBTRACT,
                new BinaryOp(leftOp, Operator.MULTIPLY, rightOp.differentiate(v))),
            Operator.DIVIDE, new BinaryOp(rightOp, Operator.EXPONENTIATE, new DoubleLeaf(2)));

        if (op == Operator.EXPONENTIATE)
            deriv= new BinaryOp(new BinaryOp(leftOp, Operator.EXPONENTIATE, rightOp),
                Operator.MULTIPLY,
                new BinaryOp(
                    new BinaryOp(new BinaryOp(rightOp, Operator.DIVIDE, leftOp), Operator.MULTIPLY,
                        leftOp.differentiate(v)),
                    Operator.ADD, new BinaryOp(new UniaryOp(UniaryOp.Operator.LOG, leftOp),
                        Operator.MULTIPLY, rightOp.differentiate(v))));

        return deriv.simplify();
    }

    /** Returns a simplified version of this tree */
    public @Override ExpressionTree simplify() {

        // simplify the left and right side of the expression first.
        ExpressionTree lop= leftOp.simplify(); // left
        ExpressionTree rop= rightOp.simplify(); // right

        // lop and rop are both double leaves.
        if (lop.getClass() == DoubleLeaf.class && rop.getClass() == DoubleLeaf.class)
            return new DoubleLeaf(eval());

        // for reference. we will compare trees to these.
        ExpressionTree zero= new DoubleLeaf(0);
        ExpressionTree one= new DoubleLeaf(1);

        if (rop.equals(zero)) {
            // x + 0 = x
            // x - 0 = x
            if (op == Operator.ADD || op == Operator.SUBTRACT) return lop;

            // x * 0 = 0
            if (op == Operator.MULTIPLY) return zero;

            // x^0 = 1
            if (op == Operator.EXPONENTIATE) return one;
        }

        if (lop.equals(zero)) {
            // 0 + x = x
            if (op == Operator.ADD) return rop;

            // 0 - x = -x
            if (op == Operator.SUBTRACT) return new UniaryOp(UniaryOp.Operator.NEGATIVE, rop);

            // 0 * x = 0
            // 0 / x = 0
            if (op == Operator.MULTIPLY || op == Operator.DIVIDE) return zero;

            // 0 ^ x = 0
            if (op == Operator.EXPONENTIATE) return zero;

        }

        // x + -y = x - y
        // -x + y = y - x

        if (lop.equals(one)) {
            // 1 * x = x
            if (op == Operator.EXPONENTIATE) return rop;

            // 1 / x = x^-1
            // IS THIS ACTUALLY SIMPLER???
            if (op == Operator.DIVIDE)
                return new BinaryOp(rop, Operator.EXPONENTIATE, new DoubleLeaf(-1));
        }

        if (rop.equals(one)) {
            // x * 1 = x
            // x / 1 = x
            // x^1 = x
            if (op == Operator.MULTIPLY || op == Operator.DIVIDE || op == Operator.EXPONENTIATE)
                return lop;

        }

        // no simplifying needed!
        return new BinaryOp(lop, op, rop);
    }

    /** returns true iff <br>
     * 1. ob is a BinaryOp <br>
     * 2. ob and this have the same operator <br>
     * 3. ob and this have the same left and right operands */
    public @Override boolean equals(Object ob) {
        if (ob == null || ob.getClass() != getClass()) return false;
        BinaryOp bob= (BinaryOp) ob;
        return op == bob.op && leftOp == bob.leftOp && rightOp == bob.rightOp;
    }

}
