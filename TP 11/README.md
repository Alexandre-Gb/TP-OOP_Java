# TP11 - Héritage, appel de constructeurs, visibilité
## GIBOZ Alexandre, INFO1 2022-2025
***

[Consignes de rendu de TP](https://monge.univ-mlv.fr/ens/IR/IR1/2022-2023/Java/index.php)

[Énoncé](https://monge.univ-mlv.fr/ens/IR/IR1/2022-2023/Java/td11.php)
***

## Exercice 1 - Découpage de la ligne de commande

1. **On souhaite écrire une classe Argument avec un champ text de tel sorte à ce que le code suivant fonctionne et affiche les bonnes valeurs**
```java
public static void main(String[] args) {
  var argument1 = new Argument("foo.txt");
  var argument2 = new Argument("bar.png");
  System.out.println(argument1);  // Argument{ text:'foo.txt' }
  System.out.println(argument2);  // Argument{ text:'bar.png' }
  ...
}
```

Ecrire la classe Argument avec les préconditions habituelles. 
```java
package fr.uge.cmdline1;

import java.util.Objects;

public class Argument {
  private final String name;

  public Argument(String name) {
    Objects.requireNonNull(name);
    this.name = name;
  }

  @Override
  public String toString() {
    return "Argument{ text:'" + name + "' }";
  }
}
```

2. **On souhaite écrire une méthode parseCmdLine dans la classe CmdLine1 qui renvoie pour chaque chaine de caractère l'argument correspondant le tout dans une liste.**
```java
...
List<Argument> arguments1 = parseCmdLine("foo.txt", "bar.png");
System.out.println(arguments1);  // [Argument{ text:'foo.txt' }, Argument{ text:'bar.png' }]
...
```

**Ecrire la méthode parseCmdLine en remarquant que l'on peut passer autant de chaines de caractères que l'on veut séparées par des virgules.**
```java
private static List<Argument> parseCmdLine(String... args) {
  var arguments = new ArrayList<Argument>();
  for (var arg : args) {
    arguments.add(new Argument(arg));
  }
  return arguments;
}
```

3. **On souhaite maintenant écrire une classe Option qui va représenter les options de la ligne de commande avec un texte (text) et la valeur de l'enum OptionInfo correspondant (nommé info) tel que le code suivant fonctionne correctement. 
    Comme c'est un TP sur l'héritage, on vous demande d'utiliser l'héritage.**
```java
package fr.uge.cmdline1;

import java.util.Objects;

public final class Option extends Argument {
  private final String argument;
  private final OptionInfo info;

  public Option(String argument, OptionInfo info) {
    super(argument);
    Objects.requireNonNull(argument);
    Objects.requireNonNull(info);
    this.argument = argument;
    this.info = info;
  }

  @Override
  public String toString() {
    return "Option{ text: '" + argument + "', info: " + info + " }";
  }
}
```

On scelle la classe Option pour éviter qu'elle soit étendue. La visibilité de chaque attribut est `package`.
```java
public sealed class Argument permits Option {
  final String name;
  
  ...
}
```

4. **On veut maintenant modifier la méthode parseCmdLine pour reconnaitre les arguments et les options. 
    Pour cela, nous allons introduire une méthode intermédiaire asOptionInfo qui prend en paramètre un argument sous forme de chaine de caractère et renvoie soit la bonne valeur de l'enum OptionInfo soit null si la chaine de caractère ne correspond pas à une des options "-v", "--verbose", "-a" ou "--all".**
```java
private static OptionInfo asOptionInfo(String option) {
  switch (option) {
    case "-v", "--verbose" -> {
      return OptionInfo.VERBOSE;
    }
    case "-a", "--all" -> {
      return OptionInfo.ALL;
    }
    default -> {
      return null;
    }
  }
}
```

On modifie la méthode parseCmdLine pour qu'elle reconnaisse les options et les arguments.
```java
private static List<Argument> parseCmdLine(String... args) {
  var arguments = new ArrayList<Argument>();
  for (var arg : args) {
    if (arg.startsWith("-")) {
      var optionInfo = asOptionInfo(arg);
      if (optionInfo == null) {
        throw new IllegalArgumentException("Unknown option: " + arg);
      }
      arguments.add(new Option(arg, optionInfo));
    } else {
      arguments.add(new Argument(arg));
    }
  }
  return arguments;
}
```

5. **En fait, au lieu de renvoyer null, on voudrait que asOptionInfo utilise un Optional. 
    En terme d'utilisation de l'API d'Optional, au lieu d'utiiser isEmpty/isPresent, vous pouvez utiliser map et orElseGet pour chainer les opération, comme ceci:**
```java
Optional<OptionInfo> optionInfoOpt = asOptionInfo(arg);
Argument argument = optionInfoOpt.map(...).orElseGet(...);
```

On modifie la méthode asOptionInfo pour qu'elle renvoie un Optional.
```java
private static Optional<OptionInfo> asOptionInfo(String option) {
  return switch (option) {
    case "-v", "--verbose" -> Optional.of(OptionInfo.VERBOSE);
    case "-a", "--all" -> Optional.of(OptionInfo.ALL);
    default -> Optional.empty();
  };
}
```

**Modifier le code de parseCmdLine en conséquence.**
```java
private static List<Argument> parseCmdLine(String... args) {
  return Arrays.stream(args).map(e -> asOptionInfo(e) 
  .<Argument>map(o -> new Option(e, o))
  .orElseGet(() -> new Argument(e))).toList();
}
```

6. **On souhaite valider que la ligne de commande ne contient pas deux fois les même arguments/les mêmes options, pour cela on va écrire une méthode checkCmdLine qui prend en paramètre une liste d'arguments et lève une exception si un des arguments est dupliqué.**
```java
private static void checkCmdLine(List<Argument> arguments) {
  var checkArgument = new HashSet<OptionInfo>();
  for (var argument : arguments) {
    var option = asOptionInfo(argument.name());
    if (option.isPresent()) {
      if (!checkArgument.add(option.get())) {
        throw new IllegalArgumentException("duplicate argument " + argument);
      }
    }
  }
}
```

7. **En fait, les méthodes equals que vous avez écrites sont fausses, car non symmétrique, c-a-d, a.equals(b) et b.equals(a) devraient renvoyer la même valeur.**
```java
var argument3 = new Argument("-v");
var option3 = new Option("-v", OptionInfo.VERBOSE);
System.out.println(argument3.equals(argument3));  // true
System.out.println(argument3.equals(option3));    // false
System.out.println(option3.equals(option3));      // true
System.out.println(option3.equals(argument3));    // false
```

**Qu'affiche votre implantation si on execute le code suivant ?**

On obtient le résultat suivant:
```java
true
false
true
false
```

**Comment doit-on corriger votre implantation ?**

Le résultat est déja le bon sans aucune méthode equals. On définit les méthodes explicitement cependant

**Faite les modifications qui s'imposent.**

On définit la méthode `equals` dans la classe `Argument`:
```java
@Override
public boolean equals(Object second) {
  Objects.requireNonNull(second);
  if (getClass() != second.getClass()) {
    return false;
  }
  Argument argument = (Argument) second;
  return Objects.equals(name, argument.name);
}
```

On définit la méthode `equals` dans la classe `Option`:
```java
@Override
public boolean equals(Object second) {
  Objects.requireNonNull(second);
  return getClass() == second.getClass() && super.equals(second);
}
```

8. **En fait, avoir des arguments identiques sur la ligne de commande est pas un vrai problème, avoir des options identiques est le vrai problème, on se propose de modifier checkCmdLine pour tester uniquement si les options sont les mêmes.
   Pour cela, on doit savoir si un Argument est une Option ou pas, il existe deux façon d'implanter ce test, avec une méthode isOption dans Argument et Option en utilisant le polymorphisme ou en faisant un switch sur Argument et Option.
   Ìmplanter la version utilisant le polymorphisme et vérifier que votre code fonctionne**
```java
var arguments4 = parseCmdLine("-v", "bar.png", "bar.png");
checkCmdLine(arguments4);  // ok !
```

Le code actuel fonctionne déja correctement. On modifie cependant le code en ajoutant pour chaque classe une méthode `isOption`.

Pour la classe `Argument`:
```java
public boolean isOption() {
  return false;
}
```

Pour la classe `Option`:
```java
@Override
public boolean isOption() {
  return true;
}
``` 

On utilise la méthode `isOption` dans la méthode `checkCmdLine`:
```java
private static void checkCmdLine(List<Argument> arguments) {
  var checkArgument = new HashSet<OptionInfo>();
  for (var argument : arguments) {
    if (argument.isOption()) {
      var option = asOptionInfo(argument.name());
      if (!checkArgument.add(option.get())) {
        throw new IllegalArgumentException("duplicate argument " + argument);
      }
    }
  }
}
```

9. **Ecrire une méthode isOption dans CmdLine1 qui prend un en paramètre un Argument et renvoie vrai si l'argument est une Option en utilisant un switch. Puis modifier checkCmdLine() pour cette méthode et enfin vérifier que le comportement du code n'a pas changé.**

On ajoute la méthode statique `isOption` dans la classe `CmdLine1`:
```java
private static boolean isOption(Argument argument) {
  return switch (argument) {
    case Option e -> true;
    case Argument e -> false;
  };
}
```

On modifie la méthode `checkCmdLine` pour utiliser la version statique de `isOption`:
```java
private static void checkCmdLine(List<Argument> arguments) {
  var checkArgument = new HashSet<OptionInfo>();
  for (var argument : arguments) {
    if (isOption(argument)) {
      var option = asOptionInfo(argument.name());
      if (!checkArgument.add(option.get())) {
        throw new IllegalArgumentException("duplicate argument " + argument);
      }
    }
  }
}
```

10. **Si on compare le polymporhisme et le pattern matching, avec le polymorphisme, si on ajoute un nouveau sous-type, on va devoir écrire une nouvelle implantation de isOption pour ce sous-type. 
    Avec le switch sur Argument, il faudrait que le code ne compile pas si on ajoute un nouveau sous-type pour forcer le programmeur à le prendre en compte ce nouveau sous-type, en Java, on utilise le mot-clé sealed pour empécher de nouveau sous-type.
    Faire les modifications de code qui s'impose pour empécher de nouveau sous-type.**

On modifie la classe `Argument` pour la rendre `sealed`:
```java
public sealed class Argument permits Option {
  final String name;
  ...
}
```

<br>

## Exercice 2 - Découpage de la ligne de commande 2 (le retour de la vengeance)

En fait, utiliser l'héritage est souvent plus compliqué (faire attention au equals()), à la visibilité des champs et donne un code moins maintenable (si on utilise Argument dans le code, il est pas clair si on parle d'un argument l'implantation ou d'un argument le type qui représente Argument | Option.
Utiliser une interface résoud ces problèmes.
On se propose de ré-implanter l'exercice précédent en utilisant une interface et des records. On utilisera Argument comme no pour l'interface et Plain pour les arguments pas spéciaux et Option pour les arguments qui sont des options.

1. **Créer un package fr.uge.cmdline2 ainsi qu'une classe CmdLine2 avec le main ci dessous (Note: pour dupliquer un package vous pouvez faire un Control+C puis un Control+V dans la vue en projet).
   Faire en sorte que le main ci-dessous fonctionne.
   Note: attention à ce que deux options soient indentiques si elles ont le même OptionIndo !**
```java
  public static void main(String[] args) {
    // 1
    var argument1 = new Plain("foo.txt");
    var argument2 = new Plain("bar.png");
    System.out.println(argument1);  // Argument{ text:'foo.txt' }
    System.out.println(argument2);  // Argument{ text:'bar.png' }

    // 2
    var arguments1 = parseCmdLine("foo.txt", "bar.png");
    System.out.println(arguments1);  // [Argument{ text:'foo.txt' }, Argument{ text:'bar.png' }]

    // 3
    var option1 = new Option("--verbose", OptionInfo.VERBOSE);
    var option2 = new Option("-v", OptionInfo.VERBOSE);
    System.out.println(option1);  // Option{ text: '--verbose', info: VERBOSE }
    System.out.println(option2);  // Option{ text: '-v', info: VERBOSE }

    // 4 & 5
    var arguments2 = parseCmdLine("-v", "bar.png");
    System.out.println(arguments2);  // [Option{ text: '-v', info: VERBOSE }, Argument{ text:'bar.png' }]

    // 6
    var arguments3 = parseCmdLine("-v", "bar.png", "--verbose");
    //checkCmdLine(arguments3);  // java.lang.IllegalArgumentException: duplicate argument Option{ text: '--verbose', info: VERBOSE }

    // 7
    var argument3 = new Plain("-v");
    var option3 = new Option("-v", OptionInfo.VERBOSE);
    System.out.println(argument3.equals(argument3));  // true
    System.out.println(argument3.equals(option3));    // false
    System.out.println(option3.equals(option3));      // true
    System.out.println(option3.equals(argument3));    // false

    // 8, 9 & 10
    var arguments4 = parseCmdLine("-v", "bar.png", "bar.png");
    checkCmdLine(arguments4);  // ok !
  }
```

Interface `Argument`:
```java
package fr.uge.cmdline2;

public sealed interface Argument permits Plain, Option { }
```

Record `Plain`:
```java
package fr.uge.cmdline2;

import java.util.Objects;

public record Plain(String text) implements Argument {
  public Plain {
    Objects.requireNonNull(text);
  }
}
```

Record `Option`:
```java
package fr.uge.cmdline2;

import java.util.Objects;

public record Option(String text, OptionInfo info) implements Argument {
  public Option {
    Objects.requireNonNull(text);
  }
}
```

Enum `OptionInfo`:
```java
package fr.uge.cmdline2;

import java.util.Optional;

public enum OptionInfo {
  ALL, VERBOSE;

  public static Optional<OptionInfo> asOptionInfo(String option) {
    return switch (option) {
      case "-v", "--verbose" -> Optional.of(VERBOSE);
      case "-a", "--all" -> Optional.of(ALL);
      default -> Optional.empty();
    };
  }
}
```

Classe `CmdLine2`:
```java
package fr.uge.cmdline2;

import java.util.ArrayList;
import java.util.List;

public class CmdLine2 {
  private static List<Argument> parseCmdLine(String... arguments) {
    var argumentsList = new ArrayList<Argument>();
    for (var argument : arguments) {
      argumentsList.add(OptionInfo.asOptionInfo(argument).<Argument>map(info -> new Option(argument, info)).orElseGet(() -> new Plain(argument)));
    }

    return List.copyOf(argumentsList);
  }

  private static void checkCmdLine(List<Argument> arguments) { /* ??? */ }

  public static void main(String[] args) {
    ...
  }
}
```