package fr.umlv.monopoly;

public record Hotel (int rooms, double efficiency) implements Asset {
	
	public Hotel(int rooms, double efficiency) {
		if (rooms < 0) {
			throw new IllegalArgumentException("Negative rooms not allowed");
		}
		if (efficiency < 0 || efficiency > 1) {
			throw new IllegalArgumentException("Efficiency value must be between 0 and 1");
		}
		this.efficiency = efficiency;
		this.rooms = rooms;
	}
	
	@Override
	public double price() {
		return (100 * rooms) * efficiency;
	}
	
	@Override
	public double efficiency() {
		return efficiency;
	}
	
	@Override
	public String toString() {
		return "Hotel " + rooms + " rooms " + efficiency;
	}
}
