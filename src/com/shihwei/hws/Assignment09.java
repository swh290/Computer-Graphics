package com.shihwei.hws;

import java.util.ArrayList;

import com.shihwei.render.Geometry;
import com.shihwei.render.Light;
import com.shihwei.render.MISApplet;
import com.shihwei.render.Material;
import com.shihwei.render.Matrix;
import com.shihwei.render.Trapezoid;
import com.shihwei.render.Zbuf;


public class Assignment09 extends MISApplet{
	Zbuf zbuf[][]; 
	double focalLength = 2;
	double v[] = {0,0,focalLength};
	double w[] = {0,0,0};
	double r[] = {0,0,0};
	double n[] = {0,0,0};
	int vpa[][] = {{0,0},{0,0},{0,0}};
	double top[] = new double[7];
	double mid[] = new double[7];
	double mid2[] = new double[7];
	double bot[] = new double[7];
	double aa[] = new double[7];
	double bb[] = new double[7];
	double cc[] = new double[7];
	double v1[] = new double[7];
	double v2[] = new double[7];
	double v3[] = new double[7];
	double v4[] = new double[7];
	
	double dxr,dxg,dxb,Lxr,Lxg,Lxb,Rx,Lx,dLr,dLg,dLb,dRr,dRg,dRb,Lr,Lg,Lb,Rr,Rg,Rb;
	double dxz,Lxz,dLz,dRz,Lz,Rz;
	
	
	Trapezoid tr1 = new Trapezoid();
	Trapezoid tr2 = new Trapezoid();
	double tri1[][] = { {-.6,.5,-5,1,0,0,1},{-.5,-.5,-5,1,0,0,1},{.5,-.3,-5,1,0,0,1}};
	
	ArrayList<Light> lights = new ArrayList<Light>();
	
	Geometry world = new Geometry();
	Geometry body, neck, rshoulder, lshoulder, relbow, lelbow, waist, rhip, lhip, rknee, lknee;
	Geometry torso, head, rupperarm, rlowerarm, lupperarm, llowerarm, rupperleg, rlowerleg, lupperleg, llowerleg, bottom;
	Geometry sphere, cylinder;
	public void initialize(){
		zbuf = new Zbuf[W][H]; 
		for (int i = 0; i<W; i++)
			for (int j = 0; j<H; j++){
				zbuf[i][j] = new Zbuf();
			}	
		lights.add(new Light(1,1,1,1,1,1));
		lights.add(new Light(-1,1,1,1,1,1));
		
		
		Material mat;
		
		sphere = world.add().cube();
		mat = sphere.getMaterial();
		mat.setAmbient(1, .5, 0);
		mat.setDiffuse(.2, .2, .2);
		mat.setSpecular(.5, .5, .5);
		mat.setSpecularPower(15);
		
		
		
	}
	
	
	public void initFrame(double time){
		for (int i = 0; i<W; i++)
			for (int j = 0; j<H; j++){				
				zbuf[i][j].setDepth(-Double.MAX_VALUE);
				zbuf[i][j].setRGB(0, 255, 255);
			}
		
		Matrix m;
		  
		m = sphere.getMatrix();
		m.identity();
		//m.translate(-.35, -.1, -0);
		m.rotateY(time);
		m.rotateY(Math.PI/3);
		m.scale(.1, .1, .1);
		
		

		
		
		
		 
		
		updateMatrix(world);
		
		draw(world);	
	}
	
	
	public void setPixel(int x, int y, int rgb[]){
		
		rgb[0] = zbuf[x][y].getR();
		rgb[1] = zbuf[x][y].getG();
		rgb[2] = zbuf[x][y].getB();
		
	}
	
	void viewport(double src[], double dst[]) {
		//System.out.print(W+" "+H); 
	      dst[0] = (int)(0.5 * W  + src[0] * W);
	      dst[1] = (int)(0.5 * H - src[1] * W);
	      
	   }
	
	
	private void getPhong(double p[],Material m, double result[]){
		for (int i = 0; i<3;i++){
			w[i] = p[i]-v[i];
			result[i] = p[i];
			n[i] = p[i+4];
		}
		
		normalize(w);
		normalize(n);
		reflect(w,n,r);
		
		for (int i = 0;i<3;i++){
			result[i+4] = m.ambient[i];
			for (int j = 0;j<lights.size();j++){
				result[i+4] += lights.get(j).illuminance[i] * (m.diffuse[i] * dot(lights.get(j).direction,n) + m.specular[i]* (Math.pow(dot(lights.get(j).direction,r), m.specularPower)));
			}
			result[i+4] = (255*Math.min(Math.max(result[i+4], 0.0), 1.0));
			;
		}
	}
	
	
	private void normalize(double vec[]){
		double norm = Math.sqrt(dot(vec,vec));
		for (int i =0;i<3;i++)
			vec[i] /= norm;
	}
	
