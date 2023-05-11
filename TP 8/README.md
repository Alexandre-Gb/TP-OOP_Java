# TP8 - Lambda, interface fonctionnelle et type fonction
## GIBOZ Alexandre, INFO1 2022-2025
***

[Consignes de rendu de TP](https://monge.univ-mlv.fr/ens/IR/IR1/2022-2023/Java/index.php)

[Énoncé](https://monge.univ-mlv.fr/ens/IR/IR1/2022-2023/Java/td08.php)
***

## Exercice 1 - upperCaseAll

On souhaite écrire, dans une classe Lambdas, une méthode statique upperCaseAll qui prend en paramètre une liste de chaînes de caractères et met chaque chaîne en majusucules.

1. **Rappeler comment on met une chaîne de caractères en majusucules, indépendamment de la langue dans laquelle l'OS est configuré.**

Afin de mettre une chaîne de caractères en majuscules, on utilise la méthode `toUpperCase()` de la classe `String`. 

2. **On va utiliser la méthode List.replaceAll pour mettre toutes les chaînes de caractères en majuscules. 
    Quelle est l'interface fonctionnelle utilisée par la méthode replaceAll ?**

L'interface fonctionnelle utilisée par la méthode `replaceAll` est `UnaryOperator<E>`.

3. **À quel type de fonction cela correspond-il ? Autrement dit, que prend la fonction en argument et que renvoie-t-elle ?)**

La fonction `replaceAll` permet de remplacer toutes les occurrences d'un élément dans une liste par un autre élément spécifié.
`UnaryOperator<E>` prend en argument une fonction prennant en paramètre un objet de type `E` et qui renvoie un objet du même type.

La fonction ne renvoi rien, elle modifie chaque élément de la liste sur laquelle elle est appliquée.

4. **Sachant que l'on appelle replaceAll avec une liste de String, quel est le type des paramètres de la lambda et quel est son type de retour ?**

Le type de retour de la lambda sera void, car la fonction ne renvoi rien et effectue les modifications directement sur la liste en paramètre.
Elle prend en paramètre un UnaryOperator qui sera dans notre cas une chaîne de caractères.

5. **Écrire le code de la méthode upperCaseAll**
```java
public static void upperCaseAll(List<String> strings) {
  Objects.requireNonNull(strings);
  strings.replaceAll(e -> e.toUpperCase(Locale.ROOT));
}
```

<br>

## Exercice 2 - occurences

On souhaite calculer le nombre d'occurences de chaque chaîne de caractères dans une liste de chaînes de caractères. 

Par exemple, avec la liste ["foo", "bar", "foo"], la méthode occurences va renvoyer {"foo" = 2, "bar" = 1}. 

1. **Quelle est le type du paramètre de la méthode occurences ?**

Le type du paramètre de la méthode `occurences` est `List<String>` (ou n'importe quelle classe implantant l'interface `List`).

**Quelle est le type de retour de la méthode occurences ?**

La méthode `occurences` renvoie un objet de type `Map<String, Integer>`. 
Chaque mot de la liste sera utilisé comme une clef (de manière unique), et se verra associé à un entier représentant le nombre d'occurences de ce mot dans la liste en paramètre.

2. **Quelle est l'implantation que l'on doit choisir ici ?**

On doit choisir une implantation de type `HashMap` car on a besoin d'une structure de données permettant de stocker des couples clef-valeur (à clef unique), et de pouvoir accéder à ces valeurs en temps constant.

3. **On veut parcourir la liste avec la méthode forEach. Quelle interface fonctionnelle prend-elle en paramètre ?**

La méthode `forEach` prend en paramètre une interface fonctionnelle de type `Consumer<? super T>`.

**Quel est le type fonction correspondant ?**

Le type fonction correspondant est `void accept(T t)`.

**Ici, quels sont les types des paramètres / de retour de la lambda que vous allez utliser ?**

La lambda prend en paramètre une chaîne de caractères, et ne renvoie rien.

4. **Pour compter le nombre d'occurences, on va utiiser la méthode merge de la structure de données que vous avez choisi de renvoyer. Quelle interface fonctionnelle prend-elle en paramètre ?**

La méthode `merge` prend en paramètre une interface fonctionnelle de type `BiFunction<? super V, ? super V, ? extends V>`.

**Quel est le type fonction correspondant ?**

Le type fonction correspondant est `V apply(V v1, V v2)`.

**Ici, quels sont les types des paramètres / de retour de la lambda que vous allez utliser ?**

La lambda prend en paramètre une clef (qui sera hashée afin d'être convertie en int), une valeur et une fonction de remapping.

Dans notre cas, la fonction de remapping sera `Integer::sum`, une méthode statique qui permet de faire la somme de deux entiers.

La lambda ne renvoie rien car elle modifie directement la Liste de chaînes de caractères.

5. **Écrire le code de occurences, toujours dans la classe Lambdas.**
```java
public static Map<String, Integer> occurences(List<String> strings) {
  Objects.requireNonNull(strings);
  HashMap<String, Integer> map = new HashMap<>();
  strings.forEach(e -> map.put(e, map.getOrDefault(e, 0) + 1));
  return Map.copyOf(map);
}
```

On teste avec les valeurs suivantes:
```java
ArrayList<String> strings = new ArrayList<>();
strings.add("foo");
strings.add("bar");
strings.add("foo");
System.out.println(occurences(strings));
```

On obtient le résultat suivant:
```
{foo=2, bar=1}
```

6. **On peut noter qu'il existe une méthode statique sum dans la classe java.lang.Integer qui fait la somme de deux valeurs, on peut donc l'utiliser sous forme de method reference à la place de la lambda, lors de l'appel à merge. 
    Modifier le code pour l’utiliser (garder la précédente version en commentaires).**
```java
public static Map<String, Integer> occurences(List<String> strings) {
  Objects.requireNonNull(strings);
  HashMap<String, Integer> map = new HashMap<>();
// strings.forEach(e -> map.put(e, map.getOrDefault(e, 0) + 1));
  strings.forEach(e -> map.merge(e, 1, Integer::sum));
  return Map.copyOf(map);
}
```

<br>

## Exercice 3 - groupBy

On souhaite pouvoir grouper des acteurs par leurs prénoms (firstName), avec les acteurs définis ainsi :
```java
public record Actor(String firstName, String lastName) {
  public Actor {
    Objects.requireNonNull(firstName);
    Objects.requireNonNull(lastName);
  }
}
```

On va pour cela écrire une méthode actorGroupByFirstName qui prend en paramètre une liste d'acteurs, par exemple [Actor("bob", "de niro"), Actor("willy", "cat"), Actor("bob", "cat")] et renvoie une Map qui, pour un prénom, contient une liste de tous les acteurs ayant ce prénom. 

Avec notre exemple de liste, cela donne :
```
{
  "bob" = [Actor("bob", "de niro"), Actor("bob", "cat")],
  "willy" = [Actor("willy", "cat")]
}
```

1. **Quel est le type de paramètre de actorGroupByFirstName ?**

Le type de paramètre de actorGroupByFirstName est `List<Actor>`.

**Quel est le type de retour de actorGroupByFirstName ?**

Le type de retour de actorGroupByFirstName est `Map<String, List<Actor>>`.

2. **Rappeler comment marche la méthode Map.computeIfAbsent. Son second paramètre est une interface fonctionnelle, à quel type de fonction correspond-elle ?
   Expliquer à quoi correspondent le premier paramètre et le second paramètre de Map.computeIfAbsent, puis comment on peut l'utiliser pour grouper les acteurs selon leur prénom.**

La méthode `Map.computeIfAbsent` prend en paramètre une clef et une interface fonctionnelle de type `Function<? super K, ? extends V>`, et renvoie la valeur associée à la clef si elle existe, ou bien la valeur renvoyée par la fonction passée en paramètre si la clef n'existe pas.

Dans notre situation ou l'on souhaite grouper les acteurs par leur prénom, la clef sera le prénom de l'acteur, et la valeur sera une liste d'acteurs ayant ce prénom.

3. **Dans notre cas, quel doit être le type de la lambda passée en second paramètre de computeIfAbsent ?**

La lambda passée en second paramètre de `computeIfAbsent` sera de type `Function<String, List<Actor>>`.

4. **Écrire la méthode actorGroupByFirstName()**
```java
public static Map<String, List<Actor>> actorGroupByFirstName(List<Actor> actors) {
  Objects.requireNonNull(actors);
  HashMap<String, List<Actor>> map = new HashMap<>();
  actors.forEach(e -> map.computeIfAbsent(e.firstName(), k -> new ArrayList<>()).add(e));
  return Map.copyOf(map);
}
```

On teste avec les valeurs suivantes:
```java
ArrayList<Actor> actors = new ArrayList<>();
actors.add(new Actor("bob", "de niro"));
actors.add(new Actor("willy", "cat"));
actors.add(new Actor("bob", "cat"));

System.out.println(actorGroupByFirstName(actors));
```

On obtient le résultat suivant:
```
{willy=[Actor[firstName=willy, lastName=cat]], bob=[Actor[firstName=bob, lastName=de niro], Actor[firstName=bob, lastName=cat]]}
```

5. **Si on veut maintenant grouper les acteurs par rapport à leur nom (lastName) au lieu du prénom, on va écrire à peu près le même code. 
    On veut généraliser le code en écrivant une méthode actorGroupBy. qui prend en paramètre une liste d'acteurs ainsi qu'une fonction qui, étant donné un acteur, renvoie la valeur par laquelle il va être groupé. 
    Et elle renvoie une Map des valeur par lesquelles on les groupe, associées aux listes d'acteurs groupés.**

**Par exemple, on va écrire :**
```java
var group1 = actorGroupBy(actors, Actor::firstName);  // groupe par prénom
var group2 = actorGroupBy(actors, Actor::lastName);   // groupe par nom
```

**Quelle doit être le type fonction du second paramètre de actorGroupBy ?**

Le type fonction du second paramètre de `actorGroupBy` doit être `Function<Actor, String>`.

**Quelle est l'interface fonctionnelle correspondante ?**

L'interface fonctionnelle correspondante est `Function<T, R>`.

**Écrire la méthode actorGroupBy.** 
```java
public static Map<String, List<Actor>> actorGroupBy(List<Actor> actors, Function<Actor, String> keyExtractor) {
  Objects.requireNonNull(actors);
  Objects.requireNonNull(keyExtractor);
  HashMap<String, List<Actor>> map = new HashMap<>();
  actors.forEach(e -> map.computeIfAbsent(keyExtractor.apply(e), k -> new ArrayList<>()).add(e));
  return Map.copyOf(map);
}
```

On teste avec les valeurs suivantes:
```java
var group1 = actorGroupBy(actors, Actor::firstName);  // groupe par prénom
var group2 = actorGroupBy(actors, Actor::lastName);   // groupe par nom
System.out.println(group1);
System.out.println(group2);
```

On obtient le résultat suivant:
```
{bob=[Actor[firstName=bob, lastName=de niro], Actor[firstName=bob, lastName=cat]], willy=[Actor[firstName=willy, lastName=cat]]}
{cat=[Actor[firstName=willy, lastName=cat], Actor[firstName=bob, lastName=cat]], de niro=[Actor[firstName=bob, lastName=de niro]]}
```

6. **Écrire la méthode groupBy qui prend en paramètre n'importe quel type de liste et n'importe quelle fonction.**
```java
public static <T, K> Map<K, List<T>> actorGroupBy(List<T> list, Function<T, K> keyExtractor) {
  Objects.requireNonNull(list);
  Objects.requireNonNull(keyExtractor);
  HashMap<K, List<T>> map = new HashMap<>();
  list.forEach(e -> map.computeIfAbsent(keyExtractor.apply(e), k -> new ArrayList<>()).add(e));
  return Map.copyOf(map);
}
```

On teste avec les valeurs suivantes:
```java
var group1 = actorGroupBy(actors, Actor::firstName);  // groupe par prénom
var group2 = actorGroupBy(actors, Actor::lastName);   // groupe par nom
System.out.println(group1);
System.out.println(group2);
```

On obtient le résultat suivant:
```
{willy=[Actor[firstName=willy, lastName=cat]], bob=[Actor[firstName=bob, lastName=de niro], Actor[firstName=bob, lastName=cat]]}
{de niro=[Actor[firstName=bob, lastName=de niro]], cat=[Actor[firstName=willy, lastName=cat], Actor[firstName=bob, lastName=cat]]}
```