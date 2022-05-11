package Src;
import java.util.ArrayList;

public class TwoDigitGenerator {
    private ArrayList<Integer> possibilites;
    private int chanceForLow;
    private double exponentialDecreaseForHigh;
    private int highestTens;

    public TwoDigitGenerator(Equation parameter) {
        chanceForLow = 50; //No numbers can have more repeats than this
        exponentialDecreaseForHigh = 0.2; //
        highestTens = 5;

        possibilites = new ArrayList<>();
        for (int i=1; i<=9; i++) { 

            possibilites.add(i); //give all digits an equal chance to be used    
            if (parameter.getPastDigits().indexOf(i) == -1 || i == 1 || 1 == 2) { //if digit does not exist in past, increase the chance to get it   
                for (int j=0; j<chanceForLow*(Math.pow(exponentialDecreaseForHigh,i)+1); j++) {  //t\left(r^{x}\right)+1
                    possibilites.add(i);
                }
            }
        }
    }

    public Number getRandom() {
        Number num = new Number();
        num.addDigit((int)(Math.random()*possibilites.size()));
        return num;
        
    }

    public int getChanceForLow() {
        return chanceForLow;
    }

    public double getExponentialDecreaseForHigh() {
        return exponentialDecreaseForHigh;
    }

    public int getHighestTens() {
        return highestTens;
    }

    public void setChanceForLow(int chanceForLow) {
        this.chanceForLow = chanceForLow;
    }

    public void setExponentialDecreaseForHigh(double exponentialDecreaseForHigh) {
        this.exponentialDecreaseForHigh = exponentialDecreaseForHigh;
    }
    public void setHighestTens(int highestTens) {
        this.highestTens = highestTens;
    }
}

