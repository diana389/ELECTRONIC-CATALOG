import java.util.ArrayList;
import java.util.HashMap;

public class BestPartialScore implements Strategy {
    // selecteaza  studentul care are cel mai mare punctaj în timpul semestrului
    @Override
    public Student bestScore(HashMap<Student, Grade> gradeHashMap, ArrayList<Student> students) {
        Double gradeMax = 0.0;
        Student bestStudent = null;

        for (Student student : students)
            if (gradeHashMap.get(student) != null && gradeHashMap.get(student).getPartialScore() >= gradeMax) {
                gradeMax = gradeHashMap.get(student).getPartialScore();
                bestStudent = student;
            }

        return bestStudent;
    }
}
