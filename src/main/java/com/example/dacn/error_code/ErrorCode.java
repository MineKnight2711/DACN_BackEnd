package com.example.dacn.error_code;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ErrorCode {
    private InvalidInput invalidInput;
    private DatabaseError databaseError;
    private AuthenticationFailed authenticationFailed;

    public static class InvalidInput {
        public static final String INVALID_EMAIL = "ERR_INVALID_EMAIL";
        public static final String INVALID_PASSWORD = "ERR_INVALID_PASSWORD";
        // Add more specific error codes for invalid input...
    }

    public static class DatabaseError {
        public static final String CONNECTION_ERROR = "ERR_DB_CONNECTION";
        public static final String QUERY_ERROR = "ERR_DB_QUERY";
        // Add more specific error codes for database errors...
    }

    public static class AuthenticationFailed {
        public static final String USER_NOT_FOUND = "ERR_USER_NOT_FOUND";
        public static final String INVALID_CREDENTIALS = "ERR_INVALID_CREDENTIALS";
        // Add more specific error codes for authentication failures...
    }


}
