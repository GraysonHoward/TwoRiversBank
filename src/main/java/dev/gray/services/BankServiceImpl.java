package dev.gray.services;
/* Author: Grayson Howard
 * Modified: 04/11/2022
 * Implements methods outlined in the interface.
 */
import dev.gray.data.AccountDAO;
import dev.gray.data.UserDAO;
import dev.gray.entities.Account;
import dev.gray.entities.User;
import dev.gray.utility.ArrayList;
import dev.gray.utility.LogLevel;
import dev.gray.utility.Logger;

public class BankServiceImpl implements BankService {

    private final UserDAO userDAO;
    private final AccountDAO accountDAO;

    public BankServiceImpl(UserDAO userDAO, AccountDAO accountDAO) {
        this.userDAO = userDAO;
        this.accountDAO = accountDAO;
    }

    @Override
    public User registerNewUser(User user) {
        return userDAO.createNewUser(user);
    }

    @Override
    //Tests stored password against provided password
    public boolean validateLogin(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        return user.validatePass(password);
    }

    @Override
    public User login(String username) {
        return userDAO.getUserByUsername(username);
    }

    @Override
    public User changeUsername(User user) {
        return userDAO.updateUser(user);
    }

    @Override
    public User changePassword(User user) {
        return userDAO.updateUser(user);
    }

    @Override
    public Account createNewAccount(Account account) {
        return accountDAO.createAccount(account);
    }

    //Ensures an account belongs to the user trying to access it
    public boolean validateOwner(User usr, int num){
        ArrayList<Account> accounts = userDAO.getAccountsByUserID(usr.getUserID());
        for(int i = 0; i < accounts.size(); i++){
            if(accounts.get(i).getAccountNum() == num)
                return true;
        }
        return false;
    }

    @Override
    public ArrayList<Account> displayAccounts(User user) {
        return userDAO.getAccountsByUserID(user.getUserID());
    }

    @Override
    public boolean withdraw(int actNum, double amount) {
        Account act = accountDAO.getAccountByNumber(actNum);
        if(amount < act.getBal()) {
            act.setBal(act.getBal() - amount);
            String message = "Withdraw. Account: " + actNum + "Amount: " + amount;
            Logger.log(message, LogLevel.INFO);
            accountDAO.updateAccount(act);
            return true;
        }
        else{
            String message = "Attempted Withdraw Failed. Account: " + actNum + "Reason: insufficient funds";
            Logger.log(message, LogLevel.INFO);
            return false;
        }
    }

    @Override
    public Account deposit(int actNum, double amount) {
        Account act = accountDAO.getAccountByNumber(actNum);
        act.setBal(act.getBal() + amount);
        String message = "Deposit. Account: " + actNum + "Amount: " + amount;
        Logger.log(message, LogLevel.INFO);
        return accountDAO.updateAccount(act);
    }

    @Override
    public Account transferTo(int sourceActNum, int destActNum, double amount) {
        Account actSource = accountDAO.getAccountByNumber(sourceActNum);
        Account actDest = accountDAO.getAccountByNumber(sourceActNum);
        if(amount < actSource.getBal()) {
            actSource.setBal(actSource.getBal() - amount);
            actDest.setBal(actDest.getBal() + amount);
            String message = "Transferring Funds. From Account: " + sourceActNum +
                    " To Account: "+ destActNum + " Amount: " + amount;
            Logger.log(message, LogLevel.INFO);
            accountDAO.updateAccount(actDest);
            return accountDAO.updateAccount(actSource);
        }
        else{
            String message = "Attempted Transfer Failed. From Account: " + sourceActNum +
                    " To Account: "+ destActNum + " Reason: insufficient funds";
            Logger.log(message, LogLevel.INFO);
            return actSource;
        }
    }

    @Override
    public boolean deleteAccount(int actNum) {
        return accountDAO.deleteAccountByNumber(actNum);
    }

    @Override
    public boolean deleteUser(User user) {
        return userDAO.deleteUserByID(user.getUserID());
    }
}