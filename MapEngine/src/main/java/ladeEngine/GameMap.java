package ladeEngine;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GameMap {
    //Low Z to High Z
    public GameObject head,back;
    public void draw(BufferedImage image, Graphics2D graphics){
        GameObject cur = head;
        while(cur != null){
            cur.draw(image,graphics);
            cur = cur.next;
        }
    }
    public void addObject(GameObject object){
        GameObject cur = head;
        if(head == null){
            head = object;
            back = object;
            return;
        }
        if(back.location.z <= object.location.z){
            back.next = object;
            object.prev = back;
            back = object;
            return;
        }
        while(cur != null){
            if(cur.location.z <= object.location.z){
                if(cur.prev != null){
                    cur.prev.next = object;
                    object.prev = cur.prev;
                }
                cur.prev = object;
                object.next = cur;
                return;
            }
            cur = cur.next;
        }
    }
    public void remove(GameObject object){
        if(object.prev != null){
            object.prev.next = object.next;
        }
        if(object.next != null){
            object.next.prev = object.prev;
        }
        object.next = null;
        object.prev = null;
    }
}
