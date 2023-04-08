package fr.uge.poo.blockbuster;

import java.io.IOException;
import java.nio.charset.Charset;import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Objects;

public class Catalog {
  private final HashMap<String, Article> articles;

  public Catalog() {
    articles = new HashMap<>();
  }

  public void add(Article article) {
    Objects.requireNonNull(article);
    if (articles.containsKey(article.name()))
      throw new IllegalStateException("Article already exists");
    articles.put(article.name(), article);
  }

  public Article lookup(String name) {
    Objects.requireNonNull(name);
    return articles.get(name);
  }

  public void save(Path path) throws IOException {
    Objects.requireNonNull(path);
    try (var writer = Files.newBufferedWriter(path)) {
      for (var article : articles.values()) {
        writer.write(article.toText());
        writer.newLine();
      }
    }
  }

  public void save(Path path, Charset charset) throws IOException {
    Objects.requireNonNull(path);
    Objects.requireNonNull(charset);
    try (var writer = Files.newBufferedWriter(path, charset)) {
      for (var article : articles.values()) {
        writer.write(article.toText());
        writer.newLine();
      }
    }
  }

  public void load(Path path) throws IOException {
    Objects.requireNonNull(path);
    try (var reader = Files.newBufferedReader(path)) {
      String l;
      while ((l = reader.readLine()) != null) {
        var article = Article.fromText(l);
        articles.put(article.name(), article);
      }
    }
  }

  public void load(Path path, Charset charset) throws IOException {
    Objects.requireNonNull(path);
    Objects.requireNonNull(charset);
    try (var reader = Files.newBufferedReader(path, charset)) {
      String l;
      while ((l = reader.readLine()) != null) {
        var article = Article.fromText(l);
        articles.put(article.name(), article);
      }
    }
  }
}
