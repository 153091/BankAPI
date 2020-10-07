package repository;

import Entity.Account;
import Entity.User;

import java.sql.SQLException;
import java.util.List;

public interface AccountRepository {

    void addAccount(Account account) throws SQLException;

    List<Account> getAllUserAccounts(User user) throws SQLException;

    Account getAccountById(Integer id) throws SQLException;

    void updateAccount(Account account);

}
