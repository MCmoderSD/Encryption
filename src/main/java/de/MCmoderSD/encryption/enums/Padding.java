package de.MCmoderSD.encryption.enums;

/**
 * Enumeration of supported padding schemes for block ciphers.
 */
@SuppressWarnings("ALL")
public enum Padding {

    /**
     * PKCS#5 (and PKCS#7 for larger block sizes).
     * <p>
     * Ensures plaintext fits exactly into cipher blocks.
     * </p>
     */
    PKCS5Padding
}