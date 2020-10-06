package repository;

import Entity.Account;
import Entity.Card;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class CardRepositoryImpl implements CardRepository {

    private DataSource dataSource;

    public CardRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addCard(Account account, Card card) throws SQLException {

    }

    @Override
    public List<Card> getAllCards(Account account) throws SQLException {
        return null;
    }

    @Override
    public Card getCardById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public void updateCard(Card card) throws SQLException {

    }
}
