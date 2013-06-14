package com.shihwei.hws;

import java.awt.Color;
import java.awt.Graphics;

import com.shihwei.render.BufferedApplet;
import com.shihwei.render.Geometry;
import com.shihwei.render.Matrix;
import com.shihwei.render.Spline;

public class Assignment06 extends BufferedApplet
{
	boolean init = true;
	int width = 0, height = 0;
	double a[][] = {{0,0,0,1},{0,0,0,1},{0,0,0,1},{0,0,0,1}};
	int ptem[][] = {{0,0},{0,0},{0,0},{0,0}};
	int pa[] = new int[4];
	int pb[] = new int[4];
	double ta[] = {0,0,0,1}, tb[] = {0,0,0,1};
	int tpa[] = {0,0}, tpb[] = {0,0};
	int count=0;
	double startTime = System.currentTimeMillis() / 1000.0;
	
	Geometry world = new Geometry();
	Geometry body, neck, rshoulder, lshoulder, relbow, lelbow, waist, rhip, lhip, rknee, lknee;
	Geometry torso, head, rupperarm, rlowerarm, lupperarm, llowerarm, rupperleg, rlowerleg, lupperleg, llowerleg, bottom;
	
	double headani[][] ={{0,0},{.5,Math.sin(.5)},{1,0},{1.5,Math.sin(.5)},{2,0}, {2.5,Math.sin(.5)},{3,0},{3.5,Math.sin(.5)},{4,0}};
	Spline headNod = new Spline(headani);
	double lhandxswin[][] = {{4,0}, {6,4*Math.PI/4},{10,4*Math.PI/4}};
	Spline lhandxmove = new Spline(lhandxswin);
	double lhandyswin[][] = {{0,0},{.5,1.5*Math.PI/4},{1,0},{1.5,-1.5*Math.PI/4},{2,0}, {2.5,1.5*Math.PI/4},{3,0},{3.5,-1.5*Math.PI/4},{4,0}};
	Spline lhandymove = new Spline(lhandyswin);
	
	double rhandswin[][] = {{0,0},{.5,1.5*Math.PI/4},{1,0},{1.5,-1.5*Math.PI/4},{2,0}, {2.5,1.5*Math.PI/4},{3,0},{3.5,-1.5*Math.PI/4},{4,0}};
	Spline rhandmove = new Spline(rhandswin);
	double bodybend[][] = {{2,0}, {4,-1.2*Math.PI/4}, {6,0}, {10,0} };
	Spline bodymove = new Spline(bodybend);
	double bodytwist[][] = {{2,0}, {4,0}, {6,Math.PI/2}, {10,Math.PI/2} };
	Spline bodytwi = new Spline(bodytwist);
	double rleg[][] = {{0,0},{.5,-1.5*Math.PI/4},{1,0},{1.5,1.5*Math.PI/4},{2,0}, {2.5,-1.5*Math.PI/4},{3,0},{3.5,1.5*Math.PI/4},{4,0}};
	Spline rlegmove = new Spline(rleg);
	double rlegz[][] = {{3,0}, {4,1*Math.PI/4},{10,1*Math.PI/4}};
	Spline rlegzmove = new Spline(rlegz);
	double rlegy[][] = {{3,0}, {4,-1*Math.PI/4},{6,1.5*Math.PI/4},{10,2*Math.PI/4}};
	Spline rlegymove = new Spline(rlegy);
	double rkneee[][] = {{0,0},{.5,1.5*Math.PI/4},{1,0},{1.5,1.5*Math.PI/4},{2,0}, {2.5,1.5*Math.PI/4},{3,0},{3.5,1.5*Math.PI/4},{4,0}};
	Spline rkneemove = new Spline(rkneee);
	double llegx[][] = {{0,0},{.5,1.5*Math.PI/4},{1,0},{1.5,-1.5*Math.PI/4},{2,0}, {2.5,1.5*Math.PI/4},{3,0},{3.5,-1.5*Math.PI/4},{4,0}};
	Spline llegxmove = new Spline(llegx);
	double llegz[][] = {{5,0},{8,-.8*Math.PI/4}, {10,-.8*Math.PI/4} };
	Spline llegzmove = new Spline(llegz);
	double lkneee[][] = {{0,0},{.5,1.5*Math.PI/4},{1,0},{1.5,1.5*Math.PI/4},{2,0}, {2.5,1.5*Math.PI/4},{3,0},{3.5,1.5*Math.PI/4},{4,0}};
	Spline lkneemove = new Spline(lkneee);
	
	double whole[][] = {{0,0},{.5,.5*Math.sin(.5)},{1,0},{1.5,.5*Math.sin(.5)},{2,0}, {2.5,.5*Math.sin(.5)},{3,0},{3.5,.5*Math.sin(.5)},{4,0}};
	Spline waistmove = new Spline(whole);
	//double relo = {{
				
	
	
