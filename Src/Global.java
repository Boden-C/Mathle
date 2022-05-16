package Src;

public class Global {
    public static void main(String args[]) {
        Mathle game = new Mathle(50);
        System.out.println("Equation: "+game.getEquation());
        //System.out.println("Value: "+game.getValue());
        game.play();
    }
}