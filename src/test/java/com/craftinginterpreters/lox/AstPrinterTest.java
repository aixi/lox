package com.craftinginterpreters.lox;

import org.junit.Assert;
import org.junit.Test;

public class AstPrinterTest {
    @Test
    public void testCase1() {
        Expr expr = new Expr.Binary(
                new Expr.Unary(
                        new Token(TokenType.MINUS, "-", null, 1),
                        new Expr.Literal(123)),
                new Token(TokenType.STAR, "*", null, 1),
                new Expr.Grouping(new Expr.Literal((45.67)))
        );
        String result = new AstPrinter().print(expr);
        Assert.assertEquals(result, "(* (- 123) (group 45.67))");
    }
}