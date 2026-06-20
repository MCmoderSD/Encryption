import de.MCmoderSD.encryption.core.Encryption;
import de.MCmoderSD.encryption.enums.Hash;
import de.MCmoderSD.encryption.enums.Transformer;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Base64;

record User(String ssn) implements Serializable { }

void main() {

    // Variables
    var password = "secure-password";
    var user = new User("123456789");
    var charset = Charset.defaultCharset();
    var hash = Hash.SHA256;
    var transformer = Transformer.AES_ECB_PKCS5;

    // Initialize Encryption
    var encryption = new Encryption(password, charset, hash, transformer);

    // Serialize user object to byte array
    var userBytes = Encryption.serialize(user);
    IO.println("Original User Bytes: " + Base64.getEncoder().encodeToString(userBytes) + "\n");

    // Benchmark Encryption
    var encryptTime1 = benchEncrypt(encryption, userBytes);
    var encryptTime2 = benchEncrypt(encryption, userBytes);
    var encryptTime3 = benchEncrypt(encryption, userBytes);
    var encryptedUserBytes = encryption.encrypt(userBytes);

    // Benchmark Decryption
    var decryptTime1 = benchDecrypt(encryption, encryptedUserBytes);
    var decryptTime2 = benchDecrypt(encryption, encryptedUserBytes);
    var decryptTime3 = benchDecrypt(encryption, encryptedUserBytes);
    var decryptedUserBytes = encryption.decrypt(encryptedUserBytes);

    // Deserialize byte array back to user object
    var decryptedUser = (User) Encryption.deserialize(decryptedUserBytes);
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