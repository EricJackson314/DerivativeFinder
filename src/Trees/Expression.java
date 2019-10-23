package Trees;

import Trees.BinaryOp.Operator;

/** An instance represents an expression */ // I'm thinking a wrapper class
public class Expression {
    ExpressionTree expression; // tree representation of the expression

    /** Constructor: */
    Expression(ExpressionTree e) {
        expression= e;
    }

    /** */
    Expression derivative(Variable v) {
        Expression temp= new Expression(expression.differentiate(v));
        temp.simplify();
        return temp;
    }

    /** Simplify this expression */
    void simplify() {

        // expression.simplify();

        if (expression.getClass() == BinaryOp.class) {
            BinaryOp bop= (BinaryOp) expression;

            // multiplication
            if (bop.op == Operator.MULTIPLY) {
                // by zero
                DoubleLeaf zero= new DoubleLeaf(0);
                if (bop.leftOp.equals(zero)) expression= zero;
                if (bop.rightOp.equals(zero)) expression= zero;

                // by one
                ExpressionTree one= new DoubleLeaf(1);
                if (bop.leftOp.equals(one)) {
                    expression= bop.rightOp;
                }
                if (bop.rightOp.equals(one)) {
                    expression= bop.leftOp;
                }
            }

            // addition
            if (bop.op == Operator.ADD) {
                // of zero
                DoubleLeaf zero= new DoubleLeaf(0);
                if (bop.leftOp.equals(zero)) expression= bop.rightOp;
                if (bop.rightOp.equals(zero)) expression= bop.leftOp;
            }

        }

        if (expression.getClass() == UniaryOp.class) {

        }

    }

    /** Representation of this expression: the toString of its ExpressionTree */
    public @Override String toString() {
        return expression.toString();
    }
}
