package fr.uge.manifest;

public sealed interface Onboard permits Container, Passenger {
  int weight();
  String destination();
  int price();
  default boolean isContainer() {
    return false;
  }

  static void main(String[] args) {
    var container11 = new Container("Monaco", 100);
    var container12 = new Container("Luxembourg", 200);
    var container13 = new Container("Monaco", 300);
    var passenger3 = new Passenger("Paris");
    var manifest8 = new Manifest();
    manifest8.add(container11);
    manifest8.add(container12);
    manifest8.add(container13);
    manifest8.add(passenger3);
    System.out.println(manifest8.weightPerDestination());
  }
}