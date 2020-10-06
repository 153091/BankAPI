package repository;

import Entity.Account;
import Entity.Card;

import java.sql.SQLException;
import java.util.List;

public interface CardRepository {

    void addCard(Account account, Card card) throws SQLException;

    List<Card> getAllCards(Account account) throws SQLException;

    Card getCardById(Integer id) throws SQLException;

    void updateCard(Card card) throws SQLException;

}
