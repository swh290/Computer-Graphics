package com.shihwei.render;

public class Surface {
	double a = 0,b = 0,c = 0,d = 0,e = 0,f = 0,g = 0,h = 0,i = 0,j = 0;
	
	
	Material m = new Material();
	public Surface(){};
	
	
	public Surface(double aa, double bb, double cc, double dd, double ee, double ff, double gg, double hh, double ii, double jj,Material mm){
		a = aa;
		b = bb;
		c = cc;
		d = dd;
		e = ee;
		f = ff;
		g = gg;
		h = hh;
		i = ii;
		j = jj;
		m = mm;
		//Id = id;
	}
	public void setSurface(double aa, double bb, double cc, double dd, double ee, double ff, double gg, double hh, double ii, double jj){
		a = aa;
		b = bb;
		c = cc;
		d = dd;
		e = ee;
		f = ff;
		g = gg;
		h = hh;
		i = ii;
		j = jj;
	}
		
	
}
