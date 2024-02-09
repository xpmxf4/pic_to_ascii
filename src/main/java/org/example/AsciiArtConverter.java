package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AsciiArtConverter {

    private static final String ASCII_CHARS = "@#=$*!;:~-,. ";
    private static final String BLOCK_CHARS = "█▓▒░─";

    public static void main(String[] args) {
        try {
//            BufferedImage image = ImageIO.read(new File("/Users/xpmxf4/Desktop/develop/pic_to_ascii_art/src/main/resources/img.png"));
            BufferedImage image = ImageIO.read(new File("/Users/xpmxf4/Desktop/develop/pic_to_ascii_art/src/main/resources/images.png"));
            String asciiArt = convertToAscii(image, 60); // 여기서 높이를 50으로 설정합니다.
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
                double grey = (pixelColor.getRed() + pixelColor.getGreen() + pixelColor.getBlue()) / 3.0;
                sb.append(greyChar(grey));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private static char greyChar(double grey) {
        String str = ASCII_CHARS + BLOCK_CHARS;
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

