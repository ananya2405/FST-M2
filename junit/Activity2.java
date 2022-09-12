package activities;

import examples.BankAccount;
import examples.NotEnoughFundsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Activity2 {

    BankAccount account;

    @Test
    public void notEnoughFunds() {
        account = new BankAccount(10);
        assertThrows(NotEnoughFundsException.class, () -> account.withdraw(11));
    }

    @Test
    public void enoughFunds() {
        account = new BankAccount(100);
        assertDoesNotThrow( () -> account.withdraw(10));
    }
}
