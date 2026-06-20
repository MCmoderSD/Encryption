import de.MCmoderSD.encryption.core.Encryption;
import de.MCmoderSD.encryption.enums.Hash;
import de.MCmoderSD.encryption.enums.Transformer;

import java.nio.charset.Charset;

void main() {

    // Variables
    var originalString = "Hello, World!";
    var password = "secure-password";
    var charset = Charset.defaultCharset();
    var hash = Hash.SHA256;
    var transformer = Transformer.AES_ECB_PKCS5;

    // Initialize Encryption
    var encryption = new Encryption(password, charset, hash, transformer);

    // Print original string
    IO.println("Original String: " + originalString + "\n");

    // Benchmark Encryption
    var encryptTime1 = benchEncrypt(encryption, originalString);
    var encryptTime2 = benchEncrypt(encryption, originalString);
    var encryptTime3 = benchEncrypt(encryption, originalString);
    var encryptedString = encryption.encrypt(originalString, charset);

    // Benchmark Decryption
    var decryptTime1 = benchDecrypt(encryption, encryptedString);
    var decryptTime2 = benchDecrypt(encryption, encryptedString);
    var decryptTime3 = benchDecrypt(encryption, encryptedString);
    var decryptedString = encryption.decrypt(encryptedString, charset);

    // Print results
    IO.println("Encrypted String: " + encryptedString);
    IO.println("Decrypted String: " + decryptedString + "\n");
    IO.println("Encryption Times (µs): " + encryptTime1 / 1000 + ", " + encryptTime2 / 1000 + ", " + encryptTime3 / 1000);
    IO.println("Decryption Times (µs): " + decryptTime1 / 1000 + ", " + decryptTime2 / 1000 + ", " + decryptTime3 / 1000);
}

private static long benchEncrypt(Encryption encryption, String input) {
    long startTime, endTime;
    startTime = System.nanoTime();
    encryption.encrypt(input);
    endTime = System.nanoTime();
    return endTime - startTime;
}

private static long benchDecrypt(Encryption encryption, String input) {
    long startTime, endTime;
    startTime = System.nanoTime();
    encryption.decrypt(input);
    endTime = System.nanoTime();
    return endTime - startTime;
}