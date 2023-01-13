import java.util.ArrayList;
import java.util.HashMap;

public class BestTotalScore implements Strategy {
    // selecteaza studentul care are punctajul total maxim
    @Override
    public Student bestScore(HashMap<Student, Grade> gradeHashMap, ArrayList<Student> students) {
        Double gradeMax = 0.0;
        Student bestStudent = null;

        for (Student student : students)
            if (gradeHashMap.get(student) != null && gradeHashMap.get(student).getTotal() >= gradeMax) {
                gradeMax = gradeHashMap.get(student).getTotal();
                bestStudent = student;
            }

        return bestStudent;
    }
}
