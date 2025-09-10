package de.MCmoderSD.encryption.enums;

import java.util.ArrayList;

import static de.MCmoderSD.encryption.enums.Algorithm.*;
import static de.MCmoderSD.encryption.enums.Mode.*;
import static de.MCmoderSD.encryption.enums.Padding.*;

public enum Transformer {
    // AES combinations
    AES_ECB_PKCS5(AES, ECB, PKCS5Padding),
//    AES_ECB_NOPAD(AES, ECB, NoPadding),
//    AES_CBC_PKCS5(AES, CBC, PKCS5Padding),
//    AES_CBC_NOPAD(AES, CBC, NoPadding),
//    AES_CFB_PKCS5(AES, CFB, PKCS5Padding),
//    AES_CFB_NOPAD(AES, CFB, NoPadding),
//    AES_OFB_PKCS5(AES, OFB, PKCS5Padding),
//    AES_OFB_NOPAD(AES, OFB, NoPadding),
//    //AES_CTR_PKCS5 is not a valid transformation
//    AES_CTR_NOPAD(AES, CTR, NoPadding),
//    //AES_GCM_PKCS5 is not a valid transformation
//    AES_GCM_NOPAD(AES, GCM, NoPadding),

    // DES combinations
    DES_ECB_PKCS5(DES, ECB, PKCS5Padding),
//    DES_ECB_NOPAD(DES, ECB, NoPadding),
//    DES_CBC_PKCS5(DES, CBC, PKCS5Padding),
//    DES_CBC_NOPAD(DES, CBC, NoPadding),
//    DES_CFB_PKCS5(DES, CFB, PKCS5Padding),
//    DES_CFB_NOPAD(DES, CFB, NoPadding),
//    DES_OFB_PKCS5(DES, OFB, PKCS5Padding),
//    DES_OFB_NOPAD(DES, OFB, NoPadding),
//    //DES_CTR_PKCS5 is not a valid transformation
//    DES_CTR_NOPAD(DES, CTR, NoPadding),
//    //DES_GCM_PKCS5 is not a valid transformation
//    //DES_GCM_NOPAD is not a valid transformation

    // TripleDES / DESede combinations
    DESede_ECB_PKCS5(DESede, ECB, PKCS5Padding);
//    DESede_ECB_NOPAD(DESede, ECB, NoPadding),
//    DESede_CBC_PKCS5(DESede, CBC, PKCS5Padding),
//    DESede_CBC_NOPAD(DESede, CBC, NoPadding),
//    DESede_CFB_PKCS5(DESede, CFB, PKCS5Padding),
//    DESede_CFB_NOPAD(DESede, CFB, NoPadding),
//    DESede_OFB_PKCS5(DESede, OFB, PKCS5Padding),
//    DESede_OFB_NOPAD(DESede, OFB, NoPadding),
//    //DESede_CTR_PKCS5 is not a valid transformation
//    DESede_CTR_NOPAD(DESede, CTR, NoPadding);
//    //DESede_GCM_PKCS5 is not a valid transformation
//    //DESede_GCM_NOPAD is not a valid transformation

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

    public ArrayList<Integer> getKeySizes() {
        return algorithm.getKeySizes();
    }

    public boolean isKeySizeSupported(int keySize) {
        return algorithm.isKeySizeSupported(keySize);
    }

    public boolean needsIV() {
        return mode.needsIV();
    }
}