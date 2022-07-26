package com.craftinginterpreters.tools;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class GenerateAst {
    // cd com/craftinginterpreters
    // java ./tools/GenerateAst.java ./lox
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

    private static void defineAst(String outputDIr, String baseName, List<String> types) throws IOException {
        String path = outputDIr + "/" + baseName + ".java";
        try (PrintWriter writer = new PrintWriter(path, StandardCharsets.UTF_8)) {
            writer.println("package com.craftinginterpreters.lox;");
            writer.println();
            writer.println("import java.util.List;");
            writer.println();
            writer.println("abstract class " + baseName + " {");
            defineVisitor(writer, baseName, types);
            // The AST classes.
            for (String type : types) {
                String[] line = type.split(":");
                String className = line[0].trim();
                String fields = line[1].trim();
                defineType(writer, baseName, className, fields);
            }
            // The base accept() method.
            writer.println();
            writer.println("  abstract <R> R accept(Visitor<R> visitor);");
            writer.println("}");
        }
    }

    private static void defineVisitor(PrintWriter writer, String baseName, List<String> types) {
        writer.println("  interface Visitor<R> {");
        for (String type : types) {
            String typeName = type.split(":")[0].trim();
            writer.println("    R visit" + typeName + baseName + "(" + typeName + " " + baseName.toLowerCase() + ");");
        }
        writer.println("  }");
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
        // Visitor pattern.
        writer.println();
        writer.println("    @Override");
        writer.println("    <R> R accept(Visitor<R> visitor) {");
        writer.println("      return visitor.visit" + className + baseName + "(this);");
        writer.println("    }");
        // Class data members.
        writer.println();
        for (String param : parameterList) {
            writer.println("    final " + param + ";");
        }
        writer.println("    }");
    }


}