	private double dot(double vec1[], double vec2[]){
		double result = 0;
		for (int i=0;i<vec1.length;i++)
			result += vec1[i] * vec2[i];
		return result;
	}
	
	private void reflect(double w[],double n[], double r[]){
        r[0] = w[0] - (2.0 * (dot(w,n)) * n[0]);
        r[1] = w[1] - (2.0 * (dot(w,n)) * n[1]);
        r[2] = w[2] - (2.0 * (dot(w,n)) * n[2]);  
	}
	
	public void perspective(double a[]){
		a[0] = a[0] * focalLength / (focalLength-a[2]);
		a[1] = a[1] * focalLength / (focalLength-a[2]);
		//a[2] = 1 / a[2];
	}
	public void drawTri(double a[],double b[],double c[],Material mat){
		aa = a.clone();
		bb = b.clone();
		cc = c.clone();
		
		getPhong(aa,mat,aa);
		getPhong(bb,mat,bb);
		getPhong(cc,mat,cc);
		
		perspective(aa);
		perspective(bb);
		perspective(cc);
		
		viewport(aa,aa);
		viewport(bb,bb);
		viewport(cc,cc);
		
		getTrapezoid(aa,bb,cc);
		
		interpolation(tr1);
		interpolation(tr2);
	}
	public void getTrapezoid(double a[],double b[],double c[]){
		
		
		top = a;
		if (b[1]<top[1])
			top = b;
		if (c[1]<top[1])
			top = c;
		bot = a;
		if (b[1]>bot[1])
			bot = b;
		if (c[1]>bot[1])
			bot = c;
		
		if (!a.equals(top)&&!a.equals(bot))
			mid = a;
		if (!b.equals(top)&&!b.equals(bot))
			mid = b;
		if (!c.equals(top)&&!c.equals(bot))
			mid = c;
		
		
		if (bot[0]>top[0]){
			mid2[0] = (top[0] + (bot[0]-top[0])*(mid[1]-top[1])/(bot[1]-top[1]));
			mid2[1] = mid[1];
			mid2[2] = top[2] + (bot[2]-top[2])*(mid[1]-top[1])/(bot[1]-top[1]);
			mid2[3] = mid[3];
			mid2[4] = (top[4] + (bot[4]-top[4])*(mid[1]-top[1])/(bot[1]-top[1]));
			mid2[5] = (top[5] + (bot[5]-top[5])*(mid[1]-top[1])/(bot[1]-top[1]));
			mid2[6] = (top[6] + (bot[6]-top[6])*(mid[1]-top[1])/(bot[1]-top[1]));
		}
		else if (bot[0]<=top[0]){
			mid2[0] = (top[0] - (top[0]-bot[0])*(mid[1]-top[1])/(bot[1]-top[1]));
			mid2[1] = mid[1];
			mid2[2] = (top[2] - (top[2]-bot[2])*(mid[1]-top[1])/(bot[1]-top[1]));;
			mid2[3] = mid[3];
			mid2[4] = (top[4] - (top[4]-bot[4])*(mid[1]-top[1])/(bot[1]-top[1]));
			mid2[5] = (top[5] - (top[5]-bot[5])*(mid[1]-top[1])/(bot[1]-top[1]));
			mid2[6] = (top[6] - (top[6]-bot[6])*(mid[1]-top[1])/(bot[1]-top[1]));
		}
		
		
		
		tr1.TL = top;
		tr1.TR = top;
		if (mid[0]>mid2[0]){
			tr1.BL = mid2;
			tr1.BR = mid;
			tr2.TL = mid2;
			tr2.TR = mid;
		}
		else if (mid[0]<=mid2[0]){
			tr1.BL = mid;
			tr1.BR = mid2;
			tr2.TL = mid;
			tr2.TR = mid2;
		}
		tr2.BL = bot;
		tr2.BR = bot;
	}
	
