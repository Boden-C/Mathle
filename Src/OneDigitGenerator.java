package Src;
import java.util.ArrayList;
import java.lang.Math;

public class OneDigitGenerator implements NumberGenerator {
    private ArrayList<Integer> possibilites;
    private int chanceForNonReapting;
    private double exponentialIncreaseForHigh;

    public OneDigitGenerator(Equation parameter) {
        chanceForNonReapting = 10; //No numbers can have more repeats than this
        exponentialIncreaseForHigh = 0.6; //

        possibilites = new ArrayList<>();
        for (int i=1; i<=9; i++) { 

            possibilites.add(i); //give all digits an equal chance to be used    
            if (parameter.getPastDigits().indexOf(i) == -1) { //if digit does not exist in past, increase the chance to get it   
                for (int j=0; j<chanceForNonReapting*(Math.pow(-exponentialIncreaseForHigh,i)+1); j++) {  //y=t\left(-r^{x}+1\right)
                    possibilites.add(i);
                }
            }
        }
    }

    public Number getRandom() {
        Number num = new Number();
        num.addDigit(possibilites.get((int)(Math.random()*possibilites.size())));
        return num;
        
    }

    public int getChanceForNonReapting() {
        return chanceForNonReapting;
    }

    public double getExponentialIncreaseForHigh() {
        return exponentialIncreaseForHigh;
    }

    public void setChanceForNonReapting(int chanceForNonReapting) {
        this.chanceForNonReapting = chanceForNonReapting;
    }

    public void setExponentialIncreaseForHigh(int exponentialIncreaseForHigh) {
        this.exponentialIncreaseForHigh = exponentialIncreaseForHigh;
    }
}
