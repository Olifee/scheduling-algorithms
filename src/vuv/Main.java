package vuv;

import java.util.*;
import java.util.stream.*;

class Main {
	public static void main(String[] args) throws Exception {
		final StartData sd = Import.importStream(Main.class.getResourceAsStream("field1.csv"));
		System.out.println(sd.courses);
		System.out.println(sd.students);
		final Map<Student, Course> assignments = solve(sd);
		System.out.print("    ");
		for (final Course c : sd.courses) {
			System.out.print(String.format("%5s", c.name));
		}
		System.out.println();
		for (final Student s : sd.students) {
			System.out.print(String.format("%4s", s.name));
			for (final Course c : sd.courses) {
				if (assignments.get(s).equals(c)) {
					System.out.print("    x");
				} else {
					System.out.print("     ");
				}
			}
			System.out.println();
		}
	}

	public static Map<Student, Course> solve(StartData d) {
		return null;
	}

	public static List<Student> sortStudents(StartData d) {
		return null;
	}

	public static List<Swap> getAllSwaps(Student s, Map<Student, Course> assignments) {
		return null;
	}

	public static Swap getBestSwap(List<Swap> swaps) {
		return null;
	}

	public static Student getWorstAssignedStudent(Map<Student, Course> assignments) {
		return null;
	}

	public static Course getBestCourse(StartData d, Map<Student, Course> assignments, Student s) {
		return null;
	}

	public static double getScore(Swap swap) {
		return 0;
	}

}
