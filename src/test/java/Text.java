import de.MCmoderSD.encryption.core.Encryption;
import de.MCmoderSD.encryption.enums.Algorithm;

import java.nio.charset.Charset;

public class Text {

    public static void main(String[] args) {

        // Variables
        String originalString = "Hello, World!";
        String password = "securepassword";
        Algorithm algorithm = Algorithm.AES_ECB_PKCS5;


        // Initialize Encryption
        Encryption encryption = new Encryption(password, algorithm, Charset.defaultCharset());

        // Print original string
        System.out.println("Original String: " + originalString + "\n");

        // Encrypt first time
        String encryptedString1 = encryption.encrypt(originalString);
        System.out.println("Encrypted String 1: " + encryptedString1);

        // Encrypt second time
        String encryptedString2 = encryption.encrypt(originalString);
        System.out.println("Encrypted String 2: " + encryptedString2 + "\n");

        // Decrypt both
        String decryptedString1 = encryption.decrypt(encryptedString1);
        String decryptedString2 = encryption.decrypt(encryptedString2);

        // Output results
        System.out.println("Decrypted String 1: " + decryptedString1);
        System.out.println("Decrypted String 2: " + decryptedString2);
    }
}