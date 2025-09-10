package de.MCmoderSD.encryption.core;

import de.MCmoderSD.encryption.enums.*;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.Charset;
import java.security.*;
import java.util.Base64;
import java.util.concurrent.ConcurrentHashMap;

import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

/**
 * The {@code Encryption} class provides a high-level API for symmetric encryption and decryption
 * of byte arrays, strings, and serializable objects using configurable algorithms,
 * hash functions, modes, and padding schemes.
 * <p>
 * It automatically generates a {@link SecretKeySpec} from a password and supports
 * caching of encrypted/decrypted values when the chosen mode does not require an IV.
 * </p>
 */
@SuppressWarnings("ALL")
public class Encryption {

    // Attributes
    private final Charset charset;
    private final Hash hash;
    private final Transformer transformer;
    private final Algorithm algorithm;
    private final Mode mode;
    private final Padding padding;
    private final SecretKeySpec key;

    // Caches                                                       // Key: Input, Value: Output
    private final ConcurrentHashMap<byte[], byte[]> encryptCache;   // Decrypted to Encrypted
    private final ConcurrentHashMap<byte[], byte[]> decryptCache;   // Encrypted to Decrypted

    /**
     * Constructs an {@code Encryption} instance using the platform default {@link Charset}.
     *
     * @param password     the password to derive the encryption key
     * @param hash         the hash algorithm used to derive the key
     * @param transformer  the transformer defining algorithm, mode, and padding
     */
    public Encryption(String password, Hash hash, Transformer transformer) {
        this(password, Charset.defaultCharset(), hash, transformer);
    }

    /**
     * Constructs an {@code Encryption} instance with a specified charset.
     *
     * @param password     the password to derive the encryption key
     * @param charset      the charset to encode the password
     * @param hash         the hash algorithm used to derive the key
     * @param transformer  the transformer defining algorithm, mode, and padding
     */
    public Encryption(String password, Charset charset, Hash hash, Transformer transformer) {

        // Set Attributes
        this.charset = charset;
        this.hash = hash;
        this.transformer = transformer;
        algorithm = transformer.getAlgorithm();
        mode = transformer.getMode();
        padding = transformer.getPadding();

        // Generate SecretKeySpec
        key = generateKey(password, charset, hash, algorithm);

        // Initialize Caches
        encryptCache = new ConcurrentHashMap<>();
        decryptCache = new ConcurrentHashMap<>();
    }

    /**
     * Generates a {@link SecretKeySpec} from the given password, charset, hash, and algorithm.
     *
     * @param password   the password to derive the key
     * @param charset    the charset for encoding the password
     * @param hash       the hash algorithm used for digesting the password
     * @param algorithm  the encryption algorithm
     * @return a {@link SecretKeySpec} suitable for the algorithm
     */
    private static SecretKeySpec generateKey(String password, Charset charset, Hash hash, Algorithm algorithm) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(hash.getName());
            byte[] passwordHash = messageDigest.digest(password.getBytes(charset));
            byte[] keyBytes = new byte[algorithm.getKeySizes().getLast()];
            System.arraycopy(passwordHash, 0, keyBytes, 0, Math.min(passwordHash.length, keyBytes.length));
            return new SecretKeySpec(keyBytes, algorithm.name());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Encrypts a byte array.
     *
     * @param decryptedData the plaintext data
     * @return the encrypted data
     * @throws RuntimeException if encryption fails
     */
    public byte[] encrypt(byte[] decryptedData) {
        if (!mode.needsIV() && encryptCache.containsKey(decryptedData)) return encryptCache.get(decryptedData);
        try {

            // Encrypt
            Cipher cipher = Cipher.getInstance(transformer.getTransformation());
            cipher.init(ENCRYPT_MODE, key);
            byte[] encryptedData = cipher.doFinal(decryptedData);

            // Cache if no IV is needed
            if (!mode.needsIV()) {
                encryptCache.put(decryptedData, encryptedData);
                decryptCache.put(encryptedData, decryptedData);
            }

            // Return encrypted data
            return encryptedData;
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("Failed to encrypt data", e);
        }
    }

