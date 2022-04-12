package dev.gray.data;

import dev.gray.entities.Account;
import dev.gray.entities.User;
import dev.gray.utility.*;

import java.sql.*;

public class UserDAOPostgres implements UserDAO{
    @Override
    public User createNewUser(User user) {
        //int userID, String username, String firstName, String lastName, String password
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "insert into users values(default,?,?,?,?)";
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getPassword());

            ps.execute();

            ResultSet rs =ps.getGeneratedKeys();
            rs.next();
            int userID = rs.getInt("user_id");
            user.setUserID(userID);

            String message = "Creating User: " + user.getUserID();
            Logger.log(message, LogLevel.INFO);

            return user;
        } catch (SQLException e) {
            Logger.log(e.getMessage(), LogLevel.ERROR);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User getUserByUsername(String usrName) {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "select * from users where username = ?";
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,usrName);

            ResultSet rs = ps.executeQuery();

            rs.next(); // move to first record
            User user = new User();
            user.setUserID(rs.getInt("user_id"));
            user.setUsername(rs.getString("username"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setPassword(rs.getString("pass"));
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            Logger.log(e.getMessage(), LogLevel.ERROR);
            return null;
        }
    }

    @Override
    public User getUserByID(int ID) {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "select * from users where user_id = ?";
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,ID);

            ResultSet rs = ps.executeQuery();

            rs.next(); // move to first record
            User user = new User();
            user.setUserID(rs.getInt("user_id"));
            user.setUsername(rs.getString("username"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setPassword(rs.getString("pass"));
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            Logger.log(e.getMessage(), LogLevel.ERROR);
            return null;
        }
    }

    @Override
    public ArrayList<Account> getAccountsByUserID(int id) {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "select * from accounts where owner = ?";
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            ArrayList<Account> accounts = new ArrayList<>();

            while(rs.next()) { // move to first record
                Account act = new Account();
                act.setAccountNum(rs.getInt("account_num"));
                act.setUserID(rs.getInt("owner"));
                act.setBal(rs.getDouble("balance"));
                act.setType(rs.getString("account_type"));
                accounts.addLast(act);
            }
            return accounts;
        } catch (SQLException e) {
            e.printStackTrace();
            Logger.log(e.getMessage(), LogLevel.ERROR);
            return null;
        }
    }

    @Override
    public User updateUser(User user) {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "update users set username = ?, first_name = ?, last_name = ?, pass = ? where user_id = ?";
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getPassword());
            ps.setInt(5, user.getUserID());

            ps.executeUpdate();

            String message = "Updating User: " + user.getUserID();
            Logger.log(message, LogLevel.INFO);

            return user;

        } catch (SQLException e) {
            Logger.log(e.getMessage(), LogLevel.ERROR);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteUserByID(int usrID) {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "delete from users where user_id = ?";
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, usrID);

            ps.executeUpdate();

            String message = "Deleting User: " + usrID;
            Logger.log(message, LogLevel.INFO);

            return true;
        } catch (SQLException e) {
            Logger.log(e.getMessage(), LogLevel.ERROR);
            e.printStackTrace();
            return false;
        }

    }
}
