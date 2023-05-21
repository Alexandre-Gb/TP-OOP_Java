package fr.uge.cmdline2;

import java.util.ArrayList;
import java.util.List;

public class CmdLine2 {
  private static List<Argument> parseCmdLine(String... arguments) {
    var argumentsList = new ArrayList<Argument>();
    for (var argument : arguments) {
      argumentsList.add(OptionInfo.asOptionInfo(argument).<Argument>map(info -> new Option(argument, info)).orElseGet(() -> new Plain(argument)));
    }

    return List.copyOf(argumentsList);
  }

  private static void checkCmdLine(List<Argument> arguments) { /* ??? */ }

  public static void main(String[] args) {
    // 1
    var argument1 = new Plain("foo.txt");
    var argument2 = new Plain("bar.png");
    System.out.println(argument1);  // Argument{ text:'foo.txt' }
    System.out.println(argument2);  // Argument{ text:'bar.png' }

    // 2
    var arguments1 = parseCmdLine("foo.txt", "bar.png");
    System.out.println(arguments1);  // [Argument{ text:'foo.txt' }, Argument{ text:'bar.png' }]

    // 3
    var option1 = new Option("--verbose", OptionInfo.VERBOSE);
    var option2 = new Option("-v", OptionInfo.VERBOSE);
    System.out.println(option1);  // Option{ text: '--verbose', info: VERBOSE }
    System.out.println(option2);  // Option{ text: '-v', info: VERBOSE }

    // 4 & 5
    var arguments2 = parseCmdLine("-v", "bar.png");
    System.out.println(arguments2);  // [Option{ text: '-v', info: VERBOSE }, Argument{ text:'bar.png' }]

    // 6
    var arguments3 = parseCmdLine("-v", "bar.png", "--verbose");
    //checkCmdLine(arguments3);  // java.lang.IllegalArgumentException: duplicate argument Option{ text: '--verbose', info: VERBOSE }

    // 7
    var argument3 = new Plain("-v");
    var option3 = new Option("-v", OptionInfo.VERBOSE);
    System.out.println(argument3.equals(argument3));  // true
    System.out.println(argument3.equals(option3));    // false
    System.out.println(option3.equals(option3));      // true
    System.out.println(option3.equals(argument3));    // false

    // 8, 9 & 10
    var arguments4 = parseCmdLine("-v", "bar.png", "bar.png");
    checkCmdLine(arguments4);  // ok !
  }
}
