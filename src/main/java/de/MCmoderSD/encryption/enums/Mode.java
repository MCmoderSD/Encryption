package de.MCmoderSD.encryption.enums;

@SuppressWarnings("ALL")
public enum Mode {

    // Modes
    ECB(false); // Electronic Codebook

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