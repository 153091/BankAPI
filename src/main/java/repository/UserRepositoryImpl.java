package repository;

import Entity.User;

import java.sql.SQLException;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private final static String GET_USER_BY_ID = "select id, name, email, registered from clients where id = ?";


    @Override
    public List<User> getAll() throws SQLException {
        return null;
    }

    @Override
    public User getUserById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public void updateUser(User user) throws SQLException {

    }
}
