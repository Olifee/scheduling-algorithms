package vuv;

import java.util.*;
import java.util.stream.*;

public class Student {
	public final String name;
	public final List<Course> courses;

	public Student(String name, List<Course> courses) {
		this.name = name;
		this.courses = new ArrayList<>(courses);
	}

	@Override
	public String toString() {
		return "Student[" + name + ", " + courses + "]";
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Student)) {
			return false;
		}

		return Objects.equals(name, ((Student) other).name) && Objects.equals(courses, ((Student) other).courses);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, courses);
	}

	public int getFirstCourseCapacity() {
		return this.courses.get(0).capacity;
	}

	public int getRating(Map<Student, Course> assignments) {
		if (assignments.containsKey(this)) {
			return getRatingForCourse(assignments.get(this));
		}
		return 0;
	}

	public int getRatingForCourse(Course c) {
		if (c.name.equals(this.courses.get(0))) {
			return 50;
		}
		if (c.name.equals(this.courses.get(1))) {
			return 45;
		}
		if (c.name.equals(this.courses.get(2))) {
			return 30;
		}
		return 0;
	}

}
