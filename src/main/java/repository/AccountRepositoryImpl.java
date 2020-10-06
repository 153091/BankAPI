package repository;

import Entity.Account;
import Entity.User;

import java.sql.SQLException;
import java.util.List;

public class AccountRepositoryImpl implements AccountRepository {

    @Override
    public List<Account> getAllClientAccounts(User user) throws SQLException {
        return null;
    }

    @Override
    public Account getAccountById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public Account getAccountByClientId(User user) throws SQLException {
        return null;
    }

    @Override
    public void updateAccount(User user, Account account) {

    }
}
