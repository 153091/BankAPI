package repository;

import impl.Account;
import impl.User;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository {

    // all users of DB
    List<User> getAll() throws SQLException;

    // add user to DB
    void addUser(User user) throws SQLException;

    // search user by id
    User getUserById(Integer id) throws SQLException;

    // update user
    void updateUser(User user) throws SQLException;

    // delete user
    boolean deleteUser(User user) throws SQLException;

    // add account to given user
    void addUserAccount(User user, Account account) throws SQLException;

}
