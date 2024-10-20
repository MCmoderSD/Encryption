package de.MCmoderSD.encryption;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Base64;
import java.util.HashMap;

@SuppressWarnings("ALL")
public class Encryption {

    // Constants
    private static final Charset CHARSET = StandardCharsets.UTF_8;
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static final String ALGORITHM = "SHA-256";

    // Attributes
    private final SecretKey secretKey;
    private final HashMap<String, String> cache;

    // Constructor
    public Encryption(String key) {

        // Generate secret key using SHA-256 hash of the bot token
        try {
            secretKey = new SecretKeySpec(MessageDigest.getInstance(ALGORITHM).digest(key.getBytes(CHARSET)), "AES"); // Key for AES encryption
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        // Initialize the cache
        cache = new HashMap<>();
    }

    // Encrypt token
    public String encrypt(String token) {

        // Check cache for encrypted token
        if (cache.containsValue(token)) for (HashMap.Entry<String, String> entry : cache.entrySet()) if (entry.getValue().equals(token)) return entry.getKey();
        try {

            // Create a new Cipher instance and initialize it to ENCRYPT mode
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            // Perform encryption
            byte[] encryptedToken = cipher.doFinal(token.getBytes(CHARSET));
            String encodedToken = Base64.getEncoder().encodeToString(encryptedToken);

            // Store the encrypted token in the cache
            cache.put(encodedToken, token);
            return encodedToken; // Return the encrypted token
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    // Decrypt token
    public String decrypt(String encryptedToken) {

        // Check cache for decrypted token
        if (cache.containsKey(encryptedToken)) return cache.get(encryptedToken);
        try {
            // Create a new Cipher instance and initialize it to DECRYPT mode
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            // Perform decryption
            byte[] decodedToken = Base64.getDecoder().decode(encryptedToken);
            byte[] originalToken = cipher.doFinal(decodedToken);
            String token = new String(originalToken, CHARSET);

            // Store the decrypted token in the cache
            cache.put(encryptedToken, token);
            return token; // Return the decrypted token
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    // Add to cache
    public void add(String key, String value) {
        cache.put(key, value);
    }

    // Remove from cache
    public void remove(String key) {
        cache.remove(key);
    }

    // Clear cache
    public void clear() {
        cache.clear();
    }

    // Get from cache
    public String get(String key) {
        return cache.get(key);
    }

    // Get cache
    public HashMap<String, String> getCache() {
        return cache;
    }

    // Get secret key
    public SecretKey getSecretKey() {
        return secretKey;
    }

    // Get charset
    public Charset getCharset() {
        return CHARSET;
    }

    // Get transformation
    public String getTransformation() {
        return TRANSFORMATION;
    }

    // Get algorithm
    public String getAlgorithm() {
        return ALGORITHM;
    }
}