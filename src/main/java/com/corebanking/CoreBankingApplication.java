package com.corebanking;

import com.corebanking.ui.ConsoleUI;

/**
 * Core Banking Transaction System
 * 
 * A console-based banking application demonstrating:
 * - ACID-compliant transactions
 * - Layered architecture (Controller-Service-Repository)
 * - Secure authentication with SQL injection prevention
 * 
 * @author Srinivas
 * @version 1.0
 */
public class CoreBankingApplication {

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║     CORE BANKING TRANSACTION SYSTEM          ║");
        System.out.println("║     Secure • ACID-Compliant • Enterprise     ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.println();

        ConsoleUI consoleUI = new ConsoleUI();
        consoleUI.start();
    }
}
