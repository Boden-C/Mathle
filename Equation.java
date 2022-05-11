import java.util.ArrayList;
import java.util.stream.Collectors;

public class Equation {
    private String equation;
    private ArrayList<Number> pastNumbers;
    private ArrayList<Operator> pastOperators;

    public Equation() {
        equation = "";
        pastNumbers = new ArrayList<>();
        pastOperators = new ArrayList<>();
    }

    public boolean addNumber(Number number) {
        pastNumbers.add(number);
        equation += number.getDigits().stream().map(String::valueOf).collect(Collectors.joining(","));
        return true;
    }

    public boolean addOperator(Operator operator) {
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
}
