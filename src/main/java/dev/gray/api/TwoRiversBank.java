package dev.gray.api;
/* Author: Grayson Howard
 * Modified: 04/12/2022
 * This class manages user interactions and input
 * Allows users to view and interact with their
 * personal bank accounts
 */

import org.apache.commons.lang3.StringUtils;
import dev.gray.data.AccountDAOPostgres;
import dev.gray.data.UserDAOPostgres;
import dev.gray.entities.Account;
import dev.gray.entities.User;
import dev.gray.services.BankServiceImpl;
import dev.gray.utility.ArrayList;
import dev.gray.utility.LogLevel;
import dev.gray.utility.Logger;

import java.util.Scanner;
import java.util.regex.Pattern;

public class TwoRiversBank {
    static BankServiceImpl service = new BankServiceImpl(new UserDAOPostgres(), new AccountDAOPostgres());
    static Scanner scanner = new Scanner(System.in);
    static User current = null;

    public static void main(String[] args) {
        boolean flag = true;
        while(flag) {
            flag = homePage();
        }
    }

    static boolean homePage() {

        System.out.println("Welcome to the Two Rivers Banking Portal:");
        System.out.println("Please Select an Option:\n1. Login\n2. New User\n3. Quit");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                return login(0);
            case 2:
                return newUser();
            case 3:
                return false;
            default:
                return true;
        }
    }

    static boolean login(int attempts){
        scanner.nextLine();
        System.out.println("Please provide your username:");
        String username = scanner.nextLine();
        System.out.println("Please provide your password:");
        String password = scanner.nextLine();
        if(service.validateLogin(username, password)) {
            current = service.login(username);
            if(current == null){
                return userPortal();
            }

        }
        else if(attempts > 3){
            System.out.println("Exceeded Login Attempts!");
            return false;
        }
        String message = "Login Failed. Attempt: " + (attempts+1);
        Logger.log((message + "Username: " + username), LogLevel.WARNING);
        System.out.println(message);
        return login(attempts+1);
    }

    static boolean newUser() {
        boolean passflag;
        String username;
        String first;
        String last;
        String pass;
        String passConfirm;
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$";

        scanner.nextLine();
        System.out.println("Thank you for choosing to bank with Two Rivers Bank");
        System.out.println("Please enter your desired username:");
        username = scanner.nextLine();

        System.out.println("Please enter your first name:");
        first = scanner.next();

        System.out.println("Please enter your last name:");
        last = scanner.next();

        System.out.println("Please enter your new password:");
        System.out.println("Password should contain a Capital letter, a lower case letter, and a number and should be between 8 and 20 characters long.");
        do {
            pass = scanner.next();
            if (!Pattern.matches(regex, pass)) {
                System.out.println("Password conditions not met.");
                System.out.println("Password should contain a Capital letter, a lower case letter, and a number and should be between 8 and 20 characters long.");
                passflag = true;
            } else
                passflag = false;
        } while (passflag);
        System.out.println("Please confirm your password:");
        do {
            passConfirm = scanner.next();
            if (!pass.equals(passConfirm)) {
                System.out.println("Passwords do not match");
                passflag = true;
            } else
                passflag = false;
        } while (passflag);
        User user = new User(0, username, first, last, pass);
        user = service.registerNewUser(user);
        if (user != null){
            System.out.println("New User created.");
            scanner.nextLine();
            return true;
        }
        else
            System.out.println("Failed to create new User");
            scanner.nextLine();
            return true;
    }

    static boolean userPortal(){
        System.out.println("Welcome to the Two Rivers User Portal:");

        // Nicely print accounts upon login
        ArrayList<Account> accounts = service.displayAccounts(current);
        if(accounts.size() > 0){
            System.out.println(StringUtils.repeat('_', 75));
            System.out.printf("Account Number: %-10s | Account Type: %-10s | Balance: \n", " ", " ");
            System.out.println(StringUtils.repeat('_', 75));
            for(int i = 0; i < accounts.size(); i++){
                System.out.println(accounts.get(i).toString());
                System.out.println(StringUtils.repeat('_', 75));
            }
        }else{
            System.out.println("You currently have not accounts with us.");
        }

        // Now give options
        System.out.println("Please Select an Option:\n1. Deposit    2. Withdraw    3. Transfer    4. New Account\n" +
                "5. Logout     6. Close Account");
        int choice = scanner.nextInt();

        switch (choice){
            case 1: deposit(); break;
            case 2: withdraw(); break;
            case 3: transfer(); break;
            case 4: newAccount(); break;
            case 5: {
                current = null;
                return true;
            }
            case 6: removeAccount(); break;
        }
        return userPortal();
    }

    //There's not anything stopping someone from depositing into someone else's account...
    static void deposit(){
        scanner.nextLine();
        System.out.print("Which Account are you depositing into?\nPlease type the number:");
        int number = scanner.nextInt();
        if(!service.validateOwner(current, number)){
            System.out.println("Account number is invalid or not owned by you.");
            System.out.println("Try again?Y/N");
            String ans = scanner.next();
            if(ans.equals("Y")||ans.equals("y"))
                deposit();
            else
                return;
        }
        System.out.println("Please enter the amount you wish to deposit:");
        double amt = scanner.nextDouble();
        service.deposit(number,amt);
        System.out.println("Deposited: $" + amt);
    }

    static void withdraw(){
        scanner.nextLine();
        System.out.print("Which Account are you withdrawing from?\nPlease type the number:");
        int number = scanner.nextInt();
        if(!service.validateOwner(current, number)){
            System.out.println("Account number is invalid or not owned by you.");
            System.out.println("Try again?Y/N");
            String ans = scanner.next();
            if(ans.equals("Y")||ans.equals("y"))
                withdraw();
            else
                return;
        }
        System.out.println("Please enter the amount you wish to withdraw:");
        double amt = scanner.nextDouble();
        boolean result = service.withdraw(number,amt);
        if(!result){
            System.out.println("Insufficient available funds.");
            System.out.println("Try again?Y/N");
            String ans = scanner.next();
            if(ans.equals("Y")||ans.equals("y"))
                withdraw();
        }
        else
            return;
        System.out.println("Withdrew: $" + amt);
    }
    static void transfer(){
        scanner.nextLine();
        System.out.print("Which Account are you transferring from?\nPlease type the number:");
        int account1 = scanner.nextInt();
        if(!service.validateOwner(current, account1)){
            System.out.println("Account number is invalid or not owned by you.");
            System.out.println("Try again?Y/N");
            String ans = scanner.next();
            if(ans.equals("Y")||ans.equals("y"))
                transfer();
            else
                return;
        }
        System.out.println("Please enter the destination account:");
        int account2 = scanner.nextInt();
        System.out.println("Please enter the amount you would like transferred:");
        double amt = scanner.nextDouble();
        Account result = service.transferTo(account1,account2,amt);
        if(result == null){
            System.out.println("Something went wrong!");
            System.out.println("Try again?Y/N");
            String ans = scanner.next();
            if(ans.equals("Y")||ans.equals("y"))
                transfer();
        }
        else
            return;
        System.out.println("Transferred: $" + amt);
    }

    static void transfer(int actNum, double amt){
        scanner.nextLine();
        System.out.println("Please enter the destination account:");
        int account2 = scanner.nextInt();
        Account result = service.transferTo(actNum,account2,amt);
        if(result == null){
            System.out.println("Something went wrong!");
            System.out.println("Try again?Y/N");
            String ans = scanner.next();
            if(ans.equals("Y")||ans.equals("y"))
                removeAccount();
        }
        else
            return;
        System.out.println("Transferred: $" + amt);
    }

    static void newAccount(){
        scanner.nextLine();
        System.out.println("What Type of account will this be?\n1. Checking\n2. Savings\n3. Other");
        int choice = scanner.nextInt();
        String type;
        switch (choice){
            case 1:type = "Checking";break;
            case 2:type = "Saving";break;
            case 3:type = "Other";break;
            default: type = "Checking";
        }
        Account act = new Account(0, current.getUserID(), 0, type);
        if(service.createNewAccount(act) == null){
            System.out.println("Something went wrong!");
        }
        System.out.println("Created new " + type +" account.");
    }

    private static void removeAccount() {
        System.out.println("Please enter the account you'd like to close: ");
        int actNum = scanner.nextInt();
        if(!service.validateOwner(current, actNum)){
            System.out.println("Account number is invalid or not owned by you.");
            System.out.println("Try again?Y/N");
            String ans = scanner.next();
            if(ans.equals("Y")||ans.equals("y"))
                removeAccount();
            else
                return;
        }
        // Make sure they want to close the account
        System.out.println("Are you sure you want to close this account: " + actNum + "? Y/N");
        String ans = scanner.next();
        if(ans.equals("N")||ans.equals("n"))
            return;

        //Make sure the account is empty
        if(service.isEmpty(actNum)){
            service.deleteAccount(actNum);
        }else{
            //Decide what to do with remaining funds
            Account act = service.deposit(actNum,0);
            double bal = act.getBal();
            System.out.println("This account is not empty would you like either:\n1. Transfer\n2. Withdraw ");
            int choice = scanner.nextInt();
            if(choice == 1){
                System.out.println("The remaining balance of the account will be transferred.");
                transfer(actNum, bal);
                service.deleteAccount(actNum);
            }else{
                System.out.println("The remaining balance of the account will be withdrawn.");
                service.withdraw(actNum, bal);
                System.out.println("Withdrew: $" + bal);
                service.deleteAccount(actNum);
            }
        }
    }
}