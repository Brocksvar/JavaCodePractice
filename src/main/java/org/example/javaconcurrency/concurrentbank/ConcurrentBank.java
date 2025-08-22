package org.example.javaconcurrency.concurrentbank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConcurrentBank {

    private final List<BankAccount> listOfBankAccount = Collections.synchronizedList(new ArrayList<>());

    private int totalBalance = 0;

    public BankAccount createAccount(int amount) {
        BankAccount newBankAccount = new BankAccount(amount);
        totalBalance += amount;
        listOfBankAccount.add(newBankAccount);
        return newBankAccount;
    }

    public synchronized void transfer(BankAccount senderAccount, BankAccount recipientAccount, int amount) {
        senderAccount.withdraw(amount);
        recipientAccount.deposit(amount);
    }

    public int getTotalBalance() {
        for (BankAccount bankAccount : listOfBankAccount) {
            System.out.println(bankAccount.getBalance());
        }
        return totalBalance;
    }
}
