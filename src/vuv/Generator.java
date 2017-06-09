package vuv;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Generator {
  private static final String DELIM = ",";
  private static final String path = "/tmp/x";

  public static String randomCsv(int courses, int students, int overCapacity, long cSeed, long sSeed) {
    final StringBuilder builder = new StringBuilder();

    builder.append(nCourses(courses)).append("\n");
    builder.append(capacites(courses, students + overCapacity, cSeed)).append("\n");

    final Random r = new Random(sSeed);
    for (int n = 1; n <= students; n++) {
      final String[] array = new String[courses];
      Arrays.fill(array, "");
      final List<Integer> preferences = numbers(0, courses).collect(Collectors.toList());
      Collections.shuffle(preferences, r);
      for (int i = 0; i < 3; i++) {
        array[preferences.get(i)] = "" + (i + 1);
      }

      builder.append("S" + n + DELIM).append(Stream.of(array).collect(Collectors.joining(DELIM)));
      if (n != students) {
        builder.append("\n");
      }
    }

    return builder.toString();
  }

  private static String nCourses(int courses) {
    return DELIM + numbers(1, courses).map(number -> "C" + number).collect(Collectors.joining(DELIM));
  }

  private static String capacites(int courses, int capacities, long cSeed) {
    final Integer[] array = new Integer[courses];
    Arrays.fill(array, 0);
    final Random r = new Random(cSeed);

    for (int i = 0; i < capacities; i++) {
      array[r.nextInt(courses)]++;
    }

    return DELIM + Stream.of(array).map(i -> i.toString()).collect(Collectors.joining(DELIM));
  }

  private static Stream<Integer> numbers(int from, int count) {
    return IntStream.range(from, from + count).boxed();
  }

  //public static void main(String[] args) throws IOException {
  //  for (int i = 1; i < 21 ; i++) {
  //    Files.write(Paths.get(path + i), randomCsv(10, 30, 10, i, i).getBytes());
  //  }
  //}
}
