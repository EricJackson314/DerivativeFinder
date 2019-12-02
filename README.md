# DerivativeFinder

Inspired by a lecture given in CS 2110 (Cornell University) by Professor Anne Bracy about expression trees.

This program can parse expressions from string input and then differentiate them.

Example Usage:

```java
Expression expr= Expression.parseExpression("3*X^2);

System.out.println(expr);
System.out.println(expr.differentiate(new Variable("X"));
```

Output:
```
sin(3.0*X)
(cos(3.0*x)*((0.0*x)+(3.0*0.0)))
```

See ExpressionTest.java for more examples.

Obviously writing a simplify method is now at the top of the todo list.
