package de.MCmoderSD.encryption.enums;

public enum Hash {

    // Hash algorithms
    SHA256("SHA-256", 32),      // 256 bits = 32 bytes
    SHA3_256("SHA3-256", 32);   // 256 bits = 32 bytes

    // Attributes
    private final String name;
    private final int bytes;
    private final int bits;

    // Constructor
    Hash(String name, int bytes) {
        this.name = name;
        this.bytes = bytes;
        bits = bytes * 8;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getBytes() {
        return bytes;
    }

    public int getBits() {
        return bits;
    }
}