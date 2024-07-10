package lademapengine.lademapengine.Math;

public class MVector {
    public float x,y,z;
    public MVector add(MVector vector){
        x = x + vector.x;
        y = y + vector.y;
        z = z + vector.z;
        return this;
    }
    public float DotMultiple(MVector vector){
        return x*vector.x + y*vector.y + z*vector.z;
    }
    public MVector Multiple(float value){
        x = x + value;
        y = y + value;
        z = z + value;
        return this;
    }
}
