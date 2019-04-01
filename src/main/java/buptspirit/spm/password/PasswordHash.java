package buptspirit.spm.password;

import javax.inject.Singleton;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

@Singleton
public class PasswordHash {
    public String generate(char[] password) {
        String salt = UUID.randomUUID().toString().replaceAll("-", "");
        return generateWithSalt(password, salt);
    }

    public boolean verify(char[] password, String hashedPassword) {
        String[] parts = hashedPassword.split(":");
        if (parts.length != 2)
            return false;
        String salt = parts[0];
        String result = generateWithSalt(password, salt);
        return result.equals(hashedPassword);
    }

    private String generateWithSalt(char[] password, String salt) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("sha-256 hash not provided");
        }
        digest.update(salt.getBytes());
        digest.update(new String(password).getBytes());
        byte[] digested = digest.digest();
        String base64 = Base64.getEncoder().encodeToString(digested);
        return salt + ":" + base64;
    }
}
