public class Assistant extends User implements Element {
    public Assistant(String firstName, String lastName) {
        super(firstName, lastName);
    }

    // valideaza notele
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
