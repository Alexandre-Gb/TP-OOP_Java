# TP5 - Interface et Collection
## GIBOZ Alexandre, INFO1 2022-2025
***

[Consignes de rendu de TP](https://monge.univ-mlv.fr/ens/IR/IR1/2022-2023/Java/index.php)

[Énoncé](https://monge.univ-mlv.fr/ens/IR/IR1/2022-2023/Java/td05.php)
***

## Exercice 1 - Manifeste d'un porte conteneur

Un porte-conteneur (container en Anglais) est un bateau qui, comme son nom l'indique, transporte des conteneurs d'un port à l'autre. 
Chaque porte-conteneur possède un manifeste (manifest), qui est un document papier contenant une liste de l'ensemble des conteneurs qu'il transporte. 

Le but de ce TP est de modéliser ce document papier. 

Vous écrirez toutes les classes de ce TP dans un package nommé `fr.uge.manifest`. 
Vous devez tester toutes les méthodes demandées et vous écrirez tous vos tests dans la classe Main du package `fr.uge.manifest.main`.

1. **Dans un premier temps, on cherche à définir un Container. 
   Un conteneur possède une destination sous forme de chaîne de caractères ainsi qu'un poids (weight en Anglais) qui est une valeur entière. 
   Il ne doit pas être possible de créer un conteneur avec des valeurs invalides : la destination doit exister et le poids doit être positif ou nul.
   Écrire le type Container de telle façon à ce que le code suivant fonctionne :**
```java
public static void main(String[] args) {
  var container = new Container("Germany", 500);
  System.out.println(container.destination());  // Germany
  System.out.println(container.weight());  // 500
}
```

On créé un record `Container`:
```java
package fr.uge.manifest;

import java.util.Objects;

public record Container(
  String destination,
  int weight
) {
  public Container {
    Objects.requireNonNull(destination, "A destination is required");
    if (weight < 0) {
      throw new IllegalArgumentException("Weight must be superior or equal to 0");
    }
  } 
  
  public static void main(String[] args) {
    var container = new Container("Germany", 500);
    System.out.println(container.destination());  // Germany
    System.out.println(container.weight());  // 500
  }
}
```

On obtient le résultat suivant:
```
Germany
500
```

2. **On veut maintenant introduire la notion de Manifest, un manifeste contient une liste de conteneurs. 
   Pour l'instant, un manifeste définit une seule méthode add(conteneur) qui permet d'ajouter un conteneur au manifeste.
   Il ne doit pas être possible d'ajouter un conteneur null.
   Écrire le type Manifest tel que le code suivant fonctionne :**
```java
public static void main(String[] args) {
  var container2 = new Container("Italy", 400);
  var container3 = new Container("Austria", 200);
  var manifest = new Manifest();
  manifest.add(container2);
  manifest.add(container3);
}
```

On met en place la classe Manifest:
```java
package fr.uge.manifest;

import java.util.ArrayList;
import java.util.Objects;

public class Manifest {
  private final ArrayList<Container> containers;
  
  public Manifest() {
  	containers = new ArrayList<>();
  }
  
  public void add(Container newContainer) {
  	Objects.requireNonNull(newContainer, "Please provide a container");
  	containers.add(newContainer);
  }
}
```

3. **On souhaite maintenant pouvoir afficher un manifeste. 
   Cela revient à afficher chaque conteneur sur une ligne, avec un numéro, 1 pour le premier conteneur, 2 pour le suivant, etc, suivi de la destination du conteneur ainsi que de son poids.
   Le formatage exact pour une ligne est :**
```bash
[numéro]. [destination] [poids]kg
```

**suivi d'un retour à la ligne (y compris après la dernière ligne).
Modifier le type Manifest pour que le code suivant ait le comportement attendu :**
```java
public static void main(String[] args) {
  ...
  var container4 = new Container("Spain", 250);
  var container5 = new Container("Swiss", 200);
  var manifest2 = new Manifest();
  manifest2.add(container4);
  manifest2.add(container5);
  System.out.println(manifest2);
  // 1. Spain 250kg
  // 2. Swiss 200kg
}
```

On override la méthode `toString` de la classe Manifest:
```java
@Override
public String toString() {
  StringBuilder sb = new StringBuilder(); 
  int i = 0;
  for (Onboard onboard : onboards) {
    sb.append(++i)
      .append(". ")
      .append(onboard.toString())
      .append("\n");
  }
  return sb.toString();
}
```

On modifie également la méthode `toString` du record Container:
```java
@Override
public String toString() {
  return destination + " " + weight + "kg";
}
```

On obtient le résultat suivant après avoir modifié le code:
```
1. Spain 250kg
2. Swiss 200kg
```

4. **Un porte-conteneur, comme son nom ne l'indique pas, peut aussi transporter des passagers. 
   Un Passenger est défini par une destination uniquement, les passagers ne sont pas assez lourds pour avoir un vrai poids.
   Dans un premier temps, définir un Passenger afin que l'on puisse créer un passager uniquement avec sa destination. 
   Puis expliquer comment modifier Manifest pour que l'on puisse enregistrer aussi bien des conteneurs que des passagers.
   Pour l'affichage, un passager affiche la destination ainsi que "(passenger)" entre parenthèse (cf le code plus bas).
   Écrire le code de Passenger et modifier le code de Manifest de telle façon que le code ci-dessous fonctionne.**
```java
public static void main(String[] args) {
  ...
  var passenger1 = new Passenger("France");
  var container6 = new Container("England", 350);
  var manifest3 = new Manifest();
  manifest3.add(passenger1);
  manifest3.add(container6);
  System.out.println(manifest3);
  // 1. France (passenger)
  // 2. England 350kg
}
```

Le Manifest pouvant contenir deux types d'objets qui n'ont pas de lien de parenté, on créé une interface `Onboard` que seuls `Passenger` et `Container` implémenteront:
```java
package fr.uge.manifest;

public sealed interface Onboard permits Container, Passenger {
   int weight();
   String destination();
}
```

On créé le record `Passenger` (de la même façon que pour le record `Container`, la destination n'est pas supposée être mutable, ce qui justifie le choix d'un record une fois encore):
```java
package fr.uge.manifest;

import java.util.Objects;

public record Passenger (String destination) implements Onboard {
  public Passenger {
    if (destination == null) {
      throw new IllegalArgumentException("A destination is required");
    }
  }

  @Override
  public String toString() {
    return destination + " (passenger)";
  }

  @Override
  public int weight() {
    return 0;
  }
}
```

Le Passenger n'a pas de réel poid, ce dernier sera implanté automatiquement à 0.

On modifie le record `Container` pour qu'il implémente l'interface `Load`:
```java
public record Container(String destination, int weight) implements Onboard {
   ... (inchangé)
}
```

Le résultat obtenu est le suivant si l'on utilise la méthode `toString` sur `manifest3`:
```bash
1. France (passenger)
2. England 350kg
```

5. **On souhaite ajouter une méthode price à Manifest qui calcule le prix pour qu'un conteneur ou qu'un passager soit sur le bateau.**
   - Le prix pour un passager est 10.
   - Le prix pour un conteneur est le poids du conteneur multiplié par 2.

**Ajouter une méthode price à Manifest et faites en sorte que le prix soit calculé correctement.**
```java 
System.out.println(manifest3.price()); // 710
```

Pour ce faire, on ajoute une méthode `price` à l'interface `Onboard`. Cette méthode sera obligatoirement implantée par les deux classes `Passenger` et `Container`:
```java
package fr.uge.manifest;

public sealed interface Onboard permits Container, Passenger {
  int weight();
  String destination();
  int price();
}
```

On implante la méthode `price` dans la classe `Container`:
```java
@Override
public int price() {
	return weight * 2;
}
```

On implémente la méthode `price` dans la classe `Passenger`:
```java
@Override
public int price() {
   return 10;
}
```

Enfin, on créé la méthode `price` dans la classe `Manifest`, qui additionnera le prix de chaque entité inscrite dans le manifeste:
```java
public int price() {
  int price = 0;
  for (Onboard onboard : onboards) {
    price += onboard.price();
  }
  return price;
}
```

Le résultat obtenu est le suivant :
```bash
710
```

6. **On veut maintenant rajouter une méthode weight à Manifest qui renvoie le poids total en considérant qu'un passager n'a pas de poids.**
```java
var container8 = new Container("Russia", 450);
var container9 = new Container("China", 200);
var container10 = new Container("Russia", 125);
var passenger2 = new Passenger("Russia");
var manifest4 = new Manifest();
manifest4.add(container8);
manifest4.add(container9);
manifest4.add(container10);
manifest4.add(passenger2);
System.out.println(manifest4.weight()); // 775
```

On ajoute la méthode à la classe `Manifest`:
```java
public int weight() {
  int weight = 0;
  for (Onboard onboard : onboards) {
    weight += onboard.weight();
  }
  return weight;
}
```

On obtient le résultat suivant:
```bash
775
```

7. **Il arrive que l'on soit obligé de décharger tous les conteneurs liés à une destination s'il y a des problèmes d'embargo (quand un dictateur se dit qu'il s’offrirait bien une partie d'un pays voisin par exemple). 
   Dans ce cas, il faut aussi supprimer tous les conteneurs liés à cette destination au niveau du manifeste (mais pas les passagers).
   Pour prendre en compte cela, on introduit une méthode removeAllContainersFrom(destination) qui supprime tous les conteneurs liés à une destination. 
   S'il n'y a pas de conteneur pour cette destination, on ne fait rien.**
   
**Modifier le code pour introduire cette méthode pour que l'exemple ci-dessous fonctionne :
Note : pour savoir si il s'agit d'un conteneur ou pas, on pourrait introduire une méthode isContainer dont l'implantation par défaut sera de retourner false.**
```java
public static void main(String[] args) {
    ...  
    manifest4.removeAllContainersFrom("Russia");
    System.out.println(manifest4);
    // 1. China 200kg
    // 2. Russia (passenger)
}
```

Pour savoir si un objet est une instance d'une classe, on ajoute la méthode `isContainer` dans l'interface `Onboard`, qui devra être implantée par les deux classes `Passenger` et `Container`:
```java
boolean isContainer();
```

On implante la méthode `isContainer` dans la classe `Container`:
```java
@Override
public boolean isContainer() {
  return true;
}
```

On implante la méthode `isContainer` dans la classe `Passenger`:
```java
@Override
public boolean isContainer() {
  return false;
}
```

On ajoute la méthode `removeAllContainersFrom` à la classe `Manifest`:
```java
public void removeAllContainersFrom(String destination) {
  onboards.removeIf(onboard -> onboard.isContainer() && onboard.destination().equals(destination));
}
```

On vérifie que les objets sont des instances de `Container` et que leur destination est égale à celle passée en paramètre. Si c'est le cas, on supprime l'objet du manifeste.

Si aucun objet ne correspond à ces critères, la méthode ne fait rien.

On obtient le résultat suivant:
```bash
1. China 200kg
2. Russia (passenger)
```

8. **Pour résoudre la question précédente, au lieu de faire des appels de méthode, on peut aussi utiliser instanceof. Expliquer comment on peut utiliser instanceof. 
   Puis expliquer, selon vous, quel est le problème d'utiliser instanceof dans ce contexte et pourquoi on ne doit pas l'utiliser.**

Il est possible de remplacer les appels à la méthode `isContainer` par des appels à `instanceof` de cette façon:
```java
public void removeAllContainersFrom(String destination) {
  onboards.removeIf(onboard -> onboard instanceof Container && onboard.destination().equals(destination));
}
```

`instanceof` permet de vérifier si un objet est une instance d'une classe. Il est donc possible de remplacer les appels à la méthode `isContainer` par des appels à `instanceof`.

L'utilisation de `instanceof` pose problème dans le maintient du code à long terme, pouvant occasionner des erreurs difficiles à trouver et nécessitant de fouiller dans le code pour les résoudre.
Cela est du au fait que cette façon de procéder ne tient pas compte de la logique métier, mais de la logique technique. En effet, si on décide de changer la façon dont on implémente les classes `Container` et `Passenger`, 
il faudra modifier la méthode `removeAllContainersFrom` pour qu'elle fonctionne correctement, la ou il serait préférable de modifier directement la méthode `isContainer` implantée par les sous-classes.

9. **On met les conteneurs ayant la destination au même endroit sur le porte-conteneur, et si un porte-conteneur est mal équilibré il a une fâcheuse tendance à se retourner. 
   Donc, pour aider au placement des conteneurs, il doit être possible de fournir un dictionnaire qui, pour chaque destination, indique le poids de l'ensemble des conteneurs liés à cette destination.
   Pour cela, écrire une méthode weightPerDestination qui, pour un manifeste donné, renvoie un dictionnaire qui indique le poids des conteneurs pour chaque destination.
   Par exemple, avec le code ci-dessous, il y a deux conteneurs qui ont comme destination "Monaco", avec un poids combiné de 100 + 300 = 400, tandis que "Luxembourg" a un seul conteneur de poids 200.**
```java
public static void main(String[] args) {
  ...
  var container11 = new Container("Monaco", 100);
  var container12 = new Container("Luxembourg", 200);
  var container13 = new Container("Monaco", 300);
  var passenger3 = new Passenger("Paris");
  var manifest8 = new Manifest();
  manifest8.add(container11);
  manifest8.add(container12);
  manifest8.add(container13);
  manifest8.add(passenger3);
  System.out.println(manifest8.weightPerDestination());
  // {Monaco=400, Luxembourg=200}
}
```

On implante la méthode `weightPerDestination` dans la classe `Manifest`:
```java
public HashMap<String, Integer> weightPerDestination() {
  HashMap<String, Integer> weights = new HashMap<>();
  for (Onboard onboard : onboards) {
  	if (onboard.isContainer()) {
  		weights.merge(onboard.destination(), onboard.weight(), Integer::sum);
  	}
  }
  return weights;
}
```

Il est préferable, dans notre cas, d'utiliser une `HashMap` pour stocker les poids des conteneurs par destination, car elle permet d'associer à des clefs uniques correspondant à chaque destination une valeur correspondant au poids des conteneurs pour cette destination.

Les Dictionnaires ne pouvant contenir de types primitifs, on utilise la classe `Integer` pour stocker les poids des conteneurs.

On vérifie que l'objet est une instance de `Container` (afin de ne pas ajouter un poid innutile de "0"), puis on ajoute le poids du conteneur à la valeur associée à la destination du conteneur dans le dictionnaire.

On obtient le résultat suivant:
```bash
{Monaco=400, Luxembourg=200}
```