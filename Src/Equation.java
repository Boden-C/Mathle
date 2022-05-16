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

    public void addNumber(Number number) throws Exception {
        String numString = number.getDigits().stream().map(String::valueOf).collect(Collectors.joining(""));
        if (log) System.out.println("Trying:"+equation+numString);
        Double check = eval(equation+numString);
        if (check > 0 && check % 1 == 0 && check < 100) {
            pastNumbers.add(number);
            equation += numString;
            currentValue = check.intValue();
            return;
        }
        if (log) System.out.println("Failed:"+check);
        throw new Exception("Number Is Not a Positive Integer");
    }

    public void addOperator(Operator operator) throws Exception {
        String lastChar = equation.substring(equation.length()-1);
        if (lastChar.equals("+") || lastChar.equals("-") || lastChar.equals("*") || lastChar.equals("/")) {
            //throw new Exception("Operator in Past");
        }
        String operatorString;
        switch (operator) {
            case ADD: operatorString = "+"; break;
            case SUBTRACT: operatorString = "-"; break;
            case MULTIPLY: operatorString = "*"; break;
            case DIVIDE: operatorString = "/"; break;
            default: throw new Exception("Unknown Operator");
        }
        equation += operatorString;
        pastOperators.add(operator);
        return;
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

    public Number getLastNumber() {
        if (pastNumbers.size() < 1) {
            return new Number();
        }
        return pastNumbers.get(pastNumbers.size()-1);
    }

    public Operator getLastOperator() {
        if (pastNumbers.size() < 1) {
            return Operator.ADD;
        }
        return pastOperators.get(pastOperators.size()-1);
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

    //Stole this code from https://stackoverflow.com/questions/3422673/how-to-evaluate-a-math-expression-given-in-string-form/3423360#:~:text=All%20code%20in%20this%20answer%20released%20to%20the%20public%20domain.%20Have%20fun!
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
                // if (eat('+')) return +parseFactor(); // unary plus
                // if (eat('-')) return -parseFactor(); // unary minus
                
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
