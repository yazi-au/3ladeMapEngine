package ladeEngine.Render.GameObjects;

import ladeEngine.GameObject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ImageObject extends GameObject {
    BufferedImage image;
    int width,height;
    int rotate;
    @Override
    public void draw(BufferedImage frame, Graphics2D graphics) {
        double radians = Math.toRadians(rotate);
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));
        int newWidth = (int) Math.floor(image.getWidth() * cos + image.getHeight() * sin);
        int newHeight = (int) Math.floor(image.getHeight() * cos + image.getWidth() * sin);

        BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotatedImage.createGraphics();

        int centerX = newWidth / 2;
        int centerY = newHeight / 2;

        AffineTransform transform = new AffineTransform();
        transform.translate(centerX, centerY);
        transform.rotate(radians);
        transform.translate(-image.getWidth() / 2, -image.getHeight() / 2);

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(image, transform, null);
        g2d.dispose();

        graphics.drawImage(rotatedImage, location.x, location.y, width, height, null);
    }
}
