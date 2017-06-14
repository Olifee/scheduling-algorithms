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
		Map<Student, Course> assignments = new HashMap<Student, Course>();
		for (Student s : sortStudents(d)) {
			assignments.put(s, getBestCourse(d, assignments, s));
		}
		boolean posSwapsPossible = true;
		while (posSwapsPossible) {
			Swap swap = getBestSwap(getAllSwaps(getWorstAssignedStudent(assignments), assignments));
			if (getScore(swap)>0) {
				assignments.remove(swap.studentA);
				assignments.remove(swap.studentB);
				assignments.put(swap.studentA, swap.courseB);
				assignments.put(swap.studentB, swap.courseA);
			}
			else {
				posSwapsPossible = false;
			}
		}
		return assignments;
	}

	public static List<Student> sortStudents(StartData d) {
		return d.students.stream().sorted(Comparator.comparingInt(Student::getFirstCourseCapacity)).collect(Collectors.toList());
	}

	public static List<Swap> getAllSwaps(Student s, Map<Student, Course> assignments) {
		List<Swap> result = new ArrayList<>();
		List<Student> otherStudents = assignments.keySet().stream().filter(os -> !os.name.equals(s.name)).collect(Collectors.toList());
		for (Student os: otherStudents) {
			result.add(new Swap(s, os, assignments.get(s), assignments.get(os)));
		}
		return result;
	}

	public static Swap getBestSwap(List<Swap> swaps) {
		Swap bestSwap = swaps.get(0);
		for (Swap swap : swaps) {
			if (getScore(swap)>getScore(bestSwap)) {
				bestSwap = swap;
			}
		}
		return bestSwap;
	}

	public static Student getWorstAssignedStudent(Map<Student, Course> assignments) {
		if (assignments.size()==0) {
			return null;
		}
		Student worstAssignedStudent = assignments.keySet().stream().collect(Collectors.toList()).get(0);
		for (Student s : assignments.keySet()) {
			if (s.getRating(assignments)<worstAssignedStudent.getRating(assignments)) {
				worstAssignedStudent = s;
			}
		}
		return worstAssignedStudent;
	}

	public static Course getBestCourse(StartData d, Map<Student, Course> assignments, Student s) {
		if (courseAvailable(s.courses.get(0), assignments)) {
			return s.courses.get(0);
		}
		else if (courseAvailable(s.courses.get(1), assignments)) {
			return s.courses.get(1);
		}
		else if (courseAvailable(s.courses.get(2), assignments)) {
			return s.courses.get(2);
		}
		else {
			return getAvailableCourse(d, assignments);
		}
		
	}

	public static double getScore(Swap swap) {
		int beforeSwap = swap.studentA.getRatingForCourse(swap.courseA)+swap.studentB.getRatingForCourse(swap.courseB);
		int afterSwap = swap.studentA.getRatingForCourse(swap.courseB)+swap.studentB.getRatingForCourse(swap.courseA);
		return afterSwap-beforeSwap;
	}
	
	public static boolean courseAvailable(Course course, Map<Student, Course> assignments) {
		return (course.capacity-assignments.values().stream().filter(c -> c.name.equals(course.name)).collect(Collectors.toList()).size() >0);
	}
	
	public static Course getAvailableCourse(StartData d, Map<Student, Course> assignments) {
		for (Course c : d.courses) {
			if (courseAvailable(c, assignments)) {
				return c;
			}
		}
		return null;
	}

}
