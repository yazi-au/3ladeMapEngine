package lademapengine.lademapengine.Engine;

import lademapengine.lademapengine.Math.MVector;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    public MVector location;
    public abstract void draw(BufferedImage frame, Graphics2D graphics);
}
