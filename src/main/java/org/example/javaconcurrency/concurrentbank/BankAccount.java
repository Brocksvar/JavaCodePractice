package org.example.javaconcurrency.concurrentbank;

import java.util.concurrent.atomic.AtomicInteger;

public class BankAccount {

    private final AtomicInteger amount;

    public BankAccount(int amount) {
        this.amount = new AtomicInteger(amount);
    }

    public void deposit(int amount) {
        System.out.println("Сумма к начислению: " + amount);
        int deposited = this.amount.addAndGet(amount);
        System.out.println("Начислено: " + deposited);
    }

    public void withdraw(int amount) {
        System.out.println("Сумма к списанию: " + amount);
        int withdrawed = this.amount.addAndGet(-amount);
        System.out.println("Списано: " + withdrawed);
    }

    public int getBalance() {
        return amount.get();
    }
}