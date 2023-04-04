package fr.uge.manifest;

import java.util.Objects;

public record Container(String destination, int weight) implements Onboard {
	public Container {
		Objects.requireNonNull(destination, "A destination is required");
		if (weight < 0) {
			throw new IllegalArgumentException("Weight must be superior or equal to 0");
		}
	}

	@Override
	public int price() {
		return weight * 2;
	}

	@Override
	public boolean isContainer() {
		return true;
	}

	@Override
	public String toString() {
		return destination + " " + weight + "kg";
	}
	
	public static void main(String[] args) {
		var container8 = new Container("Russia", 450);
		var container9 = new Container("China", 200);
		var container10 = new Container("Russia", 125);
		var passenger2 = new Passenger("Russia");
		var manifest4 = new Manifest();
		manifest4.add(container8);
		manifest4.add(container9);
		manifest4.add(container10);
		manifest4.add(passenger2);
		manifest4.removeAllContainersFrom("Russia");
		System.out.println(manifest4);
	}
}
