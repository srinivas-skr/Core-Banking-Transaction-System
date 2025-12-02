import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<String, User> users;

    public Bank() {
        users = new HashMap<>();
        // Pre-load some users
        users.put("user1", new User("user1", "1234", 1000.0));
        users.put("user2", new User("user2", "2345", 2000.0));
    }

    public User getUser(String userId) {
        return users.get(userId);
    }

    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }
}
