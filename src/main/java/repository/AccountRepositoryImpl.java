package repository;

import impl.Account;
import impl.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class AccountRepositoryImpl implements AccountRepository {

    private final Connection conn;

    public AccountRepositoryImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void addAccount(User client, Account account) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeQuery("sql statement");

    }

    @Override
    public List<Account> getAllUserAccounts(User client) throws SQLException {
        return null;
    }
}
