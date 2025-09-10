package de.MCmoderSD.encryption.enums;

/**
 * Enumeration of supported cryptographic hash functions
 * used for password-to-key derivation.
 */
@SuppressWarnings("ALL")
public enum Hash {

    /**
     * SHA-256 (32-byte / 256-bit digest).
     */
    SHA256("SHA-256", 32),

    /**
     * SHA3-256 (32-byte / 256-bit digest).
     */
    SHA3_256("SHA3-256", 32);

    // Attributes
    private final String name;
    private final int bytes;
    private final int bits;

    /**
     * Creates a new {@link Hash} type.
     *
     * @param name  the algorithm name (as accepted by {@link java.security.MessageDigest})
     * @param bytes the digest length in bytes
     */
    Hash(String name, int bytes) {
        this.name = name;
        this.bytes = bytes;
        bits = bytes * 8;
    }

    /**
     * @return the algorithm name as recognized by {@link java.security.MessageDigest}
     */
    public String getName() {
        return name;
    }

    /**
     * @return the digest length in bytes
     */
    public int getBytes() {
        return bytes;
    }

    /**
     * @return the digest length in bits
     */
    public int getBits() {
        return bits;
    }
}