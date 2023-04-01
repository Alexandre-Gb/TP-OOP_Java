import java.util.Objects;

public record Book(String title, String author) {
  public Book {
    Objects.requireNonNull(title);
    Objects.requireNonNull(author);
  }

  @Override
  public String toString() {
    return title + " by " + author;
  }

  public static void main(String[] args) {
//    Library library = new Library();
//    library.add(new Book("Critique of Judgement", "Immanuel Kant"));
//    library.add(new Book("The society of the spectacle", "Guy Debord"));
//    library.add(new Book("What is to be done?", "Vladimir Ilyich Ulyanov"));
//    library.add(new Book("Critique of Pure Reason", "Immanuel Kant"));

//    System.out.println(library.toString());

    var library2 = new Library();
    library2.add(new Book("Da Vinci Code", "Dan Brown"));
    library2.add(new Book("Angels & Demons", "Dan Brown"));

    System.out.println(library2.toString());
    library2.removeAllBooksFromAuthor("Dan Brown");
    System.out.println(library2.toString());
  }
}