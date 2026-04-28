package project.storage.user;

import project.model.User;
import java.util.Collection;

public interface UserStorage {

    User addUser(User user);

    User updateUser(User user);

    void deleteUser(long id);

    Collection<User> getUsers();
}
