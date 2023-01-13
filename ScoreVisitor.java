import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ScoreVisitor implements Visitor {
    HashMap<Teacher, List<Tuple>> teacherHashMap = new HashMap<>(); // dictionar cu notele nevalidate ale profesorilor
    HashMap<Assistant, List<Tuple>> assistantHashMap = new HashMap<>(); // dictionar cu notele nevalidate ale profesorilor

    // adauga o nota nevalidata in dictionar
    public void addTupleTeacher(Teacher teacher, Student student, String course, Double examScore) {
        Tuple tuple = new Tuple<>(student, course, examScore);

        teacherHashMap.computeIfAbsent(teacher, k -> new ArrayList<>());

        teacherHashMap.get(teacher).add(tuple);
    }

    // adauga o nota nevalidata in dictionar
    public void addTupleAssistant(Assistant assistant, Student student, String course, Double partialScore) {
        Tuple tuple = new Tuple<>(student, course, partialScore);

        assistantHashMap.computeIfAbsent(assistant, k -> new ArrayList<>());

        assistantHashMap.get(assistant).add(tuple);
    }

    // muta notele din dictionar in catalog
    @Override
    public void visit(Assistant assistant) {
        List<Tuple> tuples = assistantHashMap.get(assistant);
        Grade grade = null;

        if (tuples == null)
            return;

        for (Tuple tuple : tuples) {
            for (Course course : Catalog.getInstance().courses)
                if (course.getName().equals(tuple.nameCourse)) {
                    boolean found = false;

                    for (Student student : course.getAllStudentGrades().keySet())
                        if (student == tuple.student) {
                            grade = course.getAllStudentGrades().get(student);
                            course.getAllStudentGrades().get(student).setPartialScore((Double) tuple.grade);
                            found = true;
                            break;
                        }

                    if (!found) {
                        grade = new Grade((Double) tuple.grade, null, (Student) tuple.student, (String) tuple.nameCourse);
                        course.addGrade(grade);
                    }

                    break;
                }

            assert grade != null;
            Catalog.getInstance().notifyObservers(grade);
        }

        assistantHashMap.remove(assistant);
    }

    // muta notele din dictionar in catalog
    @Override
    public void visit(Teacher teacher) {
        List<Tuple> tuples = teacherHashMap.get(teacher);
        Grade grade = null;

        if (tuples == null)
            return;

        for (Tuple tuple : tuples) {
            for (Course course : Catalog.getInstance().courses)
                if (course.getName().equals(tuple.nameCourse)) {
                    boolean found = false;

                    for (Student student : course.getAllStudentGrades().keySet())
                        if (student == tuple.student) {
                            grade = course.getAllStudentGrades().get(student);
                            course.getAllStudentGrades().get(student).setExamScore((Double) tuple.grade);
                            found = true;
                            break;
                        }

                    if (!found) {
                        grade = new Grade(null, (Double) tuple.grade, (Student) tuple.student, (String) tuple.nameCourse);
                        course.addGrade(grade);
                    }

                    break;
                }

            assert grade != null;
            Catalog.getInstance().notifyObservers(grade);
        }

        teacherHashMap.remove(teacher);
    }

    public String toString(Assistant assistant) {
        StringBuilder output = new StringBuilder();

        if (assistantHashMap.get(assistant) != null)
            for (Tuple tuple : assistantHashMap.get(assistant))
                output.append(tuple).append("\n");

        return output.toString();
    }

    public String toString(Teacher teacher) {
        StringBuilder output = new StringBuilder();

        if (teacherHashMap.get(teacher) != null)
            for (Tuple tuple : teacherHashMap.get(teacher))
                output.append(tuple).append("\n");

        return output.toString();
    }

    @Override
    public String toString() {
        return "ScoreVisitor{" +
                "teacherHashMap=" + teacherHashMap +
                ", assistantHashMap=" + assistantHashMap +
                '}';
    }

    private static class Tuple<S, C, G> {
        S student; // studentul
        C nameCourse; // numele cursului
        G grade; // nota

        public Tuple(S student, C nameCourse, G grade) {
            this.student = student;
            this.nameCourse = nameCourse;
            this.grade = grade;
        }

        @Override
        public String toString() {
            return "Student: " + ((Student) student).getFirstName() + " " + ((Student) student).getLastName() +
                    "; Course Name: " + nameCourse +
                    "; Grade: " + grade;
        }
    }
}
