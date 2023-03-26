import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Calc {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int[] values = new int[2];

    Pattern pattern = Pattern.compile("-?[0-9]+");
    Matcher matcher;

    for (int i = 0; i < 2; i++) {
      System.out.print("Please provide an integer value : ");
      String input = scanner.nextLine();
      matcher = pattern.matcher(input);
      if (matcher.find()) {
        values[i] = Integer.parseInt(input);
      } else {
        System.out.println("Invalid input, please try again.");
        i--;
      }
    }

    System.out.println(values[0] + " + " + values[1] + " = " + (values[0] + values[1]));
    System.out.println(values[0] + " - " + values[1] + " = " + (values[0] - values[1]));
    System.out.println(values[0] + " / " + values[1] + " = " + (values[0] / values[1]));
    System.out.println(values[0] + " % " + values[1] + " = " + (values[0] % values[1]));
  }
}