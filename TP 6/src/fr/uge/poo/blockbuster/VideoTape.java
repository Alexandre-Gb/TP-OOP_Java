package fr.uge.poo.blockbuster;

import java.time.Duration;
import java.util.Objects;

public record VideoTape(String name, Duration length) implements Article {
	public VideoTape {
		Objects.requireNonNull(name, "Name is null");
		Objects.requireNonNull(length, "Length is null");
	}

	@Override
	public String toText() {
		return "VideoTape:" + name + ":" +	length.toMinutes();
	}
}
