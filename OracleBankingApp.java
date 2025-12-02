import java.util.Scanner;

public class OracleBankingApp {
    public static void main(String[] args) {
        AccountManager accountManager = new AccountManager();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Oracle Core Banking System");
        System.out.println("-------------------------------------");

        // Login Loop
        System.out.print("Enter Email ID: ");
        String email = scanner.nextLine();
        System.out.print("Enter Security PIN: ");
        int pin = scanner.nextInt();

        if (accountManager.login(email, pin)) {
            long myAccountNum = accountManager.getAccountNumber(email);
            System.out.println("Login Successful! Account Number: " + myAccountNum);
            
            int choice;
            do {
                System.out.println("\n--- Banking Menu ---");
                System.out.println("1. Check Balance");
                System.out.println("2. Transfer Money");
                System.out.println("3. Transaction History");
                System.out.println("4. Logout");
                System.out.print("Enter choice: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("Current Balance: Rs." + accountManager.getBalance(myAccountNum));
                        break;
                    case 2:
                        System.out.print("Enter Receiver Account Number: ");
                        long receiverAcc = scanner.nextLong();
                        System.out.print("Enter Amount: ");
                        double amount = scanner.nextDouble();
                        accountManager.transferMoney(myAccountNum, receiverAcc, amount);
                        break;
                    case 3:
                        accountManager.printHistory(myAccountNum);
                        break;
                    case 4:
                        System.out.println("Logged out successfully.");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } while (choice != 4);

        } else {
            System.out.println("Invalid Credentials. System Exiting.");
        }
        
        scanner.close();
    }
}
