package dev.gray.LoggerTests;


import dev.gray.utility.Logger;
import dev.gray.utility.LogLevel;
import org.junit.jupiter.api.Test;

public class LoggerTest {

    @Test
    void info_log_test(){
        Logger.log("Hello", LogLevel.INFO);
        Logger.log("Wassup", LogLevel.DEBUG);
    }
}