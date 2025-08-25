package org.example.javaconcurrency.concurrentbank;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {

    private final AtomicInteger amount;

    private final Lock lock = new ReentrantLock();

    public BankAccount(int amount) {
        this.amount = new AtomicInteger(amount);
    }

    public Lock getLock() {
        return lock;
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