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
