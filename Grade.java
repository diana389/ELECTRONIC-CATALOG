public class Grade implements Comparable, Cloneable {
    private Double partialScore, examScore;
    private Student student; // studentul caruia ii apartine nota
    private final String course; // numele cursului

    public Grade(Double partialScore, Double examScore, Student student, String course) {
        this.partialScore = partialScore;
        this.examScore = examScore;
        this.student = student;
        this.course = course;
    }

    // compararea se face in functie de punctajul total
    @Override
    public int compareTo(Object o) {
        return (int) (this.getTotal() - ((Grade) o).getTotal());
    }

    // calculeaza punctajul total
    public Double getTotal() {
        if (partialScore == null && examScore == null)
            return 0.0;

        if (partialScore == null)
            return examScore;

        if (examScore == null)
            return partialScore;

        return partialScore + examScore;
    }

    public Double getPartialScore() {
        return partialScore;
    }

    public void setPartialScore(Double partialScore) {
        this.partialScore = partialScore;
    }

    public Double getExamScore() {
        return examScore;
    }

    public void setExamScore(Double examScore) {
        this.examScore = examScore;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return "Partial Score: " + partialScore +
                "\nExam Score: " + examScore +
                "\nTotal Score: " + getTotal();
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
