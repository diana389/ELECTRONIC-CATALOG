public class UserFactory {
    public static User getUser(String userType, String userName) {
        String[] arrOfStr = userName.split(" ", 2);
        String userFirstName = arrOfStr[0];
        String userLastName = arrOfStr[1];

        // creeaza un parinte
        if (userType.equals("Parent"))
            return new Parent(userFirstName, userLastName);

        // creeaza un student
        if (userType.equals("Student"))
            return new Student(userFirstName, userLastName);

        // creeaza un asistent
        if (userType.equals("Assistant"))
            return new Assistant(userFirstName, userLastName);

        // creeaza un profesor
        if (userType.equals("Teacher"))
            return new Teacher(userFirstName, userLastName);

        return null;
    }
}
