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

    // Constructor
    public Encryption(String password, Hash hash, Transformer transformer) {
        this(password, Charset.defaultCharset(), hash, transformer);
    }

    // Full Constructor
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

    // Generate SecretKeySpec from password
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

    // Encrypt byte[]
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
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException |
                 InvalidKeyException e) {
            throw new RuntimeException("Failed to encrypt data", e);
        }
    }

    // Decrypt byte[]
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
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException("Failed to decrypt data", e);
        }
    }

    // Encrypt String
    public String encrypt(String decryptedString) {
        return encrypt(decryptedString, charset);
    }

    public String encrypt(String decryptedString, Charset charset) {
        return Base64.getEncoder().encodeToString(encrypt(decryptedString.getBytes(charset)));
    }

    // Decrypt String
    public String decrypt(String encryptedString) {
        return decrypt(encryptedString, charset);
    }

    public String decrypt(String encryptedString, Charset charset) {
        return new String(decrypt(Base64.getDecoder().decode(encryptedString)), charset);
    }

    // Serialize Object
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

    // Deserialize Object
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

    // Getters
    public Charset getCharset() {
        return charset;
    }

    public Hash getHash() {
        return hash;
    }

    public Transformer getTransformer() {
        return transformer;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public Mode getMode() {
        return mode;
    }

    public Padding getPadding() {
        return padding;
    }

    public SecretKeySpec getKey() {
        return key;
    }

    // Setter
    public void clearCache() {
        encryptCache.clear();
        decryptCache.clear();
    }
}