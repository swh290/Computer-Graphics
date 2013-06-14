package com.shihwei.hws;

import java.util.ArrayList;

import com.shihwei.render.Geometry;
import com.shihwei.render.ImageBuffer;
import com.shihwei.render.Light;
import com.shihwei.render.MISApplet;
import com.shihwei.render.Material;
import com.shihwei.render.Matrix;
import com.shihwei.render.Trapezoid;
import com.shihwei.render.Zbuf;


public class Assignment10 extends MISApplet{
	Zbuf zbuf[][]; 
	Matrix minv = new Matrix();
	int type;
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
	double temp1[] = new double[4];
	double temp2[] = new double[4];
	double temp3[] = new double[4];
	double dxr,dxg,dxb,Lxr,Lxg,Lxb,Rx,Lx,dLr,dLg,dLb,dRr,dRg,dRb,Lr,Lg,Lb,Rr,Rg,Rb;
	double dxz,Lxz,dLz,dRz,dLx,dRx,Lz,Rz;
	int col[] = new int[3];
	int val[] = new int[3];
	ImageBuffer buffer;
	
	Trapezoid tr1 = new Trapezoid();
	Trapezoid tr2 = new Trapezoid();
	double tri1[][] = { {-.6,.5,-5,1,0,0,1},{-.5,-.5,-5,1,0,0,1},{.5,-.3,-5,1,0,0,1}};
	
	ArrayList<Light> lights = new ArrayList<Light>();
	
	Geometry world = new Geometry();
	Geometry body, neck, rshoulder, lshoulder, relbow, lelbow, waist, rhip, lhip, rknee, lknee;
	Geometry torso, head, rupperarm, rlowerarm, lupperarm, llowerarm, rupperleg, rlowerleg, lupperleg, llowerleg, bottom;
	Geometry sphere, cylinder;
	public void initialize(){
		String urlName1 = getParameter("image1");
		String urlName2 = getParameter("image2");
		//buffer = new ImageBuffer("map4.jpg", this);
		zbuf = new Zbuf[W][H]; 
		for (int i = 0; i<W; i++)
			for (int j = 0; j<H; j++){
				zbuf[i][j] = new Zbuf();
			}	
		lights.add(new Light(1,1,1,1,1,1));
		lights.add(new Light(-1,1,1,1,1,1));
		
		
		
		
		sphere = world.add().sphere(64,64);
		sphere.urlName = "map4.jpg";
		
		cylinder = world.add().cylinder(64);
		cylinder.urlName = "ave.jpg";
		
		
	}
	
	
	public void initFrame(double time){
		for (int i = 0; i<W; i++)
			for (int j = 0; j<H; j++){				
				zbuf[i][j].setDepth(-Double.MAX_VALUE);
				zbuf[i][j].setRGB(0, 0, 0);
			}
		
		Matrix m;
		  
		m = sphere.getMatrix();
		m.identity();
		m.translate(.25, 0, -0);
		m.rotateX(Math.PI/2);
		m.rotateZ(-time/2);
		m.scale(.2, .2, .2);
		
		
		m = cylinder.getMatrix();
		m.identity();
		m.translate(-.25, 0, -0);
		m.rotateX(Math.PI/2);
		m.rotateZ(time/2);
		m.scale(.08, .08, .3);

		
		
		
		 
		
		updateMatrix(world);
		
		draw(world);	
	}
	
	
	public void setPixel(int x, int y, int rgb[]){
		/*rgb[0] = buffer.get(x*buffer.width/W, y*buffer.height/H, 0);
		rgb[1] = buffer.get(x*buffer.width/W, y*buffer.height/H, 1);
		rgb[2] = buffer.get(x*buffer.width/W, y*buffer.height/H, 2);
		*/
		rgb[0] = zbuf[x][y].getR();
		rgb[1] = zbuf[x][y].getG();
		rgb[2] = zbuf[x][y].getB();
		
	}
	
	void viewport(double src[], double dst[]) {
		//System.out.print(W+" "+H); 
	      dst[0] = (int)(0.5 * W  + src[0] * W);
	      dst[1] = (int)(0.5 * H - src[1] * W);
	      dst[2] = src[2];
	      dst[3] = src[3];
	   }
	void inverseViewport(double src[], double dst[]) {
		//System.out.print(W+" "+H); 
	      dst[0] = (src[0]-.5*W)/W;
	      dst[1] = (.5*H-src[1])/W;
	      dst[2] = src[2];
	      dst[3] = src[3];
	   }
	
	/*private void getPhong(double p[],Material m, double result[]){
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
	
	
	
	
	private void reflect(double w[],double n[], double r[]){
        r[0] = w[0] - (2.0 * (dot(w,n)) * n[0]);
        r[1] = w[1] - (2.0 * (dot(w,n)) * n[1]);
        r[2] = w[2] - (2.0 * (dot(w,n)) * n[2]);  
	}
	private double dot(double vec1[], double vec2[]){
		double result = 0;
		for (int i=0;i<vec1.length;i++)
			result += vec1[i] * vec2[i];
		return result;
	}
	private void normalize(double vec[]){
		double norm = Math.sqrt(dot(vec,vec));
		for (int i =0;i<3;i++)
			vec[i] /= norm;
	}
	
	*/
	
	
	
	
	public void perspective(double src[], double dst[]){
		dst[0] = src[0] * focalLength / (focalLength-src[2]);
		dst[1] = src[1] * focalLength / (focalLength-src[2]);
		dst[2] = src[2];
		dst[3] = src[3];
	}
	
