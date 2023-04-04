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
