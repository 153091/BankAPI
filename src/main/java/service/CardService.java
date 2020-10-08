package service;

import Entity.Account;
import Entity.Card;
import dto.CardDTO;
import repository.CardRepositoryImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardService {
    CardRepositoryImpl cardRepository;

    public CardDTO register(Card card) throws SQLException {
        return fromCardDto(cardRepository.addCard(card));
    }

    public CardDTO getCardById(int id) throws SQLException {
        return fromCardDto(cardRepository.getCardById(id));
    }

    public List<CardDTO> getAll(Account account) throws SQLException {
        List<Card> listCard = cardRepository.getAllCards(account);
        List<CardDTO> listCardDto = new ArrayList<>();

        for (Card card : listCard) {
            listCardDto.add(fromCardDto(card));
        }
        return listCardDto;
    }

    private CardDTO fromCardDto(Card card) {
        return new CardDTO(card.getId(), card.getNumber(), card.getBalance(), card.getAccountId());
    }
}