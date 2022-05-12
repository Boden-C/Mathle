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
        this.equation = new Equation();

        boolean isAtNumber = true;
        for (int i=0; i<length-3; i++) {
            if (isAtNumber) {
                NumberGenerator a;
                if (Math.random() > 0.4 || i>length-5) {
                    a = new OneDigitGenerator(equation);
                    equation.addNumber(a.getRandom());
                } else {
                    a = new TwoDigitGenerator(equation);
                    equation.addNumber(a.getRandom());
                    i++;
                }
            } else {
                OperatorGenerator a = new OperatorGenerator(equation);
                equation.addOperator(a.getRandom());

            }   
            isAtNumber = !isAtNumber;
        }
        if (isAtNumber) {
            OneDigitGenerator a = new OneDigitGenerator(equation);
            equation.addNumber(a.getRandom());
            OperatorGenerator b = new OperatorGenerator(equation);
            equation.addOperator(b.getRandom());
            OneDigitGenerator c = new OneDigitGenerator(equation);
            equation.addNumber(c.getRandom());
        } else {
            OperatorGenerator a = new OperatorGenerator(equation);
            equation.addOperator(a.getRandom());
            TwoDigitGenerator b = new TwoDigitGenerator(equation);
            equation.addNumber(b.getRandom());
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