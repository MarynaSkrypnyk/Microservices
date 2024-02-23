package authservice.service;

import lombok.Getter;

import java.util.*;

public class AuthService {
    @Getter
    private static final AuthService INSTANCE = new AuthService();
    private final Map<String, String> userDatabase = new HashMap<>();
    private final Map<String, String> tokenDatabase = new HashMap<>();

    private AuthService() {
        userDatabase.put("adminUser", "adminUser");
        userDatabase.put("simpleUser", "simpleUser");
    }

    public String authenticate(String username, String password) {
        if (userDatabase.containsKey(username) && userDatabase.get(username).equals(password)) {
            String token = generateToken(username);
            tokenDatabase.put(token, username);
            return token;
        }
        return null;
    }

    public boolean validateToken(String token) {
        return tokenDatabase.containsKey(token);
    }

    private String generateToken(String username) {
        String token = String.valueOf(System.currentTimeMillis());
        if (username.equals("adminUser")) {
            token += "admin";
        } else if (username.equals("simpleUser")) {
            token += "simple";
        }
        return token;
    }
}
