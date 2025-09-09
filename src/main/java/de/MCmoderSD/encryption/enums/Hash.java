package de.MCmoderSD.encryption.enums;

public enum Hash {



    // Attributes
    private final String name;
    private final int size;

    // Constructor
    Hash(String name, int size) {
        this.name = name;
        this.size = size;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }
}