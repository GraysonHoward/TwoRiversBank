package dev.gray.data;
/* Author: Grayson Howard
 * Modified: 04/07/2022
 * This class is designed to interact with the user portion of the database
 */

import dev.gray.entities.Account;
import dev.gray.entities.User;
import dev.gray.utility.ArrayList;

public interface UserDAO {
    // Create a new user
    User createNewUser(User user);

    // Retrieve user details
    User getUserByUsername(String usrName);

    User getUserByID(int ID);

    ArrayList<Account> getAccountsByUserID(int id);

    // Update functions
    User updateUser(User user);

    //Delete Account
    boolean deleteUserByID(int usrID);
}
