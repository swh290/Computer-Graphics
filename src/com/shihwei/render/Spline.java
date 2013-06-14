package com.shihwei.render;

public class Spline {
	double data[][];
	double slope[];
	double coef[] = new double[4];
	double PnR[] = new double[4];
	double m[][] = {{2, -2, 1, 1}, {-3, 3, -2, 1}, {0, 0, 1, 0}, {1, 0, 0, 0}};
	public double result(double t){
		
		for (int i=0;i<data.length-1; i++){
			if (t > data[i][0] && t <= data[i+1][0]){
					PnR[0] = data[i][1];
					PnR[1] = data[i+1][1];
					PnR[2] = slope[i]*(data[i+1][0]-data[i][0]);
					PnR[3] = slope[i+1]*(data[i+1][0]-data[i][0]);
					t = (t-data[i][0])/(data[i+1][0]-data[i][0]);
					//transform(PnR,coef);
					
					//return Math.pow(t, 3)*coef[0] + Math.pow(t, 2)*coef[1] + t*coef[2] + coef[3];
					//P1 (2t3-3t2+1) + P4 (-2t3+3t2) + R1 (t3-2t2+t) + R4 (t3-t2)
					return PnR[0]*(2*Math.pow(t, 3)-3*Math.pow(t, 2)+1)+PnR[1]*(-2*Math.pow(t, 3)+3*Math.pow(t, 2))+PnR[2]*(Math.pow(t, 3)-2*Math.pow(t, 2)+t)+PnR[3]*(Math.pow(t, 3)-Math.pow(t, 2));
			}
			
		}
		
			return 0;
		
	}
	
	public void transform(double src[], double dst[]) {
			
		for (int row = 0 ; row < dst.length ; row++) {
			dst[row] = 0;
			for (int i = 0 ; i < src.length ; i++)
			dst[row] += m[row][i] * src[i];
		}
	}
	private void computeSlope(){
		slope = new double[data.length];
		slope[0] = 2 * (data[1][1]-data[0][1]) / (data[1][0]-data[0][0]);
		slope[data.length-1] = 2 * (data[data.length-1][1]-data[data.length-2][1]) / (data[data.length-1][0]-data[data.length-2][0]);
		for (int i=1;i<data.length-1;i++){
			slope[i] = (data[i+1][1]-data[i-1][1])/(data[i+1][0]-data[i-1][0]);
		}
	}
	public Spline(double[][] animate){
		data = new double[animate.length][2];
		for (int i = 0; i< animate.length; i++)
			for(int j=0;j<2;j++)
				data[i][j]=animate[i][j];
		computeSlope();
	}
	
}
