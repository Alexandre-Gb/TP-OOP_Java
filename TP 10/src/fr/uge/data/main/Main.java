package fr.uge.data.main;

import fr.uge.data.LinkedLink;

import java.util.ArrayList;

public class Main {
  public static void main(String[] args) {
    var list = new LinkedLink<Integer>();
    list.add(12);
    list.add(144);
    System.out.println(list);

    var l = new LinkedLink<String>();
    l.add("hello");
    l.add("world");
    l.forEach(s -> System.out.println("string " + s + " length " + s.length()));

    var l2 = new LinkedLink<Integer>();
    l2.add(24);
    l2.add(17);
    l2.add(12);
//    l2.removeIf(i -> i % 2 == 0);
    System.out.println(l2); // 17
  }
}