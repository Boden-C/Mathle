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
        log = true;
        if (length<3) {
            this.length = 3;
            System.out.println("Smallest length is 3");
        } else {
            this.length = length;
        }
        equation = new Equation();
        while (!create(length, 0.4));
    }

    //disclaimer: everything below is made by a very sleep deprived Boden. Please don't look.

    public boolean create(int length, Double tensChance) {
        equation = new Equation();

        boolean isAtNumber = true;
        for (int i=0; i<length-3; i++) {
            if (isAtNumber) {
                if (Math.random() > tensChance || i>length-5) {
                    findNumber(new OneDigitGenerator(equation));
                } else {
                    if (!findNumber(new TwoDigitGenerator(equation))) {
                        if (log) { System.out.println("Restarting..."); };
                        return false;
                    };
                    i++;
                }
            } else {
                findOperator(new OperatorGenerator(equation));
                System.out.println(equation.getEquation());
            }   
            isAtNumber = !isAtNumber;
        }
        if (isAtNumber) {
            findNumber(new OneDigitGenerator(equation));
            findOperator(new OperatorGenerator(equation));
            findNumber(new OneDigitGenerator(equation));
        } else {
            findOperator(new OperatorGenerator(equation));
            if (!findNumber(new TwoDigitGenerator(equation))) {
                if (log) { System.out.println("Restarting..."); };
                return false;
            };
        }
        return true;
    }

    public boolean findNumber(NumberGenerator n) {
        Number i = n.getRandom();
        for (int j=0; j<100; j++) {
            if (equation.addNumber(i)) {
                return true;
            }
            i = n.getRandom();
        }
        if (log) { System.out.println("Too Many Failed Attempts"); };
        return false;
    }

    public void findOperator(OperatorGenerator o) {
        Operator i = o.getRandom();
        while (!equation.addOperator(i)) {
            i = o.getRandom();
        }
    }

    public int getLength() {
        return this.length;
    }

    public void play() {
        System.out.println("Find a "+length+" character equation that equals "+equation.getCurrentValue());
        System.out.println(equation.getEquation());
        System.out.println("Correct!");
    //     Doesn't work, not even close.
    //     String user = new String[1];
    //     while (user.equals(equation) == false){
    //     if(len(user) - user.count(" ") < 6){
    //         user = myObj.nextLine();
    //         system.out.println("You dont have enough characters");
    //     }
         
    //         else {
    //             system.out.println("Wrong, Try Again!");
    //         user.setLenth(0);
    //         }
    //     }
    //     System.out.println("Congrats you did it!");
    }
}