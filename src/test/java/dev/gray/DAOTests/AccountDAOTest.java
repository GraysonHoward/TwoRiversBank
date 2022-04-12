package dev.gray.DAOTests;
/* Author: Grayson Howard
 * Modified: 04/08/2022
 * Test to make sure the interactions with the
 * database are occurring as expected
 */


import dev.gray.data.AccountDAO;
import dev.gray.data.AccountDAOPostgres;
import dev.gray.data.UserDAO;
import dev.gray.data.UserDAOPostgres;
import dev.gray.entities.Account;
import dev.gray.entities.User;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)// to run tests in order
public class AccountDAOTest {
    static UserDAO userDAO = new UserDAOPostgres();
    static AccountDAO accountDAO = new AccountDAOPostgres();
    static Account testAccount;
    static User testUser;

    @Test
    @Order(1)
    void create_account(){
        testUser = new User(0, "GumShoe", "Detective", "Guy", "Password1!");
        testUser = userDAO.createNewUser(testUser);
        Account act = new Account(0, testUser.getUserID(), 5000.20, "CHECKING");
        testAccount = accountDAO.createAccount(act);
        Assertions.assertNotEquals(0, testAccount.getAccountNum());
    }

    @Test
    @Order(2)
    void get_account_by_num(){
        Account act = accountDAO.getAccountByNumber(testAccount.getAccountNum());
        Assertions.assertEquals(act.getAccountNum(), testAccount.getAccountNum());
    }

    @Test
    @Order(3)
    void update_account(){
        testAccount.setBal(1000);
        Account returned = accountDAO.updateAccount(testAccount);
        Assertions.assertEquals(1000, returned.getBal());
    }

    @Test
    @Order(4)
    void delete_account(){
        Assertions.assertTrue(accountDAO.deleteAccountByNumber(testAccount.getAccountNum()));
        userDAO.deleteUserByID(testUser.getUserID());
    }
}
