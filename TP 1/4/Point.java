public record Point(int x, int y) {
  public Double distance() {
    return Math.sqrt(x*x + y*y);
  }

  public static void main(String[] args) {
    int x = Integer.parseInt(args[0]);
    int y = Integer.parseInt(args[1]);
    System.out.println("x=" + x + ", y=" + y);

    Point p = new Point(x, y);
    System.out.println(p);
    System.out.println(p.distance());
  }
}