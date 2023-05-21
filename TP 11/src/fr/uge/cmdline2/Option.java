package fr.uge.cmdline2;

import java.util.Objects;

public record Option(String text, OptionInfo info) implements Argument {
  public Option {
    Objects.requireNonNull(text);
  }
}
