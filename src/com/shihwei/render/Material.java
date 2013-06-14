package com.shihwei.render;

public class Material {
	
	double ambient[] = {0,0,0};
	double diffuse[] = {0,0,0};
	double specular[] = {0,0,0};
	double specularPower = 1;
	double mirror[] = {0,0,0};
	
	public double[] getAmbient(){
		return ambient;
	}
	
	public double[] getDiffuse(){
		return diffuse;
	}
	
	public double[] getSpecular(){
		return specular;
	}
	
	public double getSpecularPower(){
		return specularPower;
	}
	
	public double[] getMirror(){
		return mirror;
	}
	public void setAmbient(double a, double b, double c){
		ambient[0] = a;
		ambient[1] = b;
		ambient[2] = c;
	}
	
	public void setDiffuse(double a, double b, double c){
		diffuse[0] = a;
		diffuse[1] = b;
		diffuse[2] = c;
	}
	
	public void setSpecular(double a, double b, double c){
		specular[0] = a;
		specular[1] = b;
		specular[2] = c;
	}
	
	public void setSpecularPower(double a){
		specularPower = a;
	}
	
	public void setMirror(double a, double b, double c){
		mirror[0] = a;
		mirror[1] = b;
		mirror[2] = c;
	}
	
}
