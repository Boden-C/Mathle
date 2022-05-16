package Src;

import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;

public class OperatorGenerator {
    private ArrayList<Operator> possibilites;
    private int chanceForNonReapting;

    public OperatorGenerator(Equation parameter) {
        chanceForNonReapting = 20; // No operators can have more repeats than this

        possibilites = new ArrayList<>(Arrays.asList(Operator.values()));

        for (Operator symbol : new ArrayList<>(Arrays.asList(Operator.values()))) {
            if (parameter.getPastOperators().indexOf(symbol) == -1) { // if digit does not exist in past, increase the
                                                                      // chance to get it
                if (isPrime(parameter.getLastNumber().getNumber())) {
                    possibilites.remove(Operator.DIVIDE);
                    continue;
                } else {
                    for (int j = 0; j < chanceForNonReapting; j++) {
                        possibilites.add(Operator.DIVIDE);
                    }
                }

                for (int j = 0; j < chanceForNonReapting; j++) {
                    possibilites.add(symbol);
                }
            }
            if (symbol.equals(Operator.DIVIDE)) {
                possibilites.add(symbol);
            }
        }

    }

    public Operator getRandom() {
        return possibilites.get((int) (Math.random() * possibilites.size()));
    }

    private boolean isPrime(Integer n) {
        // Corner case
        if (n <= 1)
            return false;

        // Check from 2 to n-1
        for (int i = 2; i < n; i++)
            if (n % i == 0)
                return false;

        return true;
    }
}