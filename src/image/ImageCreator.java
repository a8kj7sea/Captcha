package dev.a8kj7sea.captcha.image;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import dev.a8kj7sea.captcha.settings.ImageSettings;
import dev.a8kj7sea.captcha.utils.ColorUtils;

/**
 * The ImageCreator class provides a static method to generate a CAPTCHA image
 * from a given text string.
 */
public class ImageCreator {

    /**
     * Generates a CAPTCHA image from the given text string and saves it to a file.
     *
     * @param captchaText the text to use in the CAPTCHA image
     * @return a File object representing the saved image file
     */
    public static File generateImage(String captchaText) {

        BufferedImage image = new BufferedImage(ImageSettings.IMAGE_WIDTH, ImageSettings.IMAGE_HEIGHT,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();

        // Set background color to white
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, ImageSettings.IMAGE_WIDTH, ImageSettings.IMAGE_HEIGHT);

        // Set text color and font
        Random random = new Random();
        Font font = new Font("Segoe Script", Font.BOLD, 60);
        graphics.setFont(font);

        for (int i = 0; i < captchaText.length(); i++) {
            char character = captchaText.charAt(i);
            Color textColor = ColorUtils.randomColor();
            int x = (int) (10 + i * (ImageSettings.IMAGE_WIDTH - 20) / captchaText.length());
            int y = ImageSettings.IMAGE_HEIGHT / 2 + random.nextInt(25) - 10;

            AffineTransform transform = new AffineTransform();
            transform.rotate(Math.toRadians(random.nextInt(15) - 7), x, y);
            graphics.setTransform(transform);
            graphics.setColor(textColor);
            graphics.drawString(String.valueOf(character), x, y);
        }

        // Add more noise to the image
        addNoise(graphics);

        // Dispose graphics resources
        graphics.dispose();

        // Save image to file
        File imageFile = new File("captcha.png");
        try {
            ImageIO.write(image, "png", imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageFile;
    }

    /**
     * Adds noise to the CAPTCHA image to make it harder to read.
     *
     * @param graphics the graphics object to use to draw the noise
     */
    private static void addNoise(Graphics2D graphics) {
        Random random = new Random();

        for (int i = 0; i < 350; i++) {
            graphics.setColor(ColorUtils.randomColor());
            graphics.fillOval(random.nextInt(ImageSettings.IMAGE_WIDTH), random.nextInt(ImageSettings.IMAGE_HEIGHT),
                    random.nextInt(6) + 3,
                    random.nextInt(6) + 3);
        }

        for (int i = 0; i < 12; i++) {
            graphics.setColor(ColorUtils.randomColor());
            graphics.setStroke(new BasicStroke(2.8f));
            graphics.drawLine(random.nextInt(ImageSettings.IMAGE_WIDTH), random.nextInt(ImageSettings.IMAGE_HEIGHT),
                    random.nextInt(ImageSettings.IMAGE_WIDTH),
                    random.nextInt(ImageSettings.IMAGE_HEIGHT));
        }

    }
}