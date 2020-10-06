package impl;

import java.util.Objects;

public class Card {
    private int id;
    private Account account;
    private String number;
    private User user;

    public Card(int id, Account account, String number, User user) {
        this.id = id;
        this.account = account;
        this.number = number;
        this.user = user;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Card{" +
                "account=" + account +
                ", number='" + number + '\'' +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card that = (Card) o;
        return Objects.equals(account, that.account) &&
                Objects.equals(number, that.number) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, number, user);
    }
}
