package com.shihwei.render;

public class Light {
	double direction[] = new double[3];
	double illuminance[] = {1.0, 1.0, 1.0};
	
	public Light(double a,double b, double c, double x, double y, double z){
		direction[0] = a;
		direction[1] = b;
		direction[2] = c;
		illuminance[0] = x;
		illuminance[1] = y;
		illuminance[2] = z;
		normalize(direction);
	}
	
	public void setDirection(double x, double y, double z){
        direction[0] = x;
        direction[1] = y; 
        direction[2] = z;
        normalize(direction);
    }
    
	private void normalize(double vec[]){
		double norm = Math.sqrt(dot(vec,vec));
		for (int i =0;i<3;i++)
			vec[i] /= norm;
	}
	
	private double dot(double vec1[], double vec2[]){
		double result = 0;
		for (int i=0;i<vec1.length;i++)
			result += vec1[i] * vec2[i];
		return result;
	}
    
    public void setIlluminance(double r, double g, double b){
        illuminance[0] = r; 
        illuminance[1] = g; 
        illuminance[2] = b;
    }
}
