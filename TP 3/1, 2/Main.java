public class Main {
  public static void main(String[] args) {
//    var book = new Book("Critique of Judgment", "Immanuel Kant");
//    var book2 = new Book("Critique of Pure Reason", "Immanuel Kant");
//    var book3 = new Book("What Is Property?", "Pierre-Joseph Proudhon");
//    System.out.println(book.isFromTheSameAuthor(book2));
//    System.out.println(book.isFromTheSameAuthor(book3));
    var book1 = new Book("Da Vinci Code", "Dan Brown");
    var book2 = new Book("Angels & Demons", new String("Dan Brown"));
    System.out.println(book1.isFromTheSameAuthor(book2));
  }
}