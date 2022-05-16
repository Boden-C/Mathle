package Src;
import java.util.ArrayList;

public class TwoDigitGenerator implements NumberGenerator {
    private ArrayList<Integer> possibilites;
    private OneDigitGenerator ones;
    private int chanceForLow;
    private double exponentialDecreaseForHigh;
    private int highestTens;

    public TwoDigitGenerator(Equation parameter) {
        chanceForLow = 100; //No numbers can have more repeats than this
        exponentialDecreaseForHigh = 0.3; //
        highestTens = 2;
        ones = new OneDigitGenerator(parameter);

        possibilites = new ArrayList<>();
        for (int i=1; i<=highestTens; i++) { 

            possibilites.add(i); //give all digits an equal chance to be used    
            if (parameter.getPastDigits().indexOf(i) == -1 || i == 1 || i == 2) { //if digit does not exist in past, increase the chance to get it   
                for (int j=0; j<chanceForLow*Math.pow(exponentialDecreaseForHigh,i)+1; j++) {  //y=t\left(r^{x}\right)+1
                    possibilites.add(i);
                }
            }
        }
    }

    public Number getRandom() {
        Number num = new Number();
        try {
            num.addDigit(possibilites.get((int)(Math.random()*possibilites.size())));
            num.addDigit(ones.getRandom().getNumber());
        } catch(Exception e) {
            e.printStackTrace();
        }
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

