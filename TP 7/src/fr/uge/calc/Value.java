package fr.uge.calc;

public record Value(int value) implements Expr {
  @Override
  public int eval() {
    return value;
  }

  @Override
  public String toExprString() {
    return String.valueOf(value);
  }
}
