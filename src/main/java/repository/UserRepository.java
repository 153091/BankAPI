package repository;

import Entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository {

    User addUser(User user) throws SQLException;

    // all users of DB
    List<User> getAll() throws SQLException;

    // search user by id
    User getUserById(Integer id) throws SQLException;

    // update user
    User updateUser(User user) throws SQLException;

}
