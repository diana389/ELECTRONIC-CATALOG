import java.util.*;

public abstract class Course {
    private final String name; // un nume
    private final Teacher teacher; //  un profesor titular
    private HashSet<Assistant> assistants; //  o multime de asistenti
    private ArrayList<Grade> grades; // o colectie ordonată cu obiecte de tipul Grade
    private HashMap<String, Group> groupHashMap; // un dictionar ce contine grupele
    private final int creditPoints; // numărul de puncte credit

    // Seteaza asistentul în grupa cu ID-ul indicat
    // Daca nu exista deja, adauga asistentul si în multimea asistentilor
    public void addAssistant(String ID, Assistant assistant) {
        groupHashMap.get(ID).setAssistant(assistant);

        if (assistants == null)
            assistants = new HashSet<>();

        assistants.add(assistant);
    }

    // Adauga studentul în grupa cu ID-ul indicat
    public void addStudent(String ID, Student student) {
        groupHashMap.get(ID).getStudents().add(student);
    }

    // Adauga grupa
    public void addGroup(Group group) {
        if (groupHashMap == null)
            groupHashMap = new HashMap<>();

        groupHashMap.put(group.getID(), group);
    }

    // Instantiaza o grupa si o adauga
    public void addGroup(String ID, Assistant assistant) {
        if (groupHashMap == null)
            groupHashMap = new HashMap<>();

        Group group = new Group(ID, assistant);
        groupHashMap.put(ID, group);
    }

    // Instantiaza o grupa si o adauga
    public void addGroup(String ID, Assistant assistant, Comparator<Student> comp) {
        if (groupHashMap == null)
            groupHashMap = new HashMap<>();

        Group group = new Group(ID, assistant, comp);
        groupHashMap.put(ID, group);
    }

    // Returneaza nota unui student sau null
    public Grade getGrade(Student student) {
        if (grades == null)
            return null;

        for (Grade grade : grades)
            if (grade.getStudent() == student)
                return grade;

        return null;
    }

    // Adauga o nota
    public void addGrade(Grade grade) {
        if (grades == null)
            grades = new ArrayList<>();

        grades.add(grade);
    }

    // Returneaza o lista cu toti studentii
    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> students = new ArrayList<>();

        if (groupHashMap == null)
            groupHashMap = new HashMap<>();

        for (Group group : groupHashMap.values())
            students.addAll(group.getStudents());

        return students;
    }

    // Returneaza un dictionar cu situatia studentilor
    public HashMap<Student, Grade> getAllStudentGrades() {
        HashMap<Student, Grade> gradeHashMap = new HashMap<>();

        if (grades == null)
            grades = new ArrayList<>();

        for (Grade grade : grades)
            gradeHashMap.put(grade.getStudent(), grade);

        return gradeHashMap;
    }

    // Metoda ce o sa fie implementata pentru a determina studentii promovati
    public abstract ArrayList<Student> getGraduatedStudents();

    public Course(CourseBuilder builder) {
        this.name = builder.name;
        this.teacher = builder.teacher;
        this.assistants = builder.assistants;
        this.grades = builder.grades;
        this.groupHashMap = builder.groupHashMap;
        this.creditPoints = builder.creditPoints;
    }

    public String getName() {
        return name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public HashSet<Assistant> getAssistants() {
        return assistants;
    }

    public ArrayList<Grade> getGrades() {
        return grades;
    }

    public HashMap<String, Group> getGroupHashMap() {
        return groupHashMap;
    }

    @Override
    public String toString() {
        return "Course{" + '\n' +
                "name='" + name + ", \n" +
                "teacher=" + teacher + ", \n" +
                "assistants=" + assistants + ", \n" +
                "grades=" + grades + ", \n" +
                "groupHashMap=" + groupHashMap + ", \n" +
                "creditPoints=" + creditPoints + ", \n" +
                "strategy=" + strategy +
                "}\n";
    }

    public abstract static class CourseBuilder {
        private final String name; // un nume
        private Teacher teacher; //  un profesor titular
        private HashSet<Assistant> assistants; //  o multime de asistenti
        private ArrayList<Grade> grades; // o colectie ordonată cu obiecte de tipul Grade
        private HashMap<String, Group> groupHashMap; // un dictionar ce contine grupele
        private int creditPoints; // numărul de puncte credit

        public CourseBuilder(String name) {
            this.name = name;
        }

        public CourseBuilder teacher(Teacher teacher) {
            this.teacher = teacher;
            return this;
        }

        public CourseBuilder assistants(HashSet<Assistant> assistants) {
            this.assistants = assistants;
            return this;
        }

        public CourseBuilder grades(ArrayList<Grade> grades) {
            this.grades = grades;
            return this;
        }

        public CourseBuilder groupHashMap(HashMap<String, Group> groupHashMap) {
            this.groupHashMap = groupHashMap;
            return this;
        }

        public CourseBuilder creditPoints(int creditPoints) {
            this.creditPoints = creditPoints;
            return this;
        }

        public abstract Course build() throws CloneNotSupportedException;
    }

    private Strategy strategy;

    public void setCourseStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    // Va returna cel mai bun student tinând cont de strategia aleasa de profesor pentru curs
    public Student getBestStudent() {
        HashMap<Student, Grade> gradeHashMap = getAllStudentGrades();
        ArrayList<Student> students = getAllStudents();
        return strategy.bestScore(gradeHashMap, students);
    }

    // creeaza un nou snapshot
    public void makeBackup() throws CloneNotSupportedException {
        snapshot = new Snapshot();
    }

    // revine la informatiile salvate
    public void undo() {
        if (snapshot != null) {
            grades.removeAll(grades);
            grades.addAll(snapshot.getGradeHashMap().values());
        }
    }

    private Snapshot snapshot = null;

    private class Snapshot {
        private final HashMap<Student, Grade> gradeHashMap;

        // salveaza in snaphot notele curente
        public Snapshot() throws CloneNotSupportedException {
            gradeHashMap = new HashMap<>();

            for (Student student : getAllStudentGrades().keySet())
                gradeHashMap.put(student, (Grade) getAllStudentGrades().get(student).clone());
        }

        public HashMap<Student, Grade> getGradeHashMap() {
            return gradeHashMap;
        }
    }


}
