# TP4 - Objets, délégation, ArrayList, HashMap
## GIBOZ Alexandre, INFO1 2022-2025
***

Chaque répertoire a pour nom le numéro d'exercice auquel il est rattaché.
Ces répertoires contiennent les fichiers ".java" associés, ainsi que tout autre fichier en lien avec l'exercice en question.

[Consignes de rendu de TP](https://monge.univ-mlv.fr/ens/IR/IR1/2022-2023/Java/index.php)

[Énoncé](https://monge.univ-mlv.fr/ens/IR/IR1/2022-2023/Java/td04.php)
***

## Exercice 1 - Eclipe :)

À partir de maintenant, nous allons utiliser Eclipse (lancer la commande eclipse dans un terminal) comme environnement pour faire les TPs. 

5. **A faire à la maison pour le compte rendu:**

I. **Que fait sysout + Ctrl + Space dans un main ?**

Ce racourci dans un main permet de générer automatiquement une instruction `System.out.println()`.

II. **Que fait toStr + Ctrl + Space dans une classe ?**

Ce racourci dans une classe permet de générer automatiquement une méthode `toString()`.

III. **Définir un champs foo de type int, que fait get + Ctrl + Space, et set + Ctrl + Space.**

Le racourci get + Ctrl + Space permet de générer automatiquement une méthode `getFoo()` (un getter).

Le racourci set + Ctrl + Space permet de générer automatiquement une méthode `setFoo(int foo)` (un setter).

IV. **Dans le menu Source, comment générer un constructeur initialisant le champ foo ?**

Dans le menu Source, on clique sur Generate `Constructor using Fields`, puis on sélectionne le champ foo.

V. **Sélectionner le nom de la classe puis Alt + Shift + R, qu'obtient-on ? Même question avec le champ foo.**

Sélectionner le nom de la classe puis Alt + Shift + R permet de renommer la classe.

VI. **Écrire a = 2 + 3 + 4, puis sélectionner 2 + 3 puis Alt + Shift + L.**

Cette manipulation permet de créer une variable temporaire `int tmp = 2 + 3` et de remplacer `2 + 3` par `tmp` (extraire l'expression dans une variable locale).

VII. **Écrire new Integer(2), en gardant le curseur après ')', appuyer sur Ctrl + 1, que se passe-t-il ?**

Cette manipulation permet de remplacer `new Integer(2)` par `Integer.valueOf(2)`.

VIII. **Déclarer une variable s de type String et cliquer sur String en maintenant la touche Ctrl . Que se passe-t-il ?**

Cette manipulation permet d'afficher toutes les occurences de la classe String dans le code.

IX. **Dans la méthode toString(), que fait un Ctrl + Clic sur super.toString() ?**

Cette manipulation permet d'aller directement à la définition de la méthode `toString()` de la classe `Object`.

X. **Sélectionner le champs foo, puis Ctrl + Shift + G. Que se passe-t-il ?**

Cela permet d'afficher les références du champs foo dans le code, afin de faciliter la navigation.

XI. **À quoi sert Ctrl + Shift + O ?**

Ce racourci permet d'organiser les imports.

XII. **À quoi sert Ctrl + Shift + C ?**

Ctrl + Shift + C sert à commenter chaque ligne sélectionnée

<br>

## Exercice 2 - Library

1. **Écrire une classe Library avec un champs books de type ArrayList ainsi qu'un constructeur sans paramètre initialisant le champ books.
   Attention à déclarer les bons modificateurs pour le champ books.**

On créer une classe Library qui contient un ArrayList de Book. On initialise cet ArrayList dans le constructeur de Library.

```java
public class Library {
  private final ArrayList<Book> books;

  public Library () {
    books = new ArrayList<>();
  }
}
```

**Note : il est possible que le compilateur lève un warning, si c'est le cas, comment corriger le problème ?**

Aucun warning n'est levé par javac.

2. **Ajouter une méthode add qui permet d'ajouter des books (non null) à la liste de livres.**

On ajoute la méthode suivante à la classe Library :

```java
public void add(Book newBook){
  Objects.requireNonNull(newBook);
  books.add(newBook);
}
```

3. **Écrire une méthode findByTitle qui permet de trouver un livre en fonction de son titre dans la bibliothèque. 
   La méthode doit renvoyer null dans le cas où aucun livre n'a le bon titre.**

On ajoute la méthode suivante à la classe Library :

```java
  public Book findByTitle(String title) {
    for (Book book : books) {
      if (book.title().equals(title)) {
        return book;
      }
    }
    return null;
  }
```

4. **Comment le compilateur compile-t-il une boucle foreach sur une collection ?
   Utiliser la commande javap pour vérifier !**

On utilise la commande suivante pour vérifier la compilation de la boucle foreach :

```bash
javap -c Library.class
```

On obtient le résultat suivant:
```java
Compiled from "Library.java"
public class Library {
  public Library();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: aload_0
       5: new           #7                  // class java/util/ArrayList
       8: dup
       9: invokespecial #9                  // Method java/util/ArrayList."<init>":()V
      12: putfield      #10                 // Field books:Ljava/util/ArrayList;
      15: return

  public void add(Book);
    Code:
       0: aload_0
       1: getfield      #10                 // Field books:Ljava/util/ArrayList;
       4: aload_1
       5: invokevirtual #16                 // Method java/util/ArrayList.add:(Ljava/lang/Object;)Z
       8: pop
       9: return

  public Book findByTitle(java.lang.String);
    Code:
       0: aload_0
       1: getfield      #10                 // Field books:Ljava/util/ArrayList;
       4: invokevirtual #20                 // Method java/util/ArrayList.iterator:()Ljava/util/Iterator;
       7: astore_2
       8: aload_2
       9: invokeinterface #24,  1           // InterfaceMethod java/util/Iterator.hasNext:()Z
      35: ifeq          40
      38: aload_3
      39: areturn
      40: goto          8
      43: aconst_null
      44: areturn
}
```

On en déduit que la boucle foreach est compilée en une boucle for.

5. **Expliquer pourquoi la méthode findByTitle doit renvoier null plutôt que de lever une exception.**

La méthode findByTitle doit renvoyer null plutôt que de lever une exception car il est possible que le titre du livre recherché ne soit pas présent dans la bibliothèque, ou que la bibliothèque ne contienne aucun livre.

6. **Écrire une méthode toString permettant d'afficher les livres de la bibliothèque dans l'ordre d'insertion, un livre par ligne.**

On ajoute la méthode suivante à la classe Library :
```java
@Override
public String toString() {
  StringBuilder sb = new StringBuilder();
  var separator = "";
  for (Book book : books) {
    sb.append(separator).append(book.toString());
    separator = "\n";
  }
  return sb.toString();
}
```

On ajoute le code suivant dans le point d'entrée du programme afin de tester la méthode toString :
```java
public static void main(String[] args) {
  Library library = new Library();
  
  library.add(new Book("Critique of Judgement", "Immanuel Kant"));
  library.add(new Book("The society of the spectacle", "Guy Debord"));
  library.add(new Book("What is to be done?", "Vladimir Ilyich Ulyanov"));
  library.add(new Book("Critique of Pure Reason", "Immanuel Kant"));
  
  System.out.println(library.toString());
}
```

On obtient le résultat suivant :
```
Critique of Judgement by Immanuel Kant
The society of the spectacle by Guy Debord
What is to be done? by Vladimir Ilyich Ulyanov
Critique of Pure Reason by Immanuel Kant
```

<br>

## Exercice 3 - Librarie 2 (le retour de la vengeance)

L'implantation faite dans l'exercice précédent est lente si la méthode findByTitle est appelée fréquemment. 
On va changer l'implantation en gardant la même API, c'est à dire avec les mêmes méthodes publiques (ayant les mêmes signatures). 
De cette façon, il ne sera pas nécessaire de changer le code du main ! 

1. **Quelle est la complexité de la méthode findByTitle de la classe Library ?**

La complexité de la méthode findByTitle est en O(n), car on parcourt la liste de livres pour trouver le bon titre via une boucle simple.

2. **Regarder la javadoc de la classe java.util.HashMap ainsi que celle de ses méthodes put et get.
   Quelle est la structure de données algorithmique dont java.util.HashMap est une implantation ?**

java.util.HashMap est une implantation de la structure de données algorithmique qu'on appelle une "table de hachage".

**Sachant que l'on veut améliorer la performance de findByTitle comment peut on utiliser la classe java.util.HashMap pour cela ?**

On peut utiliser la classe HashMap pour améliorer la performance de findByTitle en faisant du titre du livre la clef et le livre lui-même la valeur qui est associée à cette même clef.

On remplacera donc la liste de livres `ArrayList<Book> books` par une HashMap `HashMap<String, Book> books`.

**Quelle sera alors la complexité de findByTitle ?**

La complexité de findByTitle sera O(1), car on accède directement au livre par son titre. 

On passe ainsi de O(n) à O(1), ce qui représente un gain de performance considérable.

3. **Commenter entièrement le code de la classe Library (pour ne pas perdre votre travail) et recopier les signatures des méthodes commentées. 
   Pour l'instant, laisser la méthode toString de côté. 
   Modifier les champs afin d'utiliser une java.util.HashMap et implanter les méthodes le constructeur et les méthodes add et findByTitle.**

On obtient le résultat suivant:
```java
public class Library {
  private final HashMap<String, Book> books;

  public Library () {
    books = new HashMap<>();
  }

  public void add(Book newBook){
    books.put(newBook.title(), newBook);
  }

  public Book findByTitle(String title) {
    return books.get(title);
  }
}
```

4. **Expliquer pourquoi, ici, on a préféré utiliser une classe pour représenter Libary plutôt qu'un record.**

La convention veut qu'un record soit utilisé pour représenter une structure de données immuable, permettant l'instanciation de valeurs définitives.

Dans le cas de notre Librairie, il est possible d'ajouter des livres à la bibliothèque, ce qui rend le record inadapté.

Bien que la HashMap des livres soit `private final` (comme pour un record), il reste possible d'ajouter du contenu à la HashMap, ce qui la rend mutable (bien qu'on n'altère pas la référence à la HashMap).

5. **Pour l'implantation de la méthode toString, quelle méthode de java.util.HashMap doit-on utiliser pour obtenir l'ensemble des valeurs stockées ? 
   Si vous ne savez pas, lisez la javadoc ! 
   Écrire la méthode toString.**

On utilise la méthode `values()` pour obtenir l'ensemble des valeurs stockées. 
```java
public String toString() {
  StringBuilder sb = new StringBuilder();
  var separator = "";
  for (Book book : books.values()) {
    sb.append(separator).append(book.toString());
    separator = "\n";
  }
  return sb.toString();
}
```

On reprend le code du main de la question 6 de l'exercice précédent pour tester la méthode toString :
```java
public static void main(String[] args) {
  Library library = new Library();
  
  library.add(new Book("Critique of Judgement", "Immanuel Kant"));
  library.add(new Book("The society of the spectacle", "Guy Debord"));
  library.add(new Book("What is to be done?", "Vladimir Ilyich Ulyanov"));
  library.add(new Book("Critique of Pure Reason", "Immanuel Kant"));
  
  System.out.println(library.toString());
}
```

On obtient un résultat identique :
```
Critique of Judgement by Immanuel Kant
The society of the spectacle by Guy Debord
What is to be done? by Vladimir Ilyich Ulyanov
Critique of Pure Reason by Immanuel Kant
```

6. **En fait, la méthode toString ne fait pas exactement ce qui est demandé, car elle ne permet pas d'afficher les éléments dans l'ordre d'insertion. 
   Sachant qu'il existe une classe LinkedHashMap, comment peut-on résoudre ce problème ?**

La classe LinkedHashMap est une implémentation de la structure de données algorithmique qu'on appelle une "table de hachage ordonnée". Elle permet ainsi de conserver l'ordre d'insertion des éléments.

Afin de l'implémenter, il nous suffit de remplacer la classe HashMap par la classe LinkedHashMap pour la déclaration de la variable `books` :
```java
// private final HashMap<String, Book> books;
private final LinkedHashMap<String, Book> books;
```

7. **On souhaite ajouter une méthode removeAllBooksFromAuthor qui prend un nom d'auteur en paramètre et supprime tous les livres de cet auteur de la bibliothèque.
   Sachant qu'il existe une méthode remove dans la classe java.util.LinkedHashMap, écrire une implantation qui parcourt tous les livres avec une boucle for each et supprime ceux de l'auteur avec remove.**

On implante la méthode removeAllBooksFromAuthor comme suit :
```java
public void removeAllBooksFromAuthor(String author) {
  for (var entry : books.entrySet()) {
    if (entry.getValue().author().equals(author)) {
      books.remove(bookEntry.getKey());
    }
  }
}
```

**Pourquoi votre implantation lève-t-elle une exception dans l'exemple suivant ?**
```java
var library2 = new Library();
library2.add(new Book("Da Vinci Code", "Dan Brown"));
library2.add(new Book("Angels & Demons", "Dan Brown"));
library2.removeAllBooksFromAuthor("Dan Brown");
```

On obtient l'Exception suivante dans le cas ou l'on utilise l'exemple suivant avec la fonction removeAllBooksFromAuthor :
```bash
Exception in thread "main" java.util.ConcurrentModificationException
        at java.base/java.util.HashMap$HashIterator.nextNode(HashMap.java:1605)
        at java.base/java.util.HashMap$EntryIterator.next(HashMap.java:1638)
        at java.base/java.util.HashMap$EntryIterator.next(HashMap.java:1636)
        at Library.removeAllBooksFromAuthor(Library.java:54)
        at Book.main(Book.java:19)
```

L'exception `java.util.ConcurrentModificationException` est levée lorsqu'une modification est effectuée sur une collection pendant qu'une itération est en cour (ce qui est le cas ici).

8. **En fait, il existe une méthode remove sur l'interface Iterator qui n'a pas ce problème, car le parcours et la suppression se font sur le même itérateur.
   Implanter correctement la méthode removeAllBooksFromAuthor.**

On implante la méthode removeAllBooksFromAuthor comme suit :
```java
public void removeAllBooksFromAuthor(String author) {
  var iterator = books.entrySet().iterator();
  while (iterator.hasNext()) {
    if (iterator.next().getValue().author().equals(author)) {
      iterator.remove();
    }
  }
}
```

On vérifie le bon fonctionnement de la méthode removeAllBooksFromAuthor avec l'exemple suivant :
```java
public static void main(String[] args) {
  var library2 = new Library();
  library2.add(new Book("Da Vinci Code", "Dan Brown"));
  library2.add(new Book("Angels & Demons", "Dan Brown"));
  
  System.out.println(library2.toString());
  library2.removeAllBooksFromAuthor("Dan Brown");
  System.out.println(library2.toString());
}
```

On obtient le résultat suivant :
```bash
Da Vinci Code by Dan Brown
Angels & Demons by Dan Brown
```

On constate que les livres de l'auteur Dan Brown ont bien été supprimés de la bibliothèque, car ils ne sont plus affichés lors du second appel à la méthode `toString`.

9. **De façon optionnelle, si vous êtes balèze, il existe une méthode removeIf sur Collection qui permet d'écrire la méthode removeAllBooksFromAuthor en une ligne !**

On implante la méthode removeAllBooksFromAuthor comme suit :
```java
public void removeAllBooksFromAuthor(String author) 
  books.entrySet().removeIf(entry -> entry.getValue().author().equals(author));
}
```

On vérifie le bon fonctionnement de la méthode removeAllBooksFromAuthor avec l'exemple suivant :
```java
public static void main(String[] args) {
  var library2 = new Library();
  library2.add(new Book("Da Vinci Code", "Dan Brown"));
  library2.add(new Book("Angels & Demons", "Dan Brown"));
  
  System.out.println(library2.toString());
  library2.removeAllBooksFromAuthor("Dan Brown");
  System.out.println(library2.toString());
}
```

On obtient le résultat suivant :
```bash
Da Vinci Code by Dan Brown
Angels & Demons by Dan Brown
```

On constate que les livres de l'auteur Dan Brown ont bien été supprimés de la bibliothèque, car ils ne sont plus affichés lors du second appel à la méthode `toString`.
