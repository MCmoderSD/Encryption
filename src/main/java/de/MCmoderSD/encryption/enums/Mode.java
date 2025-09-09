package de.MCmoderSD.encryption.enums;

public enum Mode {

    // Modes
    ECB(false),
    CBC(true),
    CFB(true),
    OFB(true),
    CTR(true),
    GCM(true);

    // Attributes
    private final boolean needsIV;

    // Constructor
    Mode(boolean needsIv) {
        this.needsIV = needsIv;
    }

    // Getter
    public boolean needsIv() {
        return needsIV;
    }
}