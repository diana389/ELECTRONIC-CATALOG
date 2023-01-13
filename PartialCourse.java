import java.util.ArrayList;
import java.util.HashMap;

public class PartialCourse extends Course {
    public PartialCourse(PartialCourseBuilder partialCourseBuilder) {
        super(partialCourseBuilder);
    }

    // returneaza lista cu studentii promovati (conditia: nota totala >= 5)
    @Override
    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> graduatedStudents = new ArrayList<>();
        HashMap<Student, Grade> gradeHashMap = getAllStudentGrades();
        ArrayList<Student> students = getAllStudents();

        for (Student student : students)
            if (gradeHashMap.get(student) != null && gradeHashMap.get(student).getTotal() != null)
                if (gradeHashMap.get(student).getTotal() >= 5)
                    graduatedStudents.add(student);

        return graduatedStudents;
    }

    public static class PartialCourseBuilder extends CourseBuilder {

        public PartialCourseBuilder(String name) {
            super(name);
        }

        @Override
        public Course build() {
            return new PartialCourse(this);
        }
    }
}
