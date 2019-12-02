package Trees;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

class ExpressionTest {

    @Test
    void test() {
        Variable v= new Variable("X");

        LinkedList<Expression> testCases= new LinkedList<>();

        testCases.add(Expression.parseExpression("3*X^2"));
        testCases.add(Expression.parseExpression("sin(X)"));
        testCases.add(Expression.parseExpression("cos(X)^2"));
        testCases.add(Expression.parseExpression("arcsin(X + 3)"));

        for (Expression e : testCases) {
            System.out.println(" f(x)= " + e);
            System.out.println("f'(x)= " + e.differentiate(v));
            System.out.println("-------------------------");
        }
    }

}
