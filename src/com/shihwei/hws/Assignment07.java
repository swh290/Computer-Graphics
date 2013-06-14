package com.shihwei.hws;

import java.awt.Color;
import java.awt.Graphics;

import com.shihwei.render.BufferedApplet;
import com.shihwei.render.Light;
import com.shihwei.render.Raytracer;
import com.shihwei.render.Sphere;

public class Assignment07 extends BufferedApplet
{
	int width = 0, height = 0;
	double startTime = System.currentTimeMillis() / 1000.0;
	static final double focalLength = 2;
	
	Light l1 = new Light(1,1,-.5,.8,.2,.8);
	Light l2 = new Light(0,-2,0,.2,.8,.8);
	Light l3 = new Light(-1,-1,1,.8,.8,.2);
	Sphere s1 = new Sphere();
	Sphere s2 = new Sphere();
	Sphere s3 = new Sphere();
	Raytracer r = new Raytracer();
	
	public Assignment07(){
		initialize();
	}
	
	public void initialize(){
		
		r.addLight(l1);
		r.addLight(l2);
		r.addLight(l3);
		r.addSphere(s1);
		r.addSphere(s2);
		r.addSphere(s3);
	}
	
	public void init(){
		s1.setCenter(0, 0,-5);
		s1.setRadius(.2);
		s1.m.setAmbient(.8, .2, .2);
		s1.m.setDiffuse(.8, .8, .8);
		s1.m.setSpecular(.5,.5,.5 );
		s1.m.setSpecularPower(50);
		s1.m.setMirror(.8, .8, .8);
		s2.setCenter(0, 0, -5);
		s2.setRadius(.2);
		s2.m.setAmbient(.2, .8, .2);
		s2.m.setDiffuse(.8, .8, .8);
		s2.m.setSpecular(.8, .8, .8);
		s2.m.setMirror(.8, .8, .8);
		s2.m.setSpecularPower(50);
		s3.setCenter(0, 0, -5);
		s3.setRadius(.2);
		s3.m.setAmbient(.2, .2, .8);
		s3.m.setDiffuse(.8, .8, .8);
		s3.m.setSpecular(.8, .8, .8);
		s3.m.setMirror(.8, .8, .8);
		s3.m.setSpecularPower(50);
	}
	
	public void animate(double time){
		r.spheres.get(0).moveCenter(Math.sin(time)-.4,0 , Math.cos(time)-.4);
		r.spheres.get(1).moveCenter(0,Math.sin(time) , Math.cos(time));
		r.spheres.get(2).moveCenter(Math.sin(time),Math.cos(time) ,0 );
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
