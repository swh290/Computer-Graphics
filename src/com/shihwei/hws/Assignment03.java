package com.shihwei.hws;

import java.awt.*;

import com.shihwei.render.BufferedApplet;
import com.shihwei.render.Matrix;

public class Assignment03 extends BufferedApplet
{
	int width = 0, height = 0;
	double startTime = System.currentTimeMillis() / 1000.0;
	
	static final double r1 = Math.sqrt((5 - Math.sqrt(5))/10);
	static final double r2 = Math.sqrt((5 + Math.sqrt(5))/10);
	double x,y,z;
	double vx = .05;
	double vy = .01;
	double vz = .02;
	
	//vertices of cube for drawing faces
	double cubeface[][][] = {
			  { {-.5,-.5,-.5,1},{.5,-.5,-.5,1},{.5,.5,-.5,1},{-.5,.5,-.5,1} },
			  { {-.5,-.5,-.5,1},{-.5,-.5,.5,1},{-.5,.5,.5,1},{-.5,.5,-.5,1}},
			  { {-.5,-.5,.5,1},{.5,-.5,.5,1},{.5,.5,.5,1},{-.5,.5,.5,1} },
			  { {.5,-.5,.5,1},{.5,-.5,-.5,1},{.5,.5,-.5,1},{.5,.5,.5,1} },
		      { {-.5,.5,-.5,1},{-.5,.5,.5,1},{.5,.5,.5,1},{.5,.5,-.5,1} },
		      { {.5,.5,.5,1},{.5,.5,-.5,1},{-.5,.5,-.5,1},{-.5,.5,.5,1}},
	};
	//vertices of cube for drawing lines
	double cube[][][] = {
			  { {-.5,-.5,-.5,1},{.5,-.5,-.5,1},{.5,.5,-.5,1},{-.5,.5,-.5,1},{-.5,-.5,-.5,1} },
			  { {-.5,-.5,-.5,1},{-.5,-.5,.5,1},{-.5,.5,.5,1},{-.5,.5,-.5,1},{-.5,-.5,-.5,1}},
			  { {-.5,-.5,.5,1},{.5,-.5,.5,1},{.5,.5,.5,1},{-.5,.5,.5,1}, {-.5,-.5,.5,1} },
			  { {.5,-.5,.5,1},{.5,-.5,-.5,1},{.5,.5,-.5,1},{.5,.5,.5,1},{.5,-.5,.5,1} },
		      { {-.5,.5,-.5,1},{-.5,.5,.5,1},{.5,.5,.5,1},{.5,.5,-.5,1},{-.5,.5,-.5,1} },
		      { {.5,.5,.5,1},{.5,.5,-.5,1},{-.5,.5,-.5,1},{-.5,.5,.5,1},{.5,.5,.5,1}},
	};
	/*double Icosahedron[][][] = {
			{ {r1,0,r2,1},{-r1,0,r2,1},{0,r2,r1,1},{0,-r2,r1,1},{r2,r1,0,1},{-r2,r1,0,1},
			  {-r2,-r1,0,1},{r2,-r1,0,1},{0,r2,-r1,1},{0,-r2,-r1,1},{r1,0,-r2,1},{-r1,0,-r2,1}
			}
	};*/
	// vertices of Icosahedron for drawing line
	double Icosahedron[][][] = {
			{ {r1,0,r2,1},{-r1,0,r2,1},{0,r2,r1,1},{r1,0,r2,1}},
			{{r1,0,r2,1},{0,-r2,r1,1},{-r1,0,r2,1},{r1,0,r2,1}},
			{{r1,0,r2,1},{0,r2,r1,1},{r2,r1,0,1},{r1,0,r2,1}},
			{{r1,0,r2,1},{r2,-r1,0,1},{0,-r2,r1,1},{r1,0,r2,1}},
			{{r1,0,r2,1},{r2,-r1,0,1},{r2,r1,0,1},{r1,0,r2,1}},
			
			{{-r1,0,-r2,1},{r1,0,-r2,1},{0,-r2,-r1,1},{-r1,0,-r2,1}},
			{{-r1,0,-r2,1},{r1,0,-r2,1},{0,r2,-r1,1},{-r1,0,-r2,1}},
			{{-r1,0,-r2,1},{0,-r2,-r1,1},{-r2,-r1,0,1},{-r1,0,-r2,1}},
			{{-r1,0,-r2,1},{-r2,r1,0,1},{0,r2,-r1,1},{-r1,0,-r2,1}},
			{{-r1,0,-r2,1},{-r2,-r1,0,1},{-r2,r1,0,1},{-r1,0,-r2,1}},
			
			{{-r1,0,r2,1},{-r2,r1,0,1},{0,r2,r1,1}},
			{{-r1,0,r2,1},{-r2,-r1,0,1},{0,-r2,r1,1}},
			{{0,r2,r1,1},{0,r2,-r1,1},{r2,r1,0,1}},
			{{r2,r1,0,1},{r1,0,-r2,1},{r2,-r1,0,1}},
			{{0,-r2,r1,1},{0,-r2,-r1,1},{r2,-r1,0,1}},
			
	};
	// vertices of Icosahedron for drawing faces
	double Icosahedronface[][][]={
			{ {r1,0,r2,1},{-r1,0,r2,1},{0,r2,r1,1}},
			{{r1,0,r2,1},{0,-r2,r1,1},{-r1,0,r2,1}},
			{{r1,0,r2,1},{0,r2,r1,1},{r2,r1,0,1}},
			{{r1,0,r2,1},{r2,-r1,0,1},{0,-r2,r1,1}},
			{{r1,0,r2,1},{r2,-r1,0,1},{r2,r1,0,1},},
			
			{{-r1,0,-r2,1},{r1,0,-r2,1},{0,-r2,-r1,1}},
			{{-r1,0,-r2,1},{r1,0,-r2,1},{0,r2,-r1,1}},
			{{-r1,0,-r2,1},{0,-r2,-r1,1},{-r2,-r1,0,1}},
			{{-r1,0,-r2,1},{-r2,r1,0,1},{0,r2,-r1,1}},
			{{-r1,0,-r2,1},{-r2,-r1,0,1},{-r2,r1,0,1}},
			
			{{-r1,0,r2,1},{-r2,r1,0,1},{0,r2,r1,1}},
			{{-r1,0,r2,1},{-r2,-r1,0,1},{0,-r2,r1,1}},
			{{0,r2,r1,1},{0,r2,-r1,1},{r2,r1,0,1}},
			{{r2,r1,0,1},{r1,0,-r2,1},{r2,-r1,0,1}},
			{{0,-r2,r1,1},{0,-r2,-r1,1},{r2,-r1,0,1}},
			
			{{-r2,r1,0,1},{0,r2,r1,1},{0,r2,-r1,1}},
			{{r1,0,-r2,1},{r2,r1,0,1},{0,r2,-r1,1}},
			{{r1,0,-r2,1},{r2,-r1,0,1},{0,-r2,-r1,1}},
			{{-r2,-r1,0,1},{0,-r2,r1,1},{0,-r2,-r1,1}},
			{{-r2,r1,0,1},{-r1,0,r2,1},{-r2,-r1,0,1}},
			
			};
	
	
	public void render(Graphics g) {
		double time = getTime() - startTime;
		width = getWidth();
		height = getHeight();
		
		//move the Icosahedron and give it a bound
		x += vx ;
		y += vy ;
		z += vz ;
		if (x > .6 || x < -.6)
			vx = -vx;
		if (y > .6 || y < -.6)
			vy = -vy;
		if (z > .6|| z < -.6)
			vz = -vz;
		
		//refresh the canvas
		g.setColor(Color.yellow);                    
		g.fillRect(0, 0, width, height);
		g.setColor(Color.black);  

		//draw four little cubes
		Matrix m1 = animation1(time);
		draw(cubeface, m1, g, 4, 20);
		drawline(cube, m1 ,g);
		
		Matrix m2 = animation2(time);
		draw(cubeface, m2, g, 4, 30);
		drawline(cube, m2 ,g);
		
		Matrix m3 = animation3(time);
		draw(cubeface, m3, g, 4, 40);
		drawline(cube, m3 ,g);
		
		Matrix m4 = animation4(time);
		draw(cubeface, m4, g, 4, 50);
		drawline(cube, m4 ,g);
		
		//draw Icosahedron
		Matrix m = animation(time);
		draw(Icosahedronface,m,g,3, 159);
		drawline(Icosahedron,m,g);
		
		//draw four big cubes
		Matrix m5 = animation5(time);
		draw(cubeface, m5, g, 4, 70);
		drawline(cube, m5 ,g);
		
		Matrix m6 = animation6(time);
		draw(cubeface, m6, g, 4, 80);
		drawline(cube, m6 ,g);
		
		Matrix m7 = animation7(time);
		draw(cubeface, m7, g, 4, 90);
		drawline(cube, m7 ,g);
		
		Matrix m8 = animation8(time);
		draw(cubeface, m8, g, 4, 100);
		drawline(cube, m8 ,g);
		
		
	}
	