	public void inversePerspective(double src[], double dst[]){
		dst[0] = src[0] * (focalLength-src[2]) / focalLength;
		dst[1] = src[1] * (focalLength-src[2]) / focalLength;
		dst[2] = src[2];
		dst[3] = src[3];
	}
	
	public void drawTri(double a[],double b[],double c[],Material mat,Matrix m){
		
		minv = m;
		
		perspective(a,aa);
		perspective(b,bb);
		perspective(c,cc);
		
		
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
			dLz = ((tr.BL[2] - tr.TL[2]) / n);	  
			dRz = ((tr.BR[2] - tr.TR[2]) / n);
			dLx = ((tr.BL[0] - tr.TL[0]) / n);	  
			dRx = ((tr.BR[0] - tr.TR[0]) / n); 
		}
		else {
			dLz = 0;	  
			dRz = 0;	
			dLx = 0;	  
			dRx = 0; 
		}
		Lz = tr.TL[2];
		Rz = tr.TR[2];
		Lx = tr.TL[0];
		Rx = tr.TR[0];
		
		for (int y = (int)tr.TL[1] ; y < (int)tr.BL[1] ; y++) {
			       // INCREMENTALLY UPDATE VALUES ALONG LEFT AND RIGHT EDGES		 
			Lz += dLz;
			Rz += dRz;
			Lx += dLx;
			Rx += dRx;
			
		       // COMPUTE TOTAL NUMBER OF PIXELS ACROSS THIS SCAN LINE
			double m = (Rx - Lx);
			if (m != 0){
				dxz = ((Rz - Lz) / m);
			}
			else {
				dxz = 0;
			}
				Lxz = Lz;
			//System.out.println("@@");
			for (int x = (int)Lx ; x < (int)Rx ; x++) {
			          // INCREMENTALLY UPDATE VALUE AT PIXEL
				Lxz += dxz;
				temp1[0] = x;
				temp1[1] = y;
				temp1[2] = Lxz;
				temp1[3] = 1;
				
				
				
				
				if (temp1[0]>=0 && temp1[0]<W && temp1[1]>=0 && temp1[1]<H && zbuf[x][y].getDepth()<temp1[2] && temp1[2]<focalLength){
					zbuf[x][y].setDepth(temp1[2]);
					inverseViewport(temp1,temp2);
					inversePerspective(temp2,temp2);
					minv.transform2(temp2, temp3);	
					if (type == 0)
						mapSphere(temp3,col);
					if (type == 1)
						mapCylinder(temp3,col);
					zbuf[x][y].setRGB(col[0], col[1], col[2]);
				}
					
			}
		}
	}
	
	public void mapCylinder(double p[], int c[]){
		//double u = Math.min(1,Math.max(0,Math.atan2(p[1], p[0])/(2*Math.PI)));
		//double v = Math.min(1,Math.max(0,Math.asin(p[2])/Math.PI+.5));
		
		double u =(Math.atan2(p[1], p[0])/(2*Math.PI))+.5;
		double v = Math.min(0.999999,Math.max(0,(p[2]+1)/2));
		//System.out.println(u+" "+v);
		
		
		c[0] = buffer.get(buffer.width-(int)(u*buffer.width), (int)(v*buffer.height), 0);
		c[1] = buffer.get(buffer.width-(int)(u*buffer.width), (int)(v*buffer.height), 1);
		c[2] = buffer.get(buffer.width-(int)(u*buffer.width), (int)(v*buffer.height), 2);
		
	}
	
	public void mapSphere(double p[], int c[]){
		//double u = Math.min(1,Math.max(0,Math.atan2(p[1], p[0])/(2*Math.PI)));
		//double v = Math.min(1,Math.max(0,Math.asin(p[2])/Math.PI+.5));
		
		double u =(Math.atan2(p[1], p[0])/(2*Math.PI))+.5;
		double v = (Math.asin(p[2])/Math.PI+.5);
		//System.out.println(u+" "+v);
		
		
		c[0] = buffer.get(buffer.width-(int)(u*buffer.width), (int)(v*buffer.height), 0);
		c[1] = buffer.get(buffer.width-(int)(u*buffer.width), (int)(v*buffer.height), 1);
		c[2] = buffer.get(buffer.width-(int)(u*buffer.width), (int)(v*buffer.height), 2);
		
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
			type = g.type;
			buffer = new ImageBuffer(g.urlName, this);
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
				
				
				drawTri(v1,v2,v3,g.mat,g.m2);
				drawTri(v1,v3,v4,g.mat,g.m2);
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

	