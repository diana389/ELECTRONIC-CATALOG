public class Student extends User {
    private Parent mother;
    private Parent father;

    public Student(String firstName, String lastName) {
        super(firstName, lastName);
    }

    // seteaza mama studentului si o aboneaza la catalog
    public void setMother(Parent mother) {
        this.mother = mother;
        Catalog.getInstance().addObserver(mother);
    }

    // seteaza tatal studentului si il aboneaza la catalog
    public void setFather(Parent father) {
        this.father = father;
        Catalog.getInstance().addObserver(father);
    }

    public Parent getMother() {
        return mother;
    }

    public Parent getFather() {
        return father;
    }

    @Override
    public String toString() {
        return super.getFirstName() + " " + super.getLastName();
    }
}
