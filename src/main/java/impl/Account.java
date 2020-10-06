package impl;

import java.util.Objects;

public class Account {
    private int id;
    private User user;
    private String number;
    private double balance;

    public Account(Integer id, User user, String number, Double balance) {
        this.id = id;
        this.user = user;
        this.number = number;
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "user=" + user +
                ", number='" + number + '\'' +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Double.compare(account.balance, balance) == 0 &&
                Objects.equals(user, account.user) &&
                Objects.equals(number, account.number);
    }
}
