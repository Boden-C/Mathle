package Src;

import java.lang.Math;
import java.util.Scanner;

enum Operator {
    ADD,
    SUBTRACT,
    DIVIDE,
    MULTIPLY
}

public class Mathle {
    private final int length;
    private Equation equation;
    private boolean log;
    Scanner myObj = new Scanner(System.in);

    public Mathle(int length) {
        log = false;
        if (length < 3) {
            this.length = 3;
            System.out.println("Smallest length is 3");
        } else {
            this.length = length;
        }
        equation = new Equation();
        while (true) {
            try {
                create(length, 0.4);
            } catch (Exception e) {
                if (log) {
                    System.out.println("Restarting...");
                }
                continue;
            }
            break;
        }
    }

    // disclaimer: everything below is made by a very sleep deprived Boden. Please
    // don't look.

    public void create(int length, Double tensChance) throws Exception {
        equation = new Equation();
        boolean isAtOperator = false;
        for (int i = 0; i < length - 3; i++) {
            if (isAtOperator) {
                findOperator(new OperatorGenerator(equation));
                isAtOperator = !isAtOperator;
                continue;
            }
            if (Math.random() > tensChance || i > length - 5 ||
                    (equation.getCurrentValue() < 10 &&
                            (equation.getLastOperator().equals(Operator.SUBTRACT)
                                    || equation.getLastOperator().equals((Operator.DIVIDE))))) {
                try {
                    findNumber(new OneDigitGenerator(equation));
                } catch (Exception e) {
                    if (log)
                        System.out.println("Trying 2 digits...");
                    findNumber(new TwoDigitGenerator(equation));
                }
            } else {
                try {
                    findNumber(new TwoDigitGenerator(equation));
                } catch (Exception e) {
                    if (log)
                        System.out.println("Trying 1 digit...");
                    findNumber(new OneDigitGenerator(equation));
                }
                i++;
            }
            isAtOperator = !isAtOperator;
        }
        if (!isAtOperator) {
            findNumber(new OneDigitGenerator(equation));
            findOperator(new OperatorGenerator(equation));
            findNumber(new OneDigitGenerator(equation));
        } else {
            findOperator(new OperatorGenerator(equation));
            findNumber(new TwoDigitGenerator(equation));
        }
        return;
    }

    public void findNumber(NumberGenerator n) throws Exception {
        Number i = n.getRandom();
        for (int j = 0; j < 100; j++) {
            try {
                equation.addNumber(i);
            } catch (Exception e) {
                i = n.getRandom();
                continue;
            }

            return;
        }
        if (log) 
            System.out.println("Too Many Failed Attempts");
        throw new Exception("Too Many Failed Attempts Resulting In Not A Positive Integer");
    }

    public void findOperator(OperatorGenerator o) {
        try {
            equation.addOperator(o.getRandom());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    public int getLength() {
        return this.length;
    }

    public void play() {
        System.out.println("Find a " + length + " character equation that equals " + equation.getCurrentValue());
        printDetail("-");
        try (Scanner myObj = new Scanner(System.in)) {
            boolean found = false;
            while (!found) {
                String guess = myObj.nextLine();
                // Enter guess and press Enter
                System.out.println(check(guess));
                if (guess.equals(equation.getEquation())) {
                    found = true;
                }
                printDetail("-");
            }
        }
        System.out.println("Correct!");
        // Doesn't work, not even close.
        // String user = new String[1];
        // while (user.equals(equation) == false){
        // if(len(user) - user.count(" ") < 6){
        // user = myObj.nextLine();
        // system.out.println("You dont have enough characters");
        // }

        // else {
        // system.out.println("Wrong, Try Again!");
        // user.setLenth(0);
        // }
        // }
        // System.out.println("Congrats you did it!");
    }

    // taken from the 2015 Ap Computer Science FRQ. yoink.
    public String check(String guess) {
        String response = "";
        for (int i = 0; i < guess.length(); i++) {
            if (guess.substring(i, i + 1).equals(equation.getEquation().substring(i, i + 1))) {
                response += "\u001B[32m"+guess.substring(i, i + 1)+"\u001B[0m";
            } else if (equation.getEquation().indexOf(guess.substring(i, i + 1)) != -1) {
                response += "\u001B[33m"+guess.substring(i, i + 1)+"\u001B[0m";
            } else {
                response += "\u001B[31m"+guess.substring(i, i + 1)+"\u001B[0m";
            }
        }
        return response;
    }

    public void printDetail(String c) {
        for (int i = 0; i < length; i++) {
            System.out.print(c);
        }
        System.out.println();
    }

    public String getEquation() {
        return equation.getEquation();
    }

    public int getValue() {
        return equation.getCurrentValue();
    }

    public void setLog(boolean value) {
        log = value;
        equation.setLog(value);
    }
}