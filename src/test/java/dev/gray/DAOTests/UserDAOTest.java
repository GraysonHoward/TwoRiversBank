package dev.gray.DAOTests;
/* Author: Grayson Howard
 * Modified: 04/07/2022
 * A suit of tests to verify functionality of the UserDAO
 */

import dev.gray.data.AccountDAO;
import dev.gray.data.AccountDAOPostgres;
import dev.gray.data.UserDAO;
import dev.gray.data.UserDAOPostgres;
import dev.gray.entities.Account;
import dev.gray.entities.User;
import dev.gray.utility.ArrayList;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)// to run tests in order
public class UserDAOTest {
    static UserDAO userDAO = new UserDAOPostgres();
    static AccountDAO accountDAO = new AccountDAOPostgres();
    static User testUser;

    @Test
    @Order(1)
    void create_user(){
        testUser = new User(0, "GumShoe", "Detective", "Guy", "Password1!");
        testUser = userDAO.createNewUser(testUser);
        Assertions.assertNotEquals(0, testUser.getUserID());
    }

    @Test
    @Order(2)
    void get_user_by_username(){
        User retrievedUser = userDAO.getUserByUsername(testUser.getUsername());
        Assertions.assertEquals(retrievedUser.getUserID(), testUser.getUserID());
    }

    @Test
    @Order(3)
    void get_user_by_ID(){
        User retrievedUser = userDAO.getUserByID(testUser.getUserID());
        Assertions.assertEquals(retrievedUser.getUserID(), testUser.getUserID());
    }

    @Test
    @Order(4)
    void update_User(){
        testUser.setUsername("StickyBoi");
        User returned = userDAO.updateUser(testUser);
        Assertions.assertEquals("StickyBoi", returned.getUsername());
    }

    @Test
    @Order(6)
    void delete_user(){
        Assertions.assertTrue(userDAO.deleteUserByID(testUser.getUserID()));
    }

    @Test
    @Order(5)
    //@Disabled
    void test_get_accounts(){
        Account act1 = new Account(0, testUser.getUserID(), 5000.20, "CHECKING");
        Account act2 = new Account(0, testUser.getUserID(), 5000.20, "SAVINGS");
        accountDAO.createAccount(act1);
        accountDAO.createAccount(act2);
        ArrayList<Account> accounts = userDAO.getAccountsByUserID(testUser.getUserID());
        Assertions.assertEquals(2, accounts.size());
    }


}
