package dev.a8kj7sea.captcha.utils;

import java.awt.Color;
import java.util.Random;

/**
 * The ColorUtils class provides utility methods for working with colors.
 */
public class ColorUtils {

    /**
     * Generates a random color with random red, green, and blue components
     * and a random alpha component between 155 and 255 (inclusive).
     *
     * @return a random Color object
     */
    public static Color randomColor() {
        Random random = new Random();
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256), random.nextInt(100) + 155);
    }
}