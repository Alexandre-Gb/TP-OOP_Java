package fr.uge.cmdline2;

import java.util.Objects;

public record Plain(String text) implements Argument {
  public Plain {
    Objects.requireNonNull(text);
  }
}