	void viewport(double src[], int dst[]) {
	      dst[0] = (int)(0.5 * width  + src[0] * width);
	      dst[1] = (int)(0.5 * height - src[1] * width);
	   }
	
	double getTime() {
		return System.currentTimeMillis() / 1000.0;
	}
	
	//draw faces with fillPolygon
	void draw(double point[][][], Matrix m, Graphics g, int n, int s){
		double a[][] = {{0,0,0},{0,0,0},{0,0,0},{0,0,0}};
		int ptem[][] = {{0,0},{0,0},{0,0},{0,0}};
		int pa[] = new int[n];
		int pb[] = new int[n];
		for (int i = 0 ; i < point.length ; i++) {  
			for (int j = 0 ; j < point[i].length ; j++) { 
			                      
				m.transform(point[i][j ], a[j]);			 
				viewport(a[j],ptem[j]);
				pa[j]=ptem[j][0];
				pb[j]=ptem[j][1];
						 
			}
			Color c=new Color((150+s*i)%255,(100+2*s*i)%255,((s+66)*i)%255);
			//Color c=new Color(20,55,43);
			g.setColor(c);
			g.fillPolygon(pa, pb, n);
		}
	}
	
	//draw line with drawLine
	void drawline(double point[][][], Matrix m, Graphics g){
		double a[] = {0,0,0}, b[] = {0,0,0};
		int pa[] = {0,0}, pb[] = {0,0};
		for (int i = 0 ; i < point.length ; i++)   // LOOP THROUGH ALL THE SHAPES
		     for (int j = 1 ; j < point[i].length ; j++) { // LOOP THROUGH ALL THE LINES IN THE SHAPE
			 m.transform(point[i][j-1], a);                      // TRANSFORM BOTH ENDPOINTS OF LINE
			 m.transform(point[i][j  ], b);

			 viewport(a, pa);
			 viewport(b, pb);
			 g.setColor(Color.black);
			 g.drawLine(pa[0], pa[1], pb[0], pb[1]);    // DRAW ONE LINE ON THE SCREEN
		     }
	}
	
	
	Matrix animation(double time){
		Matrix m = new Matrix();
		m.identity();
		m.scale(.255+.245*z, .255+.245*z, .255+.245*z);
		m.translate(x, y, z);
		m.rotateY((time));
		m.rotateX((-time));
		m.translate(.5, 0, 0);
		return m;
	}
	
