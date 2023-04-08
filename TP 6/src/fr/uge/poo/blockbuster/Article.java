package fr.uge.poo.blockbuster;

import java.nio.charset.Charset;import java.nio.file.Path;
import java.time.Duration;
import java.util.Objects;
import java.io.IOException;

public sealed interface Article permits LaserDisc, VideoTape {
  String LASERDISC = "LaserDisc";
  String VIDEOTAPE = "VideoTape";

  String name();
  String toText();

  static Article fromText(String text) {
    Objects.requireNonNull(text, "Text is null");
    var parts = text.split(":");
    if (parts.length < 2) {
      throw new IllegalArgumentException("Invalid text");
    }

    switch (parts[0]) {
      case LASERDISC -> {
        return new LaserDisc(parts[1]);
      }
      case VIDEOTAPE -> {
        if (parts.length < 3) {
          throw new IllegalArgumentException("Invalid text");
        }
        return new VideoTape(parts[1], Duration.ofMinutes(Integer.parseInt(parts[2])));
      }
      default -> throw new IllegalArgumentException("Invalid entry");
    }
  }

  static void main(String[] args) throws IOException {
    var laserDisc = new LaserDisc("Jaws");
    var videoTape = new VideoTape("The Cotton Club", Duration.ofMinutes(128));
    var videoTape2 = new VideoTape("Mission Impossible", Duration.ofMinutes(110));

    var catalog = new Catalog();
    catalog.add(laserDisc);
    catalog.add(videoTape);
    catalog.add(videoTape2);
    // catalog.add(new LaserDisc("Mission Impossible"));  // exception !

    var laserDiscText = laserDisc.toText();
    var videoTapeText = videoTape.toText();
    System.out.println(laserDiscText);  // LaserDisc:Jaws
    System.out.println(videoTapeText);  // VideoTape:The Cotton Club:128*/

    var laserDisc2 = Article.fromText(laserDiscText);
    var videoTape3 = Article.fromText(videoTapeText);
    System.out.println(laserDisc2);  // LaserDisc:Jaws
    System.out.println(videoTape3);  // VideoTape:The Cotton Club:128

    var catalog2 = new Catalog();
    catalog2.add(laserDisc);
    catalog2.add(videoTape);
    catalog2.save(Path.of("catalog.txt"));

    var catalog3 = new Catalog();
    catalog3.load(Path.of("catalog.txt"));
    System.out.println(catalog3.lookup("Jaws"));  // LaserDisc:Jaws
    System.out.println(catalog3.lookup("The Cotton Club"));  // VideoTape:The Cotton Club:128
    var catalog4 = new Catalog();
    catalog4.add(new LaserDisc("A Fistful of €"));
    catalog4.add(new VideoTape("For a Few €s More", Duration.ofMinutes(132)));
    catalog4.save(Path.of("catalog-windows-1252.txt"), Charset.forName("Windows-1252"));

    var catalog5 = new Catalog();
    catalog5.load(Path.of("catalog-windows-1252.txt"), Charset.forName("Windows-1252"));
    System.out.println(catalog5.lookup("A Fistful of €"));
    System.out.println(catalog5.lookup("For a Few €s More"));
  }
}