    /**
     * Decrypts a byte array.
     *
     * @param encryptedData the encrypted data
     * @return the decrypted data
     * @throws RuntimeException if decryption fails
     */
    public byte[] decrypt(byte[] encryptedData) {
        if (!mode.needsIV() && decryptCache.containsKey(encryptedData)) return decryptCache.get(encryptedData);
        try {

            // Decrypt
            Cipher cipher = Cipher.getInstance(transformer.getTransformation());
            cipher.init(DECRYPT_MODE, key);
            byte[] decryptedData = cipher.doFinal(encryptedData);

            // Cache if no IV is needed
            if (!mode.needsIV()) {
                decryptCache.put(encryptedData, decryptedData);
                encryptCache.put(decryptedData, encryptedData);
            }

            // Return decrypted data
            return decryptedData;
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("Failed to decrypt data", e);
        }
    }

    /**
     * Encrypts a string using the configured charset.
     *
     * @param decryptedString the plaintext string
     * @return the encrypted string in Base64 encoding
     */
    public String encrypt(String decryptedString) {
        return encrypt(decryptedString, charset);
    }

    /**
     * Encrypts a string with a specified charset.
     *
     * @param decryptedString the plaintext string
     * @param charset         the charset to encode the string
     * @return the encrypted string in Base64 encoding
     */
    public String encrypt(String decryptedString, Charset charset) {
        return Base64.getEncoder().encodeToString(encrypt(decryptedString.getBytes(charset)));
    }

    /**
     * Decrypts a Base64-encoded string using the configured charset.
     *
     * @param encryptedString the encrypted Base64 string
     * @return the decrypted plaintext string
     */
    public String decrypt(String encryptedString) {
        return decrypt(encryptedString, charset);
    }

    /**
     * Decrypts a Base64-encoded string with a specified charset.
     *
     * @param encryptedString the encrypted Base64 string
     * @param charset         the charset to decode the result
     * @return the decrypted plaintext string
     */
    public String decrypt(String encryptedString, Charset charset) {
        return new String(decrypt(Base64.getDecoder().decode(encryptedString)), charset);
    }

    /**
     * Serializes a {@link Serializable} object into a byte array.
     *
     * @param object the object to serialize
     * @return the serialized byte array
     * @throws RuntimeException if serialization fails
     */
    public static byte[] serialize(Serializable object) {
        try (
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos)
        ) {
            oos.writeObject(object);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize object", e);
        }
    }

    /**
     * Deserializes a byte array into an object.
     *
     * @param bytes the serialized byte array
     * @return the deserialized object
     * @throws RuntimeException if deserialization fails
     */
    public static Object deserialize(byte[] bytes) {
        try (
                ByteArrayInputStream bai = new ByteArrayInputStream(bytes);
                ObjectInputStream ois = new ObjectInputStream(bai)
        ) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to deserialize object", e);
        }
    }

    /**
     * @return the charset used for encoding/decoding strings
     */
    public Charset getCharset() {
        return charset;
    }

    /**
     * @return the hash algorithm used for key derivation
     */
    public Hash getHash() {
        return hash;
    }

    /**
     * @return the transformer defining algorithm, mode, and padding
     */
    public Transformer getTransformer() {
        return transformer;
    }

    /**
     * @return the encryption algorithm
     */
    public Algorithm getAlgorithm() {
        return algorithm;
    }

    /**
     * @return the cipher mode of operation
     */
    public Mode getMode() {
        return mode;
    }

    /**
     * @return the padding scheme
     */
    public Padding getPadding() {
        return padding;
    }

    /**
     * @return the generated secret key
     */
    public SecretKeySpec getKey() {
        return key;
    }

    /**
     * Clears all cached encryption and decryption results.
     */
    public void clearCache() {
        encryptCache.clear();
        decryptCache.clear();
    }
}