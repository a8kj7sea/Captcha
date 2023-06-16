package dev.a8kj7sea.captcha.object;

import java.security.SecureRandom;
import java.util.Random;

/**
 * The Captcha class represents a simple challenge-response test used to
 * determine
 * whether or not the user is human.
 */
public class Captcha {

    /**
     * The type of characters that can be used in the Captcha text.
     */
    public enum CaptchaType {
        DIGITS, TEXT, MIX;
    }

    private CaptchaType type;
    private int length;
    private String characterSet;
    private Random random;

    /**
     * Creates a new Captcha object with the specified type and length.
     *
     * @param type   the type of Captcha to create
     * @param length the length of the Captcha text
     */
    public Captcha(CaptchaType type, int length) {
        setType(type);
        setLength(length);
        setCharacterSet(getDefaultCharacterSet(type));
        setRandom(new SecureRandom());
    }

    /**
     * Creates a new Captcha object with default settings.
     */
    public Captcha() {
        this(CaptchaType.DIGITS, 5);
    }

    /**
     * Builds a new Captcha text using the current settings.
     *
     * @return a String containing the Captcha text
     */
    public String build() {
        StringBuilder captcha = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characterSet.length());
            captcha.append(characterSet.charAt(index));
        }

        return captcha.toString();
    }

    // Getters and setters

    /**
     * Sets the type of Captcha to create.
     *
     * @param type the type of Captcha to create
     */
    public void setType(CaptchaType type) {
        this.type = type;
    }

    /**
     * Returns the type of Captcha that will be created.
     *
     * @return the type of Captcha to create
     */
    public CaptchaType getType() {
        return type;
    }

    /**
     * Sets the length of the Captcha text.
     *
     * @param length the length of the Captcha text
     * @throws IllegalArgumentException if the length is not positive
     */
    public void setLength(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be positive");
        }
        this.length = length;
    }

    /**
     * Returns the length of the Captcha text.
     *
     * @return the length of the Captcha text
     */
    public int getLength() {
        return length;
    }

    /**
     * Sets the character set that can be used in the Captcha text.
     *
     * @param characterSet the character set that can be used in the Captcha text
     */
    public void setCharacterSet(String characterSet) {
        this.characterSet = characterSet;
    }

    /**
     * Returns the character set that can be used in the Captcha text.
     *
     * @return the character set that can be used in the Captcha text
     */
    public String getCharacterSet() {
        return characterSet;
    }

    /**
     * Sets the random number generator used to generate the Captcha text.
     *
     * @param random the random number generator used to generate the Captcha text
     */
    public void setRandom(Random random) {
        this.random = random;
    }

    /**
     * Returns the random number generator used to generate the Captcha text.
     *
     * @return the random number generator used to generate the Captcha text
     */
    public Random getRandom() {
        return random;
    }

    // Private methods

    /**
     * Returns the default character set for the specified Captcha type.
     *
     * @param type the type of Captcha
     * @return the default character set for the specified Captcha type
     */
    private String getDefaultCharacterSet(CaptchaType type) {
        switch (type) {
            case DIGITS:
                return "0123456789";

            case TEXT:
                return "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

            case MIX:
                return "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

            default:
                return "0123456789";
        }
    }
}