	public void initialize() {

	waist = world.add();
	body = waist.add();
	neck = body.add();
	rshoulder = body.add();
	lshoulder = body.add();
	relbow = rshoulder.add();
	lelbow = lshoulder.add();
	
	rhip = waist.add();
	lhip = waist.add();
	rknee = rhip.add();
	lknee = lhip.add();
	
	torso = body.add().cylinder(1,.5,16);
	head = neck.add().sphere(16, 16);
	rupperarm = rshoulder.add().cylinder(1, 1, 16);
	rlowerarm = relbow.add().cylinder(1, 1, 16);
	lupperarm = lshoulder.add().cylinder(1, 1, 16);
	llowerarm = lelbow.add().cylinder(1, 1, 16);
	bottom = waist.add().sphere(16, 16);
	rupperleg = rhip.add().cylinder(1, 1, 16);
	rlowerleg = rknee.add().cylinder(1, 1, 16);
	lupperleg = lhip.add().cylinder(1, 1, 16);
	llowerleg = lknee.add().cylinder(1, 1, 16);
	
	
	Matrix m;
	m = torso.getMatrix();
    m.rotateX(-Math.PI / 2); // ORIENT THE TORSO CYLINDER UPRIGHT
    m.scale(0.09, 0.07, .1);
    m.translate(0, 0, 1);

    m = head.getMatrix();
    m.translate(0, .1, 0);        // MOVE THE HEAD UP, OFF OF THE TORSO
    m.scale(0.05, 0.07, 0.05);      // CREATE THE SIZE/SHAPE OF THE HEAD

    m = rupperarm.getMatrix();
    m.rotateY(Math.PI / 2); // UPPER ARM STICKS OUT TO THE RIGHT
    m.scale(0.02, 0.02, .07);
    m.translate(0, 0, 1);

    m = rlowerarm.getMatrix();
    m.rotateY(Math.PI / 2); // LOWER ARM STICKS OUT TO THE RIGHT
    m.scale(0.02, 0.02, .07);
    m.translate(0, 0, 1);
    
    m = lupperarm.getMatrix();
    m.rotateY(-Math.PI / 2); // UPPER ARM STICKS OUT TO THE RIGHT
    m.scale(0.02, 0.02, .07);
    m.translate(0, 0, 1);

    m = llowerarm.getMatrix();
    m.rotateY(-Math.PI / 2); // LOWER ARM STICKS OUT TO THE RIGHT
    m.scale(0.02, 0.02, .07);
    m.translate(0, 0, 1);
    
    m = bottom.getMatrix();
    m.scale(0.06, 0.03, .06);
    m.translate(0, -1, 0);
	
    m = rupperleg.getMatrix();
    m.rotateX(-Math.PI / 2); // LOWER ARM STICKS OUT TO THE RIGHT
    m.scale(0.02, 0.02, .1);
    m.translate(0, 0, -1);
    
    m = rlowerleg.getMatrix();
    m.rotateX(-Math.PI / 2); // LOWER ARM STICKS OUT TO THE RIGHT
    m.scale(0.02, 0.02, .1);
    m.translate(0, 0, -1);
    
    m = lupperleg.getMatrix();
    m.rotateX(-Math.PI / 2); // LOWER ARM STICKS OUT TO THE RIGHT
    m.scale(0.02, 0.02, .1);
    m.translate(0, 0, -1);
    
    m = llowerleg.getMatrix();
    m.rotateX(-Math.PI / 2); // LOWER ARM STICKS OUT TO THE RIGHT
    m.scale(0.02, 0.02, .1);
    m.translate(0, 0, -1);
	}
	
