package Src;
import java.lang.Math;

enum Operator {
    ADD,
    SUBTRACT,
    DIVIDE,
    MULTIPLY
}

public class Mathle {
    private final int length;
    private Equation equation;
    

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
    }
}