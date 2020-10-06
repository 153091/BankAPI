package repository;

import Entity.Account;
import Entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AccountRepositoryImpl implements AccountRepository {


    @Override
    public void addAccount(Account account) throws SQLException {

    }

    @Override
    public List<Account> getAllClientAccounts(User user) throws SQLException {
    }

    @Override
    public Account getAccountById(Integer id) throws SQLException {
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
    public Account getAccountByClientId(User user) throws SQLException {
        return null;
    }

    @Override
    public void updateAccount(User user, Account account) {

    }
}
