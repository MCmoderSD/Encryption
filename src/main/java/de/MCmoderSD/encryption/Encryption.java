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

/**
 * The {@code Encryption} class provides methods for encrypting and decrypting strings
 * using AES encryption with an SHA-256 generated secret key. It includes a cache for
 * storing previously encrypted and decrypted values for quick retrieval.
 */
@SuppressWarnings("ALL")
public class Encryption {

    /**
     * Charset used for encoding and decoding strings in encryption.
     */
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    /**
     * Transformation string for the AES encryption algorithm.
     */
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    /**
     * Algorithm used to generate the secret key (SHA-256).
     */
    private static final String ALGORITHM = "SHA-256";

    /**
     * Secret key for AES encryption, derived from an SHA-256 hash of a provided key.
     */
    private final SecretKey secretKey;

    /**
     * Cache storing encrypted and decrypted values to avoid repeated computations.
     */
    private final HashMap<String, String> cache;

    /**
     * Constructs an {@code Encryption} instance using a provided key to generate a secret key.
     *
     * @param key The key used to generate the secret AES key.
     */
    public Encryption(String key) {

        // Generate secret key using SHA-256 hash of the bot token
        try {
            secretKey = new SecretKeySpec(MessageDigest.getInstance(ALGORITHM).digest(key.getBytes(CHARSET)), "AES");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        // Initialize the cache
        cache = new HashMap<>();
    }

    /**
     * Encrypts a string token using AES encryption and returns its Base64 encoded form.
     *
     * @param token The string token to encrypt.
     * @return The encrypted and Base64 encoded string token.
     */
    public String encrypt(String token) {
        if (cache.containsValue(token))
            for (HashMap.Entry<String, String> entry : cache.entrySet())
                if (entry.getValue().equals(token))
                    return entry.getKey();
        try {

            // Create a new Cipher instance and initialize it to ENCRYPT mode
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            // Perform encryption
            byte[] encryptedToken = cipher.doFinal(token.getBytes(CHARSET));
            String encodedToken = Base64.getEncoder().encodeToString(encryptedToken);

            // Store the encrypted token in the cache
            cache.put(encodedToken, token);
            return encodedToken;
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Decrypts a Base64 encoded, AES encrypted string and returns the original string.
     *
     * @param encryptedToken The Base64 encoded, AES encrypted string token to decrypt.
     * @return The decrypted original string token.
     */
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
            return token;
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a key-value pair to the cache.
     *
     * @param key   The key associated with the value.
     * @param value The value to store in the cache.
     */
    public void add(String key, String value) {
        cache.put(key, value);
    }

    /**
     * Removes a key-value pair from the cache.
     *
     * @param key The key of the cache entry to remove.
     */
    public void remove(String key) {
        cache.remove(key);
    }

    /**
     * Clears all entries in the cache.
     */
    public void clear() {
        cache.clear();
    }

    /**
     * Retrieves a value from the cache based on the given key.
     *
     * @param key The key to search for in the cache.
     * @return The value associated with the key, or {@code null} if the key is not found.
     */
    public String get(String key) {
        return cache.get(key);
    }

    /**
     * Returns the entire cache as a {@link HashMap}.
     *
     * @return The cache of encrypted and decrypted strings.
     */
    public HashMap<String, String> getCache() {
        return cache;
    }

    /**
     * Returns the secret key used for AES encryption.
     *
     * @return The {@link SecretKey} used in encryption and decryption.
     */
    public SecretKey getSecretKey() {
        return secretKey;
    }

    /**
     * Returns the charset used for encoding and decoding strings.
     *
     * @return The {@link Charset} used for character encoding.
     */
    public Charset getCharset() {
        return CHARSET;
    }

    /**
     * Returns the transformation string used for AES encryption.
     *
     * @return The transformation string.
     */
    public String getTransformation() {
        return TRANSFORMATION;
    }

    /**
     * Returns the algorithm used to generate the secret key.
     *
     * @return The algorithm string.
     */
    public String getAlgorithm() {
        return ALGORITHM;
    }
}