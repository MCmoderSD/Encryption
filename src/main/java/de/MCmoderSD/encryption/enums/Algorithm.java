package de.MCmoderSD.encryption.enums;

public enum Algorithm {

    // Constants
    AES_ECB_PKCS5("AES/ECB/PKCS5Padding");

    private final String transformation;

    Algorithm(String transformation) {
        this.transformation = transformation;
    }

    public String getTransformation() {
        return transformation;
    }
}