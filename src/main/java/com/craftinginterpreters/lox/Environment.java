package com.craftinginterpreters.lox;

import java.util.HashMap;
import java.util.Map;

// The bindings that associate variables to values stored here
public class Environment {
    final Environment enclosing;
    private final Map<String, Object> values = new HashMap<>();
    // For global variable
    Environment() {
        enclosing = null;
    }

    Environment(Environment enclosing) {
        this.enclosing = enclosing;
    }
    // Our design don't check variable already present or not
    void define(String name, Object value) {
        values.put(name, value);
    }

    Object get(Token name) {
        if (values.containsKey(name.lexeme)) {
            return values.get(name.lexeme);
        }
        if (enclosing != null) {
            return enclosing.get(name);
        }
        throw new RuntimeError(name, "Undefined variable '" + name.lexeme + "'.");
    }

    void assign(Token name, Object value) {
        if (values.containsKey(name.lexeme)) {
            values.put(name.lexeme, value);
            return;
        }
        if (enclosing != null) {
            enclosing.assign(name, value);
        }
        throw new RuntimeError(name, "Undefined variable '" + name.lexeme + "'.");
    }
}