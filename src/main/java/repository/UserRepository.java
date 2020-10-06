package repository;

import Entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository {

    // all users of DB
    List<User> getAll() throws SQLException;

    // search user by id
    User getUserById(Integer id) throws SQLException;

    // update user
    void updateUser(User user) throws SQLException;

}
