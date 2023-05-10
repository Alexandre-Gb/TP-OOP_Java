import java.util.*;

public class Lambdas {
  public static void upperCaseAll(List<String> strings) {
    Objects.requireNonNull(strings);
    strings.replaceAll(e -> e.toUpperCase(Locale.ROOT));
  }

  public static Map<String, Integer> occurences(List<String> strings) {
    Objects.requireNonNull(strings);
    HashMap<String, Integer> map = new HashMap<>();
    strings.forEach(e -> map.put(e, map.getOrDefault(e, 0) + 1));
/*
    strings.forEach(e -> map.merge(e, 1, Integer::sum));
*/
    return Map.copyOf(map);
  }

  public static void main(String[] args) {
    ArrayList<String> strings = new ArrayList<>();
    strings.add("foo");
    strings.add("bar");
    strings.add("foo");
    upperCaseAll(strings);
    System.out.println(strings);
    System.out.println(occurences(strings));
  }
}
