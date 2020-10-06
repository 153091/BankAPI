package repository;

import impl.Account;
import impl.User;

import java.sql.SQLException;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public List<User> getAll() throws SQLException {
        return null;
    }

    @Override
    public void addUser(User user) throws SQLException {

    }

    @Override
    public User getUserById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public void updateUser(User user) throws SQLException {

    }

    @Override
    public boolean deleteUser(User user) throws SQLException {
        return false;
    }

    @Override
    public void addUserAccount(User user, Account account) throws SQLException {

    }
}
