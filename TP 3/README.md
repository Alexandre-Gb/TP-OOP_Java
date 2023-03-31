# TP3 - Egalité, nullabilité, mutabilité, affichage
## GIBOZ Alexandre, INFO1 2022-2025
***

Chaque répertoire a pour nom le numéro d'exercice auquel il est rattaché. 
Ces répertoires contiennent les fichiers ".java" associés, ainsi que tout autre fichier en lien avec l'exercice en question.

[Consignes de rendu de TP](https://monge.univ-mlv.fr/ens/IR/IR1/2022-2023/Java/index.php)

[Énoncé](https://monge.univ-mlv.fr/ens/IR/IR1/2022-2023/Java/td03.php)
***

## Exercice 1 - Livre

On cherche à écrire un record Book représentant un livre avec un titre et le nom de l'auteur.

1. **Déclarer un record Book avec les composants title et author.**

On déclare le record Book :
```java
public record Book(
    String title,
    String author
) { }
```

2. **Puis essayer le code suivant dans une méthode main du record Book.**

```java
var book = new Book("Da Vinci Code", "Dan Brown");
System.out.println(book.title + ' ' + book.author);
```

**Expliquer.**

Une fois le code compilé avec la commande `javac Book.java`, on peut l'exécuter avec la commande `java Book`. Le résultat est le suivant :
```bash
Da Vinci Code Dan Brown
```

Ce qui est affiché sur la sortie standard est le titre et l'auteur de l'objet, séparés par un espace.

3. **Créer une classe Main (dans un fichier Main.java) et déplacer le main de la classe Book dans la classe Main.
   Quel est le problème ?**

`java System.out.println(book.title + ' ' + book.author);` ne fonctionne plus, car le main n'est plus dans le record Book.

On demande à la fonction println d'afficher le champ "title" et le champ "author". Cependant, les champs sont privés dans un record, ce qui ne permet pas à la fonction main d'y accéder.

**Comment peut-on le corriger ?**

On peut corriger le problème en demandant un affichage de la valeur de retour des méthodes getter du record :
```java
public class Main {
  public static void main(String[] args) {
    var book = new Book("Da Vinci Code", "Dan Brown");
    System.out.println(book.title + ' ' + book.author);
  }
}
```

4. **On peut remarquer que le code permet de créer des livres ayant un titre ou un auteur null.**
```java
var weirdBook = new Book(null, "oops");
```

**Comment faire pour éviter ce problème sachant qu'il existe une méthode static requireNonNull dans la classe java.util.Objects.**

Afin de contrôler les valeurs passées en paramètre, on peut déclarer un constructeur manuellement (remplaçant le constructeur canonique) qui utilisera la méthode static requireNonNull afin de vérifier les valeurs des champs.

```java
import java.util.Objects;

public record Book(
        String title,
        String author
) {
   public Book (String title, String author) {
      Objects.requireNonNull(title, "Book title is null");
      this.title = title;
      this.author = author;
   }
}
```

Si l'on essaye de créer un objet avec un titre null, on obtient une exception :
```bash
Exception in thread "main" java.lang.NullPointerException: Book title is null
    at Book.<init>(Book.java:8)
    at Main.main(Main.java:4)
```

5. **En fait, on peut simplifier le code que vous avez écrit à la question précédente en utilisant un constructeur compact (compact constructor). 
   Commenter le code précédent et utiliser un constructor compact à la place.**

On peut simplifier le code en utilisant un constructeur compact ainsi :
```java
import java.util.Objects;

public record Book(
        String title,
        String author
) {
   public Book{
      Objects.requireNonNull(title, "Book title is null");
   }
}
```

6. **Écrire un autre constructeur qui prend juste un titre et pas d'auteur et ajouter un code de test dans le main.
   On initialisera le champ author avec "<no author>" dans ce cas.**

On peut ajouter un constructeur qui prend juste un titre et pas d'auteur. Record Book.java :
```java
import java.util.Objects;

public record Book(
        String title,
        String author
) {
   public Book {
      Objects.requireNonNull(title, "Book title is null");
   }

   public Book(String title) {
      this(title, "<no author>");
   }
}
```

On peut tester le constructeur dans la classe Main.java :
```java
public class Main {
  public static void main(String[] args) {
    var book = new Book("Da Vinci Code", "Dan Brown");
    var book2 = new Book("Critique of Judgment");
    System.out.println(book.title() + ' ' + book.author());
    System.out.println(book2.title() + ' ' + book2.author());
  }
}
```

On obtient le résultat suivant :
```bash
Da Vinci Code Dan Brown
Critique of Judgment <no author>
```

7. **Comment le compilateur fait-il pour savoir quel constructeur appeler ?**

Le compilateur fait appel au constructeur qui correspond au nombre de paramètres passés en argument (et leur type).

8. **On souhaite maintenant pouvoir changer le titre d'un livre déjà existant en utilisant une méthode nommée withTitle qui prend en paramètre le nouveau titre.
   Pourquoi le code suivant ne marche pas ?**
```java
public void withTitle(String title) {
  this.title = title;
}
```

La méthode ne marche pas car le champ "title" est final, ce qui signifie qu'il ne peut pas être modifié. 
Les champs d'un record sont tous final, ce qui explique l'impossiblité manifeste de modifier le champ.

**Comment faire alors ? Écrire le code correspondant et ajouter un code de test dans le main.**

On peut déclarer une méthode "withTitle" qui retourne un nouvel objet avec le titre désiré :
```java
public Book withTitle(String newTitle) {
  return new Book(newTitle, author);
}
```

On peut tester la nouvelle méthode dans la classe Main.java :
```java
public class Main {
  public static void main(String[] args) {
    var book = new Book("Critique of Judgment", "Immanuel Kant");
    book = book.withTitle("Critique of Pure Reason");
    System.out.println(book.title() + ' ' + book.author());
  }
}
```

On obtient le résultat suivant :
```bash
Critique of Pure Reason Immanuel Kant
```

<br>

## Exercice 2 - Liberté, Égalité, toString

1. **Qu'affiche le code ci-dessous ?**
```java
var b1 = new Book("Da Java Code", "Duke Brown");
var b2 = b1;
var b3 = new Book("Da Java Code", "Duke Brown");

System.out.println(b1 == b2);
System.out.println(b1 == b3);
```

Le code affiche `true` puis `false`.

**Expliquer.**

La première ligne affiche `true` car les deux variables `b1` et `b2` pointent vers le même objet (la même adresse).

La deuxième ligne affiche `false` car les deux variables `b1` et `b3` pointent vers des objets différents (des adresses différentes).

2. **Comment faire pour tester si deux objets ont le même contenu ?**

On peut tester si deux objets ont le même contenu en utilisant la méthode `equals` de la classe de l'objet.

**Écrire le code qui affiche si b1 et b2, puis b1 et b3 ont le même contenu.**

```java
var b1 = new Book("Da Java Code", "Duke Brown");
var b2 = b1;
var b3 = new Book("Da Java Code", "Duke Brown");

System.out.println(b1.equals(b2));
System.out.println(b1.equals(b3));
```

3. **Écrire une méthode isFromTheSameAuthor() qui renvoie vrai si deux livres sont du même auteur.**

On ajoute la méthode `isFromTheSameAuthor` dans la classe Book.java :
```java
public boolean isFromTheSameAuthor(Book secondBook) {
  return author.equals(secondBook.author);
}
```

On teste la méthode dans la classe Main.java :
```java
public class Main {
   public static void main(String[] args) {
      var book1 = new Book("Da Vinci Code", "Dan Brown");
      var book2 = new Book("Angels & Demons", new String("Dan Brown"));
      System.out.println(book1.isFromTheSameAuthor(book2));
   }
}
```

On obtient `true`, ce qui est le résultat attendu.

4. **Comment faire pour que le code suivant**
```java
var javaBook = new Book("Da Java Code", "Duke Brown");
System.out.println(javaBook);
```

Affiche
```bash
Da Java Code by Duke Brown
```

Afin d'afficher le résultat attendu, on peut "redéfinir" la méthode `toString` de la classe `Book` :
```java
public String toString() {
  return title + " by " + author;
}
```

5. **Utiliser l'annotation @Override (java.lang.Override) sur la méthode ajoutée à Book.**

On ajoute l'annotation `@Override` sur la méthode `toString` de la classe `Book` :
```java
@Override
public String toString() {
  return title + " by " + author;
}
```

Dans ce cas précis, on redéfinit correctement la méthode `toString` de la classe.

6. **A quoi sert l'annotation @Override ?**

Le fait de spécifier "Override" permet de vérifier que la méthode surchargée existe bien dans la classe, et que le type de retour est le même.
Cela évite ainsi des erreurs de compilation, en s'assurant qu'on surcharge bien une méthode qui existe déja, et qu'on ne définit pas une nouvelle méthode (e.g. faute de frappe ou d'inattention).

<br>

## Exercice 3 - Liberté, equals, Fraternité

Au lieu d'utiliser un record, un étudiant qui aime bien ré-inventer la roue à écrit le code suivant
```java
public class Book2 {
  private final String title;
  private final String author;

  public Book2(String title, String author) {
    this.title = title;
    this.author = author;
  }
  
  public static void main(String[] args) {
    var book1 = new Book2("Da Vinci Code", "Dan Brown");
    var book2 = new Book2("Da Vinci Code", "Dan Brown");
    System.out.println(book1.equals(book2));
  }
}
```

Malheureusement, le `main` n'a pas le comportement attendu.

1. **Quel est le problème ?**

Le problème est que la méthode `equals` n'est pas définie comme on le souhaiterait.
Cela est du au fait que les records implantent la méthode `equals` par défaut, mais que les classes non (il en va de même pour la méthode hashCode()).

2. **Comment corriger le problème si on s'entête à utiliser une classe ?**

On peut corriger le problème en redéfinissant la méthode `equals` de la classe `Book2` :
```java
@Override
public boolean equals(Object o) {
  return o instanceof Book2 book
    && title.equals(book.title)
    && author.equals(book.author);
}
```

La méthode `equals` de la classe `Book2` renvoie à présent `true`. Elle est correcte.

<br>

## Exercice 4 - Tri à caillou [à la maison]

1. **Écrire une méthode swap qui échange les valeurs de deux cases d'un tableau :**

On teste avec un tableau d'entiers :
```java
public class Algo {
  public static void printArray(Integer[] values) {
    System.out.print("[ ");
    for (int i = 0; i < values.length; i++) {
      System.out.print(values[i] + " ");
    }
    System.out.println("]");
  }
  public static Integer[] swap(Integer[] values, int index1, int index2) {
    Integer temp = values[index1];
    values[index1] = values[index2];
    values[index2] = temp;
    return values;
  }

  public static void main(String[] args) {
    Integer[] values = new Integer[5];
    for (int i = 0; i < values.length; i++) {
      values[i] = i;
    }
    printArray(values);
    swap(values, 0, 4);
    printArray(values);
  }
}
```

On obtient le résultat suivant :
```bash
[ 0 1 2 3 4 ]
[ 4 1 2 3 0 ]
```

2. **Écrire une méthode indexOfMin qui renvoie l'indice de la valeur minimale d'un tableau.**

On écrit la méthode indexOfMin :
```java
 public static Integer indexOfMin(Integer[] values) {
   Integer min = values[0];
   Integer minIndex = 0;
   for (int i = 0; i < values.length; i++) {
     if (values[i] < min) {
       min = values[i];
       minIndex = i;
     }
   }
   return minIndex;
 }
```

3. **Modifier la méthode indexOfMin en ajoutant deux indices indiquant que l'on cherche l'indice du minimum, non pas sur tout le tableau, mais sur la partie de tableau entre ces deux indices (le premier inclus, le deuxième exclu).**

On modifie la méthode `indexOfMin` :
```java
public static Integer indexOfMin(Integer[] values, int lo, int hi) {
  if (lo < 0 || hi > values.length || lo >= hi) {
    return null;
  }
  
  Integer min = values[lo];
  Integer minIndex = lo;
  for (int i = lo; i < hi; i++) {
    if (values[i] < min) {
      min = values[i];
      minIndex = i;
    }
  }
  return minIndex;
}
```

4. **Écrire la méthode sort qui prend un tableau d'entiers en paramètre et qui trie celui-ci en utilisant pour cela les méthodes indexOfMin et swap.**

On écrit la méthode `sort` :
```java
public static void sort(Integer[] values) {
  Integer min;
  for (int i = 0; i < values.length; i++) {
    min = indexOfMin(values, i, values.length);
    swap(values, i, min);
  }
}
```

On teste la méthode dans l'entrée de programme :
```java
public static void main(String[] args) {
  Integer[] values = new Integer[5];
  for (int i = 0; i < values.length; i++) {
    values[i] = values.length - i;
  }
  printArray(values);
  sort(values);
  printArray(values);
}
```

On obtient le résultat suivant :
```bash
[ 5 4 3 2 1 ]
[ 1 2 3 4 5 ]
```