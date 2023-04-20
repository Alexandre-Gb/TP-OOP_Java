# TP7 - Interface, Polymorphisme, Liaison tardive
## GIBOZ Alexandre, INFO1 2022-2025
***

[Consignes de rendu de TP](https://monge.univ-mlv.fr/ens/IR/IR1/2022-2023/Java/index.php)

[Énoncé](https://monge.univ-mlv.fr/ens/IR/IR1/2022-2023/Java/td07.php)
***

## Exercice 1 - Arbre d'expressions

1. **On va créer Expr dans un fichier Expr.java avec le main suivant**
```java
public static void main(String[] args) {
  Expr expression = new Add(new Value(2), new Value(3));
  Expr expression2 = new Sub(new Mul(new Value(2), new Value(3)), new Value(4));
}
```

Et pour Value, Add, Sub et Mul, on utilisera des records chacun dans son propre fichier .java.

**Créer les records avec leurs composants nécessaires pour que le main compile.**

On créé les records suivants:
```java
public record Value(int value) implements Expr {

}
```
```java
public record Add(Expr left, Expr right) implements Expr {
  public Add {
    Objects.requireNonNull(left);
    Objects.requireNonNull(right);
  }
}
```
```java
public record Sub(Expr left, Expr right) implements Expr {
	public Sub {
		Objects.requireNonNull(left);
		Objects.requireNonNull(right);
	}
}
```
```java
public record Mul(Expr left, Expr right) implements Expr {
	public Mul {
		Objects.requireNonNull(left);
		Objects.requireNonNull(right);
	}
}
```

Chacun de ces records va implémenter l'interface Expr, qui est définie comme suit:
```java
public interface Expr {
  static void main(String[] args) {
    Expr expression = new Add(new Value(2), new Value(3));
    Expr expression2 = new Sub(new Mul(new Value(2), new Value(3)), new Value(4));
  }
}
```

**Dessinez l'arbre à la main correspondant à expression2.**

// TODO

2. **On souhaite maintenant pouvoir évaluer (trouver la valeur) d'une expression (Expr) en appelant la méthode eval comme ceci**
```java
public static void main(String[] args) {
  Expr expression = new Add(new Value(2), new Value(3));
  Expr expression2 = new Sub(new Add(new Value(2), new Value(3)), new Value(4));
  System.out.println(expression2.eval());
}
```

**Modifier votre code en conséquence.**

On ajoute la méthode abstraite eval dans l'interface Expr:
```java
public interface Expr {
  int eval();
  
  static void main(String[] args) {
    Expr expression = new Add(new Value(2), new Value(3));
    Expr expression2 = new Sub(new Mul(new Value(2), new Value(3)), new Value(4));
  }
}
```

On implémente la méthode eval dans les records Value, Add, Sub et Mul:
```java
public record Value(int value) implements Expr {
  @Override
  public int eval() {
    return value;
  }
}
```
```java
public record Add(Expr left, Expr right) implements Expr {
  public Add {
    Objects.requireNonNull(left);
    Objects.requireNonNull(right);
  }

  @Override
  public int eval() {
    return left.eval() + right.eval();
  }
}
```
```java
public record Sub(Expr left, Expr right) implements Expr {
	public Sub {
		Objects.requireNonNull(left);
		Objects.requireNonNull(right);
	}
	
	@Override
	public int eval() {
		return left.eval() - right.eval();
	}
}
```
```java
public record Mul(Expr left, Expr right) implements Expr {
	public Mul {
		Objects.requireNonNull(left);
		Objects.requireNonNull(right);
	}
	
	@Override
	public int eval() {
		return left.eval() * left.eval();
	}
}
```

On obtient le résultat `5`.

3. **Écrire une méthode parse qui prend un Scanner en entrée et crée l'arbre d'expression correspondant sachant que l'arbre sera donné au scanner en utilisant la notation préfixe (opérateur devant). Par exemple, au lieu de 2 + 3 - 4, la notation préfixe est - + 2 3 4.
   Indication : la méthode parse est naturellement récursive. Si l’expression contient encore des symboles (et qu'elle est bien formée) alors:**
   - soit le prochain symbole est un opérateur et il faut appeler parse() 2 fois pour obtenir le fils gauche et le fils droit et les combiner avec l'opérateur pour faire une nouvelle expression,
   - soit le prochain symbole est un entier et il suffit d'en faire une feuille de l’arbre d'expression.

**Enfin, pour rappel, scanner.next() renvoie le prochain mot, Integer.parseInt() permet de convertir une String en int et il est possible d'utiliser le switch (le switch qui renvoie une valeur) sur des Strings en Java.
Pour cette question, on ne vous demande pas de vérifier que l'expression fournie en notation préfixe est bien formée.
Pour les plus à l'aise, vous pouvez tenter de faire une gestion propre des cas où l'expression est mal-formée.**

On ajoute la méthode statique `parse` à l'interface Expr:
```java
static Expr parse(Scanner scanner) {
  if (!scanner.hasNext()) {
    throw new IllegalStateException();
  }
  
  var next = scanner.next();
  return switch (next) {
    case "+" -> new Add(parse(scanner), parse(scanner));
    case "-" -> new Sub(parse(scanner), parse(scanner));
    case "*" -> new Mul(parse(scanner), parse(scanner));
    default -> new Value(Integer.parseInt(next));
  };
}
```

On ajoute la ligne suivante au point d'entrée du programme afin de tester le bon comportement de la fonction:
```java
System.out.println(Expr.parse(new Scanner(System.in)).eval());
```

On obtient le résultat `8` si l'on entre `- + 5 5 2`, ce qui est le résultat attendu.

4. **Il y a un bug dans le code que l'on a écrit, on permet à n'importe qui d'implanter Expr mais cela ne marchera pas avec la méthode parse qui elle liste tous les sous-types possibles.
   Comment corriger ce problème ?**

Le problème vient du fait que l'interface n'est pas scellée. On modifie l'interface en conséquence en n'autorisant l'implémentation que pour les records souhaités:
```java
public sealed interface Expr permits Add, Sub, Mul, Value {
    ...
}
```

5. **Déplacer le main dans une nouvelle classe Main dans le package fr.uge.calc.main et faire les changements nécessaires.**

On ajoute la classe `Main` dans le package `fr.uge.calc.main`:
```java
package fr.uge.calc.main;

import fr.uge.calc.*;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Expr expression = new Add(new Value(2), new Value(3));
    Expr expression2 = new Sub(new Mul(new Value(2), new Value(3)), new Value(4));
//  System.out.println(expression.eval());
    System.out.println(Expr.parse(new Scanner(System.in)).eval());
  }
}
```

La classe `Main` est maintenant dans le package `fr.uge.calc.main` et chaque classe du package `fr.uge.calc` est importé.

6. **Noter que prendre un Scanner en paramètre ne permet pas de ré-utiliser la méthode parse si, par exemple, l'expression à parser est stockée dans une List de String.
   Quelle interface que doit-on utiliser à la place de Scanner pour que l'on puisse appeler la méthode parse avec un Scanner ou à partir d'une List.**

On peut utiliser l'interface `iterable`:
```java
static Expr parse(Iterator<String> tokens) {
  if (!tokens.hasNext()) {
    throw new IllegalStateException();
  }
  
  var next = tokens.next();
  return switch (next) {
    case "+" -> new Add(parse(tokens), parse(tokens));
    case "-" -> new Sub(parse(tokens), parse(tokens));
    case "*" -> new Mul(parse(tokens), parse(tokens));
    default -> new Value(Integer.parseInt(next));
  };
}
```

On ajoute la ligne suivante au point d'entrée du programme afin de tester le bon comportement de la fonction:
```java
System.out.println(Expr.parse(List.of("-","+","5","5","2").iterator()).eval());
```

On obtient le résultat `8`, ce qui est le comportement attendu.

7. **Écrire la méthode d'affichage de l'arbre d'expression pour que l'affichage se fasse dans l'ordre de lecture habituel.
   Note : il va falloir ajouter des parenthèses et peut-être des paranthèses inutiles !**

On ajoute la méthode abstraite `toExprString` à l'interface `Expr`. Chaque record va implémenter cette méthode:
```java
public sealed interface Expr permits Add, Sub, Mul, Value {
    ...
    String toExprString();
}
```

On définit la méthode `toExprString` pour chaque record.

Record `Add`:
```java
@Override
public String toExprString() {
  return '(' + left.toExprString() + " + " + right.toExprString() + ')';
}
```

Record `Sub`:
```java
@Override
public String toExprString() {
  return '(' + left.toExprString() + " - " + right.toExprString() + ')';
}
```

Record `Mul`:
```java
@Override
public String toExprString() {
  return '(' + left.toExprString() + " * " + right.toExprString() + ')';
}
```

Record `Value`:
```java
@Override
public String toExprString() {
  return String.valueOf(value);
}
```

On ajoute la ligne suivante au point d'entrée du programme afin de tester le bon comportement de la fonction:
```java
System.out.println(Expr.parse(List.of("-","+","5","5","2").iterator()).toExprString());
```

On obtient le résultat suivant, ce qui confirme le bon comportement de la fonction:
```
((5 + 5) - 2)
```

8. **Enfin, on peut voir que le code de eval dans Add, Sub et Mul est quasiment identique, dans les trois cas : la méthode eval est appelée sur left et right.
   On souhaite factoriser ce code (on ne le ferait probablement pas dans la vraie vie car il n'y a pas assez de code à partager, mais ce n'est pas la vraie vie, c'est un exercice) en introduisant un type intermédiaire BinOp, sous-type de Expr et super-type de Add, Sub et Mul.
   Le type BinOp doit-il être un record, une classe ou une interface ?**

BinOp doit être une interface qui implémente Expr.

9. **Sachant que l'on veut écrire eval dans BinOp, comment eval doit être déclarée ?** 

Eval doit être déclarée comme une méthode par défaut.

**Et comment, dans eval de BinOp, peut-on accéder aux champs left et right, qui sont déclarés dans Add, Sub et Mul ?**

On peut accéder aux champs left et right en utilisant la méthode `left()` et `right()` de l'interface `Expr`.

10. **Écrire le code de BinOp (dans BinOp.java) et modifier Add, Sub et Mul en conséquence.**

