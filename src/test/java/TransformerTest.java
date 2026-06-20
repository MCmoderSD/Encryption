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

        var transformers = new ArrayList<Trans>();
        for (var alg : Algorithm.values()) {
            for (var mode : Mode.values()) {
                for (var pad : Padding.values()) {
                    transformers.add(new Trans(alg, mode, pad));
                }
            }
        }

        var iv = new byte[16];
        var random = new SecureRandom();
        random.nextBytes(iv);

        for (var trans : Transformer.values()) {
            Thread.sleep(100);
            testTrans(trans, generateKey(password, charset, "SHA-256", trans.getAlgorithm()), new SecureRandom(iv));
        }
    }

    private static SecretKey generateKey(String password, Charset charset, String hash, Algorithm algorithm) {
        try {
            var digest = MessageDigest.getInstance(hash);
            var keyBytes = digest.digest(password.getBytes(charset));

            keyBytes = switch (algorithm.name()) {
                case "AES" -> {
                    var key = new byte[32];
                    System.arraycopy(keyBytes, 0, key, 0, Math.min(keyBytes.length, 32));
                    yield key;
                }
                case "DES" -> {
                    var key = new byte[8];
                    System.arraycopy(keyBytes, 0, key, 0, Math.min(keyBytes.length, 8));
                    yield key;
                }
                case "DESede" -> {
                    var key = new byte[24];
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
            var cipher = Cipher.getInstance(trans.getTransformation());
            if (trans.getMode().needsIV()) cipher.init(Cipher.ENCRYPT_MODE, secretKey, secureRandom);
            else cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            IO.println(trans.getTransformation());
            return true;
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
            System.err.println(trans.getTransformation() + " | " + e.getMessage());
            return false;
        }
    }
}