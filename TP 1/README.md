# TP1 - Premiers pas en Java, chaînes de caractères, tableaux, boucles
## GIBOZ Alexandre, INFO1 2022-2025
***

Chaque répertoire a pour nom le numéro d'exercice auquel il est rattaché. 
Ces répertoires contiennent les fichiers ".java" associés, ainsi que tout autre fichier en lien avec l'exercice en question.

[Consignes de rendu de TP](https://monge.univ-mlv.fr/ens/IR/IR1/2022-2023/Java/index.php)

[Énoncé](https://monge.univ-mlv.fr/ens/IR/IR1/2022-2023/Java/td01.php)
***

## Exercice 1 - Hello Groland
1. **Écrire le programme suivant**
```java
public class HelloGroland { 
  public static void main(String[] args) {
    System.out.println("Hello Groland");
  }
}
```
Le programme est présent dans le répertoire "1".


2. **Compiler le programme en utilisant le commande javac puis vérifier que le fichier .class correspondant existe bien.**

Le fichier .class est bien présent dans le répertoire "1".


3. **Exécuter le programme avec la commande java**

Le programme s'exécute correctement et affiche "Hello Groland" dans le terminal.

<br>

## Exercice 2 - Afficher les arguments de la ligne de commande 

1. **Dans un premier temps, afficher le premier argument de la ligne de commande (dans notre exemple Voici). Que se passe-t-il si l'on ne passe pas d'argument lors de l'exécution du programme ?**

On commence par écrire le code suivant dans le fichier "PrintArgs.java" :
```java
public class PrintArgs {
  public static void main(String[] args) {
    System.out.println(args[0]);
  }
}
```
On compile le fichier avec la commande `javac PrintArgs.java` puis on exécute le programme avec la commande `java PrintArgs Voici des arguments`.
Le programme affiche "Voici" dans le terminal. 

Dans le cas où l'on ne passe pas d'argument lors de l'exécution du programme, le programme affiche une erreur `ArrayIndexOutOfBoundsException`.
Cela est du au fait qu'on essaye d'accéder à un élément du tableau de chaînes `args` qui n'est pas initialisé.

2. **Écrire une boucle affichant le contenu du tableau, sachant qu'en Java, les tableaux possèdent un champ (un attribut) length qui correspond à la taille du tableau.**

On ajoute dans la fonction main du fichier PrintArgs.java (qui sert de point d'entrée au programme) la boucle suivante :
```java
public class PrintArgs {
  public static void main(String[] args){
    for (int i = 0; i < args.length; i++) {
      System.out.println(args[i]);
    }
  }
}
```

3. **Changer votre programme pour utiliser la syntaxe dite du 'for deux points', c'est à dire for(Type value: array)**

On modifie la boucle créée ci-dessus pour qu'elle utilise la syntaxe dite du 'for deux points' :
```java
public class PrintArgs {
  public static void main(String[] args){
    for (String arg : args) {
      System.out.println(arg);
    }
  }
}
```

<br>

## Exercice 3 - Calculette simple

1. **Recopier le programme précédent et le compléter pour qu'il affiche le nombre saisi par l'utilisateur.**

On commence par écrire le code suivant dans le fichier "Calc.java" :
```java
import java.util.Scanner; 

public class Calc { 
  public static void main(String[] args) {
    Scanner scanner;
    scanner = new Scanner(System.in);
    int value;
    value = scanner.nextInt();
    System.out.println(value);
  }
}
```
Ici, la cinquième et dernière ligne au seij de la fonction main permet d'afficher la valeur saisie par l'utilisateur.

2.1 **Indiquer dans le programme où sont les variables et quel est leur type associé.**

La variable `scanner` est de type `Scanner` et la variable `value` est de type `int` primitif.

2.2 **Modifier le programme pour déclarer et initialiser les variables en une seule ligne.**

On modifie le code du fichier "Calc.java" pour déclarer et initialiser les variables au mieux :
```java
public class Calc {
  public static void main(String[] args) {
    // var "scanner" of type "java.util.Scanner"
    Scanner scanner = new Scanner(System.in);

    // var "value" of type "int"
    int value = scanner.nextInt();
    
    // display value
    System.out.println(value);
  }
}
```

3.1 **Pourquoi nextInt() n'est pas une fonction ?**

`nextInt()` n'est pas une fonction car elle est associée à une classe (Scanner dans ce cas). 

3.2 **Qu'est nextInt() alors ?**

Etant donné que `nextInt()` fait partie de la classe Scanner, on dit que c'est une méthode.

4. **Expliquer la ligne :** `import java.util.Scanner;`

La ligne `import java.util.Scanner;` permet de créer un alias de la classe Scanner faisant partie du paquetage "java.util".
Cela permet à java d'identifier la classe Scanner sans avoir à écrire le chemin complet de la classe (java.util.Scanner).

5. **Modifier le programme pour qu'il demande deux entiers et affiche la somme de ceux-ci.**

On modifie le code du fichier "Calc.java" pour qu'il demande deux entiers et affiche la somme de ceux-ci :
```java
public class Calc {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Please provide two integer values :");
    int firstValue = scanner.nextInt();
    int secondValue = scanner.nextInt();

    // display result
    System.out.println(firstValue + " + " + secondValue + " = " + (firstValue + secondValue));
  }
}
```

6. **Afficher en plus de la somme, la différence, le produit, le quotient et le reste.**

On modifie le code du fichier "Calc.java" pour qu'il affiche le résultat de toutes ces opérations :
```java
public class Calc {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Please provide two integer values :");
    int firstValue = scanner.nextInt();
    int secondValue = scanner.nextInt();

    // display result
    System.out.println(firstValue + " + " + secondValue + " = " + (firstValue + secondValue));
    System.out.println(firstValue + " - " + secondValue + " = " + (firstValue - secondValue));
    System.out.println(firstValue + " / " + secondValue + " = " + (firstValue / secondValue));
    System.out.println(firstValue + " % " + secondValue + " = " + (firstValue % secondValue));
  }
}
```

<br>

## Exercice 4 - Record et conversion de String en entier

1.1 **Dans un premier temps, créer un record Point (dans un fichier Point.java) avec deux composants x et y, tous les deux de type int.**

On commence par créer le fichier "Point.java" et on y écrit le code suivant :
```java
public record Point(int x, int y) {}
```

1.2 **Quelle doit être la ligne de commande pour compiler le fichier Point.java ?**

On compile le fichier "Point.java" avec la commande `javac Point.java`.

2. **Écrire une méthode main qui prend les entiers x et y sur la ligne de commande (3 et 4 dans l'exemple ci-dessus), les convertit en valeurs entières et affiche celles-ci (la ligne x=3, y=4 dans l'exemple).**

On modifie le code du fichier "Point.java" pour qu'il affiche les valeurs saisies par l'utilisateur :
```java
public record Point(int x, int y) { 
  public Double distance() {
    return Math.sqrt(x*x + y*y);
  }

  public static void main(String[] args) {
    int x = Integer.parseInt(args[0]);
    int y = Integer.parseInt(args[1]);
    System.out.println("x=" + x + ", y=" + y);
  }
}
```
Le paquetage `java.lang` contient la classe `Integer` qui permet de convertir une chaîne de caractères en entier. 
Ce paquetage est "importé" par défaut dans tous les fichiers Java, il est futile de le rajouter en début de document.

3. **Que veut dire ``static'' pour une méthode ?**

`static` signifie que la méthode est associée à la classe et non à un objet (instance) de la classe. Elle est donc appelée sur la classe elle-même et non sur un objet.

4. **Que se passe-t-il lorsque l'un des arguments n'est pas un nombre ?**

Lorsque l'un des arguments n'est pas un nombre entier, l'Exception `java.lang.NumberFormatException` est levée, car le transtypage via la méthode statique `parseInt` est impossible sur une valeur incorrecte.

5. **Dans le main, ajouter des instructions pour créer un instance du record Point, avec le deux entiers x et y et afficher celui-ci.**

On modifie le code du fichier "Point.java" pour qu'il affiche l'instance du record Point :
```java
public record Point(int x, int y) { 
  public Double distance() {
    return Math.sqrt(x*x + y*y);
  }

  public static void main(String[] args) {
    int x = Integer.parseInt(args[0]);
    int y = Integer.parseInt(args[1]);
    System.out.println("x=" + x + ", y=" + y);

    Point p = new Point(x, y);
    System.out.println(p);
  }
}
```

6. **On souhaite ajouter au record Point une méthode d'instance (une méthode non statique) nommée distance qui calcule la distance entre deux points sous forme d'un nombre à virgule flottante.**
**Quels sont les paramètres et le type de retour de la méthode distance ?**

Il est spécifié qu'on souhaite calculer la distance entre le point instancié et les coordonnées d'origine (x=0 et y=0).

Dans ce cas, la méthode distance n'a plus besoin de prend en paramètre une instance du record Point. 

Elle retourne un nombre à virgule flottante (Double).

```java
public record Point(int x, int y) {
  public Double distance() {
    return Math.sqrt(x*x + y*y);
  }

  public static void main(String[] args) {
    int x = Integer.parseInt(args[0]);
    int y = Integer.parseInt(args[1]);
    System.out.println("x=" + x + ", y=" + y);
    
    Point p = new Point(x, y);
    System.out.println(p);
    System.out.println(p.distance());
  }
}
```

<br>

## Exercice 5 - De C vers Java

1. **Compiler (gcc pascal.c) et exécuter le programme a.out en demandant au système le temps d'exécution du programme. (time a.out).**

```bash
gcc pascal.c -Wall -ansi
time ./a.out
```

On obtient le résultat suivant:

```bash
 Cn, p = -1742193024

real    0m0.862s
user    0m0.859s
sys     0m0.001s
```

2.1 **Écrire le programme (Pascal.java) équivalent en Java. Pour une fois, servez-vous du copier/coller. Compiler le programme puis l'exécuter en mesurant le temps (toujours avec time).**

On crée le fichier "Pascal.java" et on y écrit le code suivant :
```java
public class Pascal {
    public static int pascal(int nBut, int pBut){
        int[] tab = new int[nBut+1];
        int n, i;

        tab[0] = 1;
        for(n = 1; n <= nBut; n++) {
            tab[n] = 1;
            for(i = n-1; i > 0; i--) {
                tab[i] = tab[i-1] + tab[i];
            }
        }
        return tab[pBut];
    }
    
    public static void main(String[] args) {
        System.out.println(" Cn, p = " + Pascal.pascal(30000, 250));
    }
}
```

On compare le temps d'exécution du programme en C et en Java :
```bash
javac Pascal.java
time java Pascal
```

On obtient le résultat suivant :
```bash
 Cn, p = -1512926384

real    0m0.269s
user    0m0.265s
sys     0m0.012s
```

2.2: **Comment peut-on expliquer la différence de vitesse ?**
Le code Java est, dans un premier temps, transpilé en bytecode, puis interprété par la machine virtuelle Java (JVM).

Le code C est, lui, directement compilé en langage assembleur.

Le code Java devrait donc être plus lent que le code C car il passe par la JVM. 
Cependant, la fonctionnalité "Just In Time" (JIT) permet d'optimiser la compilation du bytecode vers du langage assembleur.

C'est cela qui explique pourquoi le code Java est plus rapide que le code C dans un cas comme celui-ci. Le résultat serait surement différent sur une application de plus grande envergure.
