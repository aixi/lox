package com.craftinginterpreters.tools;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class GenerateAst {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: generate _asr <output directory>");
            System.exit(64);
        }
        String outputDir = args[0];

        defineAst(outputDir, "Expr", Arrays.asList(
                "Binary    : Expr left, Token operator, Expr right",
                "Grouping  : Expr expression",
                "Literal   : Object value",
                "Unary     : Token operator, Expr right"
        ));
    }

//    package com.craftinginterpreters.lox;
//
//    abstract class Expr {
//        static class Binary extends com.craftinginterpreters.lox.Expr {
//            final com.craftinginterpreters.lox.Expr left;
//            final Token token;
//            final com.craftinginterpreters.lox.Expr right;
//
//            Binary(com.craftinginterpreters.lox.Expr left, Token token, com.craftinginterpreters.lox.Expr right) {
//                this.left = left;
//                this.token = token;
//                this.right = right;
//            }
//        }
//    }

    private static void defineAst(String outputDIr, String baseName, List<String> types) throws IOException {
        String path = outputDIr + "/" + baseName + ".java";
        try (PrintWriter writer = new PrintWriter(path, StandardCharsets.UTF_8)) {
            writer.println("package com.craftinginterpreters.lox;");
            writer.println();
            writer.println("import java.util.List;");
            writer.println();
            writer.println("abstract class " + baseName + " {");
            // The AST classes.
            for (String type : types) {
                String[] line = type.split(":");
                String className = line[0].trim();
                String fields = line[1].trim();
                defineType(writer, baseName, className, fields);
            }
            writer.println("}");
        }
    }

    // FIXME: Maybe return String, other than pass writer into
    private static void defineType(PrintWriter writer, String baseName, String className, String fields) {
        writer.println(" static class " + className + " extends " + baseName + " {");
        // Constructor
        writer.println("    " + className + "(" + fields + ") {");
        String[] parameterList = fields.split(", ");
        for (String param : parameterList) {
            String name = param.split(" ")[1];
            writer.println("        this." + name + " = " + name + ";");
        }
        writer.println("    }");
        // Class data members
        writer.println();
        for (String param : parameterList) {
            writer.println("    final " + param + ";");
        }
        writer.println("    }");
    }


}
