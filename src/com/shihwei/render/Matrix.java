package com.shihwei.render;

public class Matrix {
	
	double data[][] = new double[4][4];
	double tem[][] = new double[4][4];
	double temp[][] = new double[4][4];
	double inv[][] = new double[4][4];
	public void set (int x, int y, double value){
		data[x][y] = value;
	}
	
	public double get (int x, int y){
		return data[x][y];
	}
	
	public void copy (Matrix src){
		for (int row = 0; row < 4; row++)
			for (int col = 0; col < 4; col++)
				data[row][col] = src.get(row, col);
	}
	
	public void createIdentityData(double dst[][]){
		dst[0][0] = 1;
		dst[0][1] = 0;
		dst[0][2] = 0;
		dst[0][3] = 0;
		
		dst[1][0] = 0;
		dst[1][1] = 1;
		dst[1][2] = 0;
		dst[1][3] = 0;
		
		dst[2][0] = 0;
		dst[2][1] = 0;
		dst[2][2] = 1;
		dst[2][3] = 0;
		
		dst[3][0] = 0;
		dst[3][1] = 0;
		dst[3][2] = 0;
		dst[3][3] = 1;
	}
	public void createTranslationData(double x, double y, double z, double dst[][]){
		createIdentityData(dst);
		dst[0][3] = x;
		dst[1][3] = y;
		dst[2][3] = z;
		   
	}
	
	public void createXRotationData(double theta, double dst[][]){
		createIdentityData(dst);
		dst[1][1] = Math.cos(theta);
		dst[1][2] = -1 * Math.sin(theta);
		dst[2][1] = Math.sin(theta);
		dst[2][2] = Math.cos(theta);
	}
	
	public void createYRotationData(double theta, double dst[][]){
		createIdentityData(dst);
		dst[0][0] = Math.cos(theta);
		dst[2][0] = -1 * Math.sin(theta);
		dst[0][2] = Math.sin(theta);
		dst[2][2] = Math.cos(theta);
	}
	
	public void createZRotationData(double theta, double dst[][]){
		createIdentityData(dst);
		dst[0][0] = Math.cos(theta);
		dst[0][1] = -1 * Math.sin(theta);
		dst[1][0] = Math.sin(theta);
		dst[1][1] = Math.cos(theta);
	}
	
	public void createScaleData(double x, double y, double z, double dst[][]){
		createIdentityData(dst);
		dst[0][0] = x;
		dst[1][1] = y;
		dst[2][2] = z;
	}
	
	public void createCameraData(double f, double dst[][]){
		createIdentityData(dst);
		dst[3][2] = -1/f;
		
	}
	public void multiply(Matrix src) {
	      multiply(src.data);
	}
	public void multiply(double src[][]){
		
		for (int row = 0 ; row < 4 ; row++){
			for (int col = 0 ; col < 4 ; col++)
				temp[row][col] = data[row][col];
		}
		for (int row = 0 ; row < 4 ; row++){
		      for (int col = 0 ; col < 4 ; col++) {
		         data[row][col] = 0;
		         for (int i = 0 ; i < 4 ; i++)
		            data[row][col] += temp[row][i] * src[i][col];
		      }
		}
	}
	public void identity() {
		   createIdentityData(data);
	}
	public void translate(double a, double b, double c) {
		createTranslationData(a,b,c,tem); 
		multiply(tem);                    
	}
	public void rotateX(double theta) {
		createXRotationData(theta,tem); 
		multiply(tem);                    
	}
	public void rotateY(double theta) {
		createYRotationData(theta,tem); 
		multiply(tem);  
	}
	public void rotateZ(double theta) {
		createZRotationData(theta,tem); 
		multiply(tem);                    
	}
	public void scale(double a, double b, double c) {
		createScaleData(a,b,c,tem); 
		multiply(tem);                    
	}
	public void setCamera(double f) {
		createCameraData(f,tem); 
		multiply(tem);                    
	}
	
	public void transform(double src[], double dst[]) {
		
		
		for (int row = 0 ; row < 4 ; row++) {
			dst[row] = 0;
			for (int i = 0 ; i < 4 ; i++)
				dst[row] += data[row][i] * src[i];
		}
		
		
		
	}
	public void transform2(double src[], double dst[]) {
		MatrixInverter.invert(data, inv);
		
		for (int row = 0 ; row < 4 ; row++) {
			dst[row] = 0;
			for (int i = 0 ; i < 4 ; i++)
				dst[row] += inv[row][i] * src[i];
		}
		
		
	}
	
}
