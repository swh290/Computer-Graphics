package com.shihwei.hws;

import java.awt.*;

import com.shihwei.render.BufferedApplet;
import com.shihwei.render.Geometry;
import com.shihwei.render.Matrix;

public class Assignment04 extends BufferedApplet
{
	int width = 0, height = 0;
	double a[][] = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
	int ptem[][] = {{0,0},{0,0},{0,0},{0,0}};
	int pa[] = new int[4];
	int pb[] = new int[4];
	double ta[] = {0,0,0,0}, tb[] = {0,0,0,0};
	int tpa[] = {0,0}, tpb[] = {0,0};
	double startTime = System.currentTimeMillis() / 1000.0;
	public void render(Graphics g) {
		double time = getTime() - startTime;
		//refresh cancas
		width = getWidth();
		height = getHeight();
		g.setColor(Color.yellow);                    
		g.fillRect(0, 0, width, height);
		
		//get geometry for later use
		Geometry cube = new Geometry();
		cube.cube();
		Geometry sphere = new Geometry();
		sphere.torus(64, 64, .5);
		Geometry cylinder = new Geometry();
		cylinder.cylinder(1, 1, 16);
		
		Matrix m = new Matrix();
		//car body 1
		g.setColor(Color.blue); 
		m.identity();
		m.rotateX(Math.PI/8);
		m.rotateY((time));
		m.translate(.45, 0, 0);
		m.scale(.1, .025, .15);
		drawline(sphere, m, g);
		//car body2
		g.setColor(Color.green); 
		m.identity();
		m.rotateX(Math.PI/8);
		m.translate(0, .02, 0);
		m.rotateY((time));
		m.translate(.45, 0, 0);
		m.scale(.08, .05, .08);
		drawline(sphere, m, g);
		//tire 1
		g.setColor(Color.black); 
		m.identity();
		m.rotateX(Math.PI/8);
		m.rotateY((time));
		m.translate(.08, -.03, .07);
		m.translate(.45, 0, 0);
		m.rotateX((Math.PI/10));
		m.rotateY((Math.PI/2));
		m.rotateZ(-4*time);
		m.scale(.04, .04, .007);
		drawline(cylinder, m, g);
		//tire2
		m.identity();
		m.rotateX(Math.PI/8);
		m.rotateY((time));
		m.translate(-.08, -.03, .07);
		m.translate(.45, 0, 0);
		m.rotateX((Math.PI/10));
		m.rotateY((Math.PI/2));
		m.rotateZ(-4*time);
		m.scale(.04, .04, .007);
		drawline(cylinder, m, g);
		//tire3
		m.identity();
		m.rotateX(Math.PI/8);
		m.rotateY((time));
		m.translate(.08, -.03, -.07);
		m.translate(.45, 0, 0);
		m.rotateX((Math.PI/10));
		m.rotateY((Math.PI/2));
		m.rotateZ(-4*time);
		m.scale(.04, .04, .007);
		drawline(cylinder, m, g);
		//tire4
		m.identity();
		m.rotateX(Math.PI/8);
		m.rotateY((time));
		m.translate(-.08, -.03, -.07);
		m.translate(.45, 0, 0);
		m.rotateX((Math.PI/10));
		m.rotateY((Math.PI/2));		
		m.rotateZ(-4*time);
		m.scale(.04, .04, .007);
		drawline(cylinder, m, g);
		//pillar in the middle
		g.setColor(Color.red); 
		Geometry cylinder2 = new Geometry();
		cylinder2.cylinder(.3, .1, 16);
		m.identity();
		m.rotateX(Math.PI/8);
		m.translate(0, .05, 0);
		m.scale(.5, .5, .3);
		drawline(cylinder2, m, g);
		//sphere on the pillar
		g.setColor(Color.magenta); 
		m.identity();
		m.rotateX(Math.PI/8);
		m.translate(0, .25, 0);
		m.rotateY(-time);
		m.scale(.1, .1, .1);
		drawline(sphere, m, g);
		//two sphere rotating
		g.setColor(Color.PINK); 
		m.identity();
		m.rotateX(Math.PI/8);
		m.translate(0, .25, 0);
		m.rotateZ(Math.PI/8);
		m.rotateY((5*time));
		m.translate(.25, .0, 0);
		m.scale(.03, .03, .03);
		drawline(sphere, m, g);
		
		g.setColor(Color.cyan); 
		m.identity();
		m.rotateX(Math.PI/8);
		m.translate(0, .25, 0);
		m.rotateZ(7*Math.PI/8);
		m.rotateY((5*time));
		m.translate(.25, .0, 0);
		m.scale(.03, .03, .03);
		drawline(sphere, m, g);
		
		
		
	}
	
	void viewport(double src[], int dst[]) {
	      dst[0] = (int)(0.5 * width  + src[0] * width);
	      dst[1] = (int)(0.5 * height - src[1] * width);
	   }
	
	double getTime() {
		return System.currentTimeMillis() / 1000.0;
	}
	
	void draw(Geometry g1, Matrix m, Graphics g, int s){
		
		
		for (int i = 0 ; i < g1.face.length ; i++) {  
			for (int j = 0 ; j < g1.face[i].length ; j++) { 
			                      
				m.transform(g1.vertices[g1.face[i][j]], a[j]);			 
				viewport(a[j],ptem[j]);
				pa[j]=ptem[j][0];
				pb[j]=ptem[j][1];						 
			}
			Color c=new Color((150+s*i)%255,(100+2*s*i)%255,((s+66)*i)%255);
			//Color c=new Color(20,55,43);
			g.setColor(c);
			g.fillPolygon(pa, pb, 4);
		}
	}
	void drawline(Geometry g1, Matrix m, Graphics g){
		for (int i = 0 ; i < g1.face.length ; i++)   // LOOP THROUGH ALL THE SHAPES
		     for (int j = 1 ; j < g1.face[i].length ; j++) { // LOOP THROUGH ALL THE LINES IN THE SHAPE
			 m.transform(g1.vertices[g1.face[i][j-1]], ta);                      // TRANSFORM BOTH ENDPOINTS OF LINE
			 m.transform(g1.vertices[g1.face[i][j]], tb);
			 viewport(ta, tpa);
			 viewport(tb, tpb);
			 g.drawLine(tpa[0], tpa[1], tpb[0], tpb[1]);    // DRAW ONE LINE ON THE SCREEN
		     }
	}

}
