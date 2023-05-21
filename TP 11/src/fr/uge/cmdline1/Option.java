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

  public OptionInfo info() {
    return info;
  }

  @Override
  public boolean isOption() {
    return true;
  }

  @Override
  public String toString() {
    return "Option{ text: '" + argument + "', info: " + info + " }";
  }

  @Override
  public boolean equals(Object second) {
    Objects.requireNonNull(second);
    return getClass() == second.getClass() && super.equals(second);
  }
}
