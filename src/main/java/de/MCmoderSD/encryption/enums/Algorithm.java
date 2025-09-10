package de.MCmoderSD.encryption.enums;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Enumeration of supported symmetric encryption algorithms and their valid key sizes.
 * <p>
 * Each algorithm specifies which key lengths (in bytes) are supported.
 * </p>
 */
@SuppressWarnings("ALL")
public enum Algorithm {

    /**
     * Advanced Encryption Standard (AES).
     * <p>
     * Supports 128, 192, and 256-bit keys → 16, 24, 32 bytes.
     * </p>
     */
    AES(16, 24, 32),

    /**
     * Data Encryption Standard (DES).
     * <p>
     * Uses a 56-bit key (+ parity) → 8 bytes.
     * </p>
     */
    DES(8),

    /**
     * Triple DES (DESede).
     * <p>
     * Uses 168-bit keys → 24 bytes.
     * </p>
     */
    DESede(24);

    // Attributes
    private final ArrayList<Integer> keySizes;

    /**
     * Creates a new {@link Algorithm} with the given valid key sizes.
     *
     * @param keySize one or more supported key sizes (in bytes)
     */
    Algorithm(int... keySize) {
        keySizes = new ArrayList<>(Arrays.stream(keySize).boxed().toList());
    }

    /**
     * Returns the valid key sizes (in bytes) for this algorithm.
     *
     * @return list of supported key sizes
     */
    public ArrayList<Integer> getKeySizes() {
        return keySizes;
    }

    /**
     * Checks if a given key size is supported by this algorithm.
     *
     * @param keySize the key size in bytes
     * @return {@code true} if supported, otherwise {@code false}
     */
    public boolean isKeySizeSupported(int keySize) {
        return keySizes.contains(keySize);
    }
}