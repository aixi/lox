package com.craftinginterpreters.lox;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ScannerTest {

    @Before
    public void setUp() {
        System.out.println("Scanner Test before");
    }

    @After
    public void tearDown() {
        System.out.println("Scanner Test after");
    }

    @Test
    public void testCase1() {
        String source = """
                var a = "hello, world";
                print a;
                """;
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();
        Assert.assertEquals(tokens.size(), 9);
        Assert.assertEquals(tokens.get(0).type, TokenType.VAR);
        Assert.assertEquals(tokens.get(1).type, TokenType.IDENTIFIER);
        Assert.assertEquals(tokens.get(2).type, TokenType.EQUAL);
        Assert.assertEquals(tokens.get(3).type, TokenType.STRING);
        Assert.assertEquals(tokens.get(4).type, TokenType.SEMICOLON);
        Assert.assertEquals(tokens.get(5).type, TokenType.PRINT);
        Assert.assertEquals(tokens.get(6).type, TokenType.IDENTIFIER);
        Assert.assertEquals(tokens.get(7).type, TokenType.SEMICOLON);
        Assert.assertEquals(tokens.get(8).type, TokenType.EOF);
    }
}