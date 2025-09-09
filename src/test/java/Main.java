import de.MCmoderSD.encryption.enums.Algorithm;
import de.MCmoderSD.encryption.enums.Mode;
import de.MCmoderSD.encryption.enums.Padding;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Main {

    public static void main(String[] args) {
//        System.out.println("--- Text Encryption/Decryption ---");
//        Text.main(args);
//
//        System.out.println("\n--- Object Encryption/Decryption ---\n");
//        Object.main(args);

        String password = "securepassword";
        Charset charset = Charset.defaultCharset();
        byte[] passwordBytes = password.getBytes(charset);


        }
}