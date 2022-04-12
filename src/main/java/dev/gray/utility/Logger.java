package dev.gray.utility;
/* Author: Grayson Howard
 * Modified: 04/07/2022
 * This class prints useful information to a text file.
 *
 * DEBUG   - log generated for debugging purposes
 * INFO    - Events that occurred during session
 * WARNING -
 * ERROR   -
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;

public class Logger {

    static String path = "C:\\Users\\Vfire\\IdeaProjects\\TwoRiversBank\\applogs.log";

    public static void log(String log, LogLevel level){

        log = level + ": " + log + " | " + new Date() + "\n";
        try {
            Files.write(Paths.get(path), log.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
