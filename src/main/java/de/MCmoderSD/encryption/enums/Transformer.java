package de.MCmoderSD.encryption.enums;

import static de.MCmoderSD.encryption.enums.Algorithm.*;
import static de.MCmoderSD.encryption.enums.Mode.*;
import static de.MCmoderSD.encryption.enums.Padding.*;

public enum Transformer {

    // AES combinations
    AES_ECB_PKCS5(AES, ECB, PKCS5Padding),
    AES_ECB_NOPAD(AES, ECB, NoPadding),
    AES_CBC_PKCS5(AES, CBC, PKCS5Padding),
    AES_CBC_NOPAD(AES, CBC, NoPadding),
    AES_CFB_PKCS5(AES, CFB, PKCS5Padding),
    AES_CFB_NOPAD(AES, CFB, NoPadding),
    AES_OFB_PKCS5(AES, OFB, PKCS5Padding),
    AES_OFB_NOPAD(AES, OFB, NoPadding),

    AES_CTR_NOPAD(AES, CTR, NoPadding),
    AES_GCM_NOPAD(AES, GCM, NoPadding),

    // DES combinations
    DES_ECB_PKCS5(DES, ECB, PKCS5Padding),
    DES_ECB_NOPAD(DES, ECB, NoPadding),
    DES_CBC_PKCS5(DES, CBC, PKCS5Padding),
    DES_CBC_NOPAD(DES, CBC, NoPadding),

    // TripleDES / DESede combinations
    DESede_ECB_PKCS5(DESede, ECB, PKCS5Padding),
    DESede_ECB_NOPAD(DESede, ECB, NoPadding),
    DESede_CBC_PKCS5(DESede, CBC, PKCS5Padding),
    DESede_CBC_NOPAD(DESede, CBC, NoPadding);

    // Attributes
    private final Algorithm algorithm;
    private final Mode mode;
    private final Padding padding;

    // Constructor
    Transformer(Algorithm algorithm, Mode mode, Padding padding) {
        this.algorithm = algorithm;
        this.mode = mode;
        this.padding = padding;
    }

    // Getters
    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public Mode getMode() {
        return mode;
    }

    public Padding getPadding() {
        return padding;
    }

    public String getTransformation() {
        return algorithm.name() + "/" + mode.name() + "/" + padding.name();
    }

    public static boolean isValidTransformation(String transformation) {
        return false;
    }
}