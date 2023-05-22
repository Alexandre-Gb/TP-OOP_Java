# TP12 - Révision
## GIBOZ Alexandre, INFO1 2022-2025
***

[Énoncé](https://monge.univ-mlv.fr/ens/IR/IR1/2022-2023/Java/td12.php)
***

## Exercice 1 - Hôtels et Appartements

Apartments.java:
```java
package fr.umlv.monopoly;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public record Apartment (int area, List<String> residents) implements Asset {
  public Apartment(int area, List<String> residents) {
    Objects.requireNonNull(residents);
    if (residents.size() == 0) {
      throw new IllegalArgumentException("Atleast one resident in the apartment");
    }

    if (area < 0) {
      throw new IllegalArgumentException("Area cannot be negative");
    }
    this.area = area;
    this.residents = List.copyOf(residents);
  }

  @Override
  public double efficiency() {
    if (residents.size() == 1) {
      return 0.5;
    }
    return 1;
  }

  @Override
  public double price() {
    return 20 * residents.size();
  }

  @Override
  public String toString() {
    return "Apartment " +
            area +
            " m2 with " +
            residents.stream().collect(Collectors.joining(", ")) +
            " " +
            efficiency();
  }
}
```

Hotel.java:
```java
package fr.umlv.monopoly;

import java.util.List;
import java.util.ArrayList;

public record Hotel (int rooms, double efficiency) implements Asset {

  public Hotel(int rooms, double efficiency) {
    if (rooms < 0) {
      throw new IllegalArgumentException("Negative rooms not allowed");
    }
    if (efficiency < 0 || efficiency > 1) {
      throw new IllegalArgumentException("Efficiency value must be between 0 and 1");
    }
    this.efficiency = efficiency;
    this.rooms = rooms;
  }

  @Override
  public double price() {
    return (100 * rooms) * efficiency;
  }

  @Override
  public double efficiency() {
    return efficiency;
  }

  @Override
  public String toString() {
    return "Hotel " + rooms + " rooms " + efficiency;
  }

  public static void main(String[] args) {
    var hotel = new Hotel(5, 0.75);
    System.out.println(hotel);  // Hotel 5 rooms 0.75

    var apartment = new Apartment(30, List.of("Bony", "Clyde"));
    System.out.println(apartment); // Apartment 30 m2 with Bony, Clyde 1.0

    var list = new ArrayList<String>();
    list.add("Bob");
    var apartment2 = new Apartment(50, list);
    list.remove("Bob");
    System.out.println(apartment2);  // Apartment 50 m2 with Bob 0.5

    var hotel3 = new Hotel(5, 0.75);
    var apartment3 = new Apartment(30, List.of("Bony", "Clyde"));
    var manager = new AssetManager();
    manager.add(hotel3);
    manager.add(apartment3);
    System.out.println(manager.profitPerNight());  // 415

    var hotel4 = new Hotel(5, 0.75);
    var apartment4 = new Apartment(30, List.of("Bony", "Clyde"));
    var manager4 = new AssetManager();
    manager4.add(hotel4);
    manager4.add(apartment4);
    System.out.println(manager4); // affiche
    // Hotel 5 rooms 0.75
    // Apartment 30 m2 with Bony, Clyde 1.0

    var hotel5 = new Hotel(5, 0.75);
    var apartment5 = new Apartment(30, List.of("Bony", "Clyde"));
    var manager5 = new AssetManager();
    manager5.add(hotel5);
    manager5.add(apartment5);
    System.out.println(manager5.lowestEfficiency(0.8));  // [Hotel 5 rooms 0.75]

    var hotel6 = new Hotel(5, 0.75);
    var apartment6 = new Apartment(30, List.of("Bony", "Clyde"));
    var manager6 = new AssetManager();
    manager6.add(hotel6);
    manager6.add(apartment6);
    manager6.remove(0.8);
    System.out.println(manager6);  // affiche
    // Apartment 30 m2 with Bony, Clyde 1.0
  }
}
```

Asset.java:
```java
package fr.umlv.monopoly;

public sealed interface Asset permits Apartment, Hotel {
	double price();
	double efficiency();
}
```

AssetManager.java:
```java
package fr.umlv.monopoly;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AssetManager {
  private final ArrayList<Asset> assets;

  public AssetManager() {
    this.assets = new ArrayList<>();
  }

  public void add(Asset asset) {
    Objects.requireNonNull(asset);
    assets.add(asset);
  }

  public double profitPerNight() {
    return assets.stream().mapToDouble(Asset::price).sum();
  }

  public List<Asset> lowestEfficiency(double maxEfficiency) {
    return assets.stream().filter(e -> e.efficiency() <= maxEfficiency).toList();
  }

  public void remove(double maxEfficiency) {
    assets.removeIf(e -> e.efficiency() <= maxEfficiency);
  }

  @Override
  public String toString() {
    var stringBuilder = new StringBuilder();
    assets.forEach(e -> stringBuilder.append(e.toString()).append("\n"));

    return stringBuilder.toString();
  }
}
```