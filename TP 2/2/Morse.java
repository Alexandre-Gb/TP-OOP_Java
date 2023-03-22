public class Morse {
  public static void main(String[] args) {
    var morseString = new StringBuilder();
    for (String arg: args) {
      morseString = morseString.append(arg).append(" Stop. ");
    }

    System.out.println(morseString);
  }
}