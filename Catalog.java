import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Catalog implements Subject {
    List<Course> courses = new LinkedList<>(); // lista cursurilor
    private static Catalog obj = null; // obiectul de tip catalog
    HashSet<Observer> observers = new HashSet<>(); // lista abonatilor la catalog
    ScoreVisitor scoreVisitor = new ScoreVisitor(); // obiect ce contine notele care trebuie validate

    private Catalog() {
    }

    // metoda prin care se creaza unica instanta a clase
    public static Catalog getInstance() {
        // daca clasa nu a fost instantiata inainte, o facem acum
        if (obj == null)
            obj = new Catalog();

        return obj;
    }

    // Adauga un curs în catalog
    public void addCourse(Course course) {
        courses.add(course);
    }

    // Sterge un curs din catalog
    public void removeCourse(Course course) {
        courses.remove(course);
    }

    // aboneaza un observator la catalog
    @Override
    public void addObserver(Observer observer) {
        for (Observer o : observers)
            if (((Parent) o).getFirstName().equals(((Parent) observer).getFirstName()) && ((Parent) o).getLastName().equals(((Parent) observer).getLastName()))
                return;

        observers.add(observer);
    }

    // dezaboneaza un observator de la catalog
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    // notifica parintii de nota primita
    @Override
    public void notifyObservers(Grade grade) {
        if (grade.getStudent().getMother() != null)
            grade.getStudent().getMother().update(new Notification(grade));

        if (grade.getStudent().getFather() != null)
            grade.getStudent().getFather().update(new Notification(grade));
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("CATALOG: \n");

        for (Course course : courses)
            output.append(course).append("\n");

        return output.toString();
    }
}
