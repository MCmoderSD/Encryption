import de.MCmoderSD.encryption.core.Encryption;
import de.MCmoderSD.encryption.enums.Hash;
import de.MCmoderSD.encryption.enums.Transformer;

import java.util.Arrays;

void main() {

    // Variables
    String originalString = "Hello, World!";
    String password = "secure-password";
    Hash hash = Hash.SHA256;
    Transformer transformer = Transformer.AES_ECB_PKCS5;

    // Initialize Encryption
    Encryption encryption = new Encryption(password, hash, transformer);

    // String Encryption/Decryption
    IO.println("\n --- String Encryption/Decryption --- \n");

    // Print original string
    IO.println("Original String: " + originalString + "\n");

    // Encrypt string
    String encryptedString1 = encryption.encrypt(originalString);
    String encryptedString2 = encryption.encrypt(originalString);
    String encryptedString3 = encryption.encrypt(originalString);

    // Decrypt string
    String decryptedString1 = encryption.decrypt(encryptedString1);
    String decryptedString2 = encryption.decrypt(encryptedString2);
    String decryptedString3 = encryption.decrypt(encryptedString3);

    // Print results
    IO.println("Encrypted String 1: " + encryptedString1);
    IO.println("Encrypted String 2: " + encryptedString2);
    IO.println("Encrypted String 3: " + encryptedString3 + "\n");
    IO.println("Decrypted String 1: " + decryptedString1);
    IO.println("Decrypted String 2: " + decryptedString2);
    IO.println("Decrypted String 3: " + decryptedString3 + "\n");


    // Byte Array Encryption/Decryption
    IO.println("\n --- Byte Array Encryption/Decryption --- \n");


    // Serialize original string to byte array and print bytes
    byte[] originalBytes = Encryption.serialize(originalString);
    IO.println("Original Bytes: " + Arrays.toString(originalBytes) + "\n");

    // Encrypt bytes
    byte[] encryptedBytes1 = encryption.encrypt(originalBytes);
    byte[] encryptedBytes2 = encryption.encrypt(originalBytes);
    byte[] encryptedBytes3 = encryption.encrypt(originalBytes);

    // Decrypt bytes
    byte[] decryptedBytes1 = encryption.decrypt(encryptedBytes1);
    byte[] decryptedBytes2 = encryption.decrypt(encryptedBytes2);
    byte[] decryptedBytes3 = encryption.decrypt(encryptedBytes3);

    // Print results
    IO.println("Encrypted Bytes 1: " + Arrays.toString(encryptedBytes1));
    IO.println("Encrypted Bytes 2: " + Arrays.toString(encryptedBytes2));
    IO.println("Encrypted Bytes 3: " + Arrays.toString(encryptedBytes3) + "\n");
    IO.println("Decrypted Bytes 1: " + Arrays.toString(decryptedBytes1));
    IO.println("Decrypted Bytes 2: " + Arrays.toString(decryptedBytes2));
    IO.println("Decrypted Bytes 3: " + Arrays.toString(decryptedBytes3));
}