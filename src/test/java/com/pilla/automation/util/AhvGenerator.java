package com.pilla.automation.util;
import java.util.concurrent.ThreadLocalRandom;

public final class AhvGenerator {

    // Generate a random AHV (format 756.9500.xxxx.xx) with a correct EAN-13 check digit
    public static String generateAhv() {
        // 5 random digits left-padded to complete the 12-digit base after the fixed prefix.
        int random5 = ThreadLocalRandom.current().nextInt(0, 100_000);
        String five = String.format("%05d", random5);

        // base 12 digits: "7569500" + 5 random digits
        String base12 = "7569500" + five; // length == 12

        int check = ean13CheckDigit(base12);
        String full13 = base12 + check; // 13 digits total

        return formatAhv(full13);
    }

    // Validate AHV (accepts formatted or unformatted). Returns true if length/prefix/checksum OK.
    public static boolean validateAhv(String ahv) {
        if (ahv == null) return false;
        String digits = ahv.replaceAll("\\D", "");
        if (digits.length() != 13) return false;
        if (!digits.startsWith("756")) return false;

        String base12 = digits.substring(0, 12);
        int expected = ean13CheckDigit(base12);
        int actual = Character.getNumericValue(digits.charAt(12));
        return expected == actual;
    }

    // EAN-13 check digit for a 12-digit numeric string
    private static int ean13CheckDigit(String twelveDigits) {
        if (twelveDigits == null || twelveDigits.length() != 12) {
            throw new IllegalArgumentException("Input must be 12 digits");
        }
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int d = Character.getNumericValue(twelveDigits.charAt(i));
            int pos = i + 1; // positions counted from 1 on the left
            if (pos % 2 == 0) {
                // even position: weight 3
                sum += d * 3;
            } else {
                // odd position: weight 1
                sum += d;
            }
        }
        int mod = sum % 10;
        return (10 - mod) % 10;
    }

    private static String formatAhv(String full13) {
        return full13.substring(0, 3) +
                full13.substring(3, 7) +
                full13.substring(7, 11) +
                full13.substring(11, 13);
    }

}
