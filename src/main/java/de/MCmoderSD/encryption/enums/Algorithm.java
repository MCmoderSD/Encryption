package de.MCmoderSD.encryption.enums;

import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("ALL")
public enum Algorithm {

    // AES supports 128, 192, 256-bit keys → 16, 24, 32 bytes
    AES(16, 24, 32),

    // DES uses 56-bit key (+ parity) → 8 bytes
    DES(8),

    // Triple DES (DESede) uses 168-bit keys → 24 bytes
    DESede(24);

    // Attributes
    private final ArrayList<Integer> keySizes;

    // Constructor
    Algorithm(int... keySize) {
        keySizes = new ArrayList<>(Arrays.stream(keySize).boxed().toList());
    }

    // Get valid key sizes
    public ArrayList<Integer> getKeySizes() {
        return keySizes;
    }

    // Check if key size is supported
    public boolean isKeySizeSupported(int keySize) {
        return keySizes.contains(keySize);
    }
}