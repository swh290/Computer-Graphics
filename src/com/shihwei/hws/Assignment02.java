package com.shihwei.hws;

import java.awt.*;
import java.util.Random;

import com.shihwei.render.BufferedApplet;
public class Assignment02 extends BufferedApplet
{
   double startTime = getTime();
   
   public void render(Graphics g) {
      double time = getTime() - startTime;

      int w = getWidth();
      int h = getHeight();

      g.setColor(Color.black);
      g.fillRect(0, 0, w, h);
      
      Random r = new Random();
      double jump = 50 * Math.abs(Math.sin(time * 4));
      double swing = 180 * Math.abs(Math.sin(time * 2));
      double swingl = 90 * Math.abs(Math.sin(time * 2));
      double handmoveX = 40 * Math.abs(Math.sin(time * 2));
      double handmoveY = 10 * Math.abs(Math.sin(time * 2));
      double ax = 100 + 50 * Math.cos(6 * time);
      double ay = 100 + 50 * Math.sin(2 * time);
      double bx = 327 + 300 * Math.sin(1 * time);
      double by = 100 + 50 * Math.cos(1 * time);
      double cx = 295 + 300 * Math.sin(1 * time);
      double cy = 110 + 50 * Math.cos(1 * time);
      double dx = 550 + 50 * Math.cos(3 * time);
      double dy = 150 + 150 * -1*Math.cos(2 * time);
      int rx = 10;
      int ry = 10;
      
      g.setColor(Color.yellow);
      rx = r.nextInt(640);
      ry = r.nextInt(480);
      g.drawString("*",rx , ry);
      g.setColor(Color.cyan);
      rx = r.nextInt(640);
      ry = r.nextInt(480);
      g.drawString("*",rx , ry);
      g.setColor(Color.magenta);
      rx = r.nextInt(640);
      ry = r.nextInt(480);
      g.drawString("*",rx , ry);
      
      g.setColor(Color.yellow);
      g.fillOval(400,50 , 50, 50);
      g.setColor(Color.black);
      g.fillOval(375,50 , 50, 50);
      g.setColor(Color.orange);
      g.fillOval((int)bx,(int)by , 30, 30);
      g.setColor(Color.red);
      g.fillOval((int)cx,(int)cy , 100, 10);
      g.setColor(Color.gray);
      g.fillOval((int)ax,(int)ay , 20, 20);
      g.setColor(Color.pink);
      g.fillOval((int)dx,(int)dy , 20, 30);
      
      
      g.setColor(Color.white);
      g.fillOval(250,50 - (int)jump,50,50);
      g.fillRect(273, 100 - (int)jump, 5, 80 );
      g.drawArc(275 - (int)handmoveX , 115 - (int)jump - (int)handmoveY , 40, 40, 180 - (int)swing, 200);
      g.drawArc(235 + (int)handmoveX , 115 - (int)jump - (int)handmoveY , 40, 40, 0 + (int)swing, 200);
      g.drawArc(230 - (int)handmoveX, 180 - (int)jump - (int)handmoveX, 80, 80, 0 - (int)swingl, 90);
      g.drawArc(190 + (int)handmoveX, 140 - (int)jump + (int)handmoveX, 80, 80, -90 + (int)swingl, 90);
      
      
      
      g.setColor(Color.blue);
      g.fillOval(25, 220, 500, 500);
      g.setColor(Color.green);
      g.drawArc(35, 230, 480, 480, 30 + (int)((30*time)%360), 20);
      g.drawArc(35, 230, 480, 480, 80 + (int)((30*time)%360), 20);
      g.drawArc(35, 230, 480, 480, 130 + (int)((30*time)%360), 20);
      g.drawArc(35, 230, 480, 480, 190 + (int)((30*time)%360), 20);
      g.drawArc(35, 230, 480, 480, 270 + (int)((30*time)%360), 40);
      g.drawArc(35, 230, 480, 480, 330 + (int)((30*time)%360), 30);
      
      g.drawArc(55, 250, 440, 440, 0 + (int)((30*time)%360), 20);
      g.drawArc(55, 250, 440, 440, 70 + (int)((30*time)%360), 30);
      g.drawArc(55, 250, 440, 440, 140 + (int)((30*time)%360), 20);
      g.drawArc(55, 250, 440, 440, 190 + (int)((30*time)%360), 40);
      g.drawArc(55, 250, 440, 440, 270 + (int)((30*time)%360), 20);
      g.drawArc(55, 250, 440, 440, 320 + (int)((30*time)%360), 20);
      
   }

   

double getTime() {
      return System.currentTimeMillis() / 1000.0;
   }
}

