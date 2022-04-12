package dev.gray.entities;
/* Author: Grayson Howard
 * Modified: 04/08/2022
 * This class is designed to represent a user stored in the database.
 */

public class User {
    private int userID;
    private String username;
    private String firstName;
    private String lastName;
    private String password;

    public User(){}

    public User(int userID, String username, String firstName, String lastName, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userID = userID;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public boolean validatePass(String password){
        return this.password.equals(password);
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", username='" + username +'\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
