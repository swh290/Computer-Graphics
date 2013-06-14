package com.shihwei.render;
import java.awt.Color;
import java.awt.Graphics;
import java.util.*;
public class Raytracer {
	
	static final double e=0.00000001;
	ArrayList<Sphere> spheres = new ArrayList<Sphere>();
	ArrayList<Light> lights = new ArrayList<Light>();
	ArrayList<Hit> hits = new ArrayList<Hit>();
	ArrayList<Shape> shapes = new ArrayList<Shape>();
	public void addLight(Light l){
		lights.add(l);
	}
	
	public void addSphere(Sphere s){
		spheres.add(s);
	}
	
	public void addShape(Shape s){
		shapes.add(s);
	}
	
	public void addHit(Hit s){
		hits.add(s);
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
	
	
	
	private double computeHits(double v[], double w[], Shape sh){
		Surface f;
		hits.clear();
		for (int i = 0; i < sh.s.size(); i++){
			f = sh.s.get(i);
			double A = f.a*w[0]*w[0] + f.b*w[1]*w[1] + f.c*w[2]*w[2]
					 + f.d*w[1]*w[2] + f.e*w[2]*w[0] + f.f*w[0]*w[1];
			double B = 2*f.a*v[0]*w[0] + 2*f.b*v[1]*w[1] + 2*f.c*v[2]*w[2]
					 + f.d*(v[1]*w[2]+v[2]*w[1]) + f.e*(v[2]*w[0]+v[0]*w[2]) + f.f*(v[0]*w[1]+v[1]*w[0])
					 + f.g*w[0] + f.h*w[1] + f.i*w[2];
			double C = f.a*v[0]*v[0] + f.b*v[1]*v[1] + f.c*v[2]*v[2]
					 + f.d*v[1]*v[2] + f.e*v[2]*v[0] + f.f*v[0]*v[1]
					 + f.g*v[0] + f.h*v[1] + f.i*v[2] +f.j;
			
			if (A == 0){
				double ans = -C/B;
					if (A*(ans+e)*(ans+e)+B*(ans+e)+C <= 0)
					hits.add(new Hit(ans, 1, sh.s.get(i)));
					else 
						hits.add(new Hit(ans, -1, sh.s.get(i)));
				
			}
			else {
				double K = B*B-4*A*C;
				if (K <= 0)
					hits.add(new Hit(-Double.MAX_VALUE, -1, sh.s.get(i)));
				else{
					
					double ans1 = (-B + Math.sqrt(K))/(2*A);
					double ans2 = (-B - Math.sqrt(K))/(2*A);
					
					
						if (A*(ans1+e)*(ans1+e)+B*(ans1+e)+C < 0)
							hits.add(new Hit(ans1, 1, sh.s.get(i)));
						else hits.add(new Hit(ans1, -1, sh.s.get(i)));
					
					
						if (A*(ans2+e)*(ans2+e)+B*(ans2+e)+C < 0)
							hits.add(new Hit(ans2, 1, sh.s.get(i)));
						else hits.add(new Hit(ans2, -1, sh.s.get(i)));
					
					
				}
			}
		}
		double maxIn = 0;
		double minOut = Double.MAX_VALUE;
		if (hits.size() == 0)
			return Double.MAX_VALUE;
		for (int i = 0; i<hits.size(); i++){
			if (hits.get(i).inOrOut == 1 && hits.get(i).t > maxIn){
				maxIn = hits.get(i).t;
			}
			if (hits.get(i).inOrOut == -1 && hits.get(i).t < minOut)
				minOut = hits.get(i).t;
		}
		if (maxIn < minOut && maxIn > 0 ){			
			return maxIn;
		}
		else return Double.MAX_VALUE;
	}
	
	//private double intersectHit(ArrayList<Hit> hits){
		
	//}

	private void normal(double p[],Surface s, double n[]){
		n[0] = 2*s.a*p[0]+s.e*p[2]+s.f*p[1]+s.g;
        n[1] = 2*s.b*p[1]+s.d*p[2]+s.f*p[0]+s.h;
        n[2] = 2*s.c*p[2]+s.d*p[1]+s.e*p[0]+s.i; 
        normalize(n);
	}
	
	private void reflect(double w[],double n[], double r[]){
        r[0] = w[0] - (2.0 * (dot(w,n)) * n[0]);
        r[1] = w[1] - (2.0 * (dot(w,n)) * n[1]);
        r[2] = w[2] - (2.0 * (dot(w,n)) * n[2]);  
	}
	
	private void point(double w[],double v[],double t,double p[]){
		p[0] = v[0] + w[0] * t;
        p[1] = v[1] + w[1] * t;
        p[2] = v[2] + w[2] * t;
	}
	
	private Surface getHitSurface(double p[], Shape sh){
		for (int i = 0; i < sh.s.size(); i++){
			Surface ss = sh.s.get(i);
			double value = ss.a*p[0]*p[0]+ss.b*p[1]*p[1]+ss.c*p[2]*p[2]
					     + ss.d*p[1]*p[2]+ss.e*p[2]*p[0]+ss.f*p[0]*p[1]
					     + ss.g*p[0]+ss.h*p[1]+ss.i*p[2]+ss.j;		 
			if (value<e && value>-e )
				return ss;
			
		}
		return new Surface(0,0,0,0,0,0,0,0,0,0,new Material());
	}
	
	private void getPhong(Surface s,double n[],double r[], double p[], double color[]){
		for (int i = 0;i<3;i++){
			color[i] = s.m.ambient[i];
			for (int j = 0;j<lights.size();j++){
				if (!isShadow(p,lights.get(j).direction) || (dot(n,lights.get(j).direction)<0))
				color[i] += lights.get(j).illuminance[i] * (s.m.diffuse[i] * dot(lights.get(j).direction,n) + s.m.specular[i]* (Math.pow(dot(lights.get(j).direction,r), s.m.specularPower)));
			}
			color[i] = Math.min(Math.max(color[i], 0.0), 1.0);
		}
	}

	private boolean rayTracing(double[] v, double[] w, double[] color, int depth){
		double t;
		double sNormal[] = {0.0, 0.0, 0.0};
	    double sReflection[] = {0.0, 0.0, 0.0};        
	    double sPoint[] = {0.0, 0.0, 0.0};
		double mint = Double.MAX_VALUE;
		Surface hitSurface = new Surface();
		Shape hitShape = null;
		for(Shape s : shapes){
			t = computeHits(v, w, s);
			if (t < mint){
				mint = t;
				hitShape = s;
			}
		}
		//hitSurface = intersect(hits);
		
		if (hitShape != null){
			
			point(w,v,mint,sPoint);
			hitSurface = getHitSurface(sPoint, hitShape);
	        normal(sPoint,hitSurface,sNormal);
	        reflect(w,sNormal,sReflection);
			getPhong(hitSurface,sNormal,sReflection,sPoint,color);
			if ((hitSurface.m.mirror[0]+hitSurface.m.mirror[1]+hitSurface.m.mirror[2]) !=0 && depth < 5){
				depth++;
				double rv[] = {0.0, 0.0, 0.0};
                double rw[] = {0.0, 0.0, 0.0};
                for (int i=0;i<3;i++){
                	rv[i] = sPoint[i] + (e * sReflection[i]);
                	rw[i] = sReflection[i];
                }
                double[] rColor = {0.0, 0.0, 0.0};
                if(rayTracing(rv, rw, rColor,depth))
                    for(int i = 0; i < 3; i++){
                        color[i] = color[i] * (1.0 - hitSurface.m.mirror[i]) + rColor[i] * hitSurface.m.mirror[i]; 
                    }
               
			}
			return true;
		}
		return false;
	}
	public void draw(double focalLength,Graphics g,int width,int height){
		
		double v[]={0,0,focalLength};
		double w[] = {0,0,0};
		double color[]={0,0,0};
		for(int x = 0; x < width; x++)
            for(int y = 0; y < height; y++){
            	w[0] = (x - (0.5 * width)) / width;
                w[1] = ((0.5 * height) - y) / width;
                w[2] = -1.0 * focalLength;
                normalize(w);
                
				if (rayTracing(v,w,color,0)){
					g.setColor(new Color((float)color[0], (float)color[1], (float)color[2]));
                    g.drawLine(x,y , x, y);
				}
				
			}
	}
	
	private boolean isShadow(double p[],double d[]){
		double t= Double.MAX_VALUE;
		double temp[] = {p[0]+e,p[1]+e,p[2]+e};
		for (int i = 0;i<shapes.size();i++){
			
				t = computeHits(p,d,shapes.get(i));
				if (t != Double.MAX_VALUE && t >0)
					return true;
			
		}
		return false;
	}
}
