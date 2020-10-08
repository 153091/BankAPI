package service;

import Entity.Account;
import Entity.User;
import dto.AccountDTO;
import repository.AccountRepositoryImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountService {
    AccountRepositoryImpl accountRepository;

    public AccountDTO register(Account account) throws SQLException {
        return fromAccountDto(accountRepository.addAccount(account));
    }

    public AccountDTO getAccountById(int id) throws SQLException {
        return fromAccountDto(accountRepository.getAccountById(id));
    }

    public List<AccountDTO> getAll(User user) throws SQLException {
        List<Account> listAccount = accountRepository.getAllUserAccounts(user);
        List<AccountDTO> listAccountDto = new ArrayList<>();

        for (Account account : listAccount) {
            listAccountDto.add(fromAccountDto(account));
        }
        return listAccountDto;
    }

    private AccountDTO fromAccountDto(Account account) {
        return new AccountDTO(account.getId(), account.getNumber(), account.getUserId());
    }
}
