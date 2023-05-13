package fr.uge.streams;

import java.util.Objects;

public record Person(String name, int age) {
  public Person {
    Objects.requireNonNull(name, "name is null");
    if (age < 0) {
      throw new IllegalArgumentException("age < 0");
    }
  }
}
