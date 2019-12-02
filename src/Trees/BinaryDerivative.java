package Trees;

public interface BinaryDerivative {
    /** Returns the derivative (with respect to variable v) of a Binary Expression with operands l
     * and r. */
    Expression differentiate(Expression l, Expression r, Variable v);
}
