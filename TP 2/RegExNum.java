import java.util.regex.Pattern;

public class RegExNum {
  public static void main(String[] args) {
    var numString = new StringBuilder();
    for (String arg: args) {
      if (Pattern.matches("^[0-9]+$", arg)) {
        numString = numString.append(arg).append(" ");
      }
    }
  }
}