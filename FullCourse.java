import java.util.ArrayList;
import java.util.HashMap;

public class FullCourse extends Course {
    public FullCourse(FullCourseBuilder fullCourseBuilder) {
        super(fullCourseBuilder);
    }

    // returneaza lista cu studentii promovati (conditia: nota partiala >= 5 si nota in examen >=2)
    @Override
    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> graduatedStudents = new ArrayList<>();
        HashMap<Student, Grade> gradeHashMap = getAllStudentGrades();
        ArrayList<Student> students = getAllStudents();

        for (Student student : students)
            if (gradeHashMap.get(student) != null && gradeHashMap.get(student).getPartialScore() != null && gradeHashMap.get(student).getExamScore() != null)
                if (gradeHashMap.get(student).getPartialScore() >= 3 && gradeHashMap.get(student).getExamScore() >= 2)
                    graduatedStudents.add(student);

        return graduatedStudents;
    }

    public static class FullCourseBuilder extends CourseBuilder {

        public FullCourseBuilder(String name) {
            super(name);
        }

        @Override
        public Course build() {
            return new FullCourse(this);
        }
    }
}
