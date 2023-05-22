package fr.umlv.monopoly;

public sealed interface Asset permits Apartment, Hotel {
	double price();
	double efficiency();
}
