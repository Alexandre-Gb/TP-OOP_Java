package fr.uge.manifest;

public sealed interface Onboard permits Container, Passenger {
  int weight();
  String destination();
  int price();
  boolean isContainer();
}