package repository;

import Entity.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private final static String SELECT_ALL_USERS = "select * from users";
    private final static String GET_USER_BY_ID = "select * from users where id = ?";
    private final static String UPDATE_USERS = "update users set age = ?  where id = ?";
    private final static String ADD_USER = "INSERT INTO users (name, age) VALUES (?, ?)";

    private DataSource dataSource;

    public UserRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addUser(User user) throws SQLException {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(ADD_USER)){
            stmt.setString(1, user.getName());
            stmt.setInt(2, user.getAge());
            stmt.execute();
        }
    }

    @Override
    public List<User> getAll() throws SQLException {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_USERS)){
            ResultSet resultSet = stmt.executeQuery();
            List<User> userList = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setAge(resultSet.getInt("age"));
                userList.add(user);
            }
            if (userList.isEmpty()) {
                throw new SQLException("Not found any client!");
            } else {
                return userList;
            }
        }
    }


    @Override
    public User getUserById(Integer id) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(GET_USER_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = User.builder()
                        .id(rs.getInt(1))
                        .name(rs.getString(2))
                        .age(rs.getInt(3))
                        .build();
                return user;
            }
            else {
                throw new SQLException("User not found.");
            }
        }
    }

    @Override
    public void updateUser(User user) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(UPDATE_USERS)) {
            stmt.setInt(1, user.getAge());
            stmt.setInt(2, user.getId());
            stmt.execute();
        } catch (SQLException throwabl) {
            throwabl.printStackTrace();
        }
    }
}
