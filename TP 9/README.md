# TP9 - Stream, Collecteur et Comparateur
## GIBOZ Alexandre, INFO1 2022-2025
***

[Consignes de rendu de TP](https://monge.univ-mlv.fr/ens/IR/IR1/2022-2023/Java/index.php)

[Énoncé](https://monge.univ-mlv.fr/ens/IR/IR1/2022-2023/Java/td09.php)
***

## Exercice 1 - Un exemple simple

1. **Quelle est l'interface fonctionnelle prise en paramètre de filter ?**

`filter` prend en paramètre une interface fonctionnelle `Predicate<T>`.

**Quels sont les types de paramètre et de retour de la lambda ?**

La lambda prend en paramètre un objet `Person` et retourne un `boolean`.

2. **Quelle est l'interface fonctionnelle prise en paramètre de map ?**

`map` prend en paramètre une interface fonctionnelle `Function<T, R>`.

**Quels sont les types de paramètre et de retour de la lambda ?**

La lambda prend en paramètre un objet `Person` et retourne un `String`.

3. **Écrire une nouvelle version de namesOfTheAdults en utilisant l'API des streams.**
```java
public static List<String> namesOfTheAdults(List<Person> persons) {
  return persons.stream()
  .filter(e -> e.age() > 18)
  .map(Person::name)
  .toList();
}
```

<br>

## Exercice 2 - Le grand Hôtel

1. **Écrire le type Hotel en faisant attention à ce que la liste des chambres soit non mutable après création.**
```java
public record Hotel(String name, List<Room> rooms) {
  public Hotel {
    Objects.requireNonNull(name, "name is null");
    Objects.requireNonNull(rooms, "rooms is null");
  }
}
```

2. **Écrire une méthode roomInfo qui renvoie une chaîne de caractères contenant les noms des chambres en utilisant un Stream.**

On ajoute la méthode suivante au record `Hotel`:
```java
public String roomInfo() {
  return rooms.stream()
  .map(Room::name)
  .collect(Collectors.joining(", "));
}
```

3. **Écrire une méthode roomInfoSortedByFloor qui renvoie une chaîne de caractère contenant les noms de chambres triées par le numéro d'étage.**

On ajoute la méthode suivante au record `Hotel`:
```java
public String roomInfoSortedByFloor() {
  return rooms.stream()
  .sorted(Comparator.comparingInt(Room::floor))
  .map(Room::name)
  .collect(Collectors.joining(", "));
}
```

4. **Écrire une méthode averagePrice qui renvoie la moyenne des prix de toutes les chambres. 
   Dans le cas où l’hôtel n'a pas de chambre, on renverra NaN (Not a Number)**

On ajoute la méthode suivante au record `Hotel`:
```java
public double averagePrice() {
  return rooms.stream()
  .mapToLong(Room::price)
  .average()
  .orElse(Double.NaN);
}
```

5. **Écrire une méthode roomForPrice1 qui prend en paramètre un prix et renvoie la chambre la plus chère en dessous de ce prix. 
   S'il y a plusieurs chambres au même prix, on prendra la première. 
   Cette méthode renvoie un Optional car il peut n'y avoir aucune chambre qui respecte la contrainte de prix.
   En termes d'implantation, on choisit de trier pour accéder à la chambre la plus chère. 
   Par ailleurs, il est plus facile de prendre le premier élément (findFirst) d'un Stream que le dernier (il n'y a pas de méthode pour ça) ; on va donc écrire le comparateur de façon à ce que la chambre qui a le prix le plus grand soit en premier.**

On ajout la méthode suivante au record `Hotel`:
```java
public Optional<Room> roomForPrice1(int price) {
  if (price < 0) throw new IllegalArgumentException("price is negative");
  return rooms.stream()
  .filter(room -> room.price() < price)
  .max(Comparator.comparingDouble(Room::price));
}
```

6. **En fait, il existe déjà une méthode max sur les Stream. Écrire une méthode roomForPrice2 qui fonctionne comme roomForPrice1 mais en utilisant la méthode max de Stream.**

On ajoute la méthode suivante au record `Hotel`:
```java
public Optional<Room> roomForPrice2(int price) {
  if (price < 0) throw new IllegalArgumentException("price is negative");
  return rooms.stream()
  .filter(room -> room.price() < price)
  .max(Comparator.comparingDouble(Room::price));
}
```

**Qu'elle implantation est la meilleure ?**

La méthode `roomForPrice2` est plus efficace car elle ne trie pas les chambres, elle ne fait que comparer les prix.
Elle est également plus lisible et concise.

7. **Écrire une méthode expensiveRoomNames qui prend en paramètre une liste d'hôtels et renvoie les nom des deux (au maximum) chambres les plus chères de chaque hôtel.**

On ajoute la méthode suivante au record `Hotel`:
```java
public static String expensiveRoomNames(List<Hotel> hotels) {
  Objects.requireNonNull(hotels, "hotels is null");
  return List.copyOf(hotels)
        .stream()
        .flatMap(hotel -> hotel.rooms().stream()
        .sorted(Comparator.comparingLong(Room::price).reversed())
        .limit(2)
        .map(Room::name))
        .collect(Collectors.joining(", "));
}
```

8. **On souhaite écrire une méthode roomInfoGroupedByFloor qui renvoie un dictionnaire qui à chaque étage associe une liste des chambres de cet étage :**
```java
System.out.println(hotel.roomInfoGroupedByFloor());
        // {100=[Room[name=blue, ...], Room[name=red, ...], Room[name=green, ...]], 120=[Room[name=fuchsia, ...]], 110=[Room[name=yellow, ...]]}
```

**Quel est le type de retour de la méthode roomInfoGroupedByFloor ?**

Le type de retour est `Map<Integer, List<Room>>`.

**Écrire la code de la méthode roomInfoGroupedByFloor.**
```java
public Map<Integer, List<Room>> roomInfoGroupedByFloor() {
  return rooms.stream()
    .collect(Collectors.groupingBy(Room::floor, Collectors.toList()));
}
```

9. **La méthode précédente ne renvoie pas un dictionnaire qui trie les clés, donc les étages ne sont pas forcément dans l'ordre. 
   En Java, il existe une classe java.util.TreeMap qui maintient les clés triées. 
   Écrire une méthode roomInfoGroupedByFloorInOrder qui a la même signature que la méthode précédente mais renvoie un dictionnaire qui stocke les clés de façon triée.**
```java
public Map<Integer, List<Room>> roomInfoGroupedByFloorInOrder() {
  return rooms.stream()
  .collect(Collectors.groupingBy(Room::floor, TreeMap::new, Collectors.toList()));
}
```

<br>

## Exercice 3 - Games Of Streams

Fonctions du fichier `StreamTest.java`:
```java
/**
 * Renvoie une chaîne des caractères contenant les entiers de la liste séparés par
 * des points virgules.
 * Par exemple, listIntegerToString(List.of(5,6,7,9)) renvoie "5;6;7;9".
 */
public static String listIntegerToString(List<Integer> list) {
  Objects.requireNonNull(list);
  return list.stream()	        .map(Object::toString)	        .collect(Collectors.joining(";"));
}
/**
 * Renvoie la somme de toutes les longueurs des chaînes de la liste.
 * Par exemple, sumLength(List.of("ABC","DE","","F")) renvoie 6.
 * <p>
 * Indication : la méthode sum n'est disponible que sur les streams
 * de types primitifs IntStream, LongStream... Vous pouvez utiliser
 * mapToInt pour créer un IntStream au lieu d'un Stream<Integer>.
 */
public static int sumLength(List<String> list) {
  Objects.requireNonNull(list);
  return list.stream()
        .mapToInt(String::length)
        .sum();
}
/**
 * Renvoie le nombre de chaînes non vides du tableau
 * Par exemple, String[] tab = {"ABC", "DE", "", "F"};
 * countNonEmpty(tab) renvoie 3.
 * <p>
 * Indication : utilisez une des méthodes Arrays.stream pour créer un stream à partir d'un tableau.
 */
public static long countNonEmpty(String[] array) {
  Objects.requireNonNull(array);
  return Arrays.stream(array)
        .filter(e -> !Objects.equals(e, ""))
        .count();
}
/**
 * Renvoie la somme des entiers du tableau
 * Par exemple, sumLength(Array.of(5, 8, -1, 2)) renvoie 14.
 */
public static long sum(int[] tab) {
  Objects.requireNonNull(tab);
  return Arrays.stream(tab)
        .sum();
}
/**
 * Renvoie la liste des chaînes mises en majuscules.
 */
public static List<String> capitalizeList(List<String> list) {
  Objects.requireNonNull(list);
  return list.stream()
        .map(String::toUpperCase)
        .collect(Collectors.toList());
}
/**
 * Renvoie une Map qui associe à chaque caractère la liste des chaînes commençant par ce caractère.
 * Par exemple, mapByFirstCharacter(List.of("AB", "A", "BA", "C") renvoie une map qui associe
 * au caractère 'A' la liste ["AB","A"], au caractère 'B' la liste ["BA"] et au caractère 'C' la liste ["C"].
 * <p>
 * Indication : utilisez Collectors.groupingBy. Et auusi, attention aux chaînes vides.
 */
public static Map<Character, List<String>> mapByFirstCharacter(List<String> list) {
  Objects.requireNonNull(list);
  return list.stream()
        .filter(e -> !Objects.equals(e, ""))
        .collect(Collectors.groupingBy(e -> e.charAt(0)));
}
/**
 * Renvoie une map qui associe à chaque caractère l'ensemble des chaînes commençant par ce caractère.
 * Par exemple, mapByFirstCharacterSet(List.of("AB","A","BA","C") renvoie une map qui associe
 * au caractère 'A' l'ensemble {"AB","A"}, au caractère 'B' l'ensemble {"BA"} et au caractère 'C' l'ensemble {"C"}.
 */
public static Map<Character, Set<String>> mapByFirstCharacterSet(List<String> list) {
  Objects.requireNonNull(list);
  return list.stream()
        .filter(e -> !Objects.equals(e, ""))
        .collect(Collectors.groupingBy(e -> e.charAt(0), Collectors.toSet()));
}
/**
 * Renvoie une map qui associe à chaque caractère le nombre de chaînes commençant par ce caractère.
 * Par exemple, mapByFirstCharacterSet(List.of("AB","A","BA","C") renvoie une map qui associe
 * au caractère 'A' la valeur 2, au caractère 'B' la valeur 1 et au caractère 'C' la valeur 1.
 */
public static Map<Character, Long> countByFirstCharacter(List<String> list) {
  Objects.requireNonNull(list);
  return list.stream()
        .filter(e -> !Objects.equals(e, ""))
        .collect(Collectors.groupingBy(e -> e.charAt(0), Collectors.counting()));
}
/**
 * Renvoie la liste de String privée de son premier élément.
 * Indication : utilisez Stream.skip.
 */
public static List<String> withoutFirstElement(List<String> list) {
  Objects.requireNonNull(list);
  return list.stream()
        .skip(1L)
        .collect(Collectors.toList());
}
/**
 * Renvoie la liste de T privée de son premier élément.
 * Maintenant cette méthode peut être appliquée à n'importe quel type de List
 * List<Integer>, ...
 */
public static <T> List<T> withoutFirstElementBetter(List<T> list) {
  Objects.requireNonNull(list);
  return list.stream()
        .skip(1L)
        .collect(Collectors.toList());
}
/**
 * Renvoie la liste des mots de la chaîne prise en argument.
 * Par exemple, words("Abc def   i") renvoie ["Abc","def","i"]
 * Indication : utilisez String.split() et éliminez les chaînes vides.
 */
public static List<String> words(String s) {
  Objects.requireNonNull(s);
  return Arrays.stream(s.split(" "))
        .filter(e -> !Objects.equals(e, ""))
        .collect(Collectors.toList());
}
/**
 * Renvoie l'ensemble des mots apparaissant dans la liste de chaînes prise en argument.
 * Par example, words(List.of("Abc def i","def i","Abc de")) renvoie l'ensemble
 * {"Abc","def","i","de"}.
 * Indication : utilisez Stream.flatmap.
 */
public static Set<String> words(List<String> list) {
  Objects.requireNonNull(list);
  return list.stream()
        .flatMap(e -> Arrays.stream(e.split(" ")))
        .filter(e -> !Objects.equals(e, ""))
        .collect(Collectors.toSet());
}
/**
 * Renvoie l'ensemble des chaînes apparaissant dans la liste d'Optional<String> prise en argument.
 * Par exemple, unpack(List.of(Optional.empty(),Optional.of("A"),Optional.of("B"),Optional.of("A"))) renvoie
 * l'ensemble {"A","B"}.
 * <p>
 * Indication : les Optional peuvent être transformés en Stream avec Optional.stream().
 */
public static Set<String> unpack(List<Optional<String>> list) {
  Objects.requireNonNull(list);
  return Optional.of(list)
        .stream()
        .flatMap(Collection::stream)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toSet());
}
/**
 * Renvoie une Map comptant le nombre d'occurences de chaque caractère dans la chaîne.
 * Par exemple, occurrences("ABBAAABBB") renvoie la map qui associe au caractère 'A' la valeur
 * 4 et au caractère 'B' la valeur 5.
 * <p>
 * Indication : vous pouvez utiliser s.chars().mapToObj( c-> (char) c) obtenir un Stream<Character> à partir d'une chaîne.
 */
public static Map<Character, Long> occurrences(String s) {
  Objects.requireNonNull(s);
  return s.chars()
        .mapToObj(c -> (char) c)
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
}
```