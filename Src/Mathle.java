package Src;

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
        this.length = length;
        this.equation = new Equation();

        for (int i=0; i<length; i++) {
            OneDigitGenerator a = new OneDigitGenerator(equation);
            equation.addNumber(a.getRandom());
        }
        System.out.println(equation.getEquation());
    }

    public int getLength() {
        return this.length;
    }

    public void play() {
    }
}