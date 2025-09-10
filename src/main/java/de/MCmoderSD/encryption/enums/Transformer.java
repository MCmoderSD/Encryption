package de.MCmoderSD.encryption.enums;

import java.util.ArrayList;

import static de.MCmoderSD.encryption.enums.Algorithm.*;
import static de.MCmoderSD.encryption.enums.Mode.*;
import static de.MCmoderSD.encryption.enums.Padding.*;

@SuppressWarnings("ALL")
public enum Transformer {

    // Transformers
    AES_ECB_PKCS5(AES, ECB, PKCS5Padding),
    DES_ECB_PKCS5(DES, ECB, PKCS5Padding),
    DESede_ECB_PKCS5(DESede, ECB, PKCS5Padding);

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