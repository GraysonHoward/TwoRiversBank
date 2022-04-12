package dev.gray.utility;
/* Author: Grayson Howard
 * Modified: 04/07/2022
 * A utility class designed to connect to the database
 * and handle resulting errors from trying to connect
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    public static Connection createConnection(){

        try {
            Connection conn = DriverManager.getConnection(System.getenv("BANKDB"));
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
}
