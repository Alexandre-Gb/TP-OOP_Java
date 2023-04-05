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
}
