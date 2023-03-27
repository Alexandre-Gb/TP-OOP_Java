import java.util.Objects;

public record Book(
  String title,
  String author
) {
  public Book {
    Objects.requireNonNull(title, "Book title is null");
  }

  public Book(String title) {
    this(title, "<no author>");
  }

  public Book withTitle(String newTitle) {
    return new Book(newTitle, author);
  }

  public boolean isFromTheSameAuthor(Book secondBook) {
    return author.equals(secondBook.author);
  }

  @Override
  public String toString() {
    return title + " by " + author;
  }
}