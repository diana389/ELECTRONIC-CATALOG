import java.util.ArrayList;
import java.util.HashMap;

public interface Strategy {
    Student bestScore(HashMap<Student, Grade> gradeHashMap, ArrayList<Student> students);
}
