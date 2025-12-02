import java.util.ArrayList;
import java.util.List;

public class Account {
    private double balance;
    private List<Transaction> transactionHistory;

    public Account(double initialBalance) {
        this.balance = initialBalance;
        transactionHistory = new ArrayList<>();
        transactionHistory.add(new Transaction("Initial", initialBalance, balance, "Account created"));
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add(new Transaction("Deposit", amount, balance, "Deposited cash"));
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdrawal", amount, balance, "Withdrew cash"));
            return true;
        } else {
            transactionHistory.add(new Transaction("Withdrawal", amount, balance, "Failed withdrawal due to insufficient funds"));
            return false;
        }
    }

    public boolean transfer(Account targetAccount, double amount) {
        if (amount <= balance) {
            balance -= amount;
            targetAccount.deposit(amount);
            transactionHistory.add(new Transaction("Transfer", amount, balance, "Transferred to account"));
            return true;
        } else {
            transactionHistory.add(new Transaction("Transfer", amount, balance, "Failed transfer due to insufficient funds"));
            return false;
        }
    }

    public void printTransactionHistory() {
        System.out.println("Transaction History:");
        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction.toString());
        }
    }
}
