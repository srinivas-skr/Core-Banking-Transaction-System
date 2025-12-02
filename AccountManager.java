import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountManager {

    // Login Method (Replaces HashMap)
    public boolean login(String email, int pin) {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM accounts WHERE email = ? AND security_pin = ?")) {
            
            ps.setString(1, email);
            ps.setInt(2, pin);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Returns true if user exists
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get Account Number by Email (Helper)
    public long getAccountNumber(String email) {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT account_number FROM accounts WHERE email = ?")) {
            
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getLong("account_number");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Get Balance
    public double getBalance(long accountNumber) {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT balance FROM accounts WHERE account_number = ?")) {
            
            ps.setLong(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble("balance");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // Transfer Money (ACID Property)
    public void transferMoney(long senderAcc, long receiverAcc, double amount) {
        Connection conn = null;
        try {
            conn = ConnectionManager.getConnection();
            if (conn == null) return;

            // 1. Disable Auto-Commit (Start Transaction)
            conn.setAutoCommit(false);

            // 2. Check Sender Balance
            PreparedStatement checkBalance = conn.prepareStatement("SELECT balance FROM accounts WHERE account_number = ?");
            checkBalance.setLong(1, senderAcc);
            ResultSet rs = checkBalance.executeQuery();
            
            if(rs.next()) {
                double currentBalance = rs.getDouble("balance");
                if(currentBalance < amount) {
                    throw new RuntimeException("Insufficient Funds!");
                }
            } else {
                throw new RuntimeException("Sender Account Not Found!");
            }

            // 3. Debit Sender
            PreparedStatement debit = conn.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE account_number = ?");
            debit.setDouble(1, amount);
            debit.setLong(2, senderAcc);
            debit.executeUpdate();

            // 4. Credit Receiver
            PreparedStatement credit = conn.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE account_number = ?");
            credit.setDouble(1, amount);
            credit.setLong(2, receiverAcc);
            int rows = credit.executeUpdate();
            if (rows == 0) throw new RuntimeException("Receiver Account Not Found!");

            // 5. Log Transaction (History)
            logTransaction(conn, senderAcc, "Transfer Sent", amount);
            logTransaction(conn, receiverAcc, "Transfer Received", amount);

            // 6. Commit (Save)
            conn.commit();
            System.out.println("[SUCCESS] Transaction Successful! Rs." + amount + " Transferred.");

        } catch (Exception e) {
            // 7. Rollback (Undo if error)
            try {
                if(conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println("[FAILED] Transaction Failed! Money Refunded.");
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Helper to Log Transaction
    private void logTransaction(Connection conn, long accNum, String type, double amount) throws SQLException {
        // For Oracle 11g, we use a subquery to generate a pseudo-ID (MAX + 1)
        PreparedStatement ps = conn.prepareStatement("INSERT INTO transactions (transaction_id, account_number, transaction_type, amount, balance_after) VALUES ((SELECT NVL(MAX(transaction_id), 0) + 1 FROM transactions), ?, ?, ?, (SELECT balance FROM accounts WHERE account_number = ?))");
        ps.setLong(1, accNum);
        ps.setString(2, type);
        ps.setDouble(3, amount);
        ps.setLong(4, accNum);
        ps.executeUpdate();
    }

    // Print History
    public void printHistory(long accountNumber) {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM transactions WHERE account_number = ? ORDER BY transaction_date DESC")) {
            
            ps.setLong(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            
            System.out.println("\n--- Transaction History ---");
            while (rs.next()) {
                System.out.println(rs.getTimestamp("transaction_date") + " | " + 
                                   rs.getString("transaction_type") + " | Rs." + 
                                   rs.getDouble("amount") + " | Bal: Rs." + 
                                   rs.getDouble("balance_after"));
            }
            System.out.println("---------------------------");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
