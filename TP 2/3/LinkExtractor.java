import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

public class LinkExtractor {
  public static void main(String[] args) throws IOException {
    var path = Path.of(args[0]);
    var lines = Files.readAllLines(path, StandardCharsets.ISO_8859_1);
    Pattern pattern = Pattern.compile("<a href=\"(.*?)\"");
    // reads all lines of the file opened in Latin-1
    for(var line : lines){
      Matcher matcher = pattern.matcher(line);
      while(matcher.find()) {
        System.out.println(line.substring(matcher.start(), matcher.end()));
        System.out.println("===>" + matcher.group(1));
      }
    }
  }
}