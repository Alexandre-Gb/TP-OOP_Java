package fr.uge.manifest;

import java.util.Objects;

public record Passenger (String destination) implements Onboard {
  public Passenger {
    Objects.requireNonNull(destination, "A destination is required");
  }

  @Override
  public int weight() {
    return 0;
  }

  @Override
  public int price() {
    return 10;
  }

  @Override
  public String toString() {
    return destination + " (passenger)";
  }
}