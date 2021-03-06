package Src;
import java.util.ArrayList;

public class Number {
    private int number;

    public Number() {
        number = 0;
    }

    public void addDigit(int digit) throws Exception{
        if (digit < 0 || digit > 9) {
            throw new Exception("Integer is not a digit");
        }
        number = number*10+digit;
        return;
    }

    public int getNumber() {
        return number;
    }

    public ArrayList<Integer> getDigits() {
        int i = number;
        ArrayList<Integer> digits = new ArrayList<Integer>();
        while (i > 0) {
            digits.add(i % 10);
            i /= 10;
        }
        return digits;
    }

    //Returns the digit at the specified place
    public int getDigit(int place) {
        return (int)((int)number / java.lang.Math.pow(10, place-1)) % 10;
    }
}

