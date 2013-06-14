package com.shihwei.hws;

import java.awt.Color;
import java.awt.Graphics;

import com.shihwei.render.BufferedApplet;
import com.shihwei.render.Light;
import com.shihwei.render.Material;
import com.shihwei.render.Matrix;
import com.shihwei.render.Raytracer;
import com.shihwei.render.Shape;

public class Assignment08 extends BufferedApplet
{
	int width = 0, height = 0;
	double startTime = System.currentTimeMillis() / 1000.0;
	static final double focalLength = 2;
	
	Light l1 = new Light(2,2,1,.8,.8,.8);
	Light l2 = new Light(0,-2,0,.8,.8,.8);
	Light l3 = new Light(-1,-1,1,.8,.8,.2);
	Shape s1 = new Shape();
	Shape s2 = new Shape();
	Shape s3 = new Shape();
	Shape s4 = new Shape();
	Raytracer r = new Raytracer();
	
	public Assignment08(){
		initialize();
	}
	
	public void initialize(){
		
		r.addLight(l1);
		//r.addLight(l2);
		//r.addLight(l3);
		r.addShape(s1);
		r.addShape(s2);
		r.addShape(s3);
		r.addShape(s4);
	}
	
	public void init(){
		Material mm = new Material();
		s1.cube();
		s1.m.setAmbient(1, 0, 0);
		s1.m.setDiffuse(.8, .8, .8);
		s1.m.setSpecular(.8,.8,.8 );
		s1.m.setSpecularPower(50);
		s1.m.setMirror(.8, .8, .8);
		s2.cylinder();
		s2.m.setAmbient(0, 1, 0);
		s2.m.setDiffuse(.8, .8, .8);
		s2.m.setSpecular(.8,.8,.8 );
		s2.m.setSpecularPower(50);
		s2.m.setMirror(.8, .8, .8);
		s3.cube();
		s3.m.setAmbient(0, 0, 1);
		s3.m.setDiffuse(.8, .8, .8);
		s3.m.setSpecular(.8,.8,.8 );
		s3.m.setSpecularPower(50);
		s3.m.setMirror(.3, .3, .3);
		s4.sphere();
		s4.m.setAmbient(0, 0, 0);
		s4.m.setDiffuse(.8, .8, .8);
		s4.m.setSpecular(.8,.8,.8 );
		s4.m.setSpecularPower(50);
		s4.m.setMirror(.3, .3, .3);
	}
	
	public void animate(double time){
		Matrix m = new Matrix();
		m.identity();
		m.rotateX(Math.PI/6);
		m.translate(0, .2, 0);
		m.rotateY(time);
		m.translate(0, 0, .5);
		m.rotateX(Math.PI/4);
		m.rotateY(time);
		m.scale(.1, .1, .1);
		s1.transform(m);
		m.identity();
		m.rotateX(Math.PI/6);
		m.translate(0, .2, 0);
		m.rotateY(-2*time);
		m.scale(.1, .1, .1);
		s2.transform(m);
		m.identity();
		m.translate(0, 0, 0);
		m.rotateX(Math.PI/6);
		m.scale(.5, .02, .5);
		s3.transform(m);
		m.identity();
		m.translate(.2, -.05, .2);
		m.translate(0, Math.abs(Math.sin(time))/2, 0);
		m.scale(.05, .05, .05);
		s4.transform(m);
	}
	
	public void render(Graphics g) {
		
		double time = getTime() - startTime;
		//refresh canvas
		width = getWidth();
		height = getHeight();
		g.setColor(Color.cyan);                    
		g.fillRect(0, 0, width, height);
		init();
		animate(time);
		r.draw(focalLength,g,width,height);
		
	}
	
	double getTime() {
		return System.currentTimeMillis() / 1000.0;
	}
}
