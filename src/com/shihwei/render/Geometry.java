package com.shihwei.render;
import java.util.*;
import java.util.Random;
public class Geometry {
	Random rand = new Random();
	Matrix m = new Matrix();
	Matrix m2 = new Matrix();
	String urlName;
	ArrayList<Geometry> children = new ArrayList<Geometry>();
	Geometry parent;
	double vertices[][];
	int face[][];
	int type;
	Material mat = new Material();
	//create cube data with 24 vertices and 6 face
	public Geometry cube(){
		vertices = new double[][]{
				{-1,1,1,1,0,0,1},{-1,-1,1,1,0,0,1},{1,-1,1,1,0,0,1},{1,1,1,1,0,0,1},
				{1,1,1,1,1,0,0},{1,-1,1,1,1,0,0},{1,-1,-1,1,1,0,0},{1,1,-1,1,1,0,0},
				{1,1,-1,1,0,0,-1},{1,-1,-1,1,0,0,-1},{-1,-1,-1,1,0,0,-1},{-1,1,-1,1,0,0,-1},
				{-1,1,-1,1,-1,0,0},{-1,-1,-1,1,-1,0,0},{-1,-1,1,1,-1,0,0},{-1,1,1,1,-1,0,0},
				{-1,1,-1,1,0,1,0},{-1,1,1,1,0,1,0},{1,1,1,1,0,1,0},{1,1,-1,1,0,1,0},
				{1,-1,-1,1,0,-1,0},{1,-1,1,1,0,-1,0},{-1,-1,1,1,0,-1,0},{-1,-1,-1,1,0,-1,0},
		};
		face = new int[][]{
				{0,1,2,3},
				{4,5,6,7},
				{8,9,10,11},
				{12,13,14,15},
				{16,17,18,19},
				{20,21,22,23}
		};
		return this;
	}
	//create cylinder data with 4n+2 vertices and 3n faces
	
	public Geometry cylinder(int n){
		vertices = new double[4*n+2][7];
		type = 1;
		//n vertices on the front face edge
		for (int i = 0; i < n ; i++){
			vertices[i][0] = Math.cos(i*2*Math.PI/n);
			vertices[i][1] = Math.sin(i*2*Math.PI/n);
			vertices[i][2] = 1;
			vertices[i][3] = 1;
			vertices[i][4] = 0;
			vertices[i][5] = 0;
			vertices[i][6] = 1;
		}
		//n vertices on the back face edge
		for (int i = 0; i < n ; i++){
			vertices[i+n][0] = Math.cos(i*2*Math.PI/n);
			vertices[i+n][1] = Math.sin(i*2*Math.PI/n);
			vertices[i+n][2] = -1;
			vertices[i+n][3] = 1;
			vertices[i+n][4] = 0;
			vertices[i+n][5] = 0;
			vertices[i+n][6] = -1;
			
		}
		//n vertices on the front face edge but different from the first n points
		for (int i = 0; i < n ; i++){
			vertices[i+2*n][0] = Math.cos(i*2*Math.PI/n);
			vertices[i+2*n][1] = Math.sin(i*2*Math.PI/n);
			vertices[i+2*n][2] = 1;
			vertices[i+2*n][3] = 1;
			vertices[i+2*n][4] = Math.cos(i*2*Math.PI/n);
			vertices[i+2*n][5] = Math.sin(i*2*Math.PI/n);
			vertices[i+2*n][6] = 0;
		}
		//n vertices on the back face edge but different from the second n points
		for (int i = 0; i < n ; i++){
			vertices[i+3*n][0] = Math.cos(i*2*Math.PI/n);
			vertices[i+3*n][1] = Math.sin(i*2*Math.PI/n);
			vertices[i+3*n][2] = -1;
			vertices[i+3*n][3] = 1;
			vertices[i+3*n][4] = Math.cos(i*2*Math.PI/n);
			vertices[i+3*n][5] = Math.sin(i*2*Math.PI/n);
			vertices[i+3*n][6] = 0;
		}
		//middle point of the front face
		vertices[4*n][0] = 0;
		vertices[4*n][1] = 0;
		vertices[4*n][2] = 1;
		vertices[4*n][3] = 1;
		vertices[4*n][4] = 0;
		vertices[4*n][5] = 0;
		vertices[4*n][6] = 1;
		
		//middle point of the back face
		vertices[4*n+1][0] = 0;
		vertices[4*n+1][1] = 0;
		vertices[4*n+1][2] = -1;
		vertices[4*n+1][3] = 1;
		vertices[4*n+1][4] = 0;
		vertices[4*n+1][5] = 0;
		vertices[4*n+1][6] = -1;
		
		face = new int[3*n][4];
		//front face
		for (int i = 0; i < n-1 ; i++){
			face[i][0] = 4*n;
			face[i][1] = i;
			face[i][2] = i+1;
			face[i][3] = 4*n;
		}
		face[n-1][0] = 4*n;
		face[n-1][1] = n-1;
		face[n-1][2] = 0;
		face[n-1][3] = 4*n;
		
		//back face
		for (int i = 0; i < n-1 ; i++){
			face[i+n][0] = 4*n+1;
			face[i+n][1] = i+n;
			face[i+n][2] = i+n+1;
			face[i+n][3] = 4*n+1;
		}
		face[2*n-1][0] = 4*n+1;
		face[2*n-1][1] = 2*n-1;
		face[2*n-1][2] = n;
		face[2*n-1][3] = 4*n+1;
		//faces compose the body
		for (int i = 0; i < n-1 ; i++){
			face[i+2*n][0] = i+2*n;
			face[i+2*n][1] = i+3*n;
			face[i+2*n][2] = i+3*n+1;
			face[i+2*n][3] = i+2*n+1;
		}
		face[3*n-1][0] = 3*n-1;
		face[3*n-1][1] = 4*n-1;
		face[3*n-1][2] = 3*n;
		face[3*n-1][3] = 2*n;
		
		
		
			
		return this;
		
	}
	
