package org.example.javaconcurrency.concurrentbank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ConcurrentBank {

    private final List<BankAccount> listOfBankAccount = Collections.synchronizedList(new ArrayList<>());

    private int totalBalance = 0;

    private final int numberOfRetries = 5;

    public BankAccount createAccount(int amount) {
        BankAccount newBankAccount = new BankAccount(amount);
        totalBalance += amount;
        listOfBankAccount.add(newBankAccount);
        return newBankAccount;
    }

    public void transfer(BankAccount senderAccount, BankAccount recipientAccount, int amount) {
        int attempt = 1;
        while (attempt <= numberOfRetries) {
            try {
                if (senderAccount.getLock().tryLock(2, TimeUnit.SECONDS)) {
                    if (recipientAccount.getLock().tryLock(2, TimeUnit.SECONDS)) {
                        try {
                            senderAccount.withdraw(amount);
                            recipientAccount.deposit(amount);
                            return;
                        } finally {
                            senderAccount.getLock().unlock();
                            recipientAccount.getLock().unlock();
                        }
                    }
                    senderAccount.getLock().unlock();
                }
                System.out.println("Номер попытки: " + attempt);
                attempt++;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public int getTotalBalance() {
        for (BankAccount bankAccount : listOfBankAccount) {
            System.out.println(bankAccount.getBalance());
        }
        return totalBalance;
    }
}
