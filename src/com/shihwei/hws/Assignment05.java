package com.shihwei.hws;

import java.awt.*;

import com.shihwei.render.BufferedApplet;
import com.shihwei.render.Geometry;
import com.shihwei.render.Matrix;
import com.shihwei.render.MatrixStack;

public class Assignment05 extends BufferedApplet
{
	int width = 0, height = 0;
	double a[][] = {{0,0,0,1},{0,0,0,1},{0,0,0,1},{0,0,0,1}};
	int ptem[][] = {{0,0},{0,0},{0,0},{0,0}};
	int pa[] = new int[4];
	int pb[] = new int[4];
	double ta[] = {0,0,0,1}, tb[] = {0,0,0,1};
	int tpa[] = {0,0}, tpb[] = {0,0};
	int count=0;
	Geometry cube = new Geometry();
	Geometry sphere = new Geometry();
	Geometry cylinder = new Geometry();
	
	
	double startTime = System.currentTimeMillis() / 1000.0;
	public void render(Graphics g) {
		double time = getTime() - startTime;
		//refresh canvas
		width = getWidth();
		height = getHeight();
		g.setColor(Color.cyan);                    
		g.fillRect(0, 0, width, height);
		
		animation(time,g);
		
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
			
			g.setColor(c);
			g.fillPolygon(pa, pb, 4);
		}
	}
	void drawline(Geometry g1, Matrix m, Graphics g){
		for (int i = 0 ; i < g1.face.length ; i++)   // LOOP THROUGH ALL THE SHAPES
		     for (int j = 1 ; j < g1.face[i].length ; j++) { // LOOP THROUGH ALL THE LINES IN THE SHAPE
			 m.transform(g1.vertices[g1.face[i][j-1]], ta);                      // TRANSFORM BOTH ENDPOINTS OF LINE
			 m.transform(g1.vertices[g1.face[i][j]], tb);
			 
			 ta[0]=ta[0]/ta[3];
			 tb[0]=tb[0]/tb[3];
			 ta[1]=ta[1]/ta[3];
			 tb[1]=tb[1]/tb[3];
			 viewport(ta, tpa);
			 viewport(tb, tpb);
			 g.drawLine(tpa[0], tpa[1], tpb[0], tpb[1]);    // DRAW ONE LINE ON THE SCREEN
		     }
	}
	void animation(double time,Graphics g){
		//get geometry for later use
				
		cube.cube();
		sphere.sphere(32,32);
		cylinder.cylinder(.5,.5,16);
				//create new stack
				MatrixStack mStack = new MatrixStack();
				g.setColor(Color.black);
				mStack.mclear();
				
				//move as a whole
				mStack.mpush();
					mStack.m().setCamera(2);
					mStack.m().translate(Math.sin(time)/5, Math.cos(time)/5, Math.sin(time));
					mStack.m().rotateX(Math.PI/4);
					mStack.m().rotateZ(Math.PI/4);
					mStack.m().rotateZ(Math.sin(4*time)/4);
					//helicopter
					mStack.mpush();
						mStack.m().scale(.1, .15, .1);
						drawline(sphere,mStack.m(),g);
					mStack.mpop();
					//tail
					mStack.mpush();
						mStack.m().translate(0, .15, 0);
						
						mStack.mpush();
							mStack.m().rotateX(Math.PI/2);
							mStack.m().translate(0, 0, -.1);
							mStack.m().scale(.02, .02, .1);
							drawline(cylinder,mStack.m(),g);
						mStack.mpop();	
						
						mStack.mpush();
							mStack.m().translate(.015, .2, 0);
							mStack.m().rotateX(Math.PI/2);
							
							mStack.mpush();
								mStack.m().translate(0, 0, .01);
								mStack.m().scale(.007, .007, .007);
								drawline(cube,mStack.m(),g);
							mStack.mpop();
							
							mStack.mpush();
								mStack.m().translate(.01, 0, .01);
								mStack.m().rotateX(10*time);
								
								mStack.mpush();
									mStack.m().rotateX(2*Math.PI/3);
									mStack.m().translate(0, 0, .03);
									mStack.m().scale(.007, .007, .03);
									drawline(cylinder,mStack.m(),g);
								mStack.mpop();
								
								mStack.mpush();
									mStack.m().rotateX(4*Math.PI/3);
									mStack.m().translate(0, 0, .03);
									mStack.m().scale(.007, .007, .03);
									drawline(cylinder,mStack.m(),g);
								mStack.mpop();
								
								mStack.mpush();
									mStack.m().translate(0, 0, .03);
									mStack.m().scale(.007, .007, .03);
									drawline(cylinder,mStack.m(),g);
								mStack.mpop();
							mStack.mpop();
						mStack.mpop();
					mStack.mpop();
					//propeller
					mStack.mpush();
						mStack.m().translate(0, 0, -.1);
						
						mStack.mpush();
							mStack.m().translate(0, 0, -.015);
							mStack.m().scale(.007, .007, .02);
							drawline(cylinder,mStack.m(),g);
						mStack.mpop();	
						
						mStack.mpush();
							mStack.m().translate(0, 0, -.03);
							mStack.m().rotateZ(10*time);
							mStack.mpush();
							mStack.m().rotateX(Math.PI/2);
							mStack.m().scale(.015, .007, .3);
							drawline(cylinder,mStack.m(),g);
						mStack.mpop();
							
						mStack.mpush();
							mStack.m().rotateZ(Math.PI/2);
							mStack.m().rotateX(Math.PI/2);
							mStack.m().scale(.015, .007, .3);
							drawline(cylinder,mStack.m(),g);
						mStack.mpop();
					mStack.mpop();
				mStack.mpop();
					//Foot rest
					mStack.mpush();
						mStack.m().translate(.05, .05, .1);
						mStack.m().scale(.015, .015, .02);
						drawline(cylinder,mStack.m(),g);	
					mStack.mpop();	
					mStack.mpush();
						mStack.m().translate(.05, -.05, .1);
						mStack.m().scale(.015, .015, .02);
					drawline(cylinder,mStack.m(),g);	
					mStack.mpop();	
					mStack.mpush();
						mStack.m().translate(-.05, .05, .1);
						mStack.m().scale(.015, .015, .02);
						drawline(cylinder,mStack.m(),g);	
					mStack.mpop();	
					mStack.mpush();
						mStack.m().translate(-.05, -.05, .1);
						mStack.m().scale(.015, .015, .02);
						drawline(cylinder,mStack.m(),g);	
					mStack.mpop();	
					mStack.mpush();
						mStack.m().translate(-.05, .025, .12);
						mStack.m().rotateX(Math.PI/2);
						mStack.m().scale(.015, .005, .16);
						drawline(cylinder,mStack.m(),g);	
					mStack.mpop();	
					mStack.mpush();
						mStack.m().translate(.05, .025, .12);
						mStack.m().rotateX(Math.PI/2);
						mStack.m().scale(.015, .005, .16);
					drawline(cylinder,mStack.m(),g);	
					mStack.mpop();
				mStack.mpop();	
	}

}
