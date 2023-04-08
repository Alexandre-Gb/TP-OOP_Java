# TP6 - Interfaces et Entrée/Sortie
## GIBOZ Alexandre, INFO1 2022-2025
***

[Consignes de rendu de TP](https://monge.univ-mlv.fr/ens/IR/IR1/2022-2023/Java/index.php)

[Énoncé](https://monge.univ-mlv.fr/ens/IR/IR1/2022-2023/Java/td06.php)
***

## Exercice 1 - Blockbuster

On souhaite modéliser le catalogue d'un magasin BlockBuster, un magasin qui loue des cassettes vidéos et des laser discs, sachant que l'on veut être capable de lire/écrire un catalogue à partir de fichiers.

Oui, on fait de l'archéologie, avant Netflix, les vrais gens allaient dans un magasin pour louer des films, soit sur des bandes analogiques (des cassettes vidéo) soit sur disques numériques (des laser discs).

Pour simplifier un peu les choses, on va dire que le magasin a deux sortes d'articles, des VideoTape et des LaserDisc et que pour un nom de film, il ne peut y avoir qu'un article au maximum dans le catalogue.

    - Une VideoTape est définie par un nom (name) et une durée (duration) de type java.time.Duration (un type déjà fourni par le JDK)
    - Un LaserDisc est uniquement défini par un nom.
    - Un Catalog permet d'ajouter (add) des articles, de chercher (lookup) un article par son nom, de charger (load des articles à partir d'un fichier et de sauvegarder (save) les articles du catalogue dans un fichier.

1. **Écrire les types VideoTape et LaserDisc tels que le code suivant fonctionne**
```java
var laserDisc = new LaserDisc("Jaws");
var videoTape = new VideoTape("The Cotton Club", Duration.ofMinutes(128));
var videoTape2 = new VideoTape("Mission Impossible", Duration.ofMinutes(110));
```

Attention à ne pas oublier les pré-conditions.

On créé un record `LaserDisk` et `VideoTape`. Ces deux records vont implanter une interface scellée `Article`.

Interface `Article`:
```java
package fr.uge.poo.blockbuster;

public sealed interface Article permits LaserDisk, VideoTape {
  String name();
}
```

Record `LaserDisk`:
```java
package fr.uge.poo.blockbuster;

import java.util.Objects;

public record LaserDisk(String name) implements Article {
  public LaserDisk {
    Objects.requireNonNull(name);
  }
}
```

Record `VideoTape`:
```java
package fr.uge.poo.blockbuster;

import java.time.Duration;
import java.util.Objects;

public record VideoTape(String name, Duration length) implements Article {
  public VideoTape {
    Objects.requireNonNull(name);
    Objects.requireNonNull(length);
  }
}
```

2. **On souhaite maintenant écrire un type `Catalog` avec une méthode**
    - `add` qui permet d'ajouter une cassette vidéo ou un laser disc. 
   Attention, cette méthode ne doit pas permettre d'ajouter deux articles ayant le même nom. 

    - `lookup` qui permet de rechercher un article par son nom.

**Quel doit être le type du paramètre de add et le type de retour de lookup ?**

Add prendra en paramètre un objet de la classe `Article`.

Il est demandé que la fonction permette de "rechercher un article", sous entendu qu'il doit renvoyer l'article complet en fonction du nom qui est donné en paramètres.
Lookup doit donc renvoyer un objet de la classe `Article`.

**Que doit renvoyer `lookup` s'il n'y a ni cassette vidéo ni laser disc ayant le nom demandé dans le catalogue ?**

La méthode `lookup` devra renvoyer "null" dans ce cas. 

**Implanter le type Catalog sachant que l'on souhaite que le code suivant fonctionne :**
```java
var laserDisc = new LaserDisc("Jaws");
var videoTape = new VideoTape("The Cotton Club", Duration.ofMinutes(128));
var videoTape2 = new VideoTape("Mission Impossible", Duration.ofMinutes(110));

var catalog = new Catalog();
catalog.add(laserDisc);
catalog.add(videoTape);
catalog.add(videoTape2);
// catalog.add(new LaserDisc("Mission Impossible"));  // exception !
System.out.println(catalog.lookup("Jaws"));
System.out.println(catalog.lookup("The Cotton Club"));
System.out.println(catalog.lookup("Indiana Jones"));
```

On implante `Catalog`:
```java
LaserDisc[name=Jaws]
VideoTape[name=The Cotton Club, length=PT2H8M]
null
```

3. **On veut pouvoir charger et sauvegarder les articles du catalogue dans un fichier, un article par ligne. 
   Pour cela, on va dans un premier temps écrire deux méthodes, toText et fromText qui permettent respectivement de renvoyer la forme textuelle d'un article et de créer un article à partir de sa représentation textuelle.
   Pourquoi fromText est-elle une méthode statique alors que toText est une méthode d'instance ?**

La méthode fromText prend une ligne et renvoi une nouvelle instance de la classe spécifiée dans le texte donné en pramètre.
Cette méthode ne sera donc pas appelée sur un objet, elle est donc statique.

A l'inverse, la méthode toText doit convertir en texte une instance, elle devra donc être appelée sur ce même objet. C'est donc une méthode d'instance.


Le format textuel est composé du type de l'article (LaserDisc ou VideoTape) suivi du nom de l'article et, dans le cas de la cassette vidéo, de la durée en minutes (il existe une méthode duration.toMinutes() et une méthode Duration.ofMinutes()). Les différentes parties du texte sont séparées par des ":".
Voici un exemple de fichier contenant un laser disc et une cassette vidéo.
```bash
LaserDisc:Jaws
VideoTape:The Cotton Club:128
```

**Dans un premier temps, écrire la méthode toText de telle façon que le code suivant est valide:**
```java
var laserDiscText = laserDisc.toText();
var videoTapeText = videoTape.toText();
System.out.println(laserDiscText);  // LaserDisc:Jaws
System.out.println(videoTapeText);  // VideoTape:The Cotton Club:128
```

On ajoute la méthode abstraite `toText` à l'interface, afin de forcer les classes implémentant l'interface à définir cette méthode.
```java
public sealed interface Article permits LaserDisc, VideoTape {
  String name();
  String toText();
}
```

toText est une méthode d'instance allant renvoyer un texte similaire à l'exemple ci-dessus à partir des propriétés de l'instance sur laquelle la méthode est appelée.
Elle ne prend donc aucun paramètre, et renverra une chaîne de caractères.

On définit les méthodes dans les deux records implantant l'interface `Article`.

Record `LaserDisc`:
```java
@Override
public String toText() {
  return "VideoTape:" + name;
}
```

Record `VideoTape`:
```java
@Override
public String toText() {
  return "VideoTape:" + name + ":" +	length.toMinutes();
}
```

On obtient le résultat suivant:
```bash
LaserDisc:Jaws
VideoTape:The Cotton Club:128
```

**Puis écrire le code de la méthode fromText sachant qu'il existe une méthode string.split() pour séparer un texte suivant un délimiteur et que l'on peut faire un switch sur des Strings. Le code suivant devra fonctionner :**
```bash
var laserDisc2 = Article.fromText(laserDiscText);
var videoTape3 = Article.fromText(videoTapeText);
System.out.println(laserDisc.equals(laserDisc2));  // true
System.out.println(videoTape.equals(videoTape3));  // true
```

Comme mentionné auparavant, la méthode fromText sera statique. Il est donc préférable de la définir dans l'interface `Article`.

On commence par ajouter les constantes suivantes à l'interface:
```java
String LASERDISC = "LaserDisc";
String VIDEOTAPE = "VideoTape";
```

Ces constantes sont public static et final par défaut, on ne le re-précise donc pas.

On ajoute ensuite la méthode `fromText` qui utilisera ces constantes:
```java
static Article fromText(String text) {
  Objects.requireNonNull(text, "Text is null");
  var parts = text.split(":");
  if (parts.length < 2) {
    throw new IllegalArgumentException("Invalid text");
  }

  switch (parts[0]) {
    case LASERDISC -> {
      return new LaserDisc(parts[1]);
    }
    case VIDEOTAPE -> {
      if (parts.length < 3) {
        throw new IllegalArgumentException("Invalid text");
      }
      return new VideoTape(parts[1], Duration.ofMinutes(Integer.parseInt(parts[2])));
    }
    default -> throw new IllegalArgumentException("Invalid entry");
  }
}
```

On obtient le résultat suivant:
```bash
true
true
```

**Enfin, discuter du fait que le type des articles doit être scellé ou non ?**

Etant donné que nous connaissons tous les sous-types allant implémenter l'interface, il est préférable de sceller cette dernière afin de ne pas permettre l'ajout de nouveaux sous-types.

4. **On souhaite maintenant ajouter une méthode save qui permet de sauvegarder les articles d'un catalogue dans un fichier.
   Quelle méthode doit-on utiliser pour créer un écrivain sur un fichier texte à partir d'un Path ?**

Dans ce cas, on utilisera la méthode `Files.newBufferedWriter`.

**Comment doit-on faire pour garantir que la ressource système associée est bien libérée ?**

On utilisera un bloc try-with-resources. Ce procédé permet de garantir la fermeture de la ressource, même dans le cas ou une Checked Exception (du à un paramètre externe au programme) est renvoyée.

**Comment doit-on gérer l'exception d'entrée/sortie ?**

On utilisera un bloc try-catch pour gérer l'exception d'entrée/sortie.

**Écrire la méthode save afin que le code suivant fonctionne :**
```java
var catalog2 = new Catalog();
catalog2.add(laserDisc);
catalog2.add(videoTape);
catalog2.save(Path.of("catalog.txt"));
```

On ajoute la méthode `save` à la classe `Catalog`:
```java
public void save(Path path) throws IOException {
  Objects.requireNonNull(path);
  try (var writer = Files.newBufferedWriter(path)) {
    for (var article : articles.values()) {
      writer.write(article.toText());
      writer.newLine();
    }
  }
}
```

On obtient le résultat suivant dans le fichier `catalog.txt`:
```text
LaserDisc:Jaws
VideoTape:The Cotton Club:128
```

**Comme Catalog est mutable, on va écrire la méthode load comme une méthode d'instance et non pas comme une méthode statique. Expliquer quel est l'intérêt.**

**Écrire la méthode load dans Catalog afin que le code suivant fonctionne :**
```java
var catalog3 = new Catalog();
catalog3.load(Path.of("catalog.txt"));
System.out.println(catalog3.lookup("Jaws"));  // LaserDisc:Jaws
System.out.println(catalog3.lookup("The Cotton Club"));  // VideoTape:The Cotton Club:128
```

On ajoute la méthode `load` à la classe `Catalog`:
```java
public void load(Path path) throws IOException {
  Objects.requireNonNull(path);
  try (var reader = Files.newBufferedReader(path)) {
    String l;
    while ((l = reader.readLine()) != null) {
      var article = Article.fromText(l);
      articles.put(article.name(), article);
    }
  }
}
```

5. **Tout le monde s'est plus ou moins mis d'accord pour que l'UTF-8 soit le format utilisé pour la stockage, malheureusement, il reste encore plein de Windows XP / Windows 7 qui ne sont pas en UTF8 par défaut. 
   On va donc ajouter deux surcharges à load et save qui prennent en paramètre l'encoding. Le code suivant doit fonctionner :**
```java
var catalog4 = new Catalog();
catalog4.add(new LaserDisc("A Fistful of €"));
catalog4.add(new VideoTape("For a Few €s More", Duration.ofMinutes(132)));
catalog4.save(Path.of("catalog-windows-1252.txt"), Charset.forName("Windows-1252"));

var catalog5 = new Catalog();
catalog5.load(Path.of("catalog-windows-1252.txt"), Charset.forName("Windows-1252"));
System.out.println(catalog5.lookup("A Fistful of €"));
System.out.println(catalog5.lookup("For a Few €s More"));
```

On ajoute les deux surcharges à la classe `Catalog`:
```java
public void load(Path path, Charset charset) throws IOException {
  Objects.requireNonNull(path);
  Objects.requireNonNull(charset);
  try (var reader = Files.newBufferedReader(path, charset)) {
    String l;
    while ((l = reader.readLine()) != null) {
      var article = Article.fromText(l);
      articles.put(article.name(), article);
    }
  }
}

public void save(Path path, Charset charset) throws IOException {
  Objects.requireNonNull(path);
  Objects.requireNonNull(charset);
  try (var writer = Files.newBufferedWriter(path, charset)) {
    for (var article : articles.values()) {
      writer.write(article.toText());
      writer.newLine();
    }
  }
}
```
