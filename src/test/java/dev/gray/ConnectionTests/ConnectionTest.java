package dev.gray.ConnectionTests;
/* Author: Grayson Howard
 * Modified: 04/07/2022
 * Tests to verify connection to the database
 */

import dev.gray.utility.ConnectionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class ConnectionTest {

    @Test
    void can_connect(){
        Connection conn = ConnectionUtil.createConnection();
        Assertions.assertNotNull(conn);
    }
}