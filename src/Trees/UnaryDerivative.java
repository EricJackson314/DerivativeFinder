package Trees;

public interface UnaryDerivative {
    /** Returns the derivative (with respect to Variable v) of a unary expression with operand o. */
    Expression differentiate(Expression o, Variable v);
}
