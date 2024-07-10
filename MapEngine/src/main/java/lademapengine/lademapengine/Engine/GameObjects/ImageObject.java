package lademapengine.lademapengine.Engine.GameObjects;

import lademapengine.lademapengine.Engine.GameObject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ImageObject extends GameObject {
    BufferedImage image;
    int width;
    int height;
    float rotate;
    public ImageObject(BufferedImage image){
        this.image = image;
        width = image.getWidth();
        height = image.getHeight();
    }
    @Override
    public void draw(BufferedImage frame,Graphics2D graphics2D) {
        if(rotate == 0) {
            graphics2D.drawImage(image, (int) location.x, (int) location.y, width, height, null);
        }else{
            int imageWidth = image.getWidth(null);
            int imageHeight = image.getHeight(null);
            AffineTransform oldTransform = graphics2D.getTransform();
            AffineTransform newTransform = new AffineTransform();
            newTransform.rotate(Math.toRadians(45), (int)location.x + imageWidth / 2, (int)location.y + imageHeight / 2);
            graphics2D.setTransform(newTransform);
            graphics2D.drawImage(image, (int)location.x, (int)location.y, imageWidth, imageHeight, null);
            graphics2D.setTransform(oldTransform);
        }
    }
}
