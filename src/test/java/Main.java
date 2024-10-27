import de.MCmoderSD.encryption.Encryption;

public class Main {
    public static void main(String[] args) {

        // Create a new instance of the Encryption class
        Encryption encryption = new Encryption("MyStrongPassword");

        // Encrypt a string
        String encryptedString = encryption.encrypt("Hello, World!");

        // Decrypt the encrypted string
        String decryptedString = encryption.decrypt(encryptedString);

        // Print the encrypted and decrypted strings
        System.out.println("Encrypted String: " + encryptedString);
        System.out.println("Decrypted String: " + decryptedString);
    }
}