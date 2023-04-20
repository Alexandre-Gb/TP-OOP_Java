package fr.uge.calc;

import java.util.Objects;

public record Mul(Expr left, Expr right) implements BinOp {
	public Mul {
		Objects.requireNonNull(left);
		Objects.requireNonNull(right);
	}

	@Override
	public int operation(int left, int right) {
		return left * right;
	}

	@Override
	public String toExprString() {
		return '(' + left.toExprString() + " * " + right.toExprString() + ')';
	}
}
