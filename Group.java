import java.util.ArrayList;
import java.util.Comparator;

public class Group {
    private Assistant assistant;  // asistentul grupei
    private final String ID; // numele grupei
    private Comparator<Student> comp;
    private final ArrayList<Student> students = new ArrayList<>(); // lista studentilor grupei

    public Group(String ID, Assistant assistant, Comparator<Student> comp) {
        this.ID = ID;
        this.assistant = assistant;
        this.comp = comp;
    }

    public Group(String ID, Assistant assistant) {
        this.ID = ID;
        this.assistant = assistant;
    }

    @Override
    public boolean equals(Object obj) {
        return this.ID.equals(((Group) obj).getID());
    }

    public Assistant getAssistant() {
        return assistant;
    }

    public void setAssistant(Assistant assistant) {
        this.assistant = assistant;
    }

    public String getID() {
        return ID;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    @Override
    public String toString() {
        return "Group{" +
                "assistant=" + assistant + ",\n" +
                "ID='" + ID + '\'' + ",\n" +
                "comp=" + comp + ",\n" +
                "students=" + students + ",\n" +
                '}';
    }
}
