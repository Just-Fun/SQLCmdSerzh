package ua.com.juja.serzh.sqlcmd.model;

import java.sql.SQLException;

/**
 * Created by serzh on 31.05.16.
 */
public class DatabaseManagerException extends RuntimeException {

    public DatabaseManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}

