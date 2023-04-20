package fr.uge.calc;

import java.util.Iterator;

public sealed interface Expr permits Add, Sub, Mul, Value {
  int eval();
  String toExprString();

  static Expr parse(Iterator<String> tokens) {
    if (!tokens.hasNext()) {
      throw new IllegalStateException();
    }

    var next = tokens.next();
    return switch (next) {
      case "+" -> new Add(parse(tokens), parse(tokens));
      case "-" -> new Sub(parse(tokens), parse(tokens));
      case "*" -> new Mul(parse(tokens), parse(tokens));
      default -> new Value(Integer.parseInt(next));
    };
  }
}
