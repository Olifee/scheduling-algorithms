package vuv;

import java.io.*;
import java.util.*;

public class Import {
  private static final String DELIM = ",";

  public static StartData importStream(InputStream in) throws IOException {
    final String[] lines = slurpLines(in);
    final int courses = lines[0].split(DELIM, -1).length - 1;
    final String[] courseNames = Arrays.copyOfRange(lines[0].split(DELIM, -1), 1, courses + 1);
    final String[] courseCapacity = Arrays.copyOfRange(lines[1].split(DELIM, -1), 1, courses + 1);

    final List<Course> courseList = new ArrayList<>(courses);
    for (int i = 0; i < courses; i++) {
      courseList.add(new Course(courseNames[i], Integer.parseInt(courseCapacity[i])));
    }

    final List<Student> studentList = new ArrayList<>(lines.length - 2);
    for (int i = 2; i < lines.length; i++) {
      final String[] lineSplitted = lines[i].split(DELIM, -1);
      Course first = null;
      Course second = null;
      Course third = null;

      for (int courseNumber = 0; courseNumber < courses; courseNumber++) {
        if (lineSplitted[1 + courseNumber].equals("1")) {
          first = new Course(courseNames[courseNumber], Integer.parseInt(courseCapacity[courseNumber]));
        }
        if (lineSplitted[1 + courseNumber].equals("2")) {
          second = new Course(courseNames[courseNumber], Integer.parseInt(courseCapacity[courseNumber]));
        }
        if (lineSplitted[1 + courseNumber].equals("3")) {
          third = new Course(courseNames[courseNumber], Integer.parseInt(courseCapacity[courseNumber]));
        }
      }

      studentList.add(new Student(lineSplitted[0], Arrays.asList(first, second, third)));
    }

    return new StartData(studentList, courseList);
  }

  private static String[] slurpLines(InputStream in) throws IOException {
    final ArrayList<String> lines = new ArrayList<>();

    final BufferedReader r = new BufferedReader(new InputStreamReader(in));

    String s = null;
    while ((s = r.readLine()) != null) {
      lines.add(s);
    }

    return lines.toArray(new String[0]);
  }
}
