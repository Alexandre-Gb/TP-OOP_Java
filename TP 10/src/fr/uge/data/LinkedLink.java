package fr.uge.data;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class LinkedLink<T> {
  private Link<T> head;
  private int size;

  public void add(T value) {
    head = new Link<>(value, head);
    size++;
  }

  public T get(int index) {
/*    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    }*/
    Objects.checkIndex(index, size);

    var current = head;
    for (int i = 0; i < index; i++) {
      current = current.next();
    }

    return current.value();
  }

  public void forEach(Consumer<T> consumer) {
    var current = head;

    while (current != null) {
      consumer.accept(current.value());
      current = current.next();
    }
  }

//  public LinkedLink<T> removeIf(Predicate<T> predicate, Link<T> read) {
//
//  }

  public String toString() {
    var joiner = new StringJoiner(" -> ");
    forEach(value -> joiner.add(value.toString()));
    return joiner.toString();
  }
}

