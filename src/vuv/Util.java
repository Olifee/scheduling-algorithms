package vuv;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class Util {
  public static <T> String join(String sep, Collection<T> cs, Function<? super T, String> f) {
    return cs.stream().map(f).collect(Collectors.joining(sep));
  }

  public static String pretty(StartData d) {
    return "students: " + join(", ", d.students, s -> s.name + "(" + join(", ", s.courses, (c -> c.name)) + ")") + "\n" +
      "courses: " + join(", ", d.courses, s -> s.name + "(" + s.capacity + ")");
  }

  public static String pretty(Map<Student, Course> m) {
    return join("\n", m.keySet(), s -> s.name + " -> " + m.get(s));
  }

  public static String prettyC(List<Course> cs) {
    return join(", ", cs, c -> c.name);
  }

  public static String pretty(Swap s) {
    return "swap: " + s.studentA.name + "(" + prettyC(s.studentA.courses) + ") in " + s.courseA.name + " with " + s.studentB.name + "(" + prettyC(s.studentA.courses) + ") in " + s.courseB.name;
  }

  public static String pretty(List<Swap> ss) {
    return join("\n", ss, Util::pretty);
  }
}
