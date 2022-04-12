package dev.gray.services;
/* Author: Grayson Howard
 * Modified: 04/08/2022
 * This outlines the interactions allowed to happen with the banking application
 * both before and after login has occurred.
 */

import dev.gray.entities.Account;
import dev.gray.entities.User;
import dev.gray.utility.ArrayList;

public interface BankService {
    /*
     * Actions available on start up
     * */
    User registerNewUser(User user);
    boolean validateLogin(String username, String password);
    User login(String username);

    /*
     * Actions available once user is logged in
     **/
    // User may update their username
    User changeUsername(User user);
    // User may update their password
    User changePassword(User user);
    // User may create a new account
    Account createNewAccount(Account account);
    // Validates that a user is the owner of the account

    // User can display all their associated accounts
    ArrayList<Account> displayAccounts(User user);
    // User may withdraw funds from a selected account
    // up to the amount stored there. Withdrawing more
    // causes an error message
    boolean withdraw(int actNum, double amount);
    // User may deposit funds into the account
    Account deposit(int actNum, double amount);
    // User may transfer money TO an account they know
    // the number of FROM one of their own accounts
    Account transferTo(int sourceActNum, int destActNum, double amount);
    // User can delete their account
    boolean deleteAccount(int actNum);
    // Option to delete user
    boolean deleteUser(User user);
}
