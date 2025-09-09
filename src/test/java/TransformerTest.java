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

    public static void main(String[] args) {

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

        for (Trans tran : transformers) {
            testTrans(tran, generateKey(password, charset, "SHA-256", tran.algorithm.name()), new SecureRandom(iv));
        }
    }

    private static SecretKey generateKey(String password, Charset charset, String hash, String algorithm) {
        try {
            var digest = MessageDigest.getInstance(hash);
            byte[] keyBytes = digest.digest(password.getBytes(charset));
            return new SecretKeySpec(keyBytes, algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to generate key", e);
        }
    }

    private static boolean testTrans(Trans trans, SecretKey secretKey, SecureRandom secureRandom) {
        try {
            Cipher cipher = Cipher.getInstance(trans.toString());
            if (trans.mode.needsIv()) cipher.init(Cipher.ENCRYPT_MODE, secretKey, secureRandom);
            else cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            System.out.println(trans.toString());
            return true;
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
            System.err.println(trans.toString() + " | " + e.getMessage());
            return false;
        }
    }
}
