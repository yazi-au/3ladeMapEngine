package ladeEngine;

import ladeEngine.Utils.M3IVector;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    public GameObject prev,next;
    public M3IVector location;
    public abstract void draw(BufferedImage frame, Graphics2D graphics);
}
