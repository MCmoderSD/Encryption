import de.MCmoderSD.encryption.core.Encryption;
import de.MCmoderSD.encryption.enums.Hash;
import de.MCmoderSD.encryption.enums.Transformer;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Base64;

record User(String ssn) implements Serializable { }

void main() {

    // Variables
    String password = "secure-password";
    User user = new User("123456789");
    Charset charset = Charset.defaultCharset();
    Hash hash = Hash.SHA256;
    Transformer transformer = Transformer.AES_ECB_PKCS5;

    // Initialize Encryption
    Encryption encryption = new Encryption(password, charset, hash, transformer);

    // Serialize user object to byte array
    byte[] userBytes = Encryption.serialize(user);
    IO.println("Original User Bytes: " + Base64.getEncoder().encodeToString(userBytes) + "\n");

    // Benchmark Encryption
    long encryptTime1 = benchEncrypt(encryption, userBytes);
    long encryptTime2 = benchEncrypt(encryption, userBytes);
    long encryptTime3 = benchEncrypt(encryption, userBytes);
    byte[] encryptedUserBytes = encryption.encrypt(userBytes);

    // Benchmark Decryption
    long decryptTime1 = benchDecrypt(encryption, encryptedUserBytes);
    long decryptTime2 = benchDecrypt(encryption, encryptedUserBytes);
    long decryptTime3 = benchDecrypt(encryption, encryptedUserBytes);
    byte[] decryptedUserBytes = encryption.decrypt(encryptedUserBytes);

    // Deserialize byte array back to user object
    User decryptedUser = (User) Encryption.deserialize(decryptedUserBytes);
    IO.println("Decrypted User SSN: " + decryptedUser.ssn() + "\n");

    // Print results
    IO.println("Encrypted User Bytes: " + Base64.getEncoder().encodeToString(encryptedUserBytes));
    IO.println("Decrypted User Bytes: " + Base64.getEncoder().encodeToString(decryptedUserBytes));
    IO.println("Encryption Times (µs): " + encryptTime1 / 1000 + ", " + encryptTime2 / 1000 + ", " + encryptTime3 / 1000);
    IO.println("Decryption Times (µs): " + decryptTime1 / 1000 + ", " + decryptTime2 / 1000 + ", " + decryptTime3 / 1000);
}

private static long benchEncrypt(Encryption encryption, byte[] input) {
    long startTime, endTime;
    startTime = System.nanoTime();
    encryption.encrypt(input);
    endTime = System.nanoTime();
    return endTime - startTime;
}

private static long benchDecrypt(Encryption encryption, byte[] input) {
    long startTime, endTime;
    startTime = System.nanoTime();
    encryption.decrypt(input);
    endTime = System.nanoTime();
    return endTime - startTime;
}