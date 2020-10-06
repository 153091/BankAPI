package repository;

import Entity.Account;
import Entity.User;

import java.sql.SQLException;
import java.util.List;

public interface AccountRepository {

    void addAccount(Account account) throws SQLException;

    List<Account> getAllClientAccounts(User user) throws SQLException;

    Account getAccountById(Integer id) throws SQLException;

    Account getAccountByClientId(User user) throws SQLException;

    void updateAccount(User user, Account account);

}
