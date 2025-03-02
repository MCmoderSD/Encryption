# Encryption

## Description
A simple Java encryption library for encrypting and decrypting text.


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
    <version>1.1.0</version>
</dependency>
```


## Usage Example
```java
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
```