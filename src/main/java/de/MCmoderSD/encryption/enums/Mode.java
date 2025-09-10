package de.MCmoderSD.encryption.enums;

public enum Mode {

    // Modes
    ECB(false), // Electronic Codebook
    CBC(true),  // Cipher Block Chaining
    CFB(true),  // Cipher Feedback
    OFB(true),  // Output Feedback
    CTR(true),  // Counter
    GCM(true);  // Galois/Counter Mode

    // Attributes
    private final boolean needsIV;

    // Constructor
    Mode(boolean needsIV) {
        this.needsIV = needsIV;
    }

    // Getter
    public boolean needsIV() {
        return needsIV;
    }
}