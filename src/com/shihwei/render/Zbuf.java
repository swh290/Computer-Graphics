package com.shihwei.render;

public class Zbuf {
	double z=0;
	int rgb[] = new int[3];
	public double getDepth(){return z;}
	public int getR(){return rgb[0];}
	public int getG(){return rgb[1];}
	public int getB(){return rgb[2];}
	public void setDepth(double depth){
		z = depth;
	}
	public void setRGB(int r, int g, int b){
		rgb[0] = r;
		rgb[1] = g;
		rgb[2] = b;
	}
}
