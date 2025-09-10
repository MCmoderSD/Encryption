package de.MCmoderSD.encryption.enums;

import java.util.ArrayList;

import static de.MCmoderSD.encryption.enums.Algorithm.*;
import static de.MCmoderSD.encryption.enums.Mode.*;
import static de.MCmoderSD.encryption.enums.Padding.*;

/**
 * Enumeration of supported cipher transformations,
 * combining {@link Algorithm}, {@link Mode}, and {@link Padding}.
 * <p>
 * Each transformer defines a fully qualified transformation string
 * usable with {@link javax.crypto.Cipher#getInstance(String)}.
 * </p>
 */
@SuppressWarnings("ALL")
public enum Transformer {

    /**
     * AES with ECB mode and PKCS#5 padding.
     */
    AES_ECB_PKCS5(AES, ECB, PKCS5Padding),

    /**
     * DES with ECB mode and PKCS#5 padding.
     */
    DES_ECB_PKCS5(DES, ECB, PKCS5Padding),

    /**
     * Triple DES (DESede) with ECB mode and PKCS#5 padding.
     */
    DESede_ECB_PKCS5(DESede, ECB, PKCS5Padding);

    // Attributes
    private final Algorithm algorithm;
    private final Mode mode;
    private final Padding padding;

    /**
     * Creates a new {@link Transformer} definition.
     *
     * @param algorithm the underlying algorithm (e.g., AES, DES, DESede)
     * @param mode      the cipher mode of operation (e.g., ECB)
     * @param padding   the padding scheme (e.g., PKCS5Padding)
     */
    Transformer(Algorithm algorithm, Mode mode, Padding padding) {
        this.algorithm = algorithm;
        this.mode = mode;
        this.padding = padding;
    }

    /**
     * @return the underlying algorithm
     */
    public Algorithm getAlgorithm() {
        return algorithm;
    }

    /**
     * @return the cipher mode of operation
     */
    public Mode getMode() {
        return mode;
    }

    /**
     * @return the padding scheme
     */
    public Padding getPadding() {
        return padding;
    }

    /**
     * Returns the transformation string (e.g., "AES/ECB/PKCS5Padding")
     * suitable for {@link javax.crypto.Cipher#getInstance(String)}.
     *
     * @return the transformation string
     */
    public String getTransformation() {
        return algorithm.name() + "/" + mode.name() + "/" + padding.name();
    }

    /**
     * @return list of supported key sizes for the algorithm
     */
    public ArrayList<Integer> getKeySizes() {
        return algorithm.getKeySizes();
    }

    /**
     * Checks if the given key size is supported by this transformer.
     *
     * @param keySize the key size in bytes
     * @return {@code true} if supported, otherwise {@code false}
     */
    public boolean isKeySizeSupported(int keySize) {
        return algorithm.isKeySizeSupported(keySize);
    }

    /**
     * Indicates whether this transformer’s mode requires an initialization vector (IV).
     *
     * @return {@code true} if IV is required, otherwise {@code false}
     */
    public boolean needsIV() {
        return mode.needsIV();
    }
}