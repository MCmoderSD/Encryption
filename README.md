# Encryption

## Description
A simple and easy-to-use Java library for encrypting and decrypting strings and objects using various hashing and transformation algorithms.

## Features
- Text and object encryption/decryption
- Serialization and deserialization of objects to/from byte arrays
- Supports multiple hashing algorithms: SHA-256, SHA3-256
- Supports multiple transformation algorithms: AES, DES, TripleDES/DESede
- Caching for improved performance
- Thread-safe implementation

## Usage

### Maven
Make sure you have my Sonatype Nexus OSS repository added to your `pom.xml` file:
```xml
<repositories>
    <repository>
        <id>Nexus</id>
        <name>Sonatype Nexus</name>
        <url>https://mcmodersd.de/nexus/repository/maven-releases/</url>
    </repository>
</repositories>
```
Add the dependency to your `pom.xml` file:
```xml
<dependency>
    <groupId>de.MCmoderSD</groupId>
    <artifactId>Encryption</artifactId>
    <version>1.4.0</version>
</dependency>
```


## Usage Example
```java
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
```

## Benchmark Example
### String Encryption/Decryption
```java
import de.MCmoderSD.encryption.core.Encryption;
import de.MCmoderSD.encryption.enums.Hash;
import de.MCmoderSD.encryption.enums.Transformer;

import java.nio.charset.Charset;

void main() {

    // Variables
    String originalString = "Hello, World!";
    String password = "secure-password";
    Charset charset = Charset.defaultCharset();
    Hash hash = Hash.SHA256;
    Transformer transformer = Transformer.AES_ECB_PKCS5;

    // Initialize Encryption
    Encryption encryption = new Encryption(password, charset, hash, transformer);

    // Print original string
    IO.println("Original String: " + originalString + "\n");

    // Benchmark Encryption
    long encryptTime1 = benchEncrypt(encryption, originalString);
    long encryptTime2 = benchEncrypt(encryption, originalString);
    long encryptTime3 = benchEncrypt(encryption, originalString);
    String encryptedString = encryption.encrypt(originalString, charset);

    // Benchmark Decryption
    long decryptTime1 = benchDecrypt(encryption, encryptedString);
    long decryptTime2 = benchDecrypt(encryption, encryptedString);
    long decryptTime3 = benchDecrypt(encryption, encryptedString);
    String decryptedString = encryption.decrypt(encryptedString, charset);

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
```

### Object/byte[] Encryption/Decryption
```java
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
```