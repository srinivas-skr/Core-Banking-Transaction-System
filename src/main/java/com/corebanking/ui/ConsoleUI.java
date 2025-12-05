package com.corebanking.ui;

import com.corebanking.dto.LoginRequest;
import com.corebanking.dto.LoginResponse;
import com.corebanking.dto.TransferRequest;
import com.corebanking.dto.TransferResult;
import com.corebanking.exception.AuthenticationException;
import com.corebanking.model.Transaction;
import com.corebanking.service.AccountService;
import com.corebanking.service.AuthenticationService;
import com.corebanking.service.impl.AccountServiceImpl;
import com.corebanking.service.impl.AuthenticationServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/**
 * Console-based user interface for the banking application.
 * This class handles all user interaction - keeping UI separate from business logic.
 */
public class ConsoleUI {

    private final AuthenticationService authService;
    private final AccountService accountService;
    private final Scanner scanner;

    private LoginResponse currentSession;

    public ConsoleUI() {
        this.authService = new AuthenticationServiceImpl();
        this.accountService = new AccountServiceImpl();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Main entry point - starts the banking session.
     */
    public void start() {
        try {
            if (login()) {
                showMainMenu();
            }
        } finally {
            scanner.close();
        }
    }

    /**
     * Handle user login.
     */
    private boolean login() {
        System.out.println("\n┌─────────────────────────────────┐");
        System.out.println("│          SECURE LOGIN           │");
        System.out.println("└─────────────────────────────────┘");

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Security PIN: ");
        int pin;
        try {
            pin = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            printError("Invalid PIN format. Must be a number.");
            return false;
        }

        try {
            currentSession = authService.authenticate(new LoginRequest(email, pin));
            printSuccess("Login Successful!");
            System.out.println(currentSession);
            return true;
        } catch (AuthenticationException e) {
            printError("Login Failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Display and handle main banking menu.
     */
    private void showMainMenu() {
        int choice;
        do {
            printMenuHeader();
            choice = readMenuChoice();

            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    transferMoney();
                    break;
                case 3:
                    showTransactionHistory();
                    break;
                case 4:
                    logout();
                    break;
                default:
                    if (choice != 4) {
                        printError("Invalid option. Please choose 1-4.");
                    }
            }
        } while (choice != 4);
    }

    private void printMenuHeader() {
        System.out.println("\n┌─────────────────────────────────┐");
        System.out.println("│         BANKING MENU            │");
        System.out.println("├─────────────────────────────────┤");
        System.out.println("│  1. Check Balance               │");
        System.out.println("│  2. Transfer Money              │");
        System.out.println("│  3. Transaction History         │");
        System.out.println("│  4. Logout                      │");
        System.out.println("└─────────────────────────────────┘");
        System.out.print("Enter choice: ");
    }

    private int readMenuChoice() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Display current account balance.
     */
    private void checkBalance() {
        BigDecimal balance = accountService.getBalance(currentSession.getAccountNumber());
        System.out.println("\n╔═══════════════════════════════════╗");
        System.out.printf("║  Current Balance: Rs.%-12.2f ║%n", balance);
        System.out.println("╚═══════════════════════════════════╝");
    }

    /**
     * Handle fund transfer.
     */
    private void transferMoney() {
        System.out.println("\n┌─────────────────────────────────┐");
        System.out.println("│        FUND TRANSFER            │");
        System.out.println("└─────────────────────────────────┘");

        System.out.print("Receiver Account Number: ");
        long receiverAccount;
        try {
            receiverAccount = Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            printError("Invalid account number format.");
            return;
        }

        System.out.print("Amount (Rs.): ");
        BigDecimal amount;
        try {
            amount = new BigDecimal(scanner.nextLine().trim());
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                printError("Amount must be greater than zero.");
                return;
            }
        } catch (NumberFormatException e) {
            printError("Invalid amount format.");
            return;
        }

        // Confirm transfer
        System.out.printf("%nTransfer Rs.%.2f to Account %d?%n", amount, receiverAccount);
        System.out.print("Confirm (Y/N): ");
        String confirm = scanner.nextLine().trim();
        if (!confirm.equalsIgnoreCase("Y")) {
            printInfo("Transfer cancelled.");
            return;
        }

        // Execute transfer
        TransferRequest request = new TransferRequest(
                currentSession.getAccountNumber(),
                receiverAccount,
                amount
        );

        TransferResult result = accountService.transfer(request);

        if (result.isSuccess()) {
            printSuccess("Transfer Successful!");
            System.out.printf("New Balance: Rs.%.2f%n", result.getNewBalance());
        } else {
            printError("Transfer Failed: " + result.getMessage());
        }
    }

    /**
     * Display transaction history.
     */
    private void showTransactionHistory() {
        List<Transaction> history = accountService.getTransactionHistory(
                currentSession.getAccountNumber()
        );

        System.out.println("\n┌───────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                        TRANSACTION HISTORY                            │");
        System.out.println("├───────────────────────────────────────────────────────────────────────┤");

        if (history.isEmpty()) {
            System.out.println("│  No transactions found.                                               │");
        } else {
            for (Transaction tx : history) {
                System.out.printf("│  %s │%n", tx.toString());
            }
        }

        System.out.println("└───────────────────────────────────────────────────────────────────────┘");
    }

    /**
     * Handle logout.
     */
    private void logout() {
        System.out.println("\n╔═══════════════════════════════════╗");
        System.out.println("║  Logged out successfully.         ║");
        System.out.println("║  Thank you for banking with us!   ║");
        System.out.println("╚═══════════════════════════════════╝");
        currentSession = null;
    }

    // UI Helper methods
    private void printSuccess(String message) {
        System.out.println("\n[✓] SUCCESS: " + message);
    }

    private void printError(String message) {
        System.out.println("\n[✗] ERROR: " + message);
    }

    private void printInfo(String message) {
        System.out.println("\n[i] INFO: " + message);
    }
}
