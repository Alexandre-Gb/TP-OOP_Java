import java.util.*;

public class Lambdas {
  public static void upperCaseAll(List<String> strings) {
    Objects.requireNonNull(strings);
    strings.replaceAll(e -> e.toUpperCase(Locale.ROOT));
  }

  public static Map<String, Integer> occurences(List<String> strings) {
    Objects.requireNonNull(strings);
    HashMap<String, Integer> map = new HashMap<>();
    strings.forEach(e -> map.merge(e, 1, Integer::sum));
    return Map.copyOf(map);
  }

  public static void main(String[] args) {
    ArrayList<String> strings = new ArrayList<>();
    strings.add("Hello");
    strings.add("World");
    strings.add("Hello");
    upperCaseAll(strings);
    System.out.println(strings);
    System.out.println(occurences(strings));
  }
}
