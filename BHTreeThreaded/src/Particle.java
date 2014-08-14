/*Jeremy King

Particle class for N-body simulation
*/

public class Particle
{
   //public to avoid calling 
   public double x,y,z;                      //units m
   public double vx,vy,vz;                   //units m/s
   public double mass;                       //units kg
   public double ax,ay,az;                   //units m/s^2
   public static final double G=6.67384E-11; //units m^3/(kg*s^2)Gravitational constant

   // construct particle
   public Particle(double xPosition,double yPosition,double zPosition,double oMass,double xVel,double yVel,double zVel)
   
   {
      x=xPosition;
      y=yPosition;
      z=zPosition;
      mass=oMass;
      vx=xVel;
      vy=yVel;
      vz=zVel;
   }
   
   public void resetAcceleration()
   {
      this.ax=0;
      this.ay=0;
      this.az=0;
   }
   public void addAcceleration(Particle b)
   {
      double xDistance=x-b.x;
      double yDistance=y-b.y;
      double zDistance=z-b.z;
      double distance=Math.sqrt(xDistance*xDistance + yDistance*yDistance + zDistance*zDistance);
      double accel=G*b.mass/(distance*distance+3e18);//softening parameter added to r^2 e18 for init1
      this.ax+=-accel*xDistance/distance;
      this.ay+=-accel*yDistance/distance;
      this.az+=-accel*zDistance/distance;
   }
   public void addVelocity(double timeStep)
   {
      vx+=timeStep*ax;
      vy+=timeStep*ay;
      vz+=timeStep*az;
   }
   
   public void changePositions(double timeStep)
   {
      x+=timeStep*vx;
      y+=timeStep*vy;
      z+=timeStep*vz;
   }
   public boolean isIn(Octant o)
   {
      return o.isOccupiedBy(this.x,this.y,this.z);
   }
   public Particle add(Particle p1,Particle p2)
   {
      double x3=(p1.mass*p1.x+p2.mass*p2.x)/(p1.mass+p2.mass);
      double y3=(p1.mass*p1.y+p2.mass*p2.y)/(p1.mass+p2.mass);
      double z3=(p1.mass*p1.z+p2.mass*p2.z)/(p1.mass+p2.mass);
      double vx3=p1.vx+p2.vx;
      double vy3=p1.vy+p2.vy;
      double vz3=p1.vz+p2.vz;
      double mass3=p1.mass+p2.mass;
      Particle p3=new Particle(x3,y3,z3,mass3,vx3,vy3,vz3);
      return p3;
   }
   public double calcDistance(Particle p)
   {
      double dx= x-p.x;
      double dy= y-p.y;
      double dz= z-p.z;
      return Math.sqrt(dx*dx+dy*dy+dz*dz);
   }
}