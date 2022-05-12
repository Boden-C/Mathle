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
    Scanner myObj = new Scanner(System.in);
    

    public Mathle(int length) {
        if (length<3) {
            this.length = 3;
            System.out.println("Smallest length is 3");
        } else {
            this.length = length;
        }
        equation = new Equation();
        create(length);
    }

    //disclaimer: everything below is made by a very sleep deprived Boden. Please don't look.

    public void create(int length) {
        equation = new Equation();

        boolean isAtNumber = true;
        for (int i=0; i<length-3; i++) {
            if (isAtNumber) {
                if (Math.random() > 0.4 || i>length-5) {
                    findNumber(new OneDigitGenerator(equation));
                } else {
                    if (!findNumber(new TwoDigitGenerator(equation))) {
                        System.out.println("Restarting...");
                        create(length);
                        return;
                    };
                    i++;
                }
            } else {
                findOperator(new OperatorGenerator(equation));

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
                System.out.println("Restarting...");
                create(length);
                return;
            };
        }
        return;
    }

    public boolean findNumber(NumberGenerator n) {
        Number i = n.getRandom();
        for (int j=0; j<100; j++) {
            if (equation.addNumber(i)) {
                return true;
            }
            i = n.getRandom();
        }
        System.out.println("Too Many Failed Attempts");
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
        System.out.println(equation.getEquation());
        String user = new String[1]
        While(user.equals(equation) == false){
        if(len(user) - user.count(" ") < 6){
            user = myObj.nextLine();
            system.out.println("You dont have enough characters");
        }
         
            else {
                system.out.println("Wrong, Try Again!");
            user.setLenth(0);
            }
        }
        system.out.println("Congrats you did it!")
    }
        

        






    }
}