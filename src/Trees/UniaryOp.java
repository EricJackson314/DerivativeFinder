package Trees;

/** Represents an instance with a uniary operator (i.e. sin, cos, log, etc.) */
class UniaryOp extends ExpressionTree {

    /** Represents instances of uniary operators (i.e. sin, cos, log, etc. ) */
    enum Operator {
        NEGATIVE("-"), ABS("abs"),
        // trig
        SIN("sin"), COS("cos"), TAN("tan"), CSC("csc"), SEC("sec"), COT("cot"),
        // inverse trig
        ARCSIN("arcsin"), ARCCOS("arccos"), ARCTAN("arctan"),
        ARCCSC("arccsc"), ARCSEC("arcsec"), ARCCOT("arccot"),
        // log and exp
        LOG("log"), EXP("exp");

        private String value; // String representation of operation

        /** An instance with value v. */
        Operator(String v) {
            value= v;
        }

        /** Returns the operation represented by this operator */
        public @Override String toString() {
            return value;
        }

    }

    Operator op; // operator
    ExpressionTree rightOp; // right operand

    /** Constructor: An instance with operator op, and right operand rightOp */
    UniaryOp(Operator op, ExpressionTree rightOp) {
        this.op= op;
        this.rightOp= rightOp;
    }

    /** Returns the value of this tree */
    public @Override double eval() {
        // negative
        if (op == Operator.NEGATIVE) return -rightOp.eval();
        if (op == Operator.ABS) return Math.abs(rightOp.eval());
        // trig
        if (op == Operator.SIN) return Math.sin(rightOp.eval());
        if (op == Operator.COS) return Math.cos(rightOp.eval());
        if (op == Operator.TAN) return Math.tan(rightOp.eval());
        if (op == Operator.CSC) return 1 / Math.sin(rightOp.eval());
        if (op == Operator.SEC) return 1 / Math.cos(rightOp.eval());
        if (op == Operator.COT) return 1 / Math.tan(rightOp.eval());
        // inverse trig
        if (op == Operator.ARCSIN) return Math.asin(rightOp.eval());
        if (op == Operator.ARCCOS) return Math.acos(rightOp.eval());
        if (op == Operator.ARCTAN) return Math.atan(rightOp.eval());
        if (op == Operator.ARCCSC) return Math.asin(1 / rightOp.eval());
        if (op == Operator.ARCSEC) return Math.acos(1 / rightOp.eval());
        if (op == Operator.ARCCOT) return Math.atan(1 / rightOp.eval());
        // log and exp
        if (op == Operator.LOG) return Math.log(rightOp.eval());
        if (op == Operator.EXP) return Math.exp(rightOp.eval());

        throw new RuntimeException("Invalid Operator: " + op.toString());
    }

    /** Returns the in-order version of this expression. */
    public @Override String inOrder() {
        return op.toString() + rightOp.toString();
    }

