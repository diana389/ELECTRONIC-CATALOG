import java.util.ArrayList;
import java.util.HashMap;

public class BestExamScore implements Strategy {
    // selecteaza studentul care are cel mai mare punctaj în examen
    @Override
    public Student bestScore(HashMap<Student, Grade> gradeHashMap, ArrayList<Student> students) {

        Double gradeMax = 0.0;
        Student bestStudent = null;

        for (Student student : students)
            if (gradeHashMap.get(student) != null && gradeHashMap.get(student).getExamScore() >= gradeMax) {
                gradeMax = gradeHashMap.get(student).getExamScore();
                bestStudent = student;
            }

        return bestStudent;
    }
}
