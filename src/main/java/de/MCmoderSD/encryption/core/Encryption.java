package de.MCmoderSD.encryption.core;

import de.MCmoderSD.encryption.enums.Algorithm;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;

import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

public class Encryption {

    // Constants
    private static final String HASH_ALGORITHM = "SHA-256";
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    // Attributes
    private final Algorithm algorithm;
    private final SecretKey secretKey;

    // Constructor
    public Encryption(String password, Algorithm algorithm) throws NoSuchAlgorithmException {

        // Set Algorithm
        this.algorithm = algorithm;

        // Initialize SecretKey
        MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
        byte[] keyBytes = digest.digest(password.getBytes(CHARSET));
        secretKey = new SecretKeySpec(keyBytes, "AES");
    }

    public byte[] encrypt(byte[] decryptedData) {
        try {
            Cipher encryptCipher = Cipher.getInstance(algorithm.getTransformation());
            encryptCipher.init(ENCRYPT_MODE, secretKey);
            return encryptCipher.doFinal(decryptedData);
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException("Failed to encrypt data", e);
        }
    }

    public byte[] decrypt(byte[] encryptedData) {
        try {
            Cipher decryptCipher = Cipher.getInstance(algorithm.getTransformation());
            decryptCipher.init(DECRYPT_MODE, secretKey);
            return decryptCipher.doFinal(encryptedData);
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException("Failed to decrypt data", e);
        }
    }
}