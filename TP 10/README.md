# TP10 - Paquetage, Structure de données, Relation d'implantation
## GIBOZ Alexandre, INFO1 2022-2025
***

[Consignes de rendu de TP](https://monge.univ-mlv.fr/ens/IR/IR1/2022-2023/Java/index.php)

[Énoncé](https://monge.univ-mlv.fr/ens/IR/IR1/2022-2023/Java/td10.php)
***

## Exercice 1 - Les listes chaînées

1. **Créer un record Link dans le paquetage fr.uge.data correspondant à un maillon de la liste chaînée stockant des entiers.**
```java
package fr.uge.data;

record Link(int value, Link next) { }
```

**Quelle doit être la visibilité du record fr.uge.data.Link ?**

La visibilité du record `Link` doit être `package` car il n'est utilisé que par `LinkedList`.

2. **Quelle est la commande pour exécuter le main du record fr.uge.data.Link à partir d'un terminal (pas dans Eclipse) ?**

```bash
java --enable-preview fr.uge.data.Link
``` 

3. **Créer une classe fr.uge.data.LinkedLink qui permettra de manipuler une liste chainée par son premier maillon**
```java
package fr.uge.data;

public class LinkedLink {
  private Link head;
  private int size;
}
```

Le constructeur java, si non déclaré explicitement, attribue des valeurs par défaut aux attributs de la classe.
head sera donc null et size à 0, ce qui correspond parfaitement aux propriétés d'une Liste vide.

3.1. **une méthode add(int value) qui ajoute un élément en tête de la liste.**
```java
public void add(int value) {
  head = new Link(value, head);
  size++;
}
```

3.2. **une méthode get(index) qui renvoie l'élément à l'index (en commençant à 0).**
```java
public Link get(int index) {
  if (index < 0 || index >= size) {
    throw new IndexOutOfBoundsException();
  }

  var current = head;
  for (int i = 0; i < index; i++) {
    current = current.next();
  }
  return current;
}
```

**Comment faire en sorte que le code qui vérifie que l'index est valide soit en O(1) ?**

La LinkedList possède un attribut size qui correspond à la taille de la liste. Il suffit donc de vérifier que l'index est compris entre 0 et size-1.

3.3. **une méthode forEach(lambda) qui appel la lambda avec la valeur de chaque maillon de la liste.**
```java
public void forEach(IntConsumer consumer) {
  var current = head;

  while (current != null) {
    consumer.accept(current.value());
    current = current.next();
  }
}
```

3.4. **une méthode toString qui représente la liste chainée avec des "-->" entre les valeurs.**
```java
public String toString() {
  var joiner = new StringJoiner(" -> ");
  forEach(value -> joiner.add(Integer.toString(value)));
  return joiner.toString();
}
```

**Pour tester la classe fr.uge.data.LinkedLink, créer une classe Main dans le package fr.uge.data.main dont la méthode main contiendra vos tests.**
```java
package fr.uge.data.main;

import fr.uge.data.LinkedLink;

public class Main {
  public static void main(String[] args) {
    var list = new LinkedLink();
    list.add(12);
    list.add(144);
    System.out.println(list);
  }
}
```

<br>

## Exercice 2 - Liste chaînée (suite)

1. **Dans le but de pouvoir ré-utiliser la liste dans différents codes, changer les classes fr.uge.data.LinkedLink et fr.uge.data.Link pour une implantation plus générique à base d'Object.**

On modifie le record `Link` pour qu'il soit plus générique qu'actuellement.
```java
package fr.uge.data;

record Link(Object value, Link next) { }
```

2. **Dans la classe Main, expliquer pourquoi le code suivant ne fonctionne pas ?***
```java
var l = new LinkedLink();
l.add("hello");
l.add("world");
l.forEach(s -> System.out.println("string " + s + " length " + s.length()));
```

Le code ne fonctionne pas car il est nécessaire de caster les objets récupérés dans la liste chainée pour pouvoir les utiliser.
```java
var l = new LinkedLink();
l.add("hello");
l.add("world");
l.forEach(s -> System.out.println("string " + s + " length " + ((String) s).length()));
```

**Que doit-on faire pour que le code fonctionne**

Pour que le code fonctionne, on spécifie explicitement le type pris en charge par la LinkedLink.
```java
var l = new LinkedLink<String>();
```

<br>

## Exercice 3 - Générification de LinkedLink

1. **Rappeler quel est l'intérêt d'utiliser un type paramétré ici ?**

Le type paramétré nous permet de spécifier le type d'objet que l'on souhaite stocker dans la liste chainée. 

Cela permet de ne pas avoir à créer une liste chainée pour chaque type d'objet que l'on souhaite stocker. On peut stocker (avec cette classe et record) des objets de type String, Integer, Double, etc.

2. **Paramétrer la classe fr.uge.data.LinkedLink pour que celle-ci soit générique.**

On modifie le record `Link` pour qu'il soit plus générique qu'actuellement.
```java
package fr.uge.data;

record Link<T>(T value, Link<T> next) { }
```

On modifie à présent la classe `LinkedLink` pour qu'elle soit plus générique qu'actuellement.
```java
package fr.uge.data;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Consumer;

public class LinkedLink<T> {
  private Link<T> head;
  private int size;

  public void add(T value) {
    head = new Link<>(value, head);
    size++;
  }

  public T get(int index) {
/*    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    }*/
    Objects.checkIndex(index, size);

    var current = head;
    for (int i = 0; i < index; i++) {
      current = current.next();
    }

    return current.value();
  }

  public void forEach(Consumer<T> consumer) {
    var current = head;

    while (current != null) {
      consumer.accept(current.value());
      current = current.next();
    }
  }

  public String toString() {
    var joiner = new StringJoiner(" -> ");
    forEach(value -> joiner.add(value.toString()));
    return joiner.toString();
  }
}
```

3. **Modifier la classe fr.uge.data.main.Main en conséquence.**
```java
package fr.uge.data.main;

import fr.uge.data.LinkedLink;

public class Main {
  public static void main(String[] args) {
    var list = new LinkedLink<Integer>();
    list.add(12);
    list.add(144);
    System.out.println(list);
  }
}
```

4. **Optionnellement, pour les plus balèzes, écrire une méthode removeIf qui supprime tous les élements vrai pour un prédicat.
   Par exemple, pour supprimer tous les éléments paires d'une liste**
```java
var l = new LinkedLink<Integer>();
l.add(24);
l.add(17);
l.add(12);
l.removeIf(i -> i % 2 == 0);
System.out.println(l); // 17
```