	//different animations for objects
	Matrix animation1(double time){
		Matrix m1 = new Matrix();
		m1.identity();
		m1.rotateZ(2 * time);
		m1.translate(-.1, -.1, -5);
		m1.scale(.05, .05, .05);
		m1.rotateX((3*time));
		m1.rotateY((time));
		return m1;
	}
	
	Matrix animation2(double time){
		Matrix m2 = new Matrix();
		m2.identity();
		m2.rotateZ(2 * time);
		m2.translate(-.1, .1, -5);
		m2.scale(.05, .05, .05);
		m2.rotateX(3*time);
		m2.rotateY((time));
		return m2;
	}
	Matrix animation3(double time){
		Matrix m3 = new Matrix();
		m3.identity();
		m3.rotateZ(2 * time);
		m3.translate(.1, -.1,-5);
		m3.scale(.05, .05, .05);
		m3.rotateX(3*time);
		m3.rotateY((time));
		return m3;
	}
	Matrix animation4(double time){
		Matrix m4 = new Matrix();
		m4.identity();
		m4.rotateZ(2 * time);
		m4.translate(.1, .1, -5);
		m4.scale(.05, .05, .05);
		m4.rotateX(3*time);
		m4.rotateY((time));
		return m4;
	}
	Matrix animation5(double time){
		Matrix m5 = new Matrix();
		m5.identity();
		m5.rotateZ(-2 * time);
		m5.translate(-.3, -.3, 0);
		m5.scale(.2, .2, .2);
		m5.rotateX(3*time);
		m5.rotateY((time));
		return m5;
	}
	Matrix animation6(double time){
		Matrix m6 = new Matrix();
		m6.identity();
		m6.rotateZ(-2 * time);
		m6.translate(.3, -.3, 0);
		m6.scale(.2, .2, .2);
		m6.rotateX(3*time);
		m6.rotateY((time));
		return m6;
	}
	Matrix animation7(double time){
		Matrix m7 = new Matrix();
		m7.identity();
		m7.rotateZ(-2 * time);
		m7.translate(-.3, .3, 0);
		m7.scale(.2, .2, .2);
		m7.rotateX(3*time);
		m7.rotateY((time));
		return m7;
	}
	Matrix animation8(double time){
		Matrix m8 = new Matrix();
		m8.identity();
		m8.rotateZ(-2 * time);
		m8.translate(.3, .3, 0);
		m8.rotateX(3*time);
		m8.rotateY((time));
		m8.scale(.2, .2, .2);
		return m8;
	}
}
