//import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

//public class Library {
//  private final ArrayList<Book> books;
//
//  public Library () {
//    books = new ArrayList<>();
//  }
//
//  public void add(Book newBook){
//    Objects.requireNonNull(newBook);
//    books.add(newBook);
//  }
//
//  public Book findByTitle(String title) {
//    for (Book book : books) {
//      if (book.title().equals(title)) {
//        return book;
//      }
//    }
//    return null;
//  }
//
//  @Override
//  public String toString() {
//    StringBuilder sb = new StringBuilder();
//    var separator = "";
//    for (Book book : books) {
//      sb.append(separator).append(book.toString());
//      separator = "\n";
//    }
//    return sb.toString();
//  }
//}

public class Library {
  private final HashMap<String, Book> books;

  public Library () {
    books = new HashMap<>();
  }

  public void add(Book newBook){
    books.put(newBook.title(), newBook);
  }

  public Book findByTitle(String title) {
    return books.get(title);
  }

//  public void removeAllBooksFromAuthor(String author) {
//    for (var entry : books.entrySet()) {
//      if (entry.getValue().author().equals(author)) {
//        books.remove(bookEntry.getKey());
//      }
//    }
//  }

//  public void removeAllBooksFromAuthor(String author) {
//    var iterator = books.entrySet().iterator();
//    while (iterator.hasNext()) {
//      if (iterator.next().getValue().author().equals(author)) {
//        iterator.remove();
//      }
//    }
//  }

  public void removeAllBooksFromAuthor(String author) {
    books.entrySet().removeIf(entry -> entry.getValue().author().equals(author));
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    var separator = "";
    for (Book book : books.values()) {
      sb.append(separator).append(book.toString());
      separator = "\n";
    }
    return sb.toString();
  }
}