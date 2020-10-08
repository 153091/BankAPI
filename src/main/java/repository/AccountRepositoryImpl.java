package repository;

import Entity.Account;
import Entity.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryImpl implements AccountRepository {

    private static final String GET_ACCOUNT_BY_ID = "SELECT * FROM accounts WHERE id = ?";
    private static final String GET_ALL_ACCOUNTS_BY_USER_ID = "select * from accounts where user_id = ?";
    private static final String ADD_ACCOUNT = "INSERT INTO accounts (user_id, number) VALUES (?, ?)";
    private static final String UPDATE_ACCOUNT = "update accounts set number = ? where id = ?";
    private final static String DELETE_ACCOUNT = "delete from accounts where id = ?";


    private DataSource dataSource;

    public AccountRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Account addAccount(Account account) throws SQLException {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(ADD_ACCOUNT, Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1, account.getUserId());
            ps.setString(2, account.getNumber());
            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating Account failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    account.setId(generatedKeys.getInt(1));
                    return account;
                } else {
                    throw new SQLException("Creating Account failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public List<Account> getAllUserAccounts(User user) throws SQLException {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(GET_ALL_ACCOUNTS_BY_USER_ID)) {
            stmt.setInt(1, user.getId());
            ResultSet rs = stmt.executeQuery();
            List<Account> accounts = new ArrayList<>();
            while (rs.next()) {
                Account account = Account.builder()
                        .id(rs.getInt("id"))
                        .userId(rs.getInt("user_id"))
                        .number(rs.getString("number"))
                        .build();
                accounts.add(account);
            }
            if (accounts.isEmpty()) {
                throw new SQLException("Not found any account!");
            } else {
                return accounts;
            }
        }
    }

    @Override
    public Account getAccountById(Integer id) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(GET_ACCOUNT_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Account account = Account.builder()
                        .id(rs.getInt("id"))
                        .userId(rs.getInt("user_id"))
                        .number(rs.getString("number"))
                        .build();
                return account;
                }
            else {
                    throw new SQLException("User not found.");
            }
        }
    }

    @Override
    public void updateAccount(Account account) throws SQLException {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(UPDATE_ACCOUNT)) {
            stmt.setString(1, account.getNumber());
            stmt.setInt(2, account.getId());
            stmt.execute();
        } catch (SQLException throwabl) {
            throwabl.printStackTrace();
        }
    }

    @Override
    public boolean deleteAccountById(int id) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(DELETE_ACCOUNT)) {
            stmt.setInt(1, id);
            int affectedRow = stmt.executeUpdate();

            return affectedRow != 0;
        }
    }
}
