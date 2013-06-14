package com.shihwei.render;
import java.util.*;

public class Shape {
	ArrayList<Surface> s = new ArrayList<Surface>();
	Material m = new Material();
	Matrix mx = new Matrix();
	Matrix invertM = new Matrix();
	Matrix transM = new Matrix();
	public void plane(){
		s.clear();
		s.add(new Surface(0,0,0,0,0,0,0,-1,0,-1,m));
		s.add(new Surface(0,0,0,0,0,0,0,1,0,-1,m));
	}
	public void cube(){
		this.s.clear();
		s.add(new Surface(0,0,0,0,0,0,-1,0,0,-1,m));
		s.add(new Surface(0,0,0,0,0,0,1,0,0,-1,m));
		s.add(new Surface(0,0,0,0,0,0,0,-1,0,-1,m));
		s.add(new Surface(0,0,0,0,0,0,0,1,0,-1,m));
		s.add(new Surface(0,0,0,0,0,0,0,0,-1,-1,m));
		s.add(new Surface(0,0,0,0,0,0,0,0,1,-1,m));
	}
	public void cylinder(){
		s.clear();
		s.add(new Surface(1,1,0,0,0,0,0,0,0,-1,m));
		s.add(new Surface(0,0,0,0,0,0,0,0,1,-1,m));
		s.add(new Surface(0,0,0,0,0,0,0,0,-1,-1,m));
	}
	public void sphere(){
		s.clear();
		s.add(new Surface(1,1,1,0,0,0,0,0,0,-1,m));
	}
	public void addSurface(Surface sf){
		s.add(sf);
	}
	public void intersect(Shape i){
		s.addAll(i.s);
	}
	private void transMatrix(double src[][], double dst[][]){
		for (int i=0;i<4;i++)
			for (int j=0;j<4;j++)
				dst[i][j] = src [j][i];
				
	}
	public void transform(Matrix mm){
		
		for (int i = 0; i < s.size(); i++){
			MatrixInverter.invert(mm.data,invertM.data);
			transMatrix(invertM.data,transM.data);
			Surface ss = s.get(i);
			mx.set(0, 0, ss.a);
			mx.set(0, 1, ss.f);
			mx.set(0, 2, ss.e);
			mx.set(0, 3, ss.g);
			mx.set(1, 0, 0);
			mx.set(1, 1, ss.b);
			mx.set(1, 2, ss.d);
			mx.set(1, 3, ss.h);
			mx.set(2, 0, 0);
			mx.set(2, 1, 0);
			mx.set(2, 2, ss.c);
			mx.set(2, 3, ss.i);
			mx.set(3, 0, 0);
			mx.set(3, 1, 0);
			mx.set(3, 2, 0);
			mx.set(3, 3, ss.j);
			transM.multiply(mx);
			transM.multiply(invertM);
			ss.a = transM.get(0, 0);
			ss.b = transM.get(1, 1);
			ss.c = transM.get(2, 2);
			ss.d = transM.get(1, 2)+transM.get(2, 1);
			ss.e = transM.get(0, 2)+transM.get(2, 0);
			ss.f = transM.get(0, 1)+transM.get(1, 0);
			ss.g = transM.get(0, 3)+transM.get(3, 0);
			ss.h = transM.get(1, 3)+transM.get(3, 1);
			ss.i = transM.get(2, 3)+transM.get(3, 2);
			ss.j = transM.get(3, 3);
			
		}
	}
}
