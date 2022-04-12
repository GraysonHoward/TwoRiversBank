package dev.gray.data;
/* Author: Grayson Howard
 * Modified: 04/08/2022
 * This class facilitates communication between the database
 * and account entities.
 */


import dev.gray.entities.Account;
import dev.gray.utility.*;

import java.sql.*;

public class AccountDAOPostgres implements AccountDAO{
    @Override
    public Account createAccount(Account account) {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "insert into accounts values(default,?,?,?)";
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, account.getUserID());
            ps.setDouble(2, account.getBal());
            ps.setString(3, account.getType());

            ps.execute();

            ResultSet rs =ps.getGeneratedKeys();
            rs.next();
            int actNum = rs.getInt("account_num");
            account.setAccountNum(actNum);

            String message = "Creating Account: " + account.getAccountNum();
            Logger.log(message, LogLevel.INFO);

            return account;
        } catch (SQLException e) {
            Logger.log(e.getMessage(), LogLevel.ERROR);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Account getAccountByNumber(int actNum) {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "select * from accounts where account_num = ?";
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, actNum);

            ResultSet rs = ps.executeQuery();

            // move to first record
            if(rs.next()) {
                Account act = new Account();
                act.setAccountNum(rs.getInt("account_num"));
                act.setUserID(rs.getInt("owner"));
                act.setBal(rs.getDouble("balance"));
                act.setType(rs.getString("account_type"));
                return act;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            Logger.log(e.getMessage(), LogLevel.ERROR);
            return null;
        }
    }

    @Override
    public Account updateAccount(Account account) {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "update accounts set owner = ?, balance = ?, account_type = ? where account_num = ?";
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(4,account.getAccountNum());
            ps.setInt(1, account.getUserID());
            ps.setDouble(2, account.getBal());
            ps.setString(3, account.getType());

            ps.executeUpdate();

            String message = "Updating Account: " + account.getAccountNum();
            Logger.log(message, LogLevel.INFO);

            return account;
        } catch (SQLException e) {
            Logger.log(e.getMessage(), LogLevel.ERROR);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteAccountByNumber(int actNum) {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "delete from accounts where account_num = ?";
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, actNum);

            ps.executeUpdate();

            String message = "Deleting Account: " + actNum;
            Logger.log(message, LogLevel.INFO);

            return true;
        } catch (SQLException e) {
            Logger.log(e.getMessage(), LogLevel.ERROR);
            e.printStackTrace();
            return false;
        }
    }
}
