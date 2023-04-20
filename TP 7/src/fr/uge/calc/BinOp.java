package fr.uge.calc;

public sealed interface BinOp extends Expr permits Add, Sub, Mul {
    Expr left();
    Expr right();
    int operation(int left, int right);
    default int eval() {
        int left = left().eval();
        int right = right().eval();
        return operation(left, right);
    }
}
