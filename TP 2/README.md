# TP2 - String, StringBuilder, égalité, et expressions régulières
## GIBOZ Alexandre, INFO1 2022-2025
***

Chaque répertoire a pour nom le numéro d'exercice auquel il est rattaché. 
Ces répertoires contiennent les fichiers ".java" associés, ainsi que tout autre fichier en lien avec l'exercice en question.

[Consignes de rendu de TP](https://monge.univ-mlv.fr/ens/IR/IR1/2022-2023/Java/index.php)

[Énoncé](https://monge.univ-mlv.fr/ens/IR/IR1/2022-2023/Java/td02.php)
***

## Exercice 1 - Assignation, égalité, référence

1. **On considère le code suivant :**
```java
var s = "toto";
System.out.println(s.length());
```

**Quel est le type de s ? Comment le compilateur fait-il pour savoir qu'il existe une méthode length() sur s ?**

Le type de la variable s est `String`. 
Le compilateur sait que la variable est de type `String` car la méthode `length()` appartient à la classe `String`.

2. **Qu'affiche le code suivant ? Expliquer.**
```java
var s1 = "toto";
var s2 = s1;
var s3 = new String(s1);

System.out.println(s1 == s2);
System.out.println(s1 == s3);
```

Le code ci-dessus affichera `true` dans un premier temps, puis `false`.

La première ligne affiche `true` car les deux variables `s1` et `s2` pointent vers la même référence.
La seconde ligne affiche `false` car les deux variables `s1` et `s3` pointent vers des références différentes.

3. **Quelle est la méthode à utiliser si l'on veut tester si le contenu des chaînes de caractères est le même ?**

Dans le cas où l'on souhaite comparer le contenu de deux String (ce qui est souvent le cas lorsqu'une comparaison entre deux chaînes est effectuée), il est préférable d'utiliser la méthode `equals()` plutôt que `==`.

Si l'on reprend l'exemple précédent, le procédé suivant est le bon :
```java
var s1 = "toto";
var s2 = s1;
var s3 = new String(s1);

System.out.println(s3.equals(s1));
```

4. **Qu'affiche le code suivant ? Expliquer**
```java
var s6 = "toto";
var s7 = "toto";
    
System.out.println(s6 == s7);
```

Le code ci-dessus affichera `true`.

Ce comportement est du au fait que les chaînes de caractères sont litérales (déclarées avec des double quotes). 
Ces chaînes sont stockées dans un pool de chaînes de caractères, et sont donc partagées entre toutes les variables qui les utilisent.

Les chaînes ayant le même contenu, elles partagent le même emplacement mémoire, ce qui explique que `s6 == s7` vaut `true`.

5. **Expliquer pourquoi il est important que java.lang.String ne soit pas mutable.**

On entend par "mutable" une classe dont les instances peuvent être modifiées après leur création.

La classe "String" est immuable, ce qui signifie que les instances de cette classe ne peuvent pas être modifiées après leur création.
L'objectif est, par mesures de sécurité, de ne pas permettre de pouvoir modifier une chaîne de caractère une fois instanciée.

L'alternative mutable de la classe "String" est la classe "StringBuilder", qui permet de modifier une chaîne de caractères.

6. **Qu'affiche le code suivant ? Expliquer**
```java
var s8 = "hello";
s8.toUpperCase();
System.out.println(s8);
```

Le code ci-dessus affichera `hello`. 
Comme mentionné précédemment, une chaîne de caractères immuable ne peut pas être modifiée après avoir été instanciée. Elle ne peut donc pas être passée en majuscules.

Si l'on souhaite afficher la chaîne de caractères en majuscules, il faut utiliser la méthode `toUpperCase()` de la classe `StringBuilder` :
```java
var s8 = "hello";
var s9 = new StringBuilder(s8);
System.out.println(s9.toUpperCase());
```