	public void interpolation(Trapezoid tr){
		
		double n = (tr.BL[1] - tr.TL[1]);
		if (n!=0){
			dLr = ((tr.BL[4] - tr.TL[4]) / n);
			dLg = ((tr.BL[5] - tr.TL[5]) / n);
			dLb = ((tr.BL[6] - tr.TL[6]) / n);
			dRr = ((tr.BR[4] - tr.TR[4]) / n);
			dRg = ((tr.BR[5] - tr.TR[5]) / n);
			dRb = ((tr.BR[6] - tr.TR[6]) / n);
			dLz = ((tr.BL[2] - tr.TL[2]) / n);	  
			dRz = ((tr.BR[2] - tr.TR[2]) / n);	 
		}
		else {
			dLr = 0;
			dLg = 0;
			dLb = 0;
			dRr = 0;
			dRg = 0;
			dRb = 0;
			dLz = 0;	  
			dRz = 0;	 
		}
		Lr = tr.TL[4];
		Lg = tr.TL[5];
		Lb = tr.TL[6];
		Rr = tr.TR[4];
		Rg = tr.TR[5];
		Rb = tr.TR[6];
		Lz = tr.TL[2];
		Rz = tr.TR[2];
		
		for (int y = (int)tr.TL[1] ; y < (int)tr.BL[1] ; y++) {
			       // INCREMENTALLY UPDATE VALUES ALONG LEFT AND RIGHT EDGES
			Lr += dLr;
			Lg += dLg;
			Lb += dLb;
			Rr += dRr;
			Rg += dRg;
			Rb += dRb;		 
			Lz += dLz;
			Rz += dRz;
			if (tr.TL[0]>tr.BL[0])
				Lx = (tr.TL[0] - (tr.TL[0]-tr.BL[0])*(y-tr.TL[1])/(tr.BL[1]-tr.TL[1]));
			else 
				Lx = (tr.TL[0] + (tr.BL[0]-tr.TL[0])*(y-tr.TL[1])/(tr.BL[1]-tr.TL[1]));
			
			if (tr.TR[0]>tr.BR[0])
				Rx = (tr.TR[0] - (tr.TR[0]-tr.BR[0])*(y-tr.TR[1])/(tr.BR[1]-tr.TR[1]));
			else 
				Rx = (tr.TR[0] + (tr.BR[0]-tr.TR[0])*(y-tr.TR[1])/(tr.BR[1]-tr.TR[1]));
			
		       // COMPUTE TOTAL NUMBER OF PIXELS ACROSS THIS SCAN LINE
			double m = (Rx - Lx);
			if (m != 0){
				dxr = ((Rr - Lr) / m);
				dxg = ((Rg - Lg) / m);
				dxb = ((Rb - Lb) / m);
				dxz = ((Rz - Lz) / m);
			}
			else {
				dxr = 0;
				dxg = 0;
				dxb = 0;
				dxz = 0;
			}
				Lxr = Lr;
				Lxg = Lg;
				Lxb = Lb;
				Lxz = Lz;
			
			for (int x = (int)Lx ; x < (int)Rx ; x++) {
			          // INCREMENTALLY UPDATE VALUE AT PIXEL
				Lxr += dxr;
				Lxg += dxg;
				Lxb += dxb;
				Lxz += dxz;
				if (x>=0 && x<W && y>=0 && y<H && zbuf[x][y].getDepth()<Lxz && Lxz<focalLength){
					zbuf[x][y].setDepth(Lxz);
					zbuf[x][y].setRGB((int)Lxr, (int)Lxg, (int)Lxb);
				}
					
			}
		}
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
	
	public void draw(Geometry g){
		if (g.face!=null){
			for (int i = 0; i<g.face.length;i++){
				
				g.m2.transform(g.vertices[g.face[i][0]], v1);
				g.m2.transform(g.vertices[g.face[i][1]], v2);
				g.m2.transform(g.vertices[g.face[i][2]], v3);
				g.m2.transform(g.vertices[g.face[i][3]], v4);
				
				/*for (int k = 0;k<7;k++)
					System.out.print(g.vertices[g.face[i][0]][k]+" ");
				System.out.println();System.out.println();
				for (int k = 0;k<7;k++)
					System.out.print(v1[k]+" ");
				System.out.println();System.out.println();*/
				
				
				drawTri(v1,v2,v3,g.mat);
				drawTri(v1,v3,v4,g.mat);
			}
		}
			
		for (int i=0 ; i < g.getNumChildren() ; i++){
			draw(g.getChild(i));
		}
	}
	
	/*public void draw(double a[][]){
		Material m=new Material();
		for (int i = 0;i<a.length;i++){
			
			drawTri(a[0],a[1],a[2],m);
			
		}
	}*/
}

	