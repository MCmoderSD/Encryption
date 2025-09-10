import de.MCmoderSD.encryption.enums.Algorithm;
import de.MCmoderSD.encryption.enums.Mode;
import de.MCmoderSD.encryption.enums.Padding;
import de.MCmoderSD.encryption.enums.Transformer;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class TransformerTest {

    private static final String input = "Hello, World!";
    private static final String password = "securepassword";
    private static final Charset charset = Charset.defaultCharset();

    public record Trans(Algorithm algorithm, Mode mode, Padding padding) {
        @Override
        public String toString() {
            return algorithm + "/" + mode + "/" + padding;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        ArrayList<Trans> transformers = new ArrayList<>();
        for (var alg : Algorithm.values()) {
            for (var mode : Mode.values()) {
                for (var pad : Padding.values()) {
                    transformers.add(new Trans(alg, mode, pad));
                }
            }
        }

        byte[] iv = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        for (Transformer tran : Transformer.values()) {
            Thread.sleep(100);
            testTrans(tran, generateKey(password, charset, "SHA-256", tran.getAlgorithm()), new SecureRandom(iv));
        }
    }

    private static SecretKey generateKey(String password, Charset charset, String hash, Algorithm algorithm) {
        try {
            var digest = MessageDigest.getInstance(hash);
            byte[] keyBytes = digest.digest(password.getBytes(charset));

            keyBytes = switch (algorithm.name()) {
                case "AES" -> {
                    byte[] key = new byte[32];
                    System.arraycopy(keyBytes, 0, key, 0, Math.min(keyBytes.length, 32));
                    yield key;
                }
                case "DES" -> {
                    byte[] key = new byte[8];
                    System.arraycopy(keyBytes, 0, key, 0, Math.min(keyBytes.length, 8));
                    yield key;
                }
                case "DESede" -> {
                    byte[] key = new byte[24];
                    System.arraycopy(keyBytes, 0, key, 0, Math.min(keyBytes.length, 24));
                    yield key;
                }
                default -> throw new IllegalStateException("Unexpected value: " + algorithm);
            };

            return new SecretKeySpec(keyBytes, algorithm.name());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to generate key", e);
        }
    }

    private static boolean testTrans(Transformer trans, SecretKey secretKey, SecureRandom secureRandom) {
        try {
            Cipher cipher = Cipher.getInstance(trans.getTransformation());
            if (trans.getMode().needsIV()) cipher.init(Cipher.ENCRYPT_MODE, secretKey, secureRandom);
            else cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            System.out.println(trans.getTransformation());
            return true;
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
            System.err.println(trans.getTransformation() + " | " + e.getMessage());
            return false;
        }
    }
}