	public Geometry sphere(int m, int n){
		type = 0;
		vertices = new double[(m+1)*(n+1)][7];
		//(m+1)*(n+1) vertices
		for (int i = 0; i < m+1; i++){
			for (int j = 0; j < n+1; j++){
				double theta = 2*Math.PI*i/m;
				double phi = -Math.PI/2 + Math.PI * j / n;
				vertices[i + (m + 1) * j][0] = Math.cos(phi)*Math.cos(theta);
				vertices[i + (m + 1) * j][1] = Math.cos(phi)*Math.sin(theta);
				vertices[i + (m + 1) * j][2] = Math.sin(phi);
				vertices[i + (m + 1) * j][3] = 1;
				vertices[i + (m + 1) * j][4] = Math.cos(phi)*Math.cos(theta);
				vertices[i + (m + 1) * j][5] = Math.cos(phi)*Math.sin(theta);
				vertices[i + (m + 1) * j][6] = Math.sin(phi);
			}
		}
		//m*n faces
		face = new int[m*n][4];
		for (int i = 0; i < m; i++){
			for (int j = 0; j < n; j++){
			face[i + m * j][0] = i + (m + 1) * j;
			face[i + m * j][1] = i + (m + 1) * j+1;
			face[i + m * j][2] = i + (m + 1) * (j+1)+1;
			face[i + m * j][3] = i + (m + 1) * (j+1);
			}
		}
		return this;
	}
	
	public Geometry torus(int m, int n,double r){
		vertices = new double[(m+1)*(n+1)][7];
		//(m+1)*(n+1) vertices
		for (int i = 0; i < m+1; i++){
			for (int j = 0; j < n+1; j++){
				double theta = 2*Math.PI*i/m;
				double phi = 2*Math.PI*j/n;
				vertices[i + (m + 1) * j][0] = Math.cos(theta) * (1 + r * Math.cos(phi));
				vertices[i + (m + 1) * j][1] = Math.sin(theta) * (1 + r * Math.cos(phi));
				vertices[i + (m + 1) * j][2] = r * Math.sin(phi);
				vertices[i + (m + 1) * j][3] = 1;
				vertices[i + (m + 1) * j][4] = Math.cos(theta)* r * Math.cos(phi);
				vertices[i + (m + 1) * j][5] = Math.sin(theta)* r * Math.cos(phi);
				vertices[i + (m + 1) * j][6] = r * Math.sin(phi);
				
			}
		}
		//m*n faces
		face = new int[m*n][4];
		for (int i = 0; i < m; i++){
			for (int j = 0; j < n; j++){
			face[i + m * j][0] = i + (m + 1) * j;
			face[i + m * j][1] = i + (m + 1) * j+1;
			face[i + m * j][2] = i + (m + 1) * (j+1)+1;
			face[i + m * j][3] = i + (m + 1) * (j+1);
			}
		}
		return this;
	}
	public Geometry() {
		m2.identity();
		m.identity();
	}
	public Geometry world() { 
		parent=this;
		return this;
	}
	public Matrix getMatrix() { 
		return m;
	}
	public Matrix getMatrix2() { 
		return m2;
	}
              // access to this geometry object's matrix
	public void add(Geometry child) { 
		child.m.copy(m);
		children.add(child);
	}
	public Geometry add() { 
		Geometry newchild = new Geometry();
		children.add(newchild);
		return newchild;
	}
       // add a child geometry object
	public void remove(Geometry child) { 
		children.remove(child);
	}
		// remove a child geometry object
	public int getNumChildren() { 
		return children.size();
	}

		// find out how many children there are
	public Geometry getChild(int index) {
		return children.get(index);
	}

	public Material getMaterial(){
		return mat;
	}
}