	public void animate(double time) {
	     
		
		  Matrix m;
		  
		  m = waist.getMatrix();
		  m.identity();
		  m.translate(0, waistmove.result(time), 0);
		  m.rotateY(Math.PI/4);
		  //m.rotateX(Math.PI/8);
		  
		  
	      m = neck.getMatrix();
	      m.identity();
	      m.translate(0, .2, 0);
	      m.rotateX(-Math.PI/4);
	      m.rotateX(headNod.result(time));
	      //System.out.println(time+" "+headNod.result(time));
	      
	      m = rshoulder.getMatrix();
	      m.identity();
	      m.translate(.1, .2, 0);
	      m.rotateZ(-Math.PI/2);
	      
	      //m.rotateY(-Math.PI/4);
	      m.rotateY(rhandmove.result(time));
	      //m.rotateZ(Math.PI / 4 * Math.sin(3 * time));

	      m = relbow.getMatrix();
	      m.identity();
	      m.translate(.15, 0, 0);
	      m.rotateY(-Math.PI/2);
	      //m.rotateZ(Math.PI / 4 * (1 + Math.sin(3 * time)));
	      
	      m = lshoulder.getMatrix();
	      m.identity();
	      m.translate(-.1, .2, 0);
	      m.rotateZ(Math.PI/2);
	      //m.rotateZ(lhandzmove.result(time));
	      //m.rotateX(lhandxmove.result(time));
	      //m.rotateY(Math.PI/4);
	      m.rotateY(lhandymove.result(time));
	      //m.rotateZ(Math.PI / 4 * -Math.sin(3 * time));

	      m = lelbow.getMatrix();
	      m.identity();
	      m.translate(-.15, 0, 0);
	      m.rotateY(Math.PI/2);
	      //m.rotateZ(Math.PI / 4 * -(1 + Math.sin(3 * time)));
	      
	      m = body.getMatrix();
	      m.identity();
	      //m.rotateY(bodytwi.result(time));
	      m.rotateX(Math.PI/4);
	     // m.rotateX(bodymove.result(time));
	      
	      //m.rotateX(Math.PI / 4 * -(1 + Math.sin(3 * time)));
	      
	      m = rhip.getMatrix();
	      m.identity();
	      m.translate(.05, -.06, 0);
	      //m.rotateZ(rlegzmove.result(time));
	      //m.rotateY(rlegymove.result(time));
	      m.rotateX(rlegmove.result(time));
	      
	     
	      //m.rotateX(Math.PI / 4 * -(1 + Math.sin(3 * time)));
	      
	      m = lhip.getMatrix();
	      m.identity();
	      m.translate(-.05, -.06, 0);
	      //m.rotateZ(llegzmove.result(time));
	      m.rotateX(llegxmove.result(time));
	      //m.rotateY(llegymove.result(time));
	      //m.rotateX(Math.PI / 4 * -(1 + Math.sin(3 * time)));
	      
	      m = rknee.getMatrix();
	      m.identity();
	      m.translate(0, -.21, 0);
	      m.rotateX(rkneemove.result(time));
	     // m.rotateX(Math.PI / 4 * -(1 + Math.sin(3 * time)));
	      
	      m = lknee.getMatrix();
	      m.identity();
	      m.translate(0, -.21, 0);
	      m.rotateX(lkneemove.result(time));
	      //m.rotateX(Math.PI / 4 * -(1 + Math.sin(3 * time)));
	   }
	
	public void render(Graphics g) {
		if	(init){
			initialize();
			init = false;
		}
		double time = getTime() - startTime;
		//refresh canvas
		width = getWidth();
		height = getHeight();
		g.setColor(Color.cyan);                    
		g.fillRect(0, 0, width, height);
		
		g.setColor(Color.black);
		animate(time);
		updateMatrix(world);
		draw(world,g);
		
		if (time >= 4)
			startTime = getTime();
		
	}
	
	public void updateMatrix(Geometry geo){
		Matrix m, m1, m2;
		m = new Matrix();		
		for (int i = 0; i < geo.getNumChildren(); i++){
			m.copy(geo.getMatrix2());
			m1 = geo.getChild(i).getMatrix();
			m2 = geo.getChild(i).getMatrix2();
			m.multiply(m1);
			m2.copy(m);
			
			updateMatrix(geo.getChild(i));
		}
	}
	
	void viewport(double src[], int dst[]) {
	      dst[0] = (int)(0.5 * width  + src[0] * width);
	      dst[1] = (int)(0.5 * height - src[1] * width);
	   }
	
	double getTime() {
		return System.currentTimeMillis() / 1000.0;
	}
	
	void drawface(Geometry g1, Matrix m, Graphics g, int s){
		
		
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
	void drawline(Geometry g1, Graphics g){
		
		
		for (int i = 0 ; i < g1.face.length ; i++)   // LOOP THROUGH ALL THE SHAPES
		     for (int j = 1 ; j < g1.face[i].length ; j++) { // LOOP THROUGH ALL THE LINES IN THE SHAPE
			 g1.m2.transform(g1.vertices[g1.face[i][j-1]], ta);                      // TRANSFORM BOTH ENDPOINTS OF LINE
			 g1.m2.transform(g1.vertices[g1.face[i][j]], tb);
			 
			 ta[0]=ta[0]/ta[3];
			 tb[0]=tb[0]/tb[3];
			 ta[1]=ta[1]/ta[3];
			 tb[1]=tb[1]/tb[3];
			 viewport(ta, tpa);
			 viewport(tb, tpb);
			 g.drawLine(tpa[0], tpa[1], tpb[0], tpb[1]);    // DRAW ONE LINE ON THE SCREEN
		     }
	}
	void draw(Geometry g1, Graphics g){
		if (g1.face!=null)
			drawline(g1,g);
		
		for (int i=0 ; i < g1.getNumChildren() ; i++){
			draw(g1.getChild(i),g);
		}
	}

}
