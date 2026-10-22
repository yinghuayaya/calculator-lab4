package com.yi;

import java.util.Locale;

public class App {

    public static void main(String[] args) {
        Calculator c = new Calculator();
        if (args.length == 0) {
            // Run real calculations so a default container run is self-explanatory.
            for (String operator : new String[]{"+", "-", "*", "/"}) {
                System.out.printf(Locale.ROOT, "12 %s 3 = %.1f%n", operator,
                        c.eval("12", operator, "3"));
            }
            return;
        }
        if (args.length != 3) {
            System.err.println("Usage: java -jar calculator.jar <number> <operator> <number>");
            System.exit(2);
        }
        // Preserve the original CLI contract: three arguments, one numeric result.
        System.out.println(c.eval(args[0], args[1], args[2]));
    }
}
