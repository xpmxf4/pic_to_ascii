package org.example;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class EnhancedAsciiArtConverter {

    private static final String ASCII_CHARS = "@#S%?*+;:,.'`\"^_-"; // 확장된 문자 세트

    public static void main(String[] args) {
        try {
            BufferedImage image = ImageIO.read(new File("/Users/xpmxf4/Desktop/develop/pic_to_ascii_art/src/main/resources/img.png"));
            String asciiArt = convertToAscii(image, 100); // 깃헙 커밋 메시지 폭에 맞춰 조정할 수 있습니다.
            System.out.println(asciiArt);
        } catch (IOException e) {
            System.out.println("IOException : " + e.getMessage());
        }
    }

    public static String convertToAscii(BufferedImage image, int height) {
        StringBuilder sb = new StringBuilder();
        int width = image.getWidth();
        int newWidth = width * height / image.getHeight();

        BufferedImage resizedImage = resizeImage(image, newWidth, height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < newWidth; x++) {
                Color pixelColor = new Color(resizedImage.getRGB(x, y));
                double grey = 0.299 * pixelColor.getRed() + 0.587 * pixelColor.getGreen() + 0.114 * pixelColor.getBlue();
                sb.append(greyChar(grey));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private static char greyChar(double grey) {
        String str = ASCII_CHARS;
        int index = (int) ((str.length() - 1) * (grey / 255.0));
        return str.charAt(index);
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }
}
