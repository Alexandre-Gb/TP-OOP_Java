public class Algo {
  public static void printArray(Integer[] values) {
    System.out.print("[ ");
    for (int i = 0; i < values.length; i++) {
      System.out.print(values[i] + " ");
    }
    System.out.println("]");
  }

  public static Integer[] swap(Integer[] values, int index1, int index2) {
    Integer temp = values[index1];
    values[index1] = values[index2];
    values[index2] = temp;
    return values;
  }

  public static Integer indexOfMin(Integer[] values, int lo, int hi) {
    if (lo < 0 || hi > values.length || lo >= hi) {
      return null;
    }

    Integer min = values[lo];
    Integer minIndex = lo;
    for (int i = lo; i < hi; i++) {
      if (values[i] < min) {
        min = values[i];
        minIndex = i;
      }
    }
    return minIndex;
  }

  public static void sort(Integer[] values) {
    Integer min;
    for (int i = 0; i < values.length; i++) {
      min = indexOfMin(values, i, values.length);
      swap(values, i, min);
    }
  }

  public static void main(String[] args) {
    Integer[] values = new Integer[5];
    for (int i = 0; i < values.length; i++) {
      values[i] = values.length - i;
    }
    printArray(values);
    sort(values);
    printArray(values);
  }
}