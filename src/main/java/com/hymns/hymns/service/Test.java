package com.hymns.hymns.service;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;

public class Test {
    public static void main(String[] args) {
        // Generate a 256-bit key
        byte[] key = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();

        // Encode to Base64
        String base64Key = Base64.getEncoder().encodeToString(key);

        System.out.println("Generated Base64-encoded key: " + base64Key);
    }
}
