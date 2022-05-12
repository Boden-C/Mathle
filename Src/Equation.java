package Src;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class Equation {
    private String equation;
    private ArrayList<Number> pastNumbers;
    private ArrayList<Operator> pastOperators;
    private int currentValue;
    private boolean log;

    public Equation() {
        equation = "";
        currentValue = 0;
        pastNumbers = new ArrayList<>();
        pastOperators = new ArrayList<>();
        log = true;
    }

    public boolean addNumber(Number number) {
        String numString = number.getDigits().stream().map(String::valueOf).collect(Collectors.joining(""));
        if (log) { System.out.println("Trying:"+equation+numString); };
        Double check = eval(equation+numString);
        if (check > 0 && check % 1 == 0 && check < 300) {
            pastNumbers.add(number);
            equation += numString;
            currentValue = check.intValue();
            return true;
        }
        if (log) { System.out.println("Failed:"+check); };
        return false;
    }

    public boolean addOperator(Operator operator) {
        String lastChar = equation.substring(equation.length()-1);
        if (lastChar.equals("+") || lastChar.equals("-") || lastChar.equals("*") || lastChar.equals("/")) {
            return false;
        }
        String operatorString;
        switch (operator) {
            case ADD: operatorString = "+"; break;
            case SUBTRACT: operatorString = "-"; break;
            case MULTIPLY: operatorString = "*"; break;
            case DIVIDE: operatorString = "/"; break;
            default: return false;
        }
        equation += operatorString;
        pastOperators.add(operator);
        return true;
    }

    public ArrayList<Number> getPastNumbers() {
        return pastNumbers;
    }

    public ArrayList<Integer> getPastDigits() {
        ArrayList<Integer> digits = new ArrayList<>();
        for (Number number:pastNumbers) {
            digits.addAll(number.getDigits());
        }
        return digits;
    }

    public ArrayList<Integer> getPastPlaces(int place) {
        ArrayList<Integer> pastOnesPlaces = new ArrayList<>();
        for (Number number:pastNumbers) {
            if (number.getDigit(place) == 0) {
                continue;
            }
            pastOnesPlaces.add(number.getDigit(place));
        }
        return pastOnesPlaces;
    }

    public ArrayList<Operator> getPastOperators() {
        return pastOperators;
    }

    public String getEquation() {
        return equation;
    }

    public Integer getResult(int of) {
        String lastChar = equation.substring(equation.length()-1);
        int lastInt = pastNumbers.get(pastNumbers.size()-1).getNumber();
        switch (lastChar) {
            case "+": return lastInt+of;
            case "-": return lastInt+of;
            case "/": return lastInt+of;
            case "*": return lastInt+of;
            default: return null;
        }
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;
            
            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }
            
            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }
            
            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }
            
            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }
            
            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }
            
            double parseFactor() {
                if (eat('+')) return +parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus
                
                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    if (!eat(')')) throw new RuntimeException("Missing ')'");
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    if (eat('(')) {
                        x = parseExpression();
                        if (!eat(')')) throw new RuntimeException("Missing ')' after argument to " + func);
                    } else {
                        x = parseFactor();
                    }
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }
                
                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation
                
                return x;
            }
        }.parse();
    }
}
