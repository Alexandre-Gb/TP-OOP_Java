package fr.uge.manifest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Manifest {
	private final ArrayList<Onboard> onboards;
	
	public Manifest() {
		onboards = new ArrayList<>();
	}
	
	public void add(Container newContainer) {
		Objects.requireNonNull(newContainer, "Please provide a container");
		onboards.add(newContainer);
	}

	public void add(Passenger newPassenger) {
		Objects.requireNonNull(newPassenger, "Please provide a passenger");
		onboards.add(newPassenger);
	}

	public int price() {
		int price = 0;
		for (Onboard onboard : onboards) {
			price += onboard.price();
		}
		return price;
	}

	public int weight() {
		int weight = 0;
		for (Onboard onboard : onboards) {
			weight += onboard.weight();
		}
		return weight;
	}

	public void removeAllContainersFrom(String destination) {
		onboards.removeIf(onboard -> onboard.isContainer() && onboard.destination().equals(destination));
		onboards.removeIf(onboard -> onboard instanceof Container && onboard.destination().equals(destination));
	}

	public HashMap<String, Integer> weightPerDestination() {
		HashMap<String, Integer> weights = new HashMap<>();
		for (Onboard onboard : onboards) {
			if (onboard.isContainer()) {
				weights.merge(onboard.destination(), onboard.weight(), Integer::sum);
			}
		}
		return weights;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		int i = 0;
		for (Onboard onboard : onboards) {
			sb.append(++i)
			  .append(". ")
			  .append(onboard.toString())
			  .append("\n");
		}
		return sb.toString();
	}
}