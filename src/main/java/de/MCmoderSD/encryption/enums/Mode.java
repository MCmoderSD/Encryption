package de.MCmoderSD.encryption.enums;

/**
 * Enumeration of supported block cipher modes of operation.
 * <p>
 * Some modes (e.g., CBC, GCM) require an initialization vector (IV),
 * while others (e.g., ECB) do not.
 * </p>
 */
@SuppressWarnings("ALL")
public enum Mode {

    /**
     * Electronic Codebook (ECB).
     * <p>
     * Simplest mode; does not use an IV.
     * Not recommended for most applications due to pattern leakage.
     * </p>
     */
    ECB(false);

    // Attributes
    private final boolean needsIV;

    /**
     * Creates a new {@link Mode}.
     *
     * @param needsIV whether this mode requires an initialization vector (IV)
     */
    Mode(boolean needsIV) {
        this.needsIV = needsIV;
    }

    /**
     * Indicates whether this mode requires an initialization vector (IV).
     *
     * @return {@code true} if an IV is required, otherwise {@code false}
     */
    public boolean needsIV() {
        return needsIV;
    }
}