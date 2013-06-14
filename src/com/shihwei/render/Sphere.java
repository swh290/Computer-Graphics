package com.shihwei.render;

public class Sphere {

    public double[] center = {0.0, 0.0, 0.0};
    public double radius = 1.0;
    public Material m = new Material();
    public void setRadius(double r){
    	radius = r;
    }
    public void setCenter(double x, double y, double z){
        center[0] = x; center[1] = y; center[2] = z;
    }
    
    public void moveCenter(double x, double y, double z){
        center[0] += x; center[1] += y; center[2] += z;
    }
    
}