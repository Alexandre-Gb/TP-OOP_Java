package fr.uge.cmdline1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class CmdLine1 {

  private static List<Argument> parseCmdLine(String... args) {
    return Arrays.stream(args).map(e -> asOptionInfo(e)
    .<Argument>map(o -> new Option(e, o))
    .orElseGet(() -> new Argument(e))).toList();
  }

  private static Optional<OptionInfo> asOptionInfo(String option) {
    return switch (option) {
      case "-v", "--verbose" -> Optional.of(OptionInfo.VERBOSE);
      case "-a", "--all" -> Optional.of(OptionInfo.ALL);
      default -> Optional.empty();
    };
  }

  private static boolean isOption(Argument argument) {
    return switch (argument) {
      case Option e -> true;
      case Argument e -> false;
    };
  }

  private static void checkCmdLine(List<Argument> arguments) {
    var checkArgument = new HashSet<OptionInfo>();
    for (var argument : arguments) {
//      var option = asOptionInfo(argument.name());
//      if (option.isPresent()) {
//        if (!checkArgument.add(option.get())) {
//          throw new IllegalArgumentException("duplicate argument " + argument);
//        }
//      }
//      if (argument.isOption()) {
      if (isOption(argument)) {
        var option = asOptionInfo(argument.name());
        if (!checkArgument.add(option.get())) {
          throw new IllegalArgumentException("duplicate argument " + argument);
        }
      }
    }
  }

  public static void main(String[] args) {
    var argument1 = new Argument("foo.txt");
    var argument2 = new Argument("bar.png");
    System.out.println(argument1);  // Argument{ text:'foo.txt' }
    System.out.println(argument2);  // Argument{ text:'bar.png' }

    List<Argument> arguments1 = parseCmdLine("foo.txt", "bar.png");
    System.out.println(arguments1);  // [Argument{ text:'foo.txt' }, Argument{ text:'bar.png' }]

    var option1 = new Option("--verbose", OptionInfo.VERBOSE);
    var option2 = new Option("-v", OptionInfo.VERBOSE);
    System.out.println(option1);  // Option{ text: '--verbose', info: VERBOSE }
    System.out.println(option2);  // Option{ text: '-v', info: VERBOSE }

    var arguments2 = parseCmdLine("-v", "bar.png");
    System.out.println(arguments2);  // [Option{ text: '-v', info: VERBOSE }, Argument{ text:'bar.png' }]

    // var arguments3 = parseCmdLine("-v", "bar.png", "--verbose");
    // checkCmdLine(arguments3);  // java.lang.IllegalArgumentException: duplicate argument Option{ text: '--verbose', info: VERBOSE }

    var argument3 = new Argument("-v");
    var option3 = new Option("-v", OptionInfo.VERBOSE);
    System.out.println(argument3.equals(argument3));  // true
    System.out.println(argument3.equals(option3));    // false
    System.out.println(option3.equals(option3));      // true
    System.out.println(option3.equals(argument3));    // false

    var arguments4 = parseCmdLine("-v", "bar.png", "bar.png");
    checkCmdLine(arguments4);  // ok !
  }
}
