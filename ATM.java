import java.util.Scanner;

public class ATM {
    private Bank bank;
    private User currentUser;

    public ATM() {
        bank = new Bank();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the ATM!");

        // Authentication
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();

        System.out.print("Enter PIN: ");
        String userPin = scanner.nextLine();

        if (authenticateUser(userId, userPin)) {
            System.out.println("Authentication Successful!");
            showMenu(scanner);
        } else {
            System.out.println("Invalid User ID or PIN. Exiting...");
        }
        scanner.close();
    }

    private boolean authenticateUser(String userId, String userPin) {
        User user = bank.getUser(userId);
        if (user != null && user.getUserPin().equals(userPin)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    private void showMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("\nATM Menu:");
            System.out.println("1. Transactions History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Enter choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid choice (1-5).");
                scanner.next();
            }
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    currentUser.getAccount().printTransactionHistory();
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    if (currentUser.getAccount().withdraw(withdrawAmount)) {
                        System.out.println("Withdrawal successful.");
                    } else {
                        System.out.println("Insufficient balance.");
                    }
                    break;
                case 3:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    currentUser.getAccount().deposit(depositAmount);
                    System.out.println("Deposit successful.");
                    break;
                case 4:
                    System.out.print("Enter recipient user ID: ");
                    scanner.nextLine(); // Consume newline
                    String recipientId = scanner.nextLine();
                    User recipient = bank.getUser(recipientId);
                    if (recipient != null) {
                        System.out.print("Enter amount to transfer: ");
                        double transferAmount = scanner.nextDouble();
                        if (currentUser.getAccount().transfer(recipient.getAccount(), transferAmount)) {
                            System.out.println("Transfer successful.");
                        } else {
                            System.out.println("Insufficient balance.");
                        }
                    } else {
                        System.out.println("Recipient user not found.");
                    }
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-5.");
            }

        } while (choice != 5);
    }

    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }
}
