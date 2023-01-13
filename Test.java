import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Test extends JFrame implements ActionListener {
    private static final File file = new File("input.txt");
    static Scanner scanner;

    static {
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // citeste un grup din fisier
    public static void readGroup(Course course) throws CloneNotSupportedException {
        String groupID = scanner.next();
        Assistant assistant = new Assistant(scanner.next(), scanner.next());

        course.addGroup(groupID, assistant);
        course.addAssistant(groupID, assistant);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.equals("***")) {
                readCourse();
                return;
            }

            if (line.equals("*")) {
                readGroup(course);
                return;
            }

            StringTokenizer nextPart = new StringTokenizer(line, ",");

            if (nextPart.hasMoreTokens()) {
                StringTokenizer next = new StringTokenizer(nextPart.nextToken(), " ");

                if (next.hasMoreTokens()) {
                    String firstName = next.nextToken();
                    String lastName = next.nextToken();

                    Student student = null;

                    boolean exist = false;
                    for (Course c : Catalog.getInstance().courses) {
                        for (Student s : c.getAllStudents())
                            if (s.getFirstName().equals(firstName) && s.getLastName().equals(lastName)) {
                                student = s;
                                exist = true;
                                break;
                            }
                    }

                    if (student == null)
                        student = new Student(firstName, lastName);

                    if (nextPart.hasMoreTokens()) {
                        next = new StringTokenizer(nextPart.nextToken(), " ");

                        if (next.hasMoreTokens()) {
                            firstName = next.nextToken();

                            if (!exist)
                                student.setMother(new Parent(firstName, next.nextToken()));
                        }
                    }

                    if (nextPart.hasMoreTokens()) {
                        next = new StringTokenizer(nextPart.nextToken(), " ");

                        if (next.hasMoreTokens()) {
                            firstName = next.nextToken();

                            if (!exist)
                                student.setFather(new Parent(firstName, next.nextToken()));
                        }
                    }
                    course.addStudent(groupID, student);

                    if (nextPart.hasMoreTokens()) {
                        Catalog.getInstance().scoreVisitor.addTupleAssistant(assistant, student, course.getName(), Double.parseDouble(nextPart.nextToken()));
                        Catalog.getInstance().scoreVisitor.addTupleTeacher(course.getTeacher(), student, course.getName(), Double.parseDouble(nextPart.nextToken()));
                    }
                }
            }
        }
    }

    // citeste un curs din fisier
    public static void readCourse() throws CloneNotSupportedException {
        while (scanner.hasNext()) {
            String courseName = scanner.next();
            int creditPoints = scanner.nextInt();
            Teacher teacher = new Teacher(scanner.next(), scanner.next());

            for(Course course : Catalog.getInstance().courses)
                if(course.getTeacher().getFirstName().equals(teacher.getFirstName()) && course.getTeacher().getLastName().equals(teacher.getLastName()))
                    teacher = course.getTeacher();

            Course course = new FullCourse.FullCourseBuilder(courseName)
                    .teacher(teacher)
                    .creditPoints(creditPoints)
                    .build();

            Catalog.getInstance().addCourse(course);

            while (scanner.hasNextLine())
                readGroup(course);

            course.setCourseStrategy(new BestTotalScore());
        }
    }

    // elementele interfetei
    JTextField userType = new JTextField("Student");
    JPanel userPanel = new JPanel();
    ArrayList<JButton> buttonsCourses = new ArrayList<>();
    JButton buttonMenu = new JButton("CLICK");
    JButton buttonBack = new JButton("Back");
    JButton buttonValidate = new JButton("Validate grades");
    JButton buttonUndo = new JButton("Undo");
    JButton buttonMakeBackup = new JButton("Make Backup");
    String info;
    JTextField nameField = new JTextField("Diana Stefan");
    JTextField name = new JTextField();
    JPanel namePanel = new JPanel();
    User user;
    JTextArea textArea = new JTextArea("Info");
    JTextArea textAreaCourses = new JTextArea();
    JScrollPane scrollPaneInfo = new JScrollPane(textArea);
    JPanel panel = new JPanel();
    JScrollPane scrollPane = new JScrollPane(panel);
    Font font = new Font ("Apple Chancery", Font.PLAIN, 13);
    int ok = 1;

    public Test() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setSize(600, 400);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(new Color(25, 50, 60));

        // menu components

        add(buttonMenu);
        buttonMenu.setVisible(false);
        buttonMenu.setBounds(225, 225, 150, 50);
        buttonMenu.addActionListener(this);

        add(nameField);
        nameField.setVisible(false);
        nameField.setBackground(new Color(243, 247, 240));
        nameField.setFont(font);

        add(namePanel);
        namePanel.setVisible(false);
        namePanel.setBounds(150, 150, 300, 50);
        namePanel.setBackground(new Color(123, 136, 111));

        add(userType);
        userType.setVisible(false);
        userType.setBackground(new Color(243, 247, 240));userType.setFont(font);

        add(userPanel);
        userPanel.setVisible(false);
        userPanel.setBounds(150, 75, 300, 50);
        userPanel.setBackground(new Color(123, 136, 111));


        // student page components

        add(scrollPane);
        scrollPane.setVisible(false);
        scrollPane.setBounds(30, 125, 130, 100);

        add(scrollPaneInfo);
        scrollPaneInfo.setVisible(false);
        scrollPaneInfo.setBounds(200, 30, 360, 230);

        // teacher / assistent page components

        add(scrollPane);
        scrollPane.setVisible(false);
        scrollPane.setBounds(30, 125, 135, 135);

        add(buttonValidate);
        buttonValidate.setVisible(false);
        buttonValidate.addActionListener(this);
        buttonValidate.setBounds(305, 290, 150, 50);

        add(buttonUndo);
        buttonUndo.setVisible(false);
        buttonUndo.addActionListener(this);
        buttonUndo.setBounds(200, 290, 70, 50);

        add(buttonMakeBackup);
        buttonMakeBackup.setVisible(false);
        buttonMakeBackup.addActionListener(this);
        buttonMakeBackup.setBounds(30, 290, 135, 50);

        // student / parent / teacher / assistent page components

        add(name);
        name.setVisible(false);
        name.setEditable(false);
        name.setBounds(30, 30, 135, 50);
        name.setBackground(new Color(25, 50, 60));
        name.setForeground(new Color(243, 247, 240));
        name.setFont(font);

        add(buttonBack);
        buttonBack.setVisible(false);
        buttonBack.setBounds(490, 290, 70, 50);
        buttonBack.addActionListener(this);

        textArea.setEditable(false);
        textArea.setBackground(new Color(123, 136, 111));
        textArea.setForeground(new Color(243, 247, 240));
        textArea.setFont(font);
        textAreaCourses.setEditable(false);
        textAreaCourses.setBackground(new Color(123, 136, 111));
        textAreaCourses.setForeground(new Color(243, 247, 240));
        textAreaCourses.setFont(font);

        mainMenu();
    }

    public void mainMenu() {
        ok = 1;

        for (JButton button : buttonsCourses) {
            button.setVisible(false);
            button.removeActionListener(this);
        }

        buttonsCourses.removeAll(buttonsCourses);
        buttonBack.setVisible(false);
        buttonValidate.setVisible(false);
        buttonUndo.setVisible(false);
        buttonMakeBackup.setVisible(false);
        scrollPaneInfo.setVisible(false);
        scrollPane.setVisible(false);
        name.setVisible(false);
        textArea.setText("Info");
        textAreaCourses.setVisible(false);

        userType.setVisible(true);
        userType.setEditable(true);

        JLabel userLabel = new JLabel("  Enter your role:     ");
        userLabel.setFont(font);
        userLabel.setForeground(new Color(243, 247, 240));

        userPanel.setLayout(new BorderLayout());
        userPanel.add(userLabel, BorderLayout.WEST);
        userPanel.add(userType, BorderLayout.CENTER);
        userPanel.setVisible(true);

        nameField.setVisible(true);
        nameField.setEditable(true);

        JLabel nameLabel = new JLabel("  Enter your name:  ");
        nameLabel.setFont(font);
        nameLabel.setForeground(new Color(243, 247, 240));

        namePanel.setLayout(new BorderLayout());
        namePanel.add(nameLabel, BorderLayout.WEST);
        namePanel.add(nameField, BorderLayout.CENTER);
        namePanel.setVisible(true);

        buttonMenu.setVisible(true);
    }

    public void student() {
        userType.setVisible(false);
        buttonMenu.setVisible(false);
        namePanel.setVisible(false);
        userPanel.setVisible(false);

        name.setText(" " + nameField.getText());
        name.setVisible(true);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (Course course : Catalog.getInstance().courses)
            for (Student student : course.getAllStudents())
                if (student.getFirstName().equals(user.getFirstName()) && student.getLastName().equals(user.getLastName()))
                    user = student;

        for (Course course : Catalog.getInstance().courses) {
            for (Student student : course.getAllStudents()) {
                if (student == user) {
                    JButton button = new JButton(course.getName());
                    button.addActionListener(this);
                    panel.add(button);
                    button.setVisible(true);
                    buttonsCourses.add(button);
                    break;
                }
            }
        }

        scrollPaneInfo.setVisible(true);
        scrollPaneInfo.setBounds(200, 30, 360, 230);
        scrollPane.setVisible(true);
        textArea.setBounds(0, 0, 400, 200);
        buttonBack.setVisible(true);
    }

    public void parent() {
        buttonMenu.setVisible(false);
        namePanel.setVisible(false);
        userPanel.setVisible(false);

        name.setText(" " + nameField.getText());
        name.setVisible(true);
        buttonBack.setVisible(true);

        for (Observer observer : Catalog.getInstance().observers)
            if (user.getFirstName().equals(((Parent) observer).getFirstName()) && user.getLastName().equals(((Parent) observer).getLastName()))
                user = (Parent) observer;

        if (((Parent) user).getNotifications().size() == 0)
            textArea.setText("No notifications");
        else {
            StringBuilder text = new StringBuilder();
            for (Notification notification : ((Parent) user).getNotifications())
                text.append("Notification: ").append(notification.grade.getCourse()).append("\n").append(notification.grade.toString()).append("\n");

            textArea.setText(text.toString());
        }

        scrollPaneInfo.setBounds(30, 100, 530, 150);
        scrollPaneInfo.setVisible(true);
        scrollPaneInfo.createVerticalScrollBar();
    }

    public void teacher() {
        buttonMenu.setVisible(false);
        namePanel.setVisible(false);
        userPanel.setVisible(false);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        name.setText(" " + nameField.getText());
        name.setVisible(true);
        buttonBack.setVisible(true);
        buttonValidate.setVisible(true);
        buttonUndo.setVisible(true);
        buttonMakeBackup.setVisible(true);
        textAreaCourses.setVisible(true);

        for (Course course : Catalog.getInstance().courses)
            if (course.getTeacher().getFirstName().equals(user.getFirstName()) && course.getTeacher().getLastName().equals(user.getLastName()))
                user = course.getTeacher();

        StringBuilder courses = new StringBuilder("COURSES LIST: \n");

        for (Course course : Catalog.getInstance().courses)
            if (course.getTeacher() == user) {
                courses.append(course.getName()).append("\n");

                if (course.getBestStudent() == null)
                    courses.append("Best student: - \n");
                else courses.append("Best student: ").append(course.getBestStudent().toString()).append("\n");

                if (course.getGraduatedStudents().size() == 0)
                    courses.append("Graduated students: - \n");
                else courses.append("Graduated students: ").append(course.getGraduatedStudents()).append("\n");
            }

        textAreaCourses.setText(courses.toString());
        panel.add(textAreaCourses);
        panel.setVisible(true);

        scrollPane.setVisible(true);
        scrollPaneInfo.setVisible(true);

        info = "Grades to validate: \n";
        info += Catalog.getInstance().scoreVisitor.toString((Teacher) user) + "\n";
        info += "Grades in catalog: \n";

        for (Course course : Catalog.getInstance().courses)
            if (course.getTeacher() == user && course.getGrades() != null)
                for (Grade grade : course.getGrades())
                    if (grade.getExamScore() != null)
                        info += grade.getExamScore() + "\n";

        textArea.setBounds(0, 0, 400, 200);
        textArea.setText(info);

        scrollPaneInfo.setBounds(200, 30, 360, 230);
    }

    public void assistant() {
        buttonMenu.setVisible(false);
        namePanel.setVisible(false);
        userPanel.setVisible(false);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        name.setText(" " + nameField.getText());
        name.setVisible(true);
        buttonBack.setVisible(true);
        buttonValidate.setVisible(true);
        buttonUndo.setVisible(true);
        buttonMakeBackup.setVisible(true);
        textAreaCourses.setVisible(true);

        StringBuilder courses = new StringBuilder("COURSES LIST: \n");

        for (Course course : Catalog.getInstance().courses)
            for (Assistant assistant : course.getAssistants())
                if (assistant.getFirstName().equals(user.getFirstName()) && assistant.getLastName().equals(user.getLastName())) {
                    user = assistant;
                    courses.append(course.getName()).append("\n");

                    if (course.getBestStudent() == null)
                        courses.append("Best student: - \n");
                    else courses.append("Best student: ").append(course.getBestStudent().toString()).append("\n");

                    if (course.getGraduatedStudents().size() == 0)
                        courses.append("Graduated students: - \n");
                    else courses.append("Graduated students: ").append(course.getGraduatedStudents()).append("\n");
                }

        textAreaCourses.setText(courses.toString());
        panel.add(textAreaCourses);
        panel.setVisible(true);

        scrollPane.setVisible(true);
        scrollPaneInfo.setVisible(true);

        info = "Grades to validate: \n";
        info += Catalog.getInstance().scoreVisitor.toString((Assistant) user) + "\n";
        info += "Grades in catalog: \n";

        for (Course course : Catalog.getInstance().courses)
            if (course.getAssistants().contains((Assistant) user) && course.getGrades() != null)
                for (Grade grade : course.getGrades())
                    if (grade.getPartialScore() != null)
                        info += grade.getPartialScore() + "\n";

        textArea.setText(info);

        scrollPaneInfo.setBounds(200, 30, 360, 230);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonMenu) {
            if (ok == 1)
                user = UserFactory.getUser(userType.getText(), nameField.getText());

            if (userType.getText().equals("Student") && ok == 1) {
                student();
                ok = 0;
            }

            if (userType.getText().equals("Parent"))
                parent();

            if (userType.getText().equals("Teacher") && ok == 1) {
                teacher();
                ok = 0;
            }

            if (userType.getText().equals("Assistant") && ok == 1) {
                assistant();
                ok = 0;
            }
        }

        for (JButton button : buttonsCourses)
            if (e.getSource() == button) {
                String selCourse = button.getText();

                for (Course course : Catalog.getInstance().courses)
                    if (course.getName().equals(selCourse)) {
                        info = "Course: " + course.getName() + "\n";
                        info += "Teacher: " + course.getTeacher().getFirstName() + " " + course.getTeacher().getLastName() + "\n";
                        info += "Assistents: " + course.getAssistants().toString() + "\n";

                        for (Group group : course.getGroupHashMap().values())
                            for (Student student : group.getStudents())
                                if (student.equals(user)) {
                                    info += "Your assistent: " + group.getAssistant().toString() + "\n";
                                    break;
                                }

                        info += "Your grades: " + "\n";

                        if (course.getGrade((Student) user) != null) {

                            if (course.getGrade((Student) user).getPartialScore() == null)
                                info += "Partial Score: -\n";
                            else info += "Partial Score: " + course.getGrade((Student) user).getPartialScore() + "\n";

                            if (course.getGrade((Student) user).getExamScore() == null)
                                info += "Exam Score: -\n";
                            else info += "Exam Score: " + course.getGrade((Student) user).getExamScore() + "\n";

                            if (course.getGrade((Student) user).getTotal() == null)
                                info += "Total Score: -\n";
                            else info += "Total Score: " + course.getGrade((Student) user).getTotal() + "\n";
                        } else info += "Partial Score: -\nExam Score: -\nTotal Score: -\n";

                        textArea.setText(info);
                        break;
                    }
            }

        if (e.getSource() == buttonBack)
            mainMenu();

        if (e.getSource() == buttonValidate) {
            String courses = "COURSES LIST: \n";

            if (user.getClass() == Teacher.class) {
                Catalog.getInstance().scoreVisitor.visit((Teacher) user);

                info = "Grades to validate: \n";
                info += Catalog.getInstance().scoreVisitor.toString((Teacher) user) + "\n";
                info += "Grades in catalog: \n";

                for (Course course : Catalog.getInstance().courses)
                    if (course.getTeacher() == user && course.getGrades() != null)
                        for (Grade grade : course.getGrades())
                            info += grade.getExamScore() + "\n";

                for (Course course : Catalog.getInstance().courses)
                    if (course.getTeacher() == user) {
                        courses += course.getName() + "\n";

                        if (course.getBestStudent() == null)
                            courses += "Best student: - \n";
                        else courses += "Best student: " + course.getBestStudent().toString() + "\n";

                        if (course.getGraduatedStudents().size() == 0)
                            courses += "Graduated students: - \n";
                        else courses += "Graduated students: " + course.getGraduatedStudents() + "\n";
                    }

            } else {
                Catalog.getInstance().scoreVisitor.visit((Assistant) user);

                info = "Grades to validate: \n";
                info += Catalog.getInstance().scoreVisitor.toString((Assistant) user) + "\n";
                info += "Grades in catalog: \n";

                for (Course course : Catalog.getInstance().courses)
                    if (course.getAssistants().contains((Assistant) user) && course.getGrades() != null)
                        for (Grade grade : course.getGrades())
                            info += grade.getPartialScore() + "\n";

                for (Course course : Catalog.getInstance().courses)
                    for (Assistant assistant : course.getAssistants())
                        if (assistant == user) {
                            courses += course.getName() + "\n";

                            if (course.getBestStudent() == null)
                                courses += "Best student: - \n";
                            else courses += "Best student: " + course.getBestStudent().toString() + "\n";

                            if (course.getGraduatedStudents().size() == 0)
                                courses += "Graduated students: - \n";
                            else courses += "Graduated students: " + course.getGraduatedStudents() + "\n";
                        }
            }

            textAreaCourses.setText(courses);
            textArea.setText(info);
        }

        if (e.getSource() == buttonUndo) {
            for (Course course : Catalog.getInstance().courses)
                course.undo();
            String courses = "COURSES LIST: \n";

            if (user.getClass() == Teacher.class) {
                info = "Grades to validate: \n";
                info += Catalog.getInstance().scoreVisitor.toString((Teacher) user) + "\n";
                info += "Grades in catalog: \n";

                for (Course course : Catalog.getInstance().courses)
                    if (course.getTeacher() == user && course.getGrades() != null)
                        for (Grade grade : course.getGrades())
                            info += grade.getExamScore() + "\n";

                for (Course course : Catalog.getInstance().courses)
                    if (course.getTeacher() == user) {
                        courses += course.getName() + "\n";

                        if (course.getBestStudent() == null)
                            courses += "Best student: - \n";
                        else courses += "Best student: " + course.getBestStudent().toString() + "\n";

                        if (course.getGraduatedStudents().size() == 0)
                            courses += "Graduated students: - \n";
                        else courses += "Graduated students: " + course.getGraduatedStudents() + "\n";
                    }

            } else {
                info = "Grades to validate: \n";
                info += Catalog.getInstance().scoreVisitor.toString((Assistant) user) + "\n";
                info += "Grades in catalog: \n";

                for (Course course : Catalog.getInstance().courses)
                    if (course.getAssistants().contains((Assistant) user) && course.getGrades() != null)
                        for (Grade grade : course.getGrades())
                            info += grade.getPartialScore() + "\n";

                for (Course course : Catalog.getInstance().courses)
                    for (Assistant assistant : course.getAssistants())
                        if (assistant == user) {
                            courses += course.getName() + "\n";

                            if (course.getBestStudent() == null)
                                courses += "Best student: - \n";
                            else courses += "Best student: " + course.getBestStudent().toString() + "\n";

                            if (course.getGraduatedStudents().size() == 0)
                                courses += "Graduated students: - \n";
                            else courses += "Graduated students: " + course.getGraduatedStudents() + "\n";
                        }
            }

            textAreaCourses.setText(courses);
            textArea.setText(info);
        }

        if (e.getSource() == buttonMakeBackup) {
            try {
                Catalog.getInstance().courses.get(0).makeBackup();
            } catch (CloneNotSupportedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        readCourse();
        scanner.close();
        new Test();
    }
}