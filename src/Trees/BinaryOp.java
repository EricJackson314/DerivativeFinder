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
        if (op == Operator.ADD)
            return new BinaryOp(leftOp.differentiate(v), Operator.ADD, rightOp.differentiate(v));

        if (op == Operator.SUBTRACT) return new BinaryOp(leftOp.differentiate(v), Operator.SUBTRACT,
            rightOp.differentiate(v));

        if (op == Operator.MULTIPLY)
            return new BinaryOp(new BinaryOp(leftOp, Operator.MULTIPLY, rightOp.differentiate(v)),
                Operator.ADD, new BinaryOp(leftOp.differentiate(v), Operator.MULTIPLY, rightOp));

        if (op == Operator.DIVIDE) return new BinaryOp(
            new BinaryOp(new BinaryOp(leftOp.differentiate(v), Operator.MULTIPLY, rightOp),
                Operator.SUBTRACT,
                new BinaryOp(leftOp, Operator.MULTIPLY, rightOp.differentiate(v))),
            Operator.DIVIDE, new BinaryOp(rightOp, Operator.EXPONENTIATE, new DoubleLeaf(2)));

        if (op == Operator.EXPONENTIATE)
            return new BinaryOp(new BinaryOp(leftOp, Operator.EXPONENTIATE, rightOp),
                Operator.MULTIPLY,
                new BinaryOp(
                    new BinaryOp(new BinaryOp(rightOp, Operator.DIVIDE, leftOp), Operator.MULTIPLY,
                        leftOp.differentiate(v)),
                    Operator.ADD, new BinaryOp(new UniaryOp(UniaryOp.Operator.LOG, leftOp),
                        Operator.MULTIPLY, rightOp.differentiate(v))));

        throw new RuntimeException("Invalid Operator: " + op.toString());
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
