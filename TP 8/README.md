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

3. **On veut parcourir la liste avec la méthode forEach. Quelle interface fonctionnelle prend-elle en paramètre ? Quel est le type fonction correspondant ? Ici, quels sont les types des paramètres / de retour de la lambda que vous allez utliser ?**