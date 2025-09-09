import de.MCmoderSD.encryption.core.Encryption;
import de.MCmoderSD.encryption.enums.Algorithm;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Base64;

public class Object {

    public record User(String ssn) implements Serializable { }

    public static void main(String[] args) {

        // Variables
        User user = new User("123456789");
        String password = "securepassword";
        Algorithm algorithm = Algorithm.AES_ECB_PKCS5;

        // Initialize Encryption
        Encryption encryption = new Encryption(password, algorithm, Charset.defaultCharset());

        // Serialize user object to byte array
        byte[] userBytes = Encryption.serialize(user);
        System.out.println("Original User Bytes: " + Base64.getEncoder().encodeToString(userBytes) + "\n");

        // Encrypt first time
        byte[] encryptedBytes1 = encryption.encrypt(userBytes);
        System.out.println("Encrypted User Bytes 1: " + Base64.getEncoder().encodeToString(encryptedBytes1));

        // Encrypt second time
        byte[] encryptedBytes2 = encryption.encrypt(userBytes);
        System.out.println("Encrypted User Bytes 2: " + Base64.getEncoder().encodeToString(encryptedBytes2) + "\n");

        // Decrypt both
        byte[] decryptedBytes1 = encryption.decrypt(encryptedBytes1);
        byte[] decryptedBytes2 = encryption.decrypt(encryptedBytes2);

        // Output results
        System.out.println("Decrypted User Bytes 1: " + Base64.getEncoder().encodeToString(decryptedBytes1));
        System.out.println("Decrypted User Bytes 2: " + Base64.getEncoder().encodeToString(decryptedBytes2));
    }

}
