package fr.uge.calc.main;

import fr.uge.calc.*;

import java.util.List;

public class Main {
  public static void main(String[] args) {
    Expr expression = new Add(new Value(2), new Value(3));
    Expr expression2 = new Sub(new Mul(new Value(2), new Value(3)), new Value(4));
    System.out.println(expression.eval());
//    System.out.println(Expr.parse(new Scanner(System.in)).eval());
    System.out.println(Expr.parse(List.of("-","+","5","5","2").iterator()).eval());
    System.out.println(Expr.parse(List.of("-","+","5","5","2").iterator()).toExprString());
  }
}
