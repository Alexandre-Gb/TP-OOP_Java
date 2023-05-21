package fr.uge.cmdline1;

import java.util.Objects;

public sealed class Argument permits Option {
  final String name;

  public Argument(String name) {
    Objects.requireNonNull(name);
    this.name = name;
  }

  public String name() {
    return name;
  }

  public boolean isOption() {
    return false;
  }

  @Override
  public String toString() {
    return "Argument{ text:'" + name + "' }";
  }

  @Override
  public boolean equals(Object second) {
    Objects.requireNonNull(second);

    if (getClass() != second.getClass()) {
      return false;
    }

    Argument argument = (Argument) second;
    return Objects.equals(name, argument.name);
  }
}
