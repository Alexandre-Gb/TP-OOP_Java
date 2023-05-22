package fr.umlv.monopoly;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AssetManager {
	private final ArrayList<Asset> assets;
	
	public AssetManager() {
		this.assets = new ArrayList<>();
	}
	
	public void add(Asset asset) {
		Objects.requireNonNull(asset);
		assets.add(asset);
	}
	
	public double profitPerNight() {
		return assets.stream().mapToDouble(Asset::price).sum();
	}
	
	public List<Asset> lowestEfficiency(double maxEfficiency) {		
		return assets.stream().filter(e -> e.efficiency() <= maxEfficiency).toList();
	}
	
	public void remove(double maxEfficiency) {
		assets.removeIf(e -> e.efficiency() <= maxEfficiency);
	}
	
	@Override
	public String toString() {
		var stringBuilder = new StringBuilder();
		assets.stream().forEach(e -> stringBuilder.append(e.toString()).append("\n"));
		
		return stringBuilder.toString();
	}
}
