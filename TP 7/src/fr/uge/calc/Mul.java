package fr.uge.calc;

import java.util.Objects;

public record Mul(Expr left, Expr right) implements Expr {
	public Mul {
		Objects.requireNonNull(left);
		Objects.requireNonNull(right);
	}
	
	@Override
	public int eval() {
		return left.eval() * left.eval();
	}

	@Override
	public String toExprString() {
		return '(' + left.toExprString() + " * " + right.toExprString() + ')';
	}
}
