import java.util.Scanner;

public class Calc {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Please provide two integer values :");
    int firstValue = scanner.nextInt();
    int secondValue = scanner.nextInt();

    System.out.println(firstValue + " + " + secondValue + " = " + (firstValue + secondValue));
    System.out.println(firstValue + " - " + secondValue + " = " + (firstValue - secondValue));
    System.out.println(firstValue + " / " + secondValue + " = " + (firstValue / secondValue));
    System.out.println(firstValue + " % " + secondValue + " = " + (firstValue % secondValue));
  }
}