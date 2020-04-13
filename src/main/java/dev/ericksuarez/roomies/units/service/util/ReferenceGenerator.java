package dev.ericksuarez.roomies.units.service.util;

import java.util.Random;

public class ReferenceGenerator {

    private final int  BASE_36 = 36;

    private final int BASE_62 = 62;

    private String base62chars;

    private Random random;

    public ReferenceGenerator() {
        this.base62chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        this.random = new Random();
    }

    public String generateBase36(int length) {
        StringBuilder reference = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            reference.append(base62chars.charAt(random.nextInt(BASE_36)));
        return reference.toString();
    }
}
