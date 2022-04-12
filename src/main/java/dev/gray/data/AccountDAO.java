package dev.gray.data;
/* Author: Grayson Howard
 * Modified: 04/07/2022
 * This class is designed to interact with the account portion of the database
 */

import dev.gray.entities.Account;

public interface AccountDAO {
    // Create a new account
    Account createAccount(Account account);

    // Retrieve account details
    Account getAccountByNumber(int actNum);

    // Update functions
    Account updateAccount(Account account);

    //Delete Account
    boolean deleteAccountByNumber(int actNum);
}
