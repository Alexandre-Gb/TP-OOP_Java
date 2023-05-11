import java.util.*;
import java.util.function.Function;

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

  public static Map<String, List<Actor>> actorGroupByFirstName(List<Actor> actors) {
    Objects.requireNonNull(actors);
    HashMap<String, List<Actor>> map = new HashMap<>();
    actors.forEach(e -> map.computeIfAbsent(e.firstName(), k -> new ArrayList<>()).add(e));
    return Map.copyOf(map);
  }

/*  public static Map<String, List<Actor>> actorGroupBy(List<Actor> actors, Function<Actor, String> keyExtractor) {
    Objects.requireNonNull(actors);
    Objects.requireNonNull(keyExtractor);
    HashMap<String, List<Actor>> map = new HashMap<>();
    actors.forEach(e -> map.computeIfAbsent(keyExtractor.apply(e), k -> new ArrayList<>()).add(e));
    return Map.copyOf(map);
  }*/

  public static <T, K> Map<K, List<T>> actorGroupBy(List<T> list, Function<T, K> keyExtractor) {
    Objects.requireNonNull(list);
    Objects.requireNonNull(keyExtractor);
    HashMap<K, List<T>> map = new HashMap<>();
    list.forEach(e -> map.computeIfAbsent(keyExtractor.apply(e), k -> new ArrayList<>()).add(e));
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

    ArrayList<Actor> actors = new ArrayList<>();
    actors.add(new Actor("bob", "de niro"));
    actors.add(new Actor("willy", "cat"));
    actors.add(new Actor("bob", "cat"));

    var group1 = actorGroupBy(actors, Actor::firstName);  // groupe par prénom
    var group2 = actorGroupBy(actors, Actor::lastName);   // groupe par nom
    System.out.println(group1);
    System.out.println(group2);
  }
}