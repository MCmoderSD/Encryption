package de.MCmoderSD.encryption.core;

import de.MCmoderSD.encryption.enums.Algorithm;
import de.MCmoderSD.encryption.enums.Mode;
import de.MCmoderSD.encryption.enums.Padding;
import de.MCmoderSD.encryption.enums.Transformer;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.Charset;
import java.security.*;
import java.util.Base64;

import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

public class Encryption {

    // Attributes
    private final Charset charset;
    private final Transformer transformer;
    private final SecretKey secretKey;

    // Constructor
    public Encryption(String password, Charset charset, Transformer transformer) {

        // Set Charset and Transformer
        this.charset = charset;
        this.transformer = transformer;


        // Generate SecretKey
        this.secretKey = generateKey(password, charset, "SHA-256", transformer.getAlgorithm().name());
    }

    private static SecretKeySpec generateKey(String password, Charset charset, String hash, String algorithm) {
        try {
            MessageDigest digest = MessageDigest.getInstance(hash);
            byte[] keyBytes = digest.digest(password.getBytes(charset));
            return new SecretKeySpec(keyBytes, algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to generate key", e);
        }
    }

    // Encrypt byte[]
    public byte[] encrypt(byte[] decryptedData) {
        try {
            Cipher encryptCipher = Cipher.getInstance(transformer.getTransformation());
            encryptCipher.init(ENCRYPT_MODE, secretKey);
            return encryptCipher.doFinal(decryptedData);
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException("Failed to encrypt data", e);
        }
    }

    // Decrypt byte[]
    public byte[] decrypt(byte[] encryptedData) {
        try {
            Cipher decryptCipher = Cipher.getInstance(transformer.getTransformation());
            decryptCipher.init(DECRYPT_MODE, secretKey);
            return decryptCipher.doFinal(encryptedData);
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
}