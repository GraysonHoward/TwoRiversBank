package dev.gray.entities;
/* Author: Grayson Howard
 * Modified: 04/06/2022
 * This class is designed to represent an account stored in a database
 */

public class Account {
    private int accountNum;
    private int userID;
    private double bal;
    private String type;

    public Account(){}

    public Account(int accountNum, int userID, double bal, String type) {
        this.accountNum = accountNum;
        this.userID = userID;
        this.bal = bal;
        this.type = type;
    }

    public int getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(int accountNum) {
        this.accountNum = accountNum;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public double getBal() {
        return bal;
    }

    public void setBal(double amt) {
        this.bal = amt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("Account Number: %-10d | Account Type: %-10s | Balance: $%.2f", accountNum, type, bal);
    }
}