    /** Returns the derivative of this expression with respect to v. */
    public @Override ExpressionTree differentiate(Variable v) {
        // negative
        if (op == Operator.NEGATIVE)
            return new UniaryOp(Operator.NEGATIVE, rightOp.differentiate(v));
        // abs
        if (op == Operator.ABS) throw new RuntimeException(
            "Invalid Derivative: Cannot compute derivative of absolute value.");

        ExpressionTree noChain= null; // derivative without the chain rule

        // trig
        if (op == Operator.SIN) noChain= new UniaryOp(Operator.COS, rightOp);

        if (op == Operator.COS)
            noChain= new UniaryOp(Operator.NEGATIVE, new UniaryOp(Operator.SIN, rightOp));

        if (op == Operator.TAN) noChain= new BinaryOp(new UniaryOp(Operator.SEC, rightOp),
            BinaryOp.Operator.EXPONENTIATE, new DoubleLeaf(2));

        if (op == Operator.CSC) noChain= new UniaryOp(Operator.NEGATIVE,
            new BinaryOp(new UniaryOp(Operator.CSC, rightOp),
                BinaryOp.Operator.MULTIPLY, new UniaryOp(Operator.COT, rightOp)));

        if (op == Operator.SEC) noChain= new BinaryOp(new UniaryOp(Operator.SEC, rightOp),
            BinaryOp.Operator.MULTIPLY, new UniaryOp(Operator.TAN, rightOp));

        if (op == Operator.COT) noChain= new UniaryOp(Operator.NEGATIVE,
            new BinaryOp(new UniaryOp(Operator.CSC, rightOp),
                BinaryOp.Operator.EXPONENTIATE, new DoubleLeaf(2)));

        // inverse trig
        if (op == Operator.ARCSIN)
            noChain= new BinaryOp(new DoubleLeaf(1), BinaryOp.Operator.DIVIDE,
                new BinaryOp(
                    new BinaryOp(new DoubleLeaf(1), BinaryOp.Operator.SUBTRACT,
                        new BinaryOp(rightOp, BinaryOp.Operator.EXPONENTIATE, new DoubleLeaf(2))),
                    BinaryOp.Operator.EXPONENTIATE, new DoubleLeaf(.5)));

        if (op == Operator.ARCCOS)
            noChain= new UniaryOp(Operator.NEGATIVE,
                new BinaryOp(new DoubleLeaf(1), BinaryOp.Operator.DIVIDE,
                    new BinaryOp(
                        new BinaryOp(new DoubleLeaf(1), BinaryOp.Operator.SUBTRACT,
                            new BinaryOp(rightOp, BinaryOp.Operator.EXPONENTIATE,
                                new DoubleLeaf(2))),
                        BinaryOp.Operator.EXPONENTIATE, new DoubleLeaf(.5))));

        if (op == Operator.ARCTAN)
            noChain= new BinaryOp(new DoubleLeaf(1), BinaryOp.Operator.DIVIDE,
                new BinaryOp(new DoubleLeaf(1), BinaryOp.Operator.ADD,
                    new BinaryOp(rightOp, BinaryOp.Operator.EXPONENTIATE, new DoubleLeaf(2))));

        if (op == Operator.ARCCSC) noChain= new UniaryOp(Operator.NEGATIVE,
            new BinaryOp(new DoubleLeaf(1), BinaryOp.Operator.DIVIDE,
                new BinaryOp(new UniaryOp(Operator.ABS, rightOp), BinaryOp.Operator.MULTIPLY,
                    new BinaryOp(
                        new BinaryOp(
                            new BinaryOp(rightOp, BinaryOp.Operator.EXPONENTIATE,
                                new DoubleLeaf(2)),
                            BinaryOp.Operator.SUBTRACT,
                            new DoubleLeaf(1)),
                        BinaryOp.Operator.EXPONENTIATE, new DoubleLeaf(.5)))));

        if (op == Operator.ARCSEC)
            noChain= new BinaryOp(new DoubleLeaf(1), BinaryOp.Operator.DIVIDE,
                new BinaryOp(new UniaryOp(Operator.ABS, rightOp), BinaryOp.Operator.MULTIPLY,
                    new BinaryOp(
                        new BinaryOp(
                            new BinaryOp(rightOp, BinaryOp.Operator.EXPONENTIATE,
                                new DoubleLeaf(2)),
                            BinaryOp.Operator.SUBTRACT,
                            new DoubleLeaf(1)),
                        BinaryOp.Operator.EXPONENTIATE, new DoubleLeaf(.5))));

        if (op == Operator.ARCCOT) noChain= new UniaryOp(Operator.NEGATIVE,
            new BinaryOp(new DoubleLeaf(1), BinaryOp.Operator.DIVIDE,
                new BinaryOp(new DoubleLeaf(1), BinaryOp.Operator.ADD,
                    new BinaryOp(rightOp, BinaryOp.Operator.EXPONENTIATE, new DoubleLeaf(2)))));

        // log and exp
        if (op == Operator.LOG)
            noChain= new BinaryOp(new DoubleLeaf(1), BinaryOp.Operator.DIVIDE, rightOp);

        if (op == Operator.EXP) noChain= new UniaryOp(Operator.EXP, rightOp);

        // never forget the chain rule
        return new BinaryOp(noChain, BinaryOp.Operator.MULTIPLY, rightOp.differentiate(v));
    }

    /** Returns true iff <br>
     * 1. ob is a Uniary tree <br>
     * 2. ob and this have the same operator <br>
     * 3. ob and this have the same right operand */
    public @Override boolean equals(Object ob) {
        if (ob == null || ob.getClass() != getClass()) return false;
        UniaryOp uob= (UniaryOp) ob;
        return uob.op == op && uob.rightOp == rightOp;
    }

}
