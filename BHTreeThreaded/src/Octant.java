public class Octant
{
   private double xMid;
   private double yMid;
   private double zMid;
   private double length;
   
   public Octant(double xMid,double yMid,double zMid,double l)
   {
      this.xMid=xMid;
      this.yMid=yMid;
      this.zMid=zMid;
      this.length=l;
   }
   
   public double getLength()
   {
      return this.length;
   }
   public boolean isOccupiedBy(double xPoint,double yPoint,double zPoint)
   {
      if (xPoint<=this.xMid+this.length/2.0 && xPoint>=this.xMid-this.length/2.0 && yPoint<=this.yMid+this.length/2.0 && yPoint>=this.yMid-this.length/2.0
            && zPoint<=this.zMid+this.length/2.0 && zPoint>=this.zMid-this.length/2.0) 
      {
         return true;
      }
      else
      {
         return false;
      }
   }
   //generate new octants row/column/level upper level is 0
   public Octant octant000()
   {
      Octant genOctant= new Octant(this.xMid-this.length/4.0, this.yMid+this.length/4.0,this.zMid+this.length/4.0,this.length/2.0);
      return genOctant;
   }
   public Octant octant010()
   {
      Octant genOctant= new Octant(this.xMid+this.length/4.0, this.yMid+this.length/4.0,this.zMid+this.length/4.0,this.length/2.0);
      return genOctant;
   }
   public Octant octant100()
   {
      Octant genOctant= new Octant(this.xMid-this.length/4.0, this.yMid-this.length/4.0,this.zMid+this.length/4.0,this.length/2.0);
      return genOctant;
   }
   public Octant octant110()
   {
      Octant genOctant= new Octant(this.xMid+this.length/4.0, this.yMid-this.length/4.0,this.zMid+this.length/4.0,this.length/2.0);
      return genOctant;
   }
   
   public Octant octant001()
   {
      Octant genOctant= new Octant(this.xMid-this.length/4.0, this.yMid+this.length/4.0,this.zMid-this.length/4.0,this.length/2.0);
      return genOctant;
   }
   public Octant octant011()
   {
      Octant genOctant= new Octant(this.xMid+this.length/4.0, this.yMid+this.length/4.0,this.zMid-this.length/4.0,this.length/2.0);
      return genOctant;
   }
   public Octant octant101()
   {
      Octant genOctant= new Octant(this.xMid-this.length/4.0, this.yMid-this.length/4.0,this.zMid-this.length/4.0,this.length/2.0);
      return genOctant;
   }
   public Octant octant111()
   {
      Octant genOctant= new Octant(this.xMid+this.length/4.0, this.yMid-this.length/4.0,this.zMid-this.length/4.0,this.length/2.0);
      return genOctant;
   }
}