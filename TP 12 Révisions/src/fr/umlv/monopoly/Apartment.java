package fr.umlv.monopoly;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public record Apartment (int area, List<String> residents) implements Asset {
	public Apartment(int area, List<String> residents) {
		Objects.requireNonNull(residents);
		if (residents.size() == 0) {
			throw new IllegalArgumentException("Atleast one resident in the apartment");
		}
		
		if (area < 0) {
			throw new IllegalArgumentException("Area cannot be negative");
		}
		this.area = area;
		this.residents = List.copyOf(residents);
	}
	
	@Override
	public double efficiency() {
		if (residents.size() == 1) {
			return 0.5;
		}
		return 1;
	}
	
	@Override
	public double price() {
		return 20 * residents.size();
	}
	
	@Override
	public String toString() {
		return "Apartment " +
						area +
						" m2 with " +
						residents.stream().collect(Collectors.joining(", ")) +
						" " +
						efficiency();
	}
}
