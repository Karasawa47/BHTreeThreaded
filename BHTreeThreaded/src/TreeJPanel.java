import javax.swing.*;

import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TreeJPanel extends JPanel
{
   static double univSize = 1e18;
   Octant octPrime= new Octant(0,0,0,4*univSize); 
   static int n = 2000;
   static int sizeCoeff =8/2;//max size divided by 2
   static double timeStep = 5e10;//range 1e10-1e12
   static double centerWeight=1e6; 
   public static Particle particle[]= new Particle[n];
   private static boolean isRunning = false;
   private static String colorType = "Green";
   private static boolean colorRed = true;
   private static boolean colorBlue = true;
   private static boolean colorGreen = false;
  
   public void paint(Graphics g)
   {
      super.paint(g);
      
      //g.translate(500,500);//translation for 0,0 coordinates in center
      //g.translate((int) Math.round(-particle[(int)(n*2/3)].x/univSize*500)+500,(int) Math.round(-particle[(int)(n*2/3)].y/univSize*500)+500);//translation for observing second object
      g.translate((int) Math.round(-particle[0].x/univSize*500)+500,(int) Math.round(-particle[0].y/univSize*500)+500);
      for(int i=0; i<n; i++)
      {
         if (Math.abs(particle[i].z)<univSize)
         {  
            //set color type
            int colorGradeRed=0;
            int colorGradeGreen=0;
            int colorGradeBlue=0;
            
            if (colorRed == true)
               colorGradeRed = (int) Math.round(-(particle[i].z*127/univSize)+127);
            else 
               colorGradeRed= 0;
            if (colorGreen == true)
               colorGradeGreen=(int) Math.round(-(particle[i].z*127/univSize)+127);
            else 
               colorGradeGreen=0;
            if (colorBlue == true)
               colorGradeBlue=(int) Math.round(-(particle[i].z*127/univSize)+127);
            else 
               colorGradeBlue=0;
            g.setColor(new Color(colorGradeRed,colorGradeGreen,colorGradeBlue));
            /*
            if (colorType.equals("Red"))
            {
               g.setColor(new Color((int) Math.round(-(particle[i].z*127/univSize)+127),0,0));
            }
            else if (colorType.equals("Green"))
            {
               g.setColor(new Color(0,(int) Math.round(-(particle[i].z*127/univSize)+127),0));
            }
            else
            {
               g.setColor(new Color(0,0,(int) Math.round(-(particle[i].z*127/univSize)+127)));
            }
            */
            //set particle size
            int size =(int) Math.round(particle[i].z*sizeCoeff/univSize+sizeCoeff);
            //draw particle
            g.fillOval((int) Math.round(particle[i].x*500/univSize),(int) Math.round(particle[i].y*500/univSize),size,size);
         }
      }
      if(isRunning){
         addAccelerations(n);
      }//end isRunning
      repaint();
   }//end paint
   //*****************************************************************************
   public void addAccelerations(int n)
   {
      Tree primeTree = new Tree(octPrime);
      //first set of threads adding to tree
      ExecutorService executor0 =Executors.newFixedThreadPool(4);
      int a=0;
      int b=(int)n/4;
      executor0.execute( new AddToTreeTask(a,b,primeTree));
      a=(int)n/4;
      b=(int)2*n/4;
      executor0.execute( new AddToTreeTask(a,b,primeTree));
      a=(int)2*n/4;
      b=(int)3*n/4;
      executor0.execute( new AddToTreeTask(a,b,primeTree));
      a=(int)3*n/4;
      b=(int)n;
      executor0.execute( new AddToTreeTask(a,b,primeTree));
      executor0.shutdown();
      while(!executor0.isTerminated()){}
      
      //execute second set of threads, adding accelerations
      ExecutorService executor = Executors.newFixedThreadPool(4);
      a=0;
      b=(int)n/4;
      executor.execute( new AddSubAccelerationsTask(a,b,primeTree));
      a=(int)n/4;
      b=(int)2*n/4;
      executor.execute( new AddSubAccelerationsTask(a,b,primeTree));
      a=(int)2*n/4;
      b=(int)3*n/4;
      executor.execute( new AddSubAccelerationsTask(a,b,primeTree));
      a=(int)3*n/4;
      b=(int)n;
      executor.execute( new AddSubAccelerationsTask(a,b,primeTree));
      
      executor.shutdown();
      while(!executor.isTerminated()){}
      /*
      for (int i=0; i < n; i++)
      {
         if (particle[i].isIn(octPrime))
         {
            primeTree.addParticle(particle[i]);
         }
      }
      for(int i= 0; i < n; i++)
      {
         particle[i].resetAcceleration();
         if(particle[i].isIn(octPrime))
         {
            primeTree.updateAccel(particle[i]);
            particle[i].addVelocity(timeStep);
            particle[i].changePositions(timeStep);
         }
      }
      */
   }
   public void addToTreeSub(int a,int b, Tree primeTree){
	   for (int i=a; i < b; i++)
	      {
	         if (particle[i].isIn(octPrime))
	         {
	            primeTree.addParticle(particle[i]);
	         }
	      }
   }
   public void addSubAccelerations(int a, int b, Tree primeTree)
   {
      
      for(int i= a; i < b; i++)
      {
         particle[i].resetAcceleration();
         if(particle[i].isIn(octPrime))
         {
            primeTree.updateAccel(particle[i]);
            particle[i].addVelocity(timeStep);
            particle[i].changePositions(timeStep);
         }
      }
   }
   
   
   //********************************************************************************
   public static double circlev(double rx, double ry,double weight) 
   {
      double solarmass=1.98892e30;
      double r2=Math.sqrt(rx*rx+ry*ry);
      double numerator=(6.67e-11)*weight*solarmass;
      return Math.sqrt(numerator/r2);
   }
  
   //Initialize n particles
   public static void initParticles(int n) 
   {
      double radius = 1e18;        // radius of universe
      double solarmass=1.98892e30;
      for (int i = 0; i < n; i++) 
      {
         double px;
         double py;
         double pz;
         do
         {
            px = 6*1e18*Math.exp(-1.8)*(.5-Math.random());
            py = 6*1e18*Math.exp(-1.8)*(.5-Math.random());
         }while(Math.sqrt(px*px+py*py)>univSize/2);
         pz= 1e15*(.5-Math.random());
         double magv = circlev(px,py,centerWeight);
      
         double absangle = Math.atan(Math.abs(py/px));
         double thetav= Math.PI/2-absangle;
         //double phiv = Math.random()*Math.PI;
         double vx   = -1*Math.signum(py)*Math.cos(thetav)*magv;
         double vy   = Math.signum(px)*Math.sin(thetav)*magv;
          
         double mass = Math.random()*solarmass*25+1e20;
         particle[i]   = new Particle(px, py, pz, mass, vx, vy,0);
      }
      particle[0]= new Particle(1,1,0,centerWeight*solarmass,1,1,0);//put a heavy body in the center
     
   }//end initParticles
   
   //second set of initial conditions... no central massive object
   public static void initParticles2(int n) 
   {
      double radius = 1e18;        // radius of universe
      double solarmass=1.98892e30;
      for (int i = 0; i < n; i++) 
      {
         double px;
         double py;
         double pz;
         
         //randomize xy positions
         px=Math.random()*univSize;
         py=Math.random()*univSize;
         if(Math.sqrt(px*px+py*py)>univSize/2)
         {
            do{
               px=Math.random()*univSize;
               py=Math.random()*univSize;
            }while(Math.sqrt(px*px+py*py)>univSize/4);
         }
         
         pz= 1e8*(.5-Math.random());
         
         if(Math.random()>.5)
         px=-px;
         if(Math.random()>.5)
         py=-py;
         if(Math.random()>.5)
         pz=-pz;
         
         double mass = Math.random()*solarmass*15+1e20;
         particle[i]   = new Particle(px, py,0,mass, 0, 0,0);
      }//end for loop
      //find and set velocities
      for (int i = 0; i < n; i++) 
      {
         double magv = circlev(particle[i].x,particle[i].y,innerMass(particle[i].x,particle[i].y)/1e31);
         double absangle = Math.atan(Math.abs(particle[i].y/particle[i].x));
         double thetav= Math.PI/2-absangle;
         //double phiv = Math.random()*Math.PI;
         particle[i].vx   = -1*Math.signum(particle[i].y)*Math.cos(thetav)*magv;
         particle[i].vy   = Math.signum(particle[i].x)*Math.sin(thetav)*magv;
      }//end for loop velocity setting
   }//end initParticles2
   
   public static double innerMass(double xMax,double yMax)
   {
      double massTotal=0;
      for(int i = 0; i < n; i++)
      {
         if(Math.sqrt(particle[i].x*particle[i].x+particle[i].y*particle[i].y)
               < Math.sqrt(xMax*xMax+yMax*yMax))
         {
            massTotal+=particle[i].mass;
         }
      }
      return massTotal;
   }
   
   public static void main(String[] args)
   {
      initParticles(n);
      TreeFrame frame = new TreeFrame();
        
   }//end main
   
    //translates initialization 22.5deg
   public static void translate45()
   {
      
      for(int i=0;i<n;i++)
      {
         
         double yPosition=particle[i].y;
         double zPosition=particle[i].z;
         double yVel=particle[i].vy;
         double zVel=particle[i].vz;
         
         //calc new position
         double yzDis= Math.sqrt(zPosition*zPosition+yPosition*yPosition);
         double theta=Math.atan(zPosition/yPosition);
      
         if (yPosition<0)
         {
            theta+=Math.PI;
         }
         yPosition=yzDis*Math.cos((theta+Math.PI/8.0));
         zPosition=yzDis*Math.sin((theta+Math.PI/8.0));
         
         //calc new velocity
         double yzVel= Math.sqrt(zVel*zVel+yVel*yVel);
         double theta2=Math.atan(zVel/yVel);
         
         if (yVel<0)
         {
            theta2+=Math.PI;
         }
         yVel=yzVel*Math.cos((theta2+Math.PI/8.0));
         zVel=yzVel*Math.sin((theta2+Math.PI/8.0));         
         
         particle[i].y=yPosition;
         particle[i].z=zPosition;
         particle[i].vy=yVel;
         particle[i].vz=zVel;
         if (particle[i].y==0 && particle[i].z==0)
         {
            particle[i].y=0;
            particle[i].z=0;
            particle[i].vy=0;
            particle[i].vz=0;
         }
           
      }//endfor
   }//end translate
   
   public static boolean getIsRunning()
   {
      return isRunning;
   }
   
   public static void setRunning(boolean s)
   {
      isRunning=s;
   }
   public static void setColor(String s)
   {
      colorType = s;
   }
   public static void setColorRed(boolean c)
   {
      colorRed=c;
   }
   public static void setColorGreen(boolean c)
   {
      colorGreen=c;
   }
   public static void setColorBlue(boolean c)
   {
      colorBlue=c;
   }
   public static boolean getColorRed()
   {
      return colorRed;
   }
   public static boolean getColorBlue()
   {
      return colorBlue;
   }
   public static boolean getColorGreen()
   {
      return colorGreen;
   }
   class AddSubAccelerationsTask implements Runnable {
	   Tree primeTree=null;
	   int a = 0;
	   int b = 0;
	   
	   public AddSubAccelerationsTask(int a,int b,Tree primeTree){
		   this.primeTree=primeTree;
		   this.a=a;
		   this.b=b;
	   }
	   
	   public void run(){
		   addSubAccelerations(a,b,primeTree);
	   }
	}
   class AddToTreeTask implements Runnable {
	   Tree primeTree=null;
	   int a = 0;
	   int b = 0;
	   
	   public AddToTreeTask(int a, int b, Tree primeTree){
		   this.primeTree=primeTree;
		   this.a=a;
		   this.b=b;
		   
	   }
	   public void run(){
		   addToTreeSub(a,b,primeTree);
	   }
   }
}
