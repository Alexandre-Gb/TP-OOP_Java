package fr.uge.calc;

import java.util.Objects;

public record Add(Expr left, Expr right) implements Expr {
  public Add {
    Objects.requireNonNull(left);
    Objects.requireNonNull(right);
  }

  @Override
  public int eval() {
    return left.eval() + right.eval();
  }

  @Override
  public String toExprString() {
    return '(' + left.toExprString() + " + " + right.toExprString() + ')';
  }
}
