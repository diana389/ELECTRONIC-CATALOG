import java.util.HashSet;

public class Parent extends User implements Observer {
    private final HashSet<Notification> notifications = new HashSet<>(); // lista notificarilor primite

    public Parent(String firstName, String lastName) {
        super(firstName, lastName);
    }

    // actualizeaza lista de notificari
    @Override
    public void update(Notification notification) {
        notifications.add(notification);
    }

    public HashSet<Notification> getNotifications() {
        return notifications;
    }
